package Part2;

import java.lang.Math;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;


/**
 * Interface for updating the race display in the GUI.
 */
interface RaceUpdateListener {
    void updateDisplay(String text);
}


/**
 * The Class Race Handles the logic behind the Horse Racing Simulator. 
 * The Class handles the movement, winning, Falling, updating the GUI and manipulating Horse statistics. 
 * 
 * @author Maks Ostrynski
 * @version 2  24/04/2024
 */
public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private RaceUpdateListener updateListener;
    private boolean finalMessageSet = false;
    private List<Horse> horses;
    private volatile boolean finished = false;
    private int raceCount = 0;


    public Race(int distance) {
        raceLength = distance;
        finished = false;
        horses = new ArrayList<>();
    }

    public void setUpdateListener(RaceUpdateListener listener) {
        this.updateListener = listener;
    }

     /**
     * Loads horses from a file and adds them to the Race to run. 
     * 
     * @param filePath the File path where the Horse Information is stored. 
     */
    public void loadHorsesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int laneNumber = 1;  // Start with lane 1
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length == 6) {  
                    char symbol = attributes[0].trim().charAt(0);
                    String name = attributes[1].trim();
                    double confidence = Double.parseDouble(attributes[2].trim());
                    String color = attributes[3].trim();
                    String breed = attributes[4].trim();
                    String accessory = attributes[5].trim();
    
                    Horse horse = new Horse(symbol, name, confidence);
                    horse.setColor(color);
                    horse.setBreed(breed);
                    horse.setAccessory(accessory);
    
                    this.addHorse(horse, laneNumber);
                    laneNumber = (laneNumber % 3) + 1; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load horses from file.");
        }
    }

     /**
     * Adds a horse to the race in a given lane
     * Checks that the lane is valid (1, 2 or 3).
     * Checks that the attributes of horse are not NULL. 
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (theHorse == null) {
            System.out.println("NULL VALUE FOUND IN HORSE CANNOT ADD");
            return;
        }
        switch (laneNumber) {
            case 1:
                lane1Horse = theHorse;
                break;
            case 2:
                lane2Horse = theHorse;
                break;
            case 3:
                lane3Horse = theHorse;
                break;
            default:
                System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
                break;
        }
    }


     /**
     * Start the race
     * The horse are brought to the start and then repeatedly moved forward until the race is finished.
     * Calls the Announce Winner method, CheckFallenHorses method and updateRaceDisplay method.
     * Calls the method to Update the Horse information in the file and Update the Race results. 
     * 
     */
    public void startRace() {
        finished = false;
        if (lane1Horse != null) lane1Horse.goBackToStart();
        if (lane2Horse != null) lane2Horse.goBackToStart();
        if (lane3Horse != null) lane3Horse.goBackToStart();
    
        new Thread(() -> {
            while (!finished) {
                if (lane1Horse != null) moveHorse(lane1Horse);
                if (lane2Horse != null) moveHorse(lane2Horse);
                if (lane3Horse != null) moveHorse(lane3Horse);
    
                AnnounceWinner();
                CheckFallenHorses();
    
                if (!finished) {
                    updateRaceDisplay();
                    
    
                }

    
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            updateHorsesInFile(System.getProperty("user.dir") + File.separator + "horseAttribute.txt");
            raceResults();
        }).start();
    }

     /**
     * Updates the Horses information from the file.
     * Allows changing the confidence after a race. 
     */
    public void updateHorsesInFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writeHorseToFile(writer, lane1Horse);
            writeHorseToFile(writer, lane2Horse);
            writeHorseToFile(writer, lane3Horse);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to update horses in file.");
        }
    }
    
    private void writeHorseToFile(PrintWriter writer, Horse horse) {
        if (horse != null) {
            writer.println(horse.getSymbol() + "," + horse.getName() + "," + horse.getConfidence() + "," + horse.getColor() + "," + horse.getBreed() + "," + horse.getAccessory());
        }
    }

    /**
     * Logic Behind moving a Horse. 
     * Ensures that the Horse has not fallen and then moves the horse. 
     * Calculates the probability of the Horse Falling. 
     * 
     * @param theHorse the horse to be added to the race
     */
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen() && Math.random() < theHorse.getConfidence()) {
            theHorse.moveForward();
        }

        double fallProbability = 0.05 * theHorse.getConfidence() * Math.pow(theHorse.getDistanceTravelled() + 1, -0.1);
        if (Math.random() < fallProbability) {
            theHorse.fall();
            theHorse.decreaseConfidence();
            theHorse.increaseFalls();
        }

        updateRaceDisplay();
    }

    /**
     * Announces the Winner of the Race or a Draw. 
     * Updates the GUI with a Winning message or draw message.
     * 
     */
    private void AnnounceWinner() {
        boolean lane1Won = raceWonBy(lane1Horse);
        boolean lane2Won = raceWonBy(lane2Horse);
        boolean lane3Won = raceWonBy(lane3Horse);

        int winners = 0;
        if (lane1Won) winners++;
        if (lane2Won) winners++;
        if (lane3Won) winners++;

        if (winners > 1) {
            String drawMessage = "";
            drawMessage = ("It's a draw!\n");
            updateListener.updateDisplay(drawMessage);
            System.out.println("It's a draw!");
            finished = true;
            finalMessageSet = true;
        } else if (winners == 1) {
            finalMessageSet = true;
            String winnerMessage = "";
            if (lane1Won) {
                lane1Horse.increaseConfidence();
                winnerMessage = lane1Horse.getName() + " has won the race!\n";
                lane1Horse.increaseWins();
            } else if (lane2Won) {
                lane2Horse.increaseConfidence();
                winnerMessage = lane2Horse.getName() + " has won the race!\n";
                lane2Horse.increaseWins();
            } else if (lane3Won) {
                lane3Horse.increaseConfidence();
                winnerMessage = lane3Horse.getName() + " has won the race!\n";
                lane3Horse.increaseWins();
            }
            updateListener.updateDisplay(winnerMessage);
            finished = true;
            finalMessageSet = true;

        }
    }


    /**
     * Checks if all three horses have fallen and announces that there are no winners.
     * If all horses have fallen, then it ends the programs runtime. 
     *
     */
    private void CheckFallenHorses() {
        if ((lane1Horse != null && lane1Horse.hasFallen()) &&
            (lane2Horse != null && lane2Horse.hasFallen()) &&
            (lane3Horse != null && lane3Horse.hasFallen())) {
            String FallMessage = "";
            finalMessageSet = true;
            FallMessage = ("All horses have fallen, No winners\n");
            updateListener.updateDisplay(FallMessage);
            finished = true;
        }
    }


    /**
     * Writes the results of the race to a file. 
     * 
     */
    public void raceResults() {
        String filePath = System.getProperty("user.dir") + File.separator + "raceResults.txt";
        System.out.println("Writing to: " + filePath); 
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            raceCount++;
            String winnerInfo = getWinnerInfo();
            if (!winnerInfo.isEmpty()) {
                out.println("Race " + raceCount + ": " + winnerInfo);
                System.out.println("Race result written: " + winnerInfo);  
            } else {
                System.out.println("No winner info available."); 
            }
        } catch (IOException e) {
            System.out.println("Error writing to race results file.");
            e.printStackTrace();
        }
    }

    private String getWinnerInfo() {
        Horse winner = null;
        if (raceWonBy(lane1Horse)) winner = lane1Horse;
        else if (raceWonBy(lane2Horse)) winner = lane2Horse;
        else if (raceWonBy(lane3Horse)) winner = lane3Horse;

        if (winner != null) {
            return winner.getName() + " - Total Wins: " + winner.getTotalWins() + ", Total Falls: " + winner.getTotalFalls() + ", Current Confidence: " + winner.getConfidence();
        }
        return "";
    }


    public boolean isFinished() {
        return finished;
    }

         
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }


     /***
     * Prints the race on the GUI through a creating a string representation of the race.
     * Sends to the updateListener to update the GUI.
     */
    private void printRace() {
        if (finalMessageSet) {
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
        sb.append(printLane(lane1Horse)).append("\n");
        sb.append(printLane(lane2Horse)).append("\n");
        sb.append(printLane(lane3Horse)).append("\n");
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
    
        if (updateListener != null) {
            updateListener.updateDisplay(sb.toString());
        }
    }
    

    /**
     * Constructs a string representation of each Horses Lane. 
     * @param theHorse the horse to be added
     * 
     */
    private String printLane(Horse theHorse) {
        StringBuilder sb = new StringBuilder();
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        sb.append('|');
        sb.append(multiplePrint(' ', spacesBefore));
        
        if(theHorse.hasFallen()) {
            sb.append('X');
        } else {
            sb.append(theHorse.getSymbol());
        }
        
        sb.append(multiplePrint(' ', spacesAfter));
        sb.append('|');
        sb.append(" ").append(theHorse.getName()).append(" (Current confidence ").append(theHorse.getConfidence()).append(")");

        return sb.toString();
    }
    

    /***
     * The method constructs a String by repeating a character a specified number of times. 
     * 
     * @param aChar the character to be repeated.
     * @param times the number of times the character is repeated.
     */
    private String multiplePrint(char aChar, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }


    /***
     * Method to Reset the Horse Race. 
     * 
     */
    public void resetRace() {
        finished = false;
        finalMessageSet = false;
    
        if (lane1Horse != null) lane1Horse.resetHorse();
        if (lane2Horse != null) lane2Horse.resetHorse();
        if (lane3Horse != null) lane3Horse.resetHorse();
    }

    private void updateRaceDisplay() {
        if (!finalMessageSet) {
            SwingUtilities.invokeLater(this::printRace);
        }
    }
}



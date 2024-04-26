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


// Race.java

/**
 * Interface for updating the race display in the GUI.
 */
interface RaceUpdateListener {
    void updateDisplay(String text);
}

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

    public void loadHorsesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int laneNumber = 1;  // Start with lane 1
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length == 6) {  // Ensure there are six attributes: symbol, name, confidence, color, breed, accessory
                    char symbol = attributes[0].trim().charAt(0);
                    String name = attributes[1].trim();
                    double confidence = Double.parseDouble(attributes[2].trim());
                    String color = attributes[3].trim();
                    String breed = attributes[4].trim();
                    String accessory = attributes[5].trim();
    
                    // Create a new Horse object with the read attributes
                    Horse horse = new Horse(symbol, name, confidence);
                    horse.setColor(color);
                    horse.setBreed(breed);
                    horse.setAccessory(accessory);
    
                    // Add the horse to the race in the current lane and then increment the lane number
                    this.addHorse(horse, laneNumber);
                    laneNumber = (laneNumber % 3) + 1;  // Cycle through lanes 1, 2, 3
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load horses from file.");
        }
    }

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

    public void updateHorsesInFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write each horse's details back to the file
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
            // Include all attributes of the horse in the output to the file.
            writer.println(horse.getSymbol() + "," + horse.getName() + "," + horse.getConfidence() + "," + horse.getColor() + "," + horse.getBreed() + "," + horse.getAccessory());
        }
    }

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

    public void raceResults() {
        String filePath = System.getProperty("user.dir") + File.separator + "raceResults.txt";
        System.out.println("Writing to: " + filePath);  // Debugging output
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            raceCount++;
            String winnerInfo = getWinnerInfo();
            if (!winnerInfo.isEmpty()) {
                out.println("Race " + raceCount + ": " + winnerInfo);
                System.out.println("Race result written: " + winnerInfo);  // Debugging output
            } else {
                System.out.println("No winner info available.");  // Debugging output
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

    private boolean raceWonBy(Horse theHorse) {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }

    private void printRace() {
        if (finalMessageSet) {
            // Do not print the race status; the final message is already displayed.
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
        sb.append(printLane(lane1Horse)).append("\n");
        sb.append(printLane(lane2Horse)).append("\n");
        sb.append(printLane(lane3Horse)).append("\n");
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
    
        // Update the display with the new frame
        if (updateListener != null) {
            updateListener.updateDisplay(sb.toString());
        }
    }
    

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
    
    private String multiplePrint(char aChar, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }

    public void resetRace() {
        // Reset race-specific flags
        finished = false;
        finalMessageSet = false;
    
        // Reset the state of each horse
        if (lane1Horse != null) lane1Horse.resetHorse();
        if (lane2Horse != null) lane2Horse.resetHorse();
        if (lane3Horse != null) lane3Horse.resetHorse();
    }

    private void updateRaceDisplay() {
        // Invoke printRace only if the race is ongoing
        if (!finalMessageSet) {
            SwingUtilities.invokeLater(this::printRace);
        }
    }
}



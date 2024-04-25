package Part2;

import java.util.concurrent.TimeUnit;
import java.lang.Math;
import javax.swing.SwingUtilities;


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
    private boolean finished;
    private RaceUpdateListener updateListener;
    private boolean finalMessageSet = false;

    public Race(int distance) {
        raceLength = distance;
        finished = false;
    }

    public void setUpdateListener(RaceUpdateListener listener) {
        this.updateListener = listener;
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
        }).start();
    }

   

    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen() && Math.random() < theHorse.getConfidence()) {
            theHorse.moveForward();
        }

        double fallProbability = 0.05 * theHorse.getConfidence() * Math.pow(theHorse.getDistanceTravelled() + 1, -0.1);
        if (Math.random() < fallProbability) {
            theHorse.fall();
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
            finalMessageSet = true;
            String drawMessage = "";
            drawMessage = ("It's a draw!\n");
            updateListener.updateDisplay(drawMessage);
            System.out.println("It's a draw!");
            finished = true;
        } else if (winners == 1) {
            finalMessageSet = true;
            String winnerMessage = "";
            if (lane1Won) {
                winnerMessage = lane1Horse.getName() + " has won the race!\n";
            } else if (lane2Won) {
                winnerMessage = lane2Horse.getName() + " has won the race!\n";
            } else if (lane3Won) {
                winnerMessage = lane3Horse.getName() + " has won the race!\n";
            }
            updateListener.updateDisplay(winnerMessage);
            finished = true;
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

    private boolean raceWonBy(Horse theHorse) {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }

    private void printRace() {
        StringBuilder sb = new StringBuilder();
        
        // Clear the JTextArea before appending new text.
        if (updateListener != null) {
            updateListener.updateDisplay(""); // Clear the display
        }
        
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
        
        sb.append(printLane(lane1Horse)).append("\n");
        sb.append(printLane(lane2Horse)).append("\n");
        sb.append(printLane(lane3Horse)).append("\n");
        
        sb.append(multiplePrint('=', raceLength + 3)).append("\n");
        
        // Now append the new frame.
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

    private void updateRaceDisplay() {
        // Only update the race display if a final message has not been set
        if (!finalMessageSet) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    printRace();
                }
            });
        }
    }
}



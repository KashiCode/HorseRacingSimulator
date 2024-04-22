import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    private boolean finished;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
        finished = false;
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

    //Checks if any attributes in `Horse` object are Null. 
    if (theHorse == null) {
        System.out.println("NULL VALUE FOUND IN HORSE CANNOT ADD");
        return; 
    }

        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {

        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            //AnnounceWinner(lane1Horse);

            moveHorse(lane2Horse);
            //AnnounceWinner(lane2Horse);

            moveHorse(lane3Horse);
            //AnnounceWinner(lane3Horse);
                        
            //print the race positions
            printRace();

            //Checks for Draws or a Singular Winning Horse.
            AnnounceWinner();           

            //Checks if all horses have fallen. 
            CheckFallenHorses();
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }

    /**
     * Checks if any horse has won the race and announces the winner.
     * If a winner is found, it sets the race to finished.
     * In the event of a draw, the draw is announced and the race finishes. 
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
            System.out.println("It's a draw!");
            finished = true;
        } else if (winners == 1) {
            if (lane1Won) {
                System.out.println(lane1Horse.getName() + " has won the race!");
            } else if (lane2Won) {
                System.out.println(lane2Horse.getName() + " has won the race!");
            } else if (lane3Won) {
                System.out.println(lane3Horse.getName() + " has won the race!");
            }
            finished = true;
        }
    }



    /**
     * Checks if all three horses have fallen and announces that there are no winners.
     * If all horses have fallen, it ends the programs runtime. 
     *
     */
    private void CheckFallenHorses() {
        if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
            System.out.println("All horses have fallen, No winners");
            finished = true;
        }
    }



    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
{
    // Check if the horse has fallen; if so, it cannot move.
    if (!theHorse.hasFallen())
    {
        // Horse moves forward based on its confidence.
        if (Math.random() < theHorse.getConfidence())
        {
            theHorse.moveForward();
        }

        // Adjusted the probability to fall to be less likely (0.05)
        // Decay factor which reduces the probability of falling over time. 
        double fallProbability = 0.05 * theHorse.getConfidence() * Math.pow(theHorse.getDistanceTravelled() + 1, -0.1);

        // Probability of Horse falling. 
        if (Math.random() < fallProbability)
        {
            theHorse.fall();
        }
    }
}

        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        //System.out.print('\u000C');  //clear the terminal window for other IDEs
        System.out.print("\033[H\033[2J");  //clear the terminal window for vscode. 
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        System.out.print(" " + theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}


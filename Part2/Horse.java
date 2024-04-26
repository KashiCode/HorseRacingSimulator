package Part2;


/**
 * The Class Horse instantiates the attributes for each Horse object including its confidence, distance travelled, name, symbol and whether it has fallen. 
 * This class ensures that Horse objects interact correctly and function correctly within the race. 
 * 
 * @author Maks Ostrynski
 * @version 2  22/04/2024
 */
public class Horse
{
    //Fields of class Horse
    private char horseSymbol;
    private String horseName;
    private int DistanceTravelled;
    private double horseConfidence;
    private boolean hasFallen;
    private String breed;
    private String color;
    private String accessory; 
    private int totalWins = 0;
    private int totalFalls = 0;
  
    
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.DistanceTravelled = 0;
        this.hasFallen = false; 
    }
    
    
    //Methods of class Horse.


    // Accessor method: returns the value of the horse breed field.
    public String getBreed() {
        return breed;
    }
    
    // mutator method: resets the horse for another race. 
    public void resetHorse() {
        DistanceTravelled = 0;
        hasFallen = false;
     }

    //Mutator method: sets the horse breed to a new value.
    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Accessor method: returns the value of the horse color field.
    public String getColor() {
        return color;
    }


    //Mutator method: sets the horse color to a new value.
    public void setColor(String color) {
        this.color = color;
    }

    // Accessor method: returns the value of the horse accessory field.
    public String getAccessory() {
        return accessory;
    }


    //Mutator method: sets the horse accesory to a new value. 
    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    // Mutator method: Increases the total wins of a Horse.
    public void increaseWins() {
        totalWins++;
    }

    // Mutator method: Increases the total falls of a Horse. 
    public void increaseFalls() {
        totalFalls++;
    }

    // Accessor method: returns the value of the total wins field.
    public int getTotalWins() {
        return totalWins;
    }

    // Accessor method: returns the value of the total falls field.
    public int getTotalFalls() {
        return totalFalls;
    }


    //Mutator method: sets the value of hasFallen to true. 
    public void fall()
    {
        this.hasFallen = true;
    }
    
    //Accessor method: returns the value of the horse confidence field. 
    public double getConfidence()
    {
        return this.horseConfidence;
    }
    
    //Accessor method: returns the value of the distance travelled field.
    public int getDistanceTravelled()
    {
        return this.DistanceTravelled;
    }
    
    //Accessor method: returns the value of the horse name field.
    public String getName()
    {
        return this.horseName;
    }
    
    //Accessor method: returns the value of the horse symbol field.
    public char getSymbol()
    {
        return this.horseSymbol;
    }
    
    //Mutator method: sets the value of the distance travelled field to 0.
    public void goBackToStart()
    {
        this.DistanceTravelled = 0;
    }
    
    //Accessor method: returns the value of the has fallen field.
    public boolean hasFallen()
    {
        return this.hasFallen;
    }

    //Mutator method: increments the value of the distance travelled field by 1.
    public void moveForward()
    {
        if(!this.hasFallen) {
            this.DistanceTravelled++;
        }
    }

    //Mutator method: sets the value of the horse confidence field to a new value.
    public void setConfidence(double newConfidence)
    {
        if(newConfidence >= 0 && newConfidence <= 1){
            this.horseConfidence = newConfidence; 
        } else {
            System.out.println("Confidence is not valid please select a number between 0 and 1");
        }
    }

    //Mutator method: increases the horse's confidence by 0.05 in the event of a win.
    public void increaseConfidence() {
        this.horseConfidence = Math.min(this.horseConfidence + 0.05, 1);
    }
    
    //Mutator method: decreases the horse's confidence by 0.05 in the event of a fall. 
    public void decreaseConfidence() {
        this.horseConfidence = Math.max(this.horseConfidence - 0.05, 0);
    }
    
    //Mutator method: sets the value of the horse symbol field to a new value.
    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;
    }
    
}

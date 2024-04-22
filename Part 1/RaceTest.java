public class RaceTest {

    public static void main(String[] args) {

        /* Collection of different Tests:
         * Use "//" to comment out the tests you do not want to run as they cannot be run simultaneously. 
         */

        //LongDistanceRace();
        //ShortDistanceRace();
        //MediumDistanceRace();
        //ShortDistanceRandom();
        //LongDistanceRandom();
        //MeidumDistanceRandom();
        //InvalidLaneAddition();
        //NullHorseAddition();
        //NullHorseRace();
       //MultipleHorsesSameLane();
       //LanesLeftEmpty();
    }

    public static void LongDistanceRace() {
        Race race = new Race(100); 
        race.addHorse(new Horse('A', "Apollo", 0.5), 1);
        race.addHorse(new Horse('B', "Blazer", 0.8), 2);
        race.addHorse(new Horse('C', "Cyclone", 0.2), 3);
        race.startRace();
        System.out.println("Test Completed\n");
    }

    public static void ShortDistanceRace() {
        Race race = new Race(15); 
        race.addHorse(new Horse('A', "Apollo", 0.1), 1);
        race.addHorse(new Horse('B', "Blazer", 0.7), 2);
        race.addHorse(new Horse('C', "Cyclone", 0.6), 3);
        race.startRace();
        System.out.println("Test Completed\n");
    }

    public static void ShortDistanceRandom() {
            Race race = new Race(15);
            race.addHorse(new Horse('A', "Pulsar", Math.random()), 1);
            race.addHorse(new Horse('B', "Quasar", Math.random()), 2);
            race.addHorse(new Horse('C', "Racer", Math.random()), 3);
            race.startRace();

        System.out.println("Test Completed\n");
    }

    public static void LongDistanceRandom() {
        Race race = new Race(100);
        race.addHorse(new Horse('A', "Pulsar", Math.random()), 1);
        race.addHorse(new Horse('B', "Quasar", Math.random()), 2);
        race.addHorse(new Horse('C', "Racer", Math.random()), 3);
        race.startRace();

    System.out.println("Test Completed\n");
    
}
        public static void MeidumDistanceRandom() {
            Race race = new Race(40);
            race.addHorse(new Horse('A', "Pulsar", Math.random()), 1);
            race.addHorse(new Horse('B', "Quasar", Math.random()), 2);
            race.addHorse(new Horse('C', "Racer", Math.random()), 3);
            race.startRace();

        System.out.println("Test Completed\n");
    }

    public static void MediumDistanceRace() {
        Race race = new Race(40); 
        race.addHorse(new Horse('A', "Apollo", 0.3), 1);
        race.addHorse(new Horse('B', "Blazer", 0.4), 2);
        race.addHorse(new Horse('C', "Cyclone", 0.2), 3);
        race.startRace();
        System.out.println("Test Completed\n");
    }

    public static void InvalidLaneAddition() {
        Race race = new Race(100);
        race.addHorse(new Horse('D', "Dasher", 0.5), 4); 
        System.out.println("Test Completed\n");
    }

    public static void NullHorseAddition() {
        Race race = new Race(40);
        race.addHorse(null, 1); 
        System.out.println("Test Completed\n");
    }

    public static void NullHorseRace() {
        Race race = new Race(40);
        race.addHorse(null, 1); 
        race.addHorse(null, 2); 
        race.addHorse(null, 3); 
        race.startRace();
        System.out.println("Test Completed\n");
    }

    public static void MultipleHorsesSameLane() {
        Race race = new Race(40);
        race.addHorse(new Horse('A', "Apollo", 0.6), 1);
        race.addHorse(new Horse('B', "Blazer", 0.6), 2);  //set to same lane.
        race.addHorse(new Horse('C', "Cyclone", 0.6), 2); //set to same lane.
        race.addHorse(new Horse('D', "Dababy", 0.6), 3);
        race.startRace();
        System.out.println("Test Completed\n");
    }

    public static void LanesLeftEmpty() {
        Race race = new Race(40);
        race.addHorse(new Horse('A', "Apollo", 0.6), 1);
        race.addHorse(new Horse('B', "Blazer", 0.6), 2);
        //No third lane added.
        race.startRace();
        System.out.println("Test Completed\n");
    }

}

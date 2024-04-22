public class GeneralRun {
    public static void main(String[] args) {
        Race race = new Race(100);
    
        Horse horse1 = new Horse('A', "Lightning", 0.7);
        Horse horse2 = new Horse('B', "Thunder", 0.4);
        Horse horse3 = new Horse('C', "Storm", 0.8);
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        race.startRace();
    }
   
}

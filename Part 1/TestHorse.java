public class TestHorse {
    private static Horse myHorse;

    public static void main(String[] args) {
        testConAcc();
        testMovement();
        testGoBackToStart();
        testConfidence();
        testSymbol();
    }

    private static void testConAcc() {
        myHorse = new Horse('H', "Silver", 0.85); 

       
        System.out.println("Testing constructor and accessors:");
        System.out.println("Expected: 'H' | Returned: " + myHorse.getSymbol());
        System.out.println("Expected: 'Silver'| Returned: " + myHorse.getName());
        System.out.println("Expected: 0.85 | Returned: " + myHorse.getConfidence());
        System.out.println("Expected: 0, | Returned: " + myHorse.getDistanceTravelled());
        System.out.println("Expected: false | Returned: " + myHorse.hasFallen());
    }

    private static void testMovement() {
        System.out.println("\nTesting moveForward method:");
        myHorse.moveForward(); 
        System.out.println("Expected distance: 1 | Returned: " + myHorse.getDistanceTravelled());

        
        System.out.println("\nTesting fall method:");
        myHorse.fall();
        System.out.println("Expected: true | Returned: " + myHorse.hasFallen());

        myHorse.moveForward();  
        System.out.println("Expected: 1 | Returned: " + myHorse.getDistanceTravelled());
    }

    private static void testGoBackToStart() {
        System.out.println("\nTesting goBackToStart method:");
        myHorse.goBackToStart();
        System.out.println("Expected distance: 0 | Returned: " + myHorse.getDistanceTravelled());
    }

    private static void testConfidence() {
        System.out.println("\nTesting setConfidence method:");
        myHorse.setConfidence(0.5);
        System.out.println("Expected confidence: 0.5 | Returned:: " + myHorse.getConfidence());
        myHorse.setConfidence(-1);  
        System.out.println("Expected (invalid set to -1): 0.5 | Returned: " + myHorse.getConfidence());
        myHorse.setConfidence(2);   
        System.out.println("Expected (invalid set to 2): 0.5 | Returned: " + myHorse.getConfidence());
    }

    private static void testSymbol() {
        System.out.println("\nTesting setSymbol method:");
        myHorse.setSymbol('Z');
        System.out.println("Expected: 'Z', | Returned: " + myHorse.getSymbol());
        try {
            myHorse = new Horse('\0', "Silver", 0.85);
            System.out.println("Expected: default or error | Returned: " + myHorse.getSymbol());
        } catch (Exception e) {
            System.out.println("Caught exception for null symbol: " + e.getMessage());
        }
    }

}

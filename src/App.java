
import java.util.ArrayList;
import java.util.List;

public class App {

    /*
     * G F G F G F G F G F
     */
    
    public static void main(String ... args) {

        final int NUMBER_OF_PHILOSOPHERS_AND_FORKS = 5;

        final List<Hashi> forks = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS_AND_FORKS; i++) {
            String name = "FORK " + Integer.toString(i + 1);
            forks.add(new Hashi(name));
        }

        final List<Philosopher> philosophers = new ArrayList<>();
    
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS_AND_FORKS; i++) {
            
            String name = "P" + Integer.toString(i + 1);
            
            Hashi leftFork = forks.get(i);
            Hashi rightFork = forks.get((i + 1) % NUMBER_OF_PHILOSOPHERS_AND_FORKS);

            Philosopher philosopher = new Philosopher(name, leftFork, rightFork);

            philosophers.add(philosopher);

            new Thread(philosopher).start();
        }

        boolean hasRunningThreads;

        do {

            hasRunningThreads = false;

            for (Philosopher p: philosophers) {

                String messageLog = p.getName() + ": " + p.getState();
                messageLog += " | Thoughts = " + p.getNumberOfThoughts();
                messageLog += " | Meals: " + p.getNumberOfMeals();

                System.out.println(messageLog);

                hasRunningThreads |= !"DONE".equals(p.getState());
            }
            
            System.out.println("********************************************\n\n");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
        
            }

        } while(hasRunningThreads);
    }
}


public class Philosopher implements Runnable {

    private String name;

    private Hashi leftFork;

    private Hashi rightFork;

    private int numberOfThoughts = 0;
    private long lastThought = 0;

    private int numberOfMeals = 0;
    private long lastMeal = 0;

    private final static int MAX_THOUGHTS = 1000;

    private String state = "Getting up";

    public Philosopher(String name, Hashi leftFork, Hashi rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return this.state;
    }

    public int getNumberOfThoughts() {
        return this.numberOfThoughts;
    }

    public int getNumberOfMeals() {
        return this.numberOfMeals;
    }

    public boolean hasLeftFork() {
        return this.leftFork.isBeingUsedBy(this);
    }

    public boolean hasRightFork() {
        return this.rightFork.isBeingUsedBy(this);
    }

    public boolean hasBothForks() {
        return this.hasLeftFork() && this.hasRightFork();
    }

    public void think() {
        this.state = "thinking";
        this.numberOfThoughts++;
        this.thinking();
    }

    public void thinking() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
    }

    private void eat() throws InterruptedException {

        this.state = "Getting forks";

        while (!this.hasBothForks()) {

            this.leftFork.pickUp(this);
            this.state = "LEFT FORK (" + this.leftFork.getName() + ") PICKED UP";

            if (!this.rightFork.isBeingUsed()) {
                this.rightFork.pickUp(this);
                this.state = "RIGHT FORK (" + this.rightFork.getName() + ") PICKED UP";
            } else {
                this.leftFork.pickDown(this);

            }
        }

        this.state = "Eating";
        this.eating();

        this.state = "Releasing forks";
        this.rightFork.pickDown(this);
        this.leftFork.pickDown(this);
    }

    public void eating() {
        this.numberOfMeals++;

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void run() {
        try {
            while (this.numberOfThoughts < MAX_THOUGHTS) {
                this.think();
                this.eat();
            }

            this.state = "DONE";

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("!!!!!!!!!!!!!!!!!");
        }
    }
}

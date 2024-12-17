
public class Hashi {

    private final String name;

    private Philosopher user;

    public Hashi(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Philosopher getUser() {
        return this.user;
    }

    public synchronized void pickUp(Philosopher philosopher) throws InterruptedException {
        while (this.user != null && !this.user.equals(philosopher)) {
            wait();
        }

        this.user = philosopher;
    }

    public synchronized void pickDown(Philosopher philosopher) {
        if (this.user.equals(philosopher)) {
            this.user = null;
            notifyAll();
        }
    }

    public boolean isBeingUsed() {
        return this.user != null;
    }

    public boolean isBeingUsedBy(Philosopher philosopher) {

        try {
            
            if (this.user == null)
                return false;

            return this.user.equals(philosopher);

        } catch (NullPointerException e) {
            return false;
        }
    }
}

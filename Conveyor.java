package project1;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Conveyor {
    public int conveyorID;
    private Lock access_lock = new ReentrantLock();

    public Conveyor(int conveyorID) {
        this.conveyorID = conveyorID;
    }

    public boolean lockConveyor() {
        try {
            // Try to acquire the lock with a timeout to prevent indefinite blocking
            return access_lock.tryLock(10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlockConveyor() {
        access_lock.unlock();
    }
}
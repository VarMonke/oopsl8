import java.util.Random;

public class Window implements Runnable {
    private final String name;
    private final Train train;
    private final BookingRequest[] requests;
    private final Random rand = new Random();

    public Window(String name, Train train, BookingRequest[] requests) {
        this.name = name;
        this.train = train;
        this.requests = requests;
    }

    public void run() {
        for (BookingRequest req : requests) {
            train.book(req);
            try {
                Thread.sleep(50 + rand.nextInt(71));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

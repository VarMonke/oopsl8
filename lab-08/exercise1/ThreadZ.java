import java.util.Random;

public class ThreadZ implements Runnable {
    private final Random rand = new Random();

    public void run() {
        String name = Thread.currentThread().getName();

        for (int k = 1; k <= 5; k++) {
            System.out.println(name + ": " + (2 * k - 1));
        }

        for (int i = 1; i <= 10; i++) {
            System.out.println(name + " counting: " + i);
            try {
                Thread.sleep(100 + rand.nextInt(401));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

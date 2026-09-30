import java.util.Random;

public class ThreadX implements Runnable {
    private final Random rand = new Random();

    public void run() {
        String name = Thread.currentThread().getName();

        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + (-i));
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

import java.util.Random;

public class ThreadY implements Runnable {
    private final Random rand = new Random();

    public void run() {
        String name = Thread.currentThread().getName();

        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + (i * 2));
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

public class Exercise1 {
    public static void main(String[] args) {
        ThreadX x = new ThreadX();
        ThreadY y = new ThreadY();
        ThreadZ z = new ThreadZ();

        Thread t1 = new Thread(x, "Thread-X");
        Thread t2 = new Thread(y, "Thread-Y");
        Thread t3 = new Thread(z, "Thread-Z");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Multithreading is over");
    }
}

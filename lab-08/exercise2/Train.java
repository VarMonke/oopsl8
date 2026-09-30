import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Train {
    public static final int TOTAL_SEATS = 15;

    private int acLeft = 4;
    private int slLeft = 6;
    private int gnLeft = 5;

    private int pnrCounter = 0;
    private final List<String> successfulPnrs = new ArrayList<>();
    private final Map<String, BookingRequest> pnrRequests = new HashMap<>();

    private int successCount = 0;
    private int failCount = 0;
    private int cancelCount = 0;

    private final Random rand = new Random();

    private int getLeft(String coach) {
        switch (coach) {
            case "AC": return acLeft;
            case "SL": return slLeft;
            default:   return gnLeft;
        }
    }

    private void setLeft(String coach, int value) {
        switch (coach) {
            case "AC": acLeft = value; break;
            case "SL": slLeft = value; break;
            default:   gnLeft = value;
        }
    }

    public String book(BookingRequest req) {
        synchronized (this) {
            int left = getLeft(req.coach);
            try {
                Thread.sleep(100 + rand.nextInt(101));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (left < req.seats) {
                failCount++;
                System.out.println(Thread.currentThread().getName()
                        + " FAILED " + req
                        + " | " + req.coach + " left: " + left);
                return null;
            }

            setLeft(req.coach, left - req.seats);
            String pnr = String.format("PNR-%03d", ++pnrCounter);
            successfulPnrs.add(pnr);
            pnrRequests.put(pnr, req);
            successCount++;
            System.out.println(Thread.currentThread().getName()
                    + " BOOKED " + req
                    + " -> " + pnr
                    + " | " + req.coach + " left: " + (left - req.seats));
            return pnr;
        }
    }

    public boolean cancel(String pnr, String coach, int seats) {
        synchronized (this) {
            int left = getLeft(coach);
            try {
                Thread.sleep(100 + rand.nextInt(101));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!successfulPnrs.contains(pnr)) {
                System.out.println(Thread.currentThread().getName()
                        + " CANCEL-FAILED " + pnr
                        + " | not a successful PNR");
                return false;
            }

            setLeft(coach, left + seats);
            successfulPnrs.remove(pnr);
            pnrRequests.remove(pnr);
            cancelCount++;
            System.out.println(Thread.currentThread().getName()
                    + " CANCELLED " + pnr + " (" + coach + " x" + seats + ")"
                    + " | " + coach + " left: " + (left + seats));
            return true;
        }
    }

    public synchronized List<String> getSuccessfulPnrs() {
        return new ArrayList<>(successfulPnrs);
    }

    public synchronized BookingRequest getRequestFor(String pnr) {
        return pnrRequests.get(pnr);
    }

    public void printSummary() {
        synchronized (this) {
            int held = 0;
            for (String pnr : successfulPnrs) {
                held += pnrRequests.get(pnr).seats;
            }
            int remaining = acLeft + slLeft + gnLeft;

            System.out.println();
            System.out.println("===== SUMMARY =====");
            System.out.println("Seats remaining  -> AC: " + acLeft
                    + ", SL: " + slLeft
                    + ", GN: " + gnLeft
                    + " (total " + remaining + ")");
            System.out.println("Successful bookings: " + successCount);
            System.out.println("Failed bookings:     " + failCount);
            System.out.println("Cancellations:       " + cancelCount);
            System.out.println("PNRs still held:     " + successfulPnrs
                    + " (" + held + " seats)");
            System.out.println("Invariant (remaining + held == "
                    + TOTAL_SEATS + "): "
                    + (remaining + held == TOTAL_SEATS ? "OK" : "BROKEN"));
        }
    }
}

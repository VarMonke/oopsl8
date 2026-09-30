import java.util.ArrayList;
import java.util.List;

public class Exercise2 {
    public static void main(String[] args) throws InterruptedException {
        Train train = new Train();

        BookingRequest[] reqsA = {
            new BookingRequest("Amit", "AC", 2),
            new BookingRequest("Riya", "SL", 3),
            new BookingRequest("Kabir", "GN", 2)
        };
        BookingRequest[] reqsB = {
            new BookingRequest("Neha", "AC", 3),
            new BookingRequest("Zara", "SL", 2),
            new BookingRequest("Omar", "GN", 3)
        };
        BookingRequest[] reqsC = {
            new BookingRequest("Dev", "AC", 2),
            new BookingRequest("Isha", "SL", 4),
            new BookingRequest("Pia", "GN", 2)
        };
        BookingRequest[] reqsD = {
            new BookingRequest("Veer", "SL", 2),
            new BookingRequest("Sara", "GN", 2),
            new BookingRequest("Anu", "AC", 1)
        };

        Thread a = new Thread(new Window("Window-A", train, reqsA), "Window-A");
        Thread b = new Thread(new Window("Window-B", train, reqsB), "Window-B");
        Thread c = new Thread(new Window("Window-C", train, reqsC), "Window-C");
        Thread d = new Thread(new Window("Window-D", train, reqsD), "Window-D");

        a.start();
        b.start();
        c.start();
        d.start();

        a.join();
        b.join();
        c.join();
        d.join();

        List<CancelJob> jobList = new ArrayList<>();
        for (String pnr : train.getSuccessfulPnrs()) {
            BookingRequest r = train.getRequestFor(pnr);
            if (r.coach.equals("AC") || r.coach.equals("SL")) {
                jobList.add(new CancelJob(pnr, r.coach, r.seats));
                if (jobList.size() == 2) break;
            }
        }
        CancelJob[] jobs = jobList.toArray(new CancelJob[0]);

        Thread desk = new Thread(new CancelDesk(train, jobs), "Cancel-Desk");
        desk.start();
        desk.join();

        BookingRequest[] reqsLate = {
            new BookingRequest("Late", "AC", 2),
            new BookingRequest("Late", "SL", 2)
        };
        Thread late = new Thread(new Window("Window-Late", train, reqsLate), "Window-Late");
        late.start();
        late.join();

        train.printSummary();
    }
}

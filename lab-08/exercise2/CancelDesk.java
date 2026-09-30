class CancelJob {
    final String pnr;
    final String coach;
    final int seats;

    CancelJob(String pnr, String coach, int seats) {
        this.pnr = pnr;
        this.coach = coach;
        this.seats = seats;
    }
}

public class CancelDesk implements Runnable {
    private final Train train;
    private final CancelJob[] jobs;

    public CancelDesk(Train train, CancelJob[] jobs) {
        this.train = train;
        this.jobs = jobs;
    }

    public void run() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (CancelJob job : jobs) {
            train.cancel(job.pnr, job.coach, job.seats);
        }
    }
}

public class BookingRequest {
    final String passenger;
    final String coach;
    final int seats;

    public BookingRequest(String passenger, String coach, int seats) {
        this.passenger = passenger;
        this.coach = coach;
        this.seats = seats;
    }

    public String toString() {
        return passenger + " " + coach + " x" + seats;
    }
}

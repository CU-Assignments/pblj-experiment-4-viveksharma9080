import java.util.*;

class SeatBookingSystem {
    private final int totalSeats;
    private final Set<Integer> bookedSeats;

    public SeatBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        this.bookedSeats = new HashSet<>();
    }

    public synchronized boolean bookSeat(int seatNumber, String userType) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.println(userType + " attempted to book an invalid seat: " + seatNumber);
            return false;
        }

        if (!bookedSeats.contains(seatNumber)) {
            bookedSeats.add(seatNumber);
            System.out.println(userType + " successfully booked seat " + seatNumber);
            return true;
        } else {
            System.out.println(userType + " failed to book seat " + seatNumber + " (Already booked)");
            return false;
        }
    }
}

class TicketBookingThread extends Thread {
    private final SeatBookingSystem system;
    private final int seatNumber;
    private final String userType;

    public TicketBookingThread(SeatBookingSystem system, int seatNumber, String userType, int priority) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.userType = userType;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, userType);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        SeatBookingSystem bookingSystem = new SeatBookingSystem(10);

        List<Thread> threads = new ArrayList<>();
        threads.add(new TicketBookingThread(bookingSystem, 3, "VIP", Thread.MAX_PRIORITY));
        threads.add(new TicketBookingThread(bookingSystem, 3, "Regular", Thread.NORM_PRIORITY));
        threads.add(new TicketBookingThread(bookingSystem, 5, "VIP", Thread.MAX_PRIORITY));
        threads.add(new TicketBookingThread(bookingSystem, 5, "Regular", Thread.NORM_PRIORITY));
        threads.add(new TicketBookingThread(bookingSystem, 7, "Regular", Thread.MIN_PRIORITY));
        threads.add(new TicketBookingThread(bookingSystem, 7, "VIP", Thread.MAX_PRIORITY));

        Collections.shuffle(threads);

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/**
 * If we lined up passenger busses nose to nose from Omaha to
 * Kansas City, then we filled each bus with golf balls, how many balls would
 * we need? This excellent code computes that quantity!
 * 
 */
public class Estimator
{

    public static void main(String[] args)
    {
        int numSeatsInBus = 40;
        long perBus = numGolfballsInBus(numSeatsInBus);
        int hoursToKC = 3;
        long numBusses = estimateNumBusses(hoursToKC, numSeatsInBus);
        long totalBalls = perBus * numBusses;
        System.out.println("The total number of golfballs required is: " + totalBalls);
    }

    /**
     * Calculate the number of golfballs it would take to fill a bus based on
     * the number of seats in the bus.
     */
    public static int numGolfballsInBus(int seats)
    {
        // first estimate the volume of a golfball in cubic feet.
        // We'll assume a cubical golfball, 1.5 inches on a side...
        double ballSizeInFeet = 1.5 / 12; // 12" per foot
        double ballVolume = ballSizeInFeet * ballSizeInFeet * ballSizeInFeet;

        Bus bus = new Bus(seats);
        int busVolume = bus.volumePassengerCompartment();

        double busFreeVolume = freeVolume(busVolume, 15);

        return (int) (busFreeVolume / ballVolume);
        
    }

    /**
     * Calculate the number of busses it would take to reach someplace end to
     * end based on how long it would take to drive there, and how many seats
     * each bus has.
     */
    public static int estimateNumBusses(int hours, int seats)
    {
        Bus bus = new Bus(seats);
        int miles = hours * 60; // assume 60 mph
        int feetPerMile = 5280;
        int totalFeet = feetPerMile * miles;
        int numBusses = totalFeet / bus.getLength();
        return numBusses;
    }

    /**
     * Calculate the amount of volume that is free, given that some percentage
     * is occupied.
     */
    public static double freeVolume(int volume, int percentOccupied)
    {
        double freeVolume = 0;
        double percentFree = 100 - percentOccupied;
        freeVolume = volume * (percentFree / 100);
        return freeVolume;

    }

}

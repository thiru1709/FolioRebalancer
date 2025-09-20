package portfolioRebalancer.service;

/**
 * UtilityService provides helper methods for common operations.
 */
public class UtilityService {

    /**
     * Rounds the given double value to two decimal places.
     *
     * @param value the double value to round
     * @return the value rounded to two decimal places
     */
    public static double roundToNearestDouble(double value) {
        return (double) Math.round((value) * 100) / 100;
    }
}

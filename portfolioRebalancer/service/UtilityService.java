package portfolioRebalancer.service;

public class UtilityService {

    public static double roundToNearestDouble(double value) {
        return (double) Math.round((value) * 100) / 100;
    }
}

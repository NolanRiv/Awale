package utils;

public class SeedUtils {

    public static int getTotalSeedsInHole(int redSeeds, int blueSeeds) {
        return redSeeds + blueSeeds;
    }

    public static boolean isHoleEmpty(int redSeeds, int blueSeeds) {
        return (redSeeds == 0 && blueSeeds == 0);
    }

    public static boolean isHoleCapturable(int redSeeds, int blueSeeds) {
        int totalSeeds = getTotalSeedsInHole(redSeeds, blueSeeds);
        return (totalSeeds == 2 || totalSeeds == 3);
    }

    public static int getTotalSeedsOnBoard(int[][] boardState) {
        int total = 0;
        for (int[] hole : boardState) {
            total += getTotalSeedsInHole(hole[0], hole[1]);
        }
        return total;
    }

    public static String formatSeedCount(int redSeeds, int blueSeeds) {
        return "[Red: " + redSeeds + ", Blue: " + blueSeeds + "]";
    }
}
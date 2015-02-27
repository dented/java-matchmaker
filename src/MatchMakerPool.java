import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// todo: add players to a pool to allow for better matching
public class MatchMakerPool {

    private static final List<Player> threeVThree = new ArrayList<Player>();
    private static final List<Player> fiveVFive = new ArrayList<Player>();


    public static void addPlayerToQueue(Player player) {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int max = 100;
        int min = 1;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum >= 50) {
            threeVThree.add(player); // can cause null pointer reference if not initialised
        } else {
            fiveVFive.add(player); // can cause null pointer reference if not initialised
        }
    }

    public static void addPlayerToThreeVThree(Player player) {
        threeVThree.add(player);
    }

    public static void addPlayerToFiveVFive(Player player) {
        fiveVFive.add(player);
    }

    public static List<Player> getThreeVThree(long mmr) {
        // sort by mmr provided
        return threeVThree;
    }

    public static List<Player> getFiveVFive(long mmr) {
        // sort by mmr provided
        return fiveVFive;
    }
}

public class Main {

    public static void main(String[] args) {
        System.out.println("Let the games begin...");
        MatchmakerImpl mmi = new MatchmakerImpl(); // this should be an outside service continuously running

        Player me = new Player("Graham Milosevic", 3, 5, 1500);

        mmi.enterMatchmaking(me);

        System.out.println(mmi.findMatch(5));
        System.out.println(mmi.findMatch(5));
        System.out.println(mmi.findMatch(3));
        System.out.println(mmi.findMatch(3));
        System.out.println(mmi.findMatch(3));

        System.out.println(mmi.findMatch(5));
        System.out.println(mmi.findMatch(5));
        System.out.println(mmi.findMatch(5));

        // todo: setup callback for when my match is found
    }
}

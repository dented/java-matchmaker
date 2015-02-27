import java.util.*;

/**
 * The matchmaking implementation that you will write.
 */
public class MatchmakerImpl implements Matchmaker {

    private ArrayList<Player> playersInQueue;
    private ArrayList<Match> currentMatches;
    private SortedMap<Long, ArrayList<Player>> matches;
    private long standardDeviation = 150;

    public MatchmakerImpl() {
        playersInQueue = new ArrayList<Player>();
        currentMatches = new ArrayList<Match>();

        for (Iterator<Player> it = SampleData.getPlayers().iterator(); it.hasNext(); ) {
            Player player = it.next();
            this.enterMatchmaking(player);
        }
        matches = groupByMMR(playersInQueue, standardDeviation);

    }

    // find players waiting in Queue for 3v3 and 5v5
    // look up mmr pool
    // create mmr pool if doesn't exist
    // remove player from queue when match starts
    public Match findMatch(int playersPerTeam) {
        return getLatestMatch(playersPerTeam);
    }

    // create a pool of games / lobbies for players per mmr rating
    // after certain time, mmr rating ratio extends by x at a rate of 1 minute
    // todo: make sure user is unique as to not add more than once
    public void enterMatchmaking(Player player) {
        // todo: verify player isn't already in queue
        if (playersInQueue.indexOf(player) == -1) {
            playersInQueue.add(player);
        }
    }

    private Match getLatestMatch(int playersPerTeam) {
        Match nextMatch = null;
        long mmrMatched = getNextTeam(playersPerTeam * 2); // todo: get mmr with sizes currently okay
        if(mmrMatched > 0) {

            ArrayList<Player> playersSelected = new ArrayList<Player>(playersPerTeam * 2);
            ArrayList<Player> group = matches.get(mmrMatched);

            Collections.shuffle(group);
            Set<Player> team1 = new HashSet<Player>(playersPerTeam);
            Set<Player> team2 = new HashSet<Player>(playersPerTeam);

            for (int i = 0; i < playersPerTeam; i++) {
                Player p1 = group.get(i);
                Player p2 = group.get(i + playersPerTeam);
                team1.add(p1);
                team2.add(p2);
                playersSelected.add(p1);
                playersSelected.add(p2);
            }

            for (Player p : playersSelected) {
                group.remove(p); // todo: change to a temporary queue just in case someone cancels game

            }
            if (group.size() == 0) {
                matches.remove(mmrMatched);
            }

            nextMatch = new Match(team1, team2);
            if(!currentMatches.add(nextMatch)) {
                // todo: alert match not followed
            }
        }
        return nextMatch;
    }

    private long getNextTeam(int teamSize) {

        standardDeviation = 150; // reset deviation to ensure caliber is not out of control
        long found = -1;
        for (int i = 0; i < 5; i++) {

            for (Map.Entry<Long, ArrayList<Player>> matchEntry : matches.entrySet()) {
                if(matchEntry.getValue().size() >= teamSize) {
                    found = matchEntry.getKey();
                    break;
                } else {
                    // todo: check if current team and next team meets teamSize requirement
                }
            }
            if(found != -1) break;
            standardDeviation += 50;
            matches = groupByMMR(playersInQueue, standardDeviation);
        }

        return found;
    }

    private SortedMap<Long, ArrayList<Player>> groupByMMR(ArrayList<Player> list, long standardDeviation) {

        SortedMap<Long, ArrayList<Player>> map = new TreeMap<Long, ArrayList<Player>>();
        for (Player p : list) {
            long mmr = p.getMMR();
            long maxRange = mmr + (standardDeviation * 3);
            long minRange = mmr - standardDeviation;
            ArrayList<Player> group;
            ArrayList<Player> higherGroup = map.get(maxRange);
            ArrayList<Player> normalGroup = map.get(mmr);
            ArrayList<Player> lowerGroup = map.get(minRange);

            if (higherGroup == null && lowerGroup == null && normalGroup == null) { // no range exists yet
                group = new ArrayList<Player>();
                map.put(maxRange, group);
            } else if (higherGroup != null) { // place player in higher bracket of mmr to help improve
                group = higherGroup;
            } else if(normalGroup != null) { // place in same level group
                group = normalGroup;
            } else { // if no higher bracket, but lower, place in
                group = lowerGroup;
            }
            group.add(p);
        }
        return map;

    }

}
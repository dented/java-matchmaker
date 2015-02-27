/**
 * <p>
 * Representation of a player.
 * </p>
 * <p>
 * As indicated in the challenge description, feel free to augment the Player
 * class in any way that you feel will improve your final matchmaking solution.
 * <strong>Do NOT remove the name, wins, or losses fields.</strong> Also note
 * that if you make any of these changes, you are responsible for updating the
 * {@link SampleData} such that it provides a useful data set to exercise your
 * solution.
 * </p>
 */
public class Player {

    private String name;
    private long wins;
    private long losses;
    private long mmr;
    private long total;

    public Player(String name, long wins, long losses, long mmr) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.mmr = mmr;
        this.total = wins + losses;
        // calculateMMR();
    }

    public String getName() {
        return name;
    }

    public long getWins() {
        return wins;
    }

    public long getLosses() {
        return losses;
    }

    public long getMMR() {
        return mmr;
    }

    public void updateWin() {
        addAWin();
        updateMMR(30); // todo: should be based off games value
    }

    public void updateLoss() {
        addALoss();
        updateMMR(-30); // todo: should be based off games value
    }

    private void addAWin() { this.wins += 1; }

    private void addALoss() { this.losses += 1; }

    private void updateMMR(int gamePoints) {
        this.mmr += gamePoints;
    }

    private void calculateMMR() {
        long baseMMR = 1500;
        this.mmr = baseMMR;
        if(this.total > 0) {
            if(this.losses > this.wins) {
                if (this.wins == 0) {
                    System.out.printf("players.add(new Player(\"%s\", %d, %d, %d));\n", this.name, this.wins, this.losses, this.mmr);
                }
                double newMMR = ((double) this.losses / (double) this.total) * (double)this.mmr;
                this.mmr = 5*(Math.round(newMMR/5));
            } else {
                double newMMR = (((double)this.wins / (double)this.total) + 1) * (double)this.mmr;
                this.mmr = 5*(Math.round(newMMR/5));
            }
        } else {
            this.mmr = 1500;
        }
        // System.out.printf("players.add(new Player(\"%s\", %d, %d, %d));\n", this.name, this.wins, this.losses, this.mmr);
    }

}
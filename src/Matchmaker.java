public interface Matchmaker {

    /**
     * <p>
     * Find a match with the given number of players per team.
     * </p>
     *
     * @param playersPerTeam
     *            the number of players required in each team
     * @return an appropriate match or null if there is no appropriate match
     */
    Match findMatch(int playersPerTeam);

    /**
     * <p>
     * Add a player for matching.
     * </p>
     */
    void enterMatchmaking(Player player);

    // todo: Add matchMaking for teamSize
    // void enterMatchmaking(Player player, int playersPerTeam);

    // todo: Add matchMaking for characterTypeSelection
    // void enterMatchmaking(Player player, CharacterType characterType);

}
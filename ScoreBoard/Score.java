package fall18_207project.ScoreBoard;

import java.io.Serializable;

/**
 * The Score for scoreboard
 */
public class Score implements Comparable<Score>, Serializable {

    private String username;
    private int current_score;
    private int game_id;

    public Score(String username, int current_score, int game_id) {
        this.username = username;
        this.current_score = current_score;
        this.game_id = game_id;
    }
    /**
     * Get the username.
     */

    public String get_username() {
        return username;
    }
    /**
     * Get the score.
     */

    public int get_score() {
        return current_score;
    }
    /**
     * Get the game id.
     */

    public int get_game_id() {
        return game_id;
    }
    /**
     * Compare the score with others.
     */

    @Override
    public int compareTo(Score other) {
        return  other.get_score() - this.get_score();
    }

    @Override
    public String toString() {
        return "User: " + username
                + ", Game ID: " + Integer.toString(game_id)
                + ", Score: " + Integer.toString(current_score);
    }

}

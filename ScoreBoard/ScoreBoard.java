package fall18_207project.ScoreBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Scoreboard being managed.
 */

public class ScoreBoard implements Serializable {
    /**
     * The scores being managed.
     */

    public List<Score> scores = new ArrayList<>();
    private Strategy strategy;
    /**
     * Add score.
     * @param score score
     */

    public void add_score(Score score) {
        scores.add(score);
    }

    /**
     * Get the scores by user.
     * @param username username
     * @param game_id game_id
     */

    public List<Score> get_scores_by_user(String username, int game_id) {
        List<Score> result = new ArrayList<>();
        for (Score item: scores) {
            if (item.get_username().equals(username) && item.get_game_id() == game_id) {
                result.add(item);
            }
        }

        strategy.doOperation(result);

        if (result.size() >= 5) {
            return result.subList(0, 5);
        }
        return result;
    }
    /**
     * Get the score by gameId.
     * @param game_id   game_id
     */

    public List<Score> get_scores_by_game_id(int game_id) {
        List<Score> result = new ArrayList<>();

        for (Score item: scores) {
            if (item.get_game_id() == game_id) {
                result.add(item);
            }
        }
        strategy.doOperation(result);

        if (result.size() >= 5) {
            return result.subList(0, 5);
        }
        return result;

    }
    /**
     * The toString method by using username and game id.
     * @param username username
     * @param game_id   game_id
     */

    public String toString_by_user(String username, int game_id) {
        List<Score> scores = get_scores_by_user(username, game_id);
        String result = "";
        int i = 0;
        for (Score item: scores) {
            result = result.concat("Top " + Integer.toString(i+1) + ": "
                    + Integer.toString(item.get_score()) + "\n");
            i++;
        }
        return result;
    }
    /**
     * The toString by gameId.
     * @param game_id game_id
     */

    public String toString_by_game_id(int game_id) {
        List<Score> scores = get_scores_by_game_id(game_id);
        String result = "";
        int i = 0;
        for (Score item: scores) {
            result = result.concat("Top " + Integer.toString(i+1) + ": " + item.get_username()
                    + ", "  + Integer.toString(item.get_score()) + "\n");
            i++;
        }
        return result;
    }
    /**
     * Setter for strategy, choosing the right scoring system.
     * @param strategy Strategy
     */

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}

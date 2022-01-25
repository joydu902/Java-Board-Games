package fall18_207project.ScoreBoard;


import java.util.Collections;
import java.util.List;
/**
 * The larger the points, the higher the score.
 */

public class OperationLarger implements Strategy {
    @Override
    public void doOperation(List<Score> result) {
        Collections.sort(result);
    }
}

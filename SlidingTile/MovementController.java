package fall18_207project.SlidingTile;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display, GameActivity gameActivity) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                System.out.println("You win");
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();

                GetScoreActivity act = new GetScoreActivity();

//                View rootView = gameActivity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView();

                Intent temp = new Intent(gameActivity, GetScoreActivity.class);
                temp.putExtra("score",Integer.toString(boardManager.getStep()));
                gameActivity.startActivity(temp);

            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    public void UndoMovement(Context context, int numStep, boolean display){
        if (boardManager.getUndoList().size() >= 4 * numStep){
            boardManager.unDoNSteps(numStep);
        }else{
            Toast.makeText(context, "No More Steps!", Toast.LENGTH_SHORT).show();
        }
    }


}

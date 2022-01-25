package fall18_207project.MatchingCard;
/*
Adapted from:
https://github.com/nirfinz/AndroidMemoryGame.git
*/
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.widget.GridLayout;

import fall18_207project.GameCenter.R;

/**
 * The class for MemoryButton
 */
public class MemoryButton extends AppCompatButton {

    protected int row;
    protected int col;
    protected int frontImageID;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    public MemoryButton(Context context,int row,int col,int frontImageID, int totalBotton){
        super(context);

        this.row = row;
        this.col = col;
        this.frontImageID = frontImageID;

        front = context.getDrawable(frontImageID);
        back = context.getDrawable(R.drawable.button_question);
        int size = 1100/totalBotton;

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(row),GridLayout.spec(col));

        tempParams.width = (int) getResources().getDisplayMetrics().density * size;
        tempParams.height = (int) getResources().getDisplayMetrics().density * size;

        setLayoutParams(tempParams);
    }

    /**
     * @return weather the button is matched or not
     */
    public boolean isMatched() {
        return isMatched;
    }

    /**
     * @param matched the button is match or not
     * set the button matched or not
     */
    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    /**
     * @return FrontImageID
     */
    public int getFrontImageID() {
        return frontImageID;
    }

    /**
     * flip the button
     */
    public void flip(){
        if(isMatched){
            return;
        }

        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        }else{
            setBackground(front);
            isFlipped = true;
        }
    }
}

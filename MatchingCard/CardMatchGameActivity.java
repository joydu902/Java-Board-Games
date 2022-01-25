package fall18_207project.MatchingCard;
/*
Adapted from:
https://github.com/nirfinz/AndroidMemoryGame.git
*/
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.Score;
import fall18_207project.ScoreBoard.ScoreBoard;
import fall18_207project.ScoreBoard.ScoreBoardActivity;
import fall18_207project.Simon.SimonGameOverActivity;
import fall18_207project.Simon.SimonMovementControler;
import fall18_207project.User.LogInActivity;

public class CardMatchGameActivity extends AppCompatActivity implements  View.OnClickListener{


    private int numOfElements;
    private  MemoryButton [] allButtons;
    private MemoryButton selectButton1;
    private MemoryButton selectButton2;
    private boolean isBusy = false;
    private int size;
    private TextView textView;
    private CardMatchGameActivity self = this;


    private MCBoardManager mcboardManager;

//    private Timer timer;

    private ScoreBoard sb = new ScoreBoard();
    public static final String SB_FILE = "sb_file.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_matching_card);
        loadFromFile(MCStartingActivity.SAVE_FILENAME);
        size = mcboardManager.sizeOfMat;

        numOfElements = size *size;
        GridLayout theGridLayout = (GridLayout)findViewById(R.id.grid_layout_for_all);
        this.allButtons = new MemoryButton[numOfElements];
        initeMemoryButtons(size,size,theGridLayout);

        textView = (TextView) findViewById(R.id.textView11);
        textView.setText("Currect Step: " + mcboardManager.getStep());
    }

    /**
     * @param numRow the place of row we should put our button
     * @param numCol  the place of col we should put our button
     * @param theGridLayout the game window
     *  create the game board in view
     */
    public void initeMemoryButtons(int numRow,int numCol,GridLayout theGridLayout){
        for (int row = 0; row < numRow ; row++){
            for(int col = 0 ; col <numCol ; col++){
                MemoryButton tempButton = new MemoryButton(this,row,col,mcboardManager.allButtonsGraphics[mcboardManager.allButtonsGraphicLocation[row *numCol +col]],
                        numCol+numCol);
                if(mcboardManager.flipOrNotList[row *numCol +col])
                {tempButton.setBackground(this.getDrawable(mcboardManager.allButtonsGraphics[mcboardManager.allButtonsGraphicLocation[row *numCol +col]]));
                tempButton.setMatched(true);
                tempButton.setEnabled(false);}
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                allButtons[row * numCol +col] = tempButton;
                theGridLayout.addView(tempButton);
            }
        }
    }

    /**
     * @return whether the game is finish or not
     */
    private boolean checkIfDone() {
        for (int i = 0; i < numOfElements; i++) {
            if (allButtons[i].isEnabled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param view
     * click one of the button and make it flip
     */
    @Override
    public void onClick(View view) {
        if(isBusy){
            modiFiedFlipList();
            return;
        }
        MemoryButton button = (MemoryButton) view;
        if(button.isMatched){
            modiFiedFlipList();
            textView.setText("Currect Step: " + mcboardManager.getStep());
            return;
        }
        if(selectButton1 == null){
            selectButton1 = button;
            selectButton1.flip();
            mcboardManager.increaseStep();
            modiFiedFlipList();
            textView.setText("Currect Step: " + mcboardManager.getStep());
            return;
        }
        if(selectButton1.getId() == button.getId()){
            modiFiedFlipList();
            textView.setText("Currect Step: " + mcboardManager.getStep());
            return;
        }
        if(selectButton1.getFrontImageID() == button.getFrontImageID()){
            mcboardManager.increaseStep();
            button.flip();
            button.setMatched(true);
            selectButton1.setMatched(true);
            selectButton1.setEnabled(false);
            button.setEnabled(false);
            selectButton1 = null;
            if(checkIfDone()){
                CardMatchGameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CardMatchGameActivity.this, "Winner", Toast.LENGTH_LONG).show();

                        Intent temp = new Intent(self, MCGameOverActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("score", self.mcboardManager.getStep());
                        temp.putExtras(b);
                        startActivity(temp);
                    }
                });
//                timer.cancel();
//                backToMenu();
            }
            modiFiedFlipList();
            textView.setText("Currect Step: " + mcboardManager.getStep());
            return;
        }else{// are not the same
            mcboardManager.increaseStep();
            selectButton2  = button;
            selectButton2.flip();
            isBusy = true;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectButton2.flip();
                    selectButton1.flip();
                    selectButton1 = null;
                    selectButton2 = null;
                    isBusy = false;
                }
            },500);
        }
        modiFiedFlipList();
        textView.setText("Currect Step: " + mcboardManager.getStep());
        saveToFile(MCStartingActivity.SAVE_FILENAME);
    }

    /**
     * @param fileName the file we want to load
     * load file form this file name
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                mcboardManager = (MCBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(mcboardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    protected void onPause() {
        super.onPause();
       saveToFile(MCStartingActivity.SAVE_FILENAME);
    }

    /**
     * The method modify the FlipList in mcBoardManager
     */
    public void modiFiedFlipList(){
        for (int i = 0; i!= size * size;i++){
            if (allButtons[i].isMatched()){
                mcboardManager.flipOrNotList[i] = true;
            }
        }
    }


}
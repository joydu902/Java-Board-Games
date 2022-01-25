package fall18_207project.SlidingTile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall18_207project.GameCenter.CustomAdapter;
import fall18_207project.GameCenter.R;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * @param savedInstanceState
     * create the game page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main2);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });
        UndoButtonListener();
        gridView.setActivity(this);
        UndoNStepsButtonListener();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     * also auto save the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.SAVE_FILENAME);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
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
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * The button for undo
     */
    private void UndoButtonListener(){
        Button Undo = findViewById(R.id.UndoButton);
        Undo.setOnClickListener((v) -> {
            if (boardManager.isValidUndo()) {
                boardManager.unDo();
            }
            else if (!boardManager.isValidUndo()){
                Toast.makeText(this, "Undo Steps Out of Range", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * The button for servel undo steps
     */
    private void UndoNStepsButtonListener(){
        EditText time = (EditText)findViewById(R.id.NumofStep);
        Button UndoNSteps = findViewById(R.id.UndoNStep);
        UndoNSteps.setOnClickListener((v) -> {
            if (time.getText().toString().equals("") || !isNumeric(time.getText().toString())){
                Toast.makeText(this, "Invalid Message", Toast.LENGTH_SHORT).show();
            }
            else if (isNumeric(time.getText().toString()) &&  boardManager.isValidUndoNSteps(Integer.valueOf(time.getText().toString()))) {
                boardManager.unDoNSteps(Integer.valueOf(time.getText().toString()));
            }
            else if (!boardManager.isValidUndoNSteps(Integer.valueOf(time.getText().toString()))){
                Toast.makeText(this, "Undo Steps Out of Range", Toast.LENGTH_SHORT).show();
            }

        });

    }

    /**
     * @param s The string to be checked
     * @return whether a string can be convert to number or not
     */
    private static boolean isNumeric(String s){
        for (int i = s.length(); --i >= 0;){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
}

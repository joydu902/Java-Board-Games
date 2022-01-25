package fall18_207project.SlidingTile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.GameCenter.UploadPictureActivity;
import fall18_207project.ScoreBoard.ScoreBoardActivity;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME;

    //public static String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME;
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SAVE_FILENAME = SaveLoad.name + SaveLoad.gameName + SaveLoad.game_id + ".ser";
        TEMP_SAVE_FILENAME = SaveLoad.name + SaveLoad.gameName + SaveLoad.game_id + ".ser";
        System.out.println(SaveLoad.name);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addSTScoreBoardButtonListener();
        addCustomizeButtonListener();
    }

    private void addSTScoreBoardButtonListener(){
        Button STScoreBoardButton = findViewById(R.id.STScoreBoardButton);
        STScoreBoardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, GameActivity.class);

            if (checkFileEmpty(StartingActivity.SAVE_FILENAME)){

                loadFromFile(StartingActivity.SAVE_FILENAME);
                startActivity(temp);
            }
            else{
                Toast.makeText(this, "Nothing Saved", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        SAVE_FILENAME = SaveLoad.name + SaveLoad.gameName + SaveLoad.game_id + ".ser";
        TEMP_SAVE_FILENAME = SaveLoad.name + SaveLoad.gameName + SaveLoad.game_id + ".ser";
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
            });
    }

    /**
     * Activate the save button.
     */
    private void addCustomizeButtonListener() {
        Button saveButton = findViewById(R.id.CustomizeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCustomize();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }
    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }
    /**
     * Switch to the Customize view to play the game.
     */
    private void switchToCustomize(){
        Intent tmp = new Intent(this, UploadPictureActivity.class);
        startActivity(tmp);
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
    /**
     * Check is the file empty or not.
     *
     * @param fileName the name of the file
     */


    private boolean checkFileEmpty(String fileName){
        boolean checkEmpty = false;

        try{
            InputStream inputStream = this.openFileInput(fileName);
            if(inputStream != null){
                checkEmpty = true;
                inputStream.close();
            }
        }catch (FileNotFoundException e){
            Log.e("login activity", "File not Found: " + e.toString());
        }catch (IOException e){
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return checkEmpty;
    }
}

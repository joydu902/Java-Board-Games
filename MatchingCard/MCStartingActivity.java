package fall18_207project.MatchingCard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.ScoreBoardActivity;

public class MCStartingActivity extends AppCompatActivity {
    public static String SAVE_FILENAME;
    private MCBoardManager mcboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SAVE_FILENAME = SaveLoad.name + SaveLoad.gameName + SaveLoad.game_id + ".ser";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcstarting);
        addStartButtonListener();
        addLoadButtonListener();
        SimonSBListener();
    }

    /**
     * begin a new game
     */
    private void addStartButtonListener(){
        Button StartButton = findViewById(R.id.MCStart);
        StartButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, CardMatchGameActivity.class);
            mcboardManager = new MCBoardManager();
            saveToFile(MCStartingActivity.SAVE_FILENAME);
            startActivity(temp);


        });
    }

    /**
     * load the game
     */
    private void addLoadButtonListener(){
        Button LoadButton = findViewById(R.id.MCLoad);
        LoadButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, CardMatchGameActivity.class);
            if (checkFileEmpty(MCStartingActivity.SAVE_FILENAME)){
                loadFromFile(MCStartingActivity.SAVE_FILENAME);
                startActivity(temp);
            }
            else{
                Toast.makeText(this, "Nothing Saved", Toast.LENGTH_SHORT).show();
            }



        });
    }

    /**
     * switch to scoreboard
     */
    private void SimonSBListener() {
        Button mcsb = findViewById(R.id.MCScordBoard);
        mcsb.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);

        });
    }

    /**
     * @param fileName file name to save
     * save the file
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

    /**
     * @param fileName file to be load
     *load the file
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
     * @param fileName the file to be checked
     * @return weather the file is empty or not
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

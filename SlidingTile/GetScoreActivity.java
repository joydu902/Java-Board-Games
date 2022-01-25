package fall18_207project.SlidingTile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall18_207project.ScoreBoard.Score;
import fall18_207project.ScoreBoard.ScoreBoard;
import fall18_207project.ScoreBoard.ScoreBoardActivity;
import fall18_207project.SlidingTile.BoardManager;
import fall18_207project.GameCenter.GameCenterActivity;
import fall18_207project.User.LogInActivity;
import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;


/**
 * The activity for get the score
 */
public class GetScoreActivity extends AppCompatActivity  {
    public BoardManager currentGame;
    private ScoreBoard sb = new ScoreBoard();
    public static final String SB_FILE = "sb_file.ser";

    /**
     * @param savedInstanceState
     * create the activity page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_score);

        Intent myIntent = getIntent(); // gets the previously created intent
        String score = myIntent.getStringExtra("score"); //
        final TextView simpleTextView = (TextView) findViewById(R.id.show_score);

        simpleTextView.setText("Your Score: ".concat(score));

        loadFromFile(SB_FILE);

        int game_id = Integer.parseInt(SaveLoad.game_id);
        System.out.println(game_id);
        Score current_score = new Score(LogInActivity.current_user, Integer.parseInt(score), game_id);
        sb.add_score(current_score);
        saveToFile(SB_FILE);

        return_mainButtonListener();
        return_scoreboardButtonListener();
    }

    /**
     * @param fileName the file we want to load
     *  load the file from this fileName
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                sb = (ScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("scoreboard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("scoreboard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("scoreboard activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * jump to GameCenterActivity
     */
    private void return_mainButtonListener(){
        Button STScoreBoardButton = findViewById(R.id.return_main);
        STScoreBoardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, GameCenterActivity.class);
            startActivity(temp);
        });
    }

    /**
     * jump to ScoreBoardActivity
     */
    private void return_scoreboardButtonListener(){
        Button STScoreBoardButton = findViewById(R.id.return_scoreboard);
        STScoreBoardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);
        });
    }

    /**
     * @param fileName file name to be save
     *  save the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(sb);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }








}

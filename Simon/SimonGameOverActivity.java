package fall18_207project.Simon;

/*
Adapted from:
https://github.com/saifcoding/Simon-Game-App.git
*/

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
import java.util.ArrayList;

import fall18_207project.GameCenter.GameCenterActivity;
import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.Score;
import fall18_207project.ScoreBoard.ScoreBoard;
import fall18_207project.ScoreBoard.ScoreBoardActivity;
import fall18_207project.User.LogInActivity;

/**
 * The activity for Simon when game finish
 */
public class SimonGameOverActivity extends AppCompatActivity {
    private ScoreBoard sb = new ScoreBoard();
    public static final String SB_FILE = "sb_file.ser";
    SimonMovementControler mc = new SimonMovementControler();

    /**
     * @param savedInstanceState
     * create the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game_over);
        SimonMenuButtonListener();
        SimonNewGameButtonListener();
        SimonSBListener();

        TextView simonScoreText = (TextView) findViewById(R.id.SimonScoreText);
        Bundle b = getIntent().getExtras();
        int score = -1;
        if(b != null)
            score = b.getInt("score");
        simonScoreText.setText("Your Score: " + score);

        loadFromFile(SB_FILE);

        int game_id = Integer.parseInt(SaveLoad.game_id);
        System.out.println(game_id);
        Score current_score = new Score(LogInActivity.current_user, score, game_id);
        sb.add_score(current_score);
        saveToFile(SB_FILE);

    }

    /**
     * switch to GameCenterActivity.
     */

    private void SimonMenuButtonListener() {
        Button SimonMediumButton = findViewById(R.id.buttonSimonMenu);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, GameCenterActivity.class);
            startActivity(temp);

        });
    }

    /**
     * switch to SimonLevelChooseActivity and clear history.
     */

    private void SimonNewGameButtonListener() {
        Button SimonMediumButton = findViewById(R.id.buttonSimonNewGame);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, SimonLevelChooseActivity.class);
            startActivity(temp);
            mc.clear();



        });
    }

    /**
     * switch to ScoreBoardActivity corresponding to this game.
     */

    private void SimonSBListener() {
        Button SimonMediumButton = findViewById(R.id.buttonSimonScoreBoard);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);

        });
    }

    /**
     * load previous Score information from file.
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
     * Add and save new Score information to file.
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

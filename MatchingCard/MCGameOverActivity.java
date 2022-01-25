package fall18_207project.MatchingCard;

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

import fall18_207project.GameCenter.GameCenterActivity;
import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.Score;
import fall18_207project.ScoreBoard.ScoreBoard;
import fall18_207project.ScoreBoard.ScoreBoardActivity;
import fall18_207project.Simon.SimonLevelChooseActivity;
import fall18_207project.User.LogInActivity;

public class MCGameOverActivity extends AppCompatActivity {

    private ScoreBoard sb = new ScoreBoard();
    public static final String SB_FILE = "sb_file.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcgame_over);

        TextView simonScoreText = (TextView) findViewById(R.id.mcScoreText);
        Bundle b = getIntent().getExtras();
        int score = -1;
        if(b != null)
            score = b.getInt("score");
        simonScoreText.setText("Your Score: " + score);


        sbLoadFromFile(SB_FILE);

        int game_id = Integer.parseInt(SaveLoad.game_id);
        System.out.println(game_id);
        Score current_score = new Score(LogInActivity.current_user, score, game_id);
        sb.add_score(current_score);
        sbSaveToFile(SB_FILE);

        MenuButtonListener();
        NewGameButtonListener();
        SBListener();
    }


    /**
     * switch to GameCenterActivity.
     */

    private void MenuButtonListener() {
        Button SimonMediumButton = findViewById(R.id.buttonMCMenu);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, GameCenterActivity.class);
            startActivity(temp);

        });
    }

    /**
     * switch to SimonLevelChooseActivity and clear history.
     */

    private void NewGameButtonListener() {
        Button SimonMediumButton = findViewById(R.id.buttonMCNewGame);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, MCChoosingLevelActivity.class);
            startActivity(temp);

        });
    }

    /**
     * switch to ScoreBoardActivity corresponding to this game.
     */

    private void SBListener() {
        Button SimonMediumButton = findViewById(R.id.buttonMCScoreBoard);
        SimonMediumButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);

        });
    }


    private void sbLoadFromFile(String fileName) {

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



    public void sbSaveToFile(String fileName) {
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

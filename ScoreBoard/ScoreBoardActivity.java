package fall18_207project.ScoreBoard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import fall18_207project.GameCenter.R;
import fall18_207project.SlidingTile.GetScoreActivity;
/**
 * The ScoreBoardActivity
 */
public class ScoreBoardActivity extends AppCompatActivity {

    public static ScoreBoard sb = new ScoreBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        // Add two button listener.
        addUserScoreBoardButtonListener();
        addAllUsersScoreBoardButtonListener();
        loadFromFile(GetScoreActivity.SB_FILE);
        System.out.println(sb.scores.size());
    }

    /**
     * Read score file
     * @param fileName the name of the file
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
     * Activate the ScoreBoard by user button.
     */

    private void addUserScoreBoardButtonListener(){
        Button STScoreBoardButton = findViewById(R.id.UserScoreBoardButton);
        STScoreBoardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, UserScoreBoardActivity.class);
            startActivity(temp);
        });
    }

    /**
     * Activate the all user ScoreBoard button.
     */

    private void addAllUsersScoreBoardButtonListener(){
        Button STScoreBoardButton = findViewById(R.id.allUserScoreBoardButton);
        STScoreBoardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardAllUsersActivity.class);
            startActivity(temp);
        });
    }
}

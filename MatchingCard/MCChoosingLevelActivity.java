package fall18_207project.MatchingCard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.ScoreBoardActivity;

public class MCChoosingLevelActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcchoosing_level);
        MCLevel1ButtonListener();
        MCLevel2ButtonListener();
        MCLevel3ButtonListener();
    }

    /**
     * switch to easy model
     */
    private void MCLevel1ButtonListener(){

        Button Level1 = findViewById(R.id.MCLevel1);
        Level1.setOnClickListener((v) -> {
            Intent temp = new Intent(this,MCStartingActivity.class);

            MCBoardManager.sizeOfMat = 2;
            SaveLoad.game_id = "7";
            startActivity(temp);
            //SaveLoad.complexity = "3";
        });
    }

    /**
     * switch to medium model
     */
    private void MCLevel2ButtonListener(){
        Button Level1 = findViewById(R.id.MCLevel2);
        Level1.setOnClickListener((v) -> {
            Intent temp = new Intent(this, MCStartingActivity.class);
            MCBoardManager.sizeOfMat = 4;
            SaveLoad.game_id = "8";
            startActivity(temp);
            //SaveLoad.complexity = "3";
        });
    }

    /**
     * switch to hard model
     */
    private void MCLevel3ButtonListener(){
        Button Level1 = findViewById(R.id.MCLevel3);
        Level1.setOnClickListener((v) -> {
            Intent temp = new Intent(this,MCStartingActivity.class);
            MCBoardManager.sizeOfMat = 6;
            SaveLoad.game_id = "9";
            startActivity(temp);

            //SaveLoad.complexity = "3";
        });
    }

}
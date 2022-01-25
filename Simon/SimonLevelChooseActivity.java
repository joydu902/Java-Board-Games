package fall18_207project.Simon;

/*
Adapted from:
https://github.com/saifcoding/Simon-Game-App.git
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;
import fall18_207project.ScoreBoard.ScoreBoardActivity;

public class SimonLevelChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_level_choose);


        SimonMediumButtonListener();
        SimonHardButtonListener();
        SimonSuperHardButtonListener();
        SimonSBButtonListener();

    }


    /**
     * switch to SimonMainActivity and start Medium level game.
     */


    private void SimonMediumButtonListener(){
        Button SimonMediumButton = findViewById(R.id.SimonMediumButton);
        SimonMediumButton.setOnClickListener((v) -> {
            SaveLoad.game_id = "4";
            Intent temp = new Intent(this, SimonMainActivity.class);
            Bundle b = new Bundle();
            b.putInt("level", 1);
            temp.putExtras(b);
            startActivity(temp);


        });
    }


    /**
     * switch to SimonMainActivity and start Hard level game.
     */

    private void SimonHardButtonListener(){
        Button SimonHardButton = findViewById(R.id.SimonHardButton);
        SimonHardButton.setOnClickListener((v) -> {
            SaveLoad.game_id = "5";
            Intent temp = new Intent(this, SimonMainActivity.class);
            Bundle b = new Bundle();
            b.putInt("level", 2);
            temp.putExtras(b);
            startActivity(temp);

        });
    }

    /**
     * switch to SimonMainActivity and start SuperHard level game.
     */

    private void SimonSuperHardButtonListener(){
        Button SimonSuperHardButton = findViewById(R.id.SimonSuperHardButton);
        SimonSuperHardButton.setOnClickListener((v) -> {
            SaveLoad.game_id = "6";
            Intent temp = new Intent(this, SimonMainActivity.class);
            Bundle b = new Bundle();
            b.putInt("level", 3);
            temp.putExtras(b);
            startActivity(temp);

        });
    }

    /**
     * switch to ScoreBoardActivity corresponding to this game.
     */

    private void SimonSBButtonListener(){
        Button SimonSuperHardButton = findViewById(R.id.simonScoreBoardButton);
        SimonSuperHardButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, ScoreBoardActivity.class);
            startActivity(temp);

        });
    }

}

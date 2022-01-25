package fall18_207project.SlidingTile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;

public class STChooseLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stchoose_level);
        Level1ButtonListener();
        Level2ButtonListener();
        Level3ButtonListener();
    }

    /**
     * button for level1 slidingTile
     */
    private void Level1ButtonListener(){
        Button Level1 = findViewById(R.id.Level1);
        Level1.setOnClickListener((v) -> {
            Intent temp = new Intent(this, StartingActivity.class);
            startActivity(temp);
            SaveLoad.game_id = "1";
            Board.NUM_ROWS = 3;
            Board.NUM_COLS = 3;

        });
    }

    /**
     * button for level2 slidingTile
     */
    private void Level2ButtonListener(){
        Button Level2 = findViewById(R.id.Level2);
        Level2.setOnClickListener((v) -> {
            Intent temp = new Intent(this, StartingActivity.class);
            startActivity(temp);
            SaveLoad.game_id = "2";
            Board.NUM_ROWS = 4;
            Board.NUM_COLS = 4;

        });
    }

    /**
     * button for level3 slidingTile
     */
    private void Level3ButtonListener(){
        Button Level3 = findViewById(R.id.Level3);
        Level3.setOnClickListener((v) -> {
            Intent temp = new Intent(this, StartingActivity.class);
            startActivity(temp);
            SaveLoad.game_id = "3";
            Board.NUM_ROWS = 5;
            Board.NUM_COLS = 5;
        });
    }
}

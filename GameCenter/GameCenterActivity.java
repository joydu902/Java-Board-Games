package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fall18_207project.MatchingCard.MCChoosingLevelActivity;
import fall18_207project.Simon.SimonLevelChooseActivity;
import fall18_207project.SlidingTile.STChooseLevelActivity;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class GameCenterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);

        addSlidingTilesButtonListener();
        addSimonButtonListener();
        addCardMatchButtonListener();

    }

    private void addSlidingTilesButtonListener() {
        Button SlidingTilesButton = findViewById(R.id.SlidingTilesButton);
        SlidingTilesButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, STChooseLevelActivity.class);
            startActivity(tmp);
            SaveLoad.gameName = "SlidingTiles";
            SaveLoad.current_game_type = 0;
        });
    }

    private void addSimonButtonListener() {
        Button SimonButton = findViewById(R.id.SimonButton);
        SimonButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, SimonLevelChooseActivity.class);
            startActivity(tmp);
            SaveLoad.gameName = "Simon";
            SaveLoad.current_game_type = 1;
        });
    }
    private void addCardMatchButtonListener() {
        Button CardMatchButton = findViewById(R.id.MatchButton);
        CardMatchButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, MCChoosingLevelActivity.class);
            startActivity(tmp);
            SaveLoad.gameName = "CardMatch";
            SaveLoad.current_game_type = 2;
        });
    }

}

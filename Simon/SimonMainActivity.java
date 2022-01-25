package fall18_207project.Simon;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import fall18_207project.GameCenter.R;

/*
Adapted from:
https://github.com/saifcoding/Simon-Game-App.git
*/

public class SimonMainActivity extends AppCompatActivity {

    View leftTop;
    View leftBottom;
    View rightTop;
    View rightBottom;
    TextView textView;
    public int k = 0, x, sadMusic, highScore = 0, hardness;
    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    final Handler handler = new Handler();
    SimonMovementControler mc = new SimonMovementControler();

    public SimonMainActivity self = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_main);
        sadMusic = sp.load(this, R.raw.sad, 1);
        leftTop = findViewById(R.id.leftTop);
        leftBottom = findViewById(R.id.leftBottom);
        rightTop = findViewById(R.id.rightTop);
        rightBottom = findViewById(R.id.rightBottom);
        textView = (TextView) findViewById(R.id.textView2);

        leftTop.setOnTouchListener(onTouch);
        leftBottom.setOnTouchListener(onTouch);
        rightBottom.setOnTouchListener(onTouch);
        rightTop.setOnTouchListener(onTouch);
        UndoButtonListener();
        ConfirmButtonListener();
        ClearButtonListener();


        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b != null)
            value = b.getInt("level");
        hardness = value;
        System.out.println(hardness);

        handler.postDelayed(
                new Runnable() {public void run() {playGame();}},
                2000 - 500 * hardness);

    }

    /**
     * check whether numberOfClicksEachStage is 0 or not, display
     * messages when numberOfClicksEachStage equals 0.
     */

    private void UndoButtonListener(){
        Button Undo = findViewById(R.id.UndoButton);
        Undo.setOnClickListener((v) -> {
            if (SimonMovementControler.numberOfClicksEachStage != 0) {
                mc.unDo();
            }
            else
            {
                Toast.makeText(this, "Invalid Undo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * call clearArrayOfClicks() method in SimonMovementControler when press ClearBotton
     * in SimonMainActivity.
     */

    private void ClearButtonListener(){
        Button Clear = findViewById(R.id.ClearButton);
        Clear.setOnClickListener((v) -> {
            mc.clearArrayOfClicks();
        });
    }

    /**
     * check whether numberOfClicksEachStage equals numberOfElmentsInMovesArray or not, return
     * message when numberOfClicksEachStage and numberOfElmentsInMovesArray are not equal. if
     * answer correct, next stage. if wrong, end game.
     */

    private void ConfirmButtonListener(){
        Button Confirm = findViewById(R.id.ConfirmButton);
        Confirm.setOnClickListener((v) -> {
            if (SimonMovementControler.numberOfElmentsInMovesArray < SimonMovementControler.numberOfClicksEachStage){
                Toast.makeText(this, "Input too long", Toast.LENGTH_SHORT).show();
            }
            else if  (SimonMovementControler.numberOfElmentsInMovesArray > SimonMovementControler.numberOfClicksEachStage){
                Toast.makeText(this, "Input too short", Toast.LENGTH_LONG).show();
            }
            else if(!mc.Confirm()){
                sp.play(sadMusic, 1, 1, 1, 0, 1f);

                Intent temp = new Intent(self, SimonGameOverActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", (SimonMovementControler.numberOfElmentsInMovesArray - 1));
                temp.putExtras(b);
                startActivity(temp);
            }
            else if(mc.Confirm()){
                SimonMovementControler.numberOfClicksEachStage = 0;
                mc.array_of_clicks = new ArrayList<>();
                if (SimonMovementControler.numberOfElmentsInMovesArray > highScore) {
                    highScore = SimonMovementControler.numberOfElmentsInMovesArray;
                }
                textView.setText("Currect score: " + SimonMovementControler.numberOfElmentsInMovesArray + "           High score: " + highScore);
                final Runnable r = new Runnable() {
                    public void run() {
                        playGame();
                    }
                };
                handler.postDelayed(r, 2000 - 500 * hardness);

            }
        });
    }

    /**
     * add corresponding value to array_of_clicks when touching Views (colors)
     */

    View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                switch (v.getId()) {
                    case R.id.leftTop:
                        x = 1;
                        mc.array_of_clicks.add(x);
                        break;
                    case R.id.rightTop:
                        x = 2;
                        mc.array_of_clicks.add(x);
                        break;
                    case R.id.rightBottom:
                        x = 4;
                        mc.array_of_clicks.add(x);
                        break;
                    case R.id.leftBottom:
                        x = 3;
                        mc.array_of_clicks.add(x);
                        break;
                }
                playSound(v.getId());
                xorMyColor(v);
                SimonMovementControler.numberOfClicksEachStage++;

            }
            return true;

        }
    };

    /**
     * set the sound of each color and playSound.
     */

    private void playSound(int id) {
        //function that play sound according to sound ID
        int audioRes = 0;
        switch (id) {
            case R.id.leftTop:
                audioRes = R.raw.doo;
                break;
            case R.id.rightTop:
                audioRes = R.raw.re;
                break;
            case R.id.rightBottom:
                audioRes = R.raw.mi;
                break;
            case R.id.leftBottom:
                audioRes = R.raw.fa;
                break;
        }
        MediaPlayer p = MediaPlayer.create(this, audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();
    }

    /**
     * function that changes the background color and get it back after 500 milliseconds
     */

    private void xorMyColor(final View v) {
        v.getBackground().setAlpha(51);
        final Runnable r = new Runnable() {
            public void run() {
                v.getBackground().setAlpha(255);
            }
        };
        handler.postDelayed(r, 300);
    }

    /**
     * display the color order that player need to mach.
     */

    public void playGame() {
        mc.appendValueToArray();
        SimonMovementControler.numberOfElmentsInMovesArray++;
        for (k = 0; k < SimonMovementControler.numberOfElmentsInMovesArray; k++) {
            click(k);
        }
    }

    /**
     * Function that clicks one place randomally on the view
     */

    public void click(final int click_index) {
        final Runnable r = new Runnable() {
            public void run() {
                if (mc.array_of_moves[click_index] == 1) {
                    playSound(R.id.leftTop);
                    xorMyColor(leftTop);
                } else if (mc.array_of_moves[click_index] == 2) {
                    playSound(R.id.rightTop);
                    xorMyColor(rightTop);
                } else if (mc.array_of_moves[click_index] == 3) {
                    playSound(R.id.leftBottom);
                    xorMyColor(leftBottom);
                } else {
                    playSound(R.id.rightBottom);
                    xorMyColor(rightBottom);
                }
            }
        };

        handler.postDelayed(r, (2000 - 500 * hardness) * click_index);
    }

}

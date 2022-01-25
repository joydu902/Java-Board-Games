package fall18_207project.MatchingCard;

import java.io.Serializable;
import java.util.Random;

import fall18_207project.GameCenter.R;

/**
 * The MCBoardManger
 */
public class MCBoardManager  implements Serializable  {
    public int numOfElements;
    public int [] allButtonsGraphicLocation; //random all the location
    public int [] allButtonsGraphics;
    private int step;

    public boolean [] flipOrNotList;
    static int sizeOfMat;

    /**
     * @param size the size we want to change
     * change the size
     */
    public static void setSizeOfMat(int size) {
        MCBoardManager.sizeOfMat = size;
    }

    /**
     * @return the size of MCBoardManager
     */
    public static int getSizeOfMat() { return sizeOfMat; }

    /**
     * constructor for MCBoardManger
     */
    public MCBoardManager() {
        int numCol =sizeOfMat;
        int numRow = sizeOfMat;
        this.numOfElements = numCol * numRow;
        this.allButtonsGraphics = new int [numOfElements/2];
        this.flipOrNotList = new boolean[numOfElements];
        if(numRow == 2){
            putAllButtonsGraphicForEasy();
        }else if(numRow == 4){
            putAllButtonsGraphicForMedium();
        }else{
            putAllButtonsGraphicForHard();
        }
        this.allButtonsGraphicLocation = new int [numOfElements];
        shuffleButtonGraphics();
        for (int i =0; i != numOfElements; i ++){this.flipOrNotList[i] = false;}

    }

    /**
     * create and shuffle the allButtonGraphics
     */
    public void shuffleButtonGraphics(){
        Random rand = new Random();

        for (int i = 0; i < numOfElements ; i++ ){
            this.allButtonsGraphicLocation[i] = i % (numOfElements/2);
        }
        for (int i = 0; i < numOfElements ; i++ ){//swap location
            int temp = this.allButtonsGraphicLocation[i];
            if(numOfElements == 4){
                int swapIndex = rand.nextInt(4);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }else if(numOfElements == 16){
                int swapIndex = rand.nextInt(16);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }else{
                int swapIndex = rand.nextInt(24);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }

        }
    }

    /**
     * @return the step of the game
     */
    public int getStep() {
        return step;
    }

    /**
     * increase the step of the game
     */
    public void increaseStep() {
        this.step ++;
    }

    /**
     * set the game in 2*2 model
     */
    public void putAllButtonsGraphicForEasy(){
        this.allButtonsGraphics[0] = R.drawable.button_1;
        this.allButtonsGraphics[1] = R.drawable.button_2;
    }

    /**
     * set the game in 4*4 model
     */
    public void putAllButtonsGraphicForMedium(){
        this.allButtonsGraphics[0] = R.drawable.button_1;
        this.allButtonsGraphics[1] = R.drawable.button_2;
        this.allButtonsGraphics[2] = R.drawable.button_3;
        this.allButtonsGraphics[3] = R.drawable.button_4;
        this.allButtonsGraphics[4] = R.drawable.button_5;
        this.allButtonsGraphics[5] = R.drawable.button_6;
        this.allButtonsGraphics[6] = R.drawable.button_7;
        this.allButtonsGraphics[7] = R.drawable.button_8;
    }

    /**
     * set the game in 6*6 model
     */
    public void putAllButtonsGraphicForHard(){
        this.allButtonsGraphics[0] = R.drawable.button_1;
        this.allButtonsGraphics[1] = R.drawable.button_2;
        this.allButtonsGraphics[2] = R.drawable.button_3;
        this.allButtonsGraphics[3] = R.drawable.button_4;
        this.allButtonsGraphics[4] = R.drawable.button_5;
        this.allButtonsGraphics[5] = R.drawable.button_6;
        this.allButtonsGraphics[6] = R.drawable.button_7;
        this.allButtonsGraphics[7] = R.drawable.button_8;
        this.allButtonsGraphics[8] = R.drawable.button_9;
        this.allButtonsGraphics[9] = R.drawable.button_10;
        this.allButtonsGraphics[10] = R.drawable.button_11;
        this.allButtonsGraphics[11] = R.drawable.button_12;
        this.allButtonsGraphics[12] = R.drawable.button_13;
        this.allButtonsGraphics[13] = R.drawable.button_14;
        this.allButtonsGraphics[14] = R.drawable.button_15;
        this.allButtonsGraphics[15] = R.drawable.button_16;
        this.allButtonsGraphics[16] = R.drawable.button_17;
        this.allButtonsGraphics[17] = R.drawable.button_18;
    }
}

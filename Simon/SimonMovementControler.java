package fall18_207project.Simon;


/*
Adapted from:
https://github.com/saifcoding/Simon-Game-App.git
*/

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SimonMovementControler {
    final static int MAX_LENGTH = 1000;
    public  int array_of_moves[] = new int[MAX_LENGTH];
    public  List<Integer> array_of_clicks = new ArrayList<>();

    public static int numberOfElmentsInMovesArray = 0, k = 0, numberOfClicksEachStage = 0;
    static Random r = new Random();

    /**
     * add random number to the first free position in the array_of_moves.
     */

    public void appendValueToArray() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (array_of_moves[i] == 0) {
                array_of_moves[i] = generateRandomNumber();
                break;
            }
        }
    }

    /**
     * remove the last element in the array_of_clicks.
     */

    public void unDo(){

        numberOfClicksEachStage -= 1;
        array_of_clicks.remove(array_of_clicks.size() - 1);
    }

    /**
     * generate random number between 1 and 4
     */

    private static int generateRandomNumber() {
        return r.nextInt(4) + 1;
    }


    /**
     * reset the game to initial state
     */

    public  void clear(){
        for (int i = 0; i < MAX_LENGTH; i++) {
            array_of_moves[i] = 0;
        }
            numberOfClicksEachStage = 0;
            numberOfElmentsInMovesArray = 0;
            array_of_clicks = new ArrayList<>();

    }

    /**
     * reset array_of_clicks as a empty ArrayList.
     */

    public  void clearArrayOfClicks(){
        array_of_clicks = new ArrayList<>();
        numberOfClicksEachStage = 0;
    }

    /**
     *
     * @return true if the elements in array_of_clicks is equal to the non zero element in
     * array_of_moves.
     */

    public boolean Confirm(){
        ArrayList<Integer> array = new ArrayList<>();
        if (array_of_clicks.size() != getvalidnumber()){
            return false;
        }
        for(int i = 0; i < array_of_clicks.size(); i++){
            array.add((Integer)array_of_moves[i]);
        }
        return array.equals(array_of_clicks);
    }

    /**
     *
     * @return the number of elements before the first 0 element in array_of_moves.
     */

    public int getvalidnumber() {
        int result = 0;
        int i = 0;
        while (i < array_of_moves.length && array_of_moves[i] != 0) {
            result = i + 1;
            i++;
        }
        return result;
    }

}

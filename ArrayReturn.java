import java.io.Serializable;

public class ArrayReturn implements Serializable {
    private int[][] arrayReturn;

    public ArrayReturn(int[][] arrayReturn){ //Constructor for the ArrayReturn class.
        this.arrayReturn = arrayReturn; //The arrayReturn is set to the array that is passed to the constructor.
    }

    public int[][] getArray(){ //Method for getting the array.
        return arrayReturn;
    }

}

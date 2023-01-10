import java.io.Serializable;

public class ArrayReturn implements Serializable {
    private int[][] arrayReturn;

    public ArrayReturn(int[][] arrayReturn){
    this.arrayReturn = arrayReturn;
    }

    public int[][] getArray(){
        return arrayReturn;
    }
}

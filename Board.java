import java.lang.reflect.Array;

public class Board {


    public int x_axis;
    public int y_axis;

    int[][] map;

    public void Board(int y_axis, int x_axis){
        this.y_axis=y_axis;
        this.x_axis=x_axis;
        this.map=new int[x_axis][y_axis];
    }
    public void Board(int size){
        this.y_axis=size;
        this.x_axis=size;
        map =new int[size][size];
    }

    public void iniGame(){
        map[x_axis/2][y_axis/2]=1;
        map[x_axis/2+1][y_axis/2]=2;
        map[x_axis/2+1][y_axis/2+1]=1;
        map[x_axis/2][y_axis/2+1]=2;
    }


    public int[][] legalSpots(int playerTurn){
        for (int i = 0; i<x_axis;i++){
            for (int j = 0; j<y_axis;j++){
                if(map[i][j]==0){

                }else;
            }
        }
        return map;
    }







}

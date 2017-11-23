// DieSimulator.java
// By: John Farina

import java.util.Random;

public class DieSimulator {
    public static void main(String[] args) {

        int maxTries = 7776;
        int setYahtz=0, rowYahtz=0, colYahtz=0, diagYahtz=0, totalYahtz=0;
        int setRows=5, setCols=5;
        int[][] set = new int [setRows][setCols];
        Random number = new Random();

        for(int i=0;i<maxTries;i++){

            for (int row=0; row < setRows; row++){
                for (int col=0; col < setCols; col++){
                    set[row][col] = number.nextInt(6)+1;
                }
            }

            setYahtz=0;

            for (int row=0; row < setRows; row++) {
                if (set[row][0] == set[row][1] && set[row][0] == set[row][2] && set[row][0] == set[row][3] && set[row][0] == set[row][4]) {
                    setYahtz++;
                    rowYahtz++;
                    totalYahtz++;
                }
            }
            for (int col=0; col < setCols; col++){
                if(set[0][col]==set[1][col] && set[0][col]==set[2][col] && set[0][col]==set[3][col] && set[0][col]==set[4][col]){
                    setYahtz++;
                    colYahtz++;
                    totalYahtz++;
                }
            }

            if(set[0][0]==set[1][1] && set[0][0]==set[2][2] && set[0][0]==set[3][3] && set[0][0]==set[4][4]){
                setYahtz++;
                diagYahtz++;
                totalYahtz++;
            }

            if(set[0][4]==set[1][3] && set[0][4]==set[2][2] && set[0][4]==set[3][1] && set[0][4]==set[4][0]){
                setYahtz++;
                diagYahtz++;
                totalYahtz++;
            }

            System.out.println("Set #"+ (i+1) +":");
            for (int row=0; row < setRows; row++){
                System.out.print("Die"+ (row+1) +": ");
                for (int col=0; col < setCols; col++){
                    System.out.print(set[row][col]+" ");
                }
                System.out.println();
            }
            System.out.println("Set of Yahtzees: "+setYahtz+"\n");
        }

        System.out.println("Total Horizontal Yahtzees: "+rowYahtz);
        System.out.println("Total Vertical Yahtzees: "+colYahtz);
        System.out.println("Total Diagonal Yahtzees: "+diagYahtz);
        System.out.print("Total Yahtzees: "+totalYahtz);

    }
}
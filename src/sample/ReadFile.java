package sample;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile { //NACITAVANIE NUMERICKEJ MAPY
    private String[] line = new String[2];
    private String velkostX,velkostY;
    private int counter =0;
    private String[][] matrix;

    ReadFile(String fileName){
        try {
            File myObj = new File("src/sample/keyMaps/"+fileName+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (counter ==0){
                    line=data.split(" ");

                    velkostX= line[1];
                    velkostY=line[0];
                    System.out.println(velkostX);
                    System.out.println(velkostY);

                    matrix=new String[Integer.parseInt(velkostY)][Integer.parseInt(velkostX)];
                    System.out.printf("Velkost matrixu: %s %s .\n",matrix.length,matrix[0].length);
                }else{
                    matrix[counter-1]=data.split(" ");
                    /*for(int i=0;i<matrix.length;i++){
                        System.out.printf("%s ",matrix[counter-1][i]);
                    }
                    System.out.println();*/

                }



                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Integer[][] getMatrix(){
        Integer[][] superMatrix = new Integer[Integer.parseInt(velkostY)][Integer.parseInt(velkostX)];

        for(int i=0;i<superMatrix.length;i++){
            for(int j=0;j<superMatrix[0].length;j++){
                superMatrix[i][j]=Integer.parseInt(matrix[i][j]);
            }
        }
        return superMatrix;
    }

}
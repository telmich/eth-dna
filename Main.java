/* Hints / Questions:
 * - stderr printing?
 */

import java.io.*;
import java.util.*; /* Scanner, Arraylist */


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        
        while (s.hasNextLine()){
            list.add(s.nextLine());
        }

        /* for(int i = 1; i < list.size(); i++) {
            System.out.println("Line " + i + ": " + list.get(i));
            } */
        
        Exercise e1 = new Exercise(list);
        
        e1.aufgabe43();
    }

}

class Exercise {

    private int numTests;
    ArrayList <String> inputlist;
   
    public Exercise(ArrayList <String> list) {
        this.inputlist = list;      /* input given to the program          */
    }

    public void aufgabe43()
    {
        int index = 0;

        /* number of tests */
        this.numTests = Integer.parseInt(this.inputlist.get(0)); /* array[0] */

        index = 1; /* first matrix dimension */
        while (index < this.inputlist.size()) { /* go through all matrices */
            index = calculateMatrix(index);
        }
        
    }
    private int calculateMatrix(int index) {
        int dimension;
        int [][]matrix;
   
        dimension = Integer.parseInt(this.inputlist.get(index));
        System.err.println("Matrix dim: " + dimension + "x" + dimension);

        /* create empty int matrix */
        matrix = new int[dimension][dimension];

        /* Setup index to row containing the first elements */
        index++;

        /* Convert lines to int Matrix */
        for(int i = 0; i < dimension; i++, index++) { /* increment both index and row number */
            
            String line = this.inputlist.get(index);
            String []elements = line.split(" ");

            /* save row into matrix */
            for(int j = 0; j < dimension; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
                System.err.print("" + matrix[i][j] + " ");
            }
            /* Print newline after each row */
            System.err.println("");
        }

        /* Set index to next line containing dimension */
        return index;
    }

}

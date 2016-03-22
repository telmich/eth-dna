/* Hints / Questions:
 * - https://en.wikipedia.org/wiki/Maximum_subarray_problem
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
    int [][]matrix;
    int [][]prefix_matrix;
    int current_index;
   
    public Exercise(ArrayList <String> list) {
        this.inputlist = list;      /* input given to the program          */
        current_index = 0;
    }

    public void aufgabe43()
    {
        /* number of tests */
        this.numTests = Integer.parseInt(this.inputlist.get(this.current_index)); /* array[0] */

        this.current_index = 1; /* first matrix dimension */
        
        while (this.current_index < this.inputlist.size()) { /* go through all matrices */
            createIntMatrix();
            createPrefixMatrix();
            
            // System.out.println("" + maxMatrixSum());
            System.out.println("" + findMaximumSubMatrix(this.matrix));
            
        }
    }

    private void createIntMatrix() {
        int dimension = Integer.parseInt(this.inputlist.get(this.current_index));
        System.err.println("Matrix dim: " + dimension + "x" + dimension);

        /* create empty int matrix */
        this.matrix = new int[dimension][dimension];

        /* Setup index to row containing the first elements */
        this.current_index++;

        /* Convert lines to int Matrix */
        for(int i = 0; i < dimension; i++, this.current_index++) { /* increment both index and row number */
            
            String line = this.inputlist.get(this.current_index);
            String []elements = line.split(" ");

            /* save row into matrix */
            for(int j = 0; j < dimension; j++) {
                this.matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }
        
        // Debug
        printMatrix("Intmatrix", this.matrix);
    }
    
    /* Create Matrix with Prefix values */
    private void createPrefixMatrix() {
        int dimension = this.matrix.length;
        this.prefix_matrix = new int[dimension][dimension];

        /* Initialise first row of prefix matrix */
        for(int i=0; i < dimension; i++) {
            this.prefix_matrix[0][i] = this.matrix[0][i];
        }
                                                  
        /* Calculate sums starting from the 2nd row (index = 1) */
        for(int i = 1; i < dimension; i++) {        
            for(int j = 0; j < dimension; j++) { /* do this for every column */
                this.prefix_matrix[i][j] = this.prefix_matrix[i-1][j] + this.matrix[i][j];
            }
        }
        
        // Debug
        printMatrix("Prefixmatrix", this.prefix_matrix);
    }

    private int maxMatrixSum()
    {
        int dimension          = this.matrix.length;
        int [] prefix          = new int[dimension];
        int bestSolution;

        if(matrix[0][0] > 0)
            bestSolution = matrix[0][0];
        else
            bestSolution = 0;

        for (int i = 0; i < dimension; i++) {              /* ok */
            for(int j = 0; j < dimension; j++) {           /* ok */
                int [] maxSubSeq       = new int[dimension];
                
                for(int a = 0; a < dimension; a++) {
                    int z;

                    /* Take the previous row, if possible, else take 0th row */
                    if (i >= 1) {
                        z = i-1;
                    } else {
                        z = 0;
                    }

                    prefix[a] = this.prefix_matrix[j][a] - this.prefix_matrix[z][a];
                }
                
                int sum = 0;
                for(int k = 0; k < dimension; k++) {
                    int current = prefix[k];

                    if((sum + current) > current)
                        sum = sum + current;
                    else
                        sum = current;

                    if(sum > bestSolution)
                        bestSolution = sum;
                }
            }
        }
       
        return bestSolution;
    }
    
    private void printMatrix(String title, int matrixToPrint[][]) {
        int dimension = matrixToPrint.length;

        System.err.println(title);
        for(int i=0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                System.err.print("" + matrixToPrint[i][j] + " ");
            }
            System.err.println(""); /* \n after every row */
        }
    }

    public int findMaximumSubMatrix(int[][] matrix){
        int dim = matrix.length;
        //computing the vertical prefix sum for columns
        int[][] ps = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (j == 0) {
                    ps[j][i] = matrix[j][i];
                } else {
                    ps[j][i] = matrix[j][i] + ps[j - 1][i];
                }
            }
        }

        int maxSum = matrix[0][0];
        int top = 0, left = 0, bottom = 0, right = 0; 

        //Auxiliary variables 
        int[] sum = new int[dim];
        int[] pos = new int[dim];
        int localMax;                        

        for (int i = 0; i < dim; i++) {
            for (int k = i; k < dim; k++) {
                // Kandane over all columns with the i..k rows
                reset(sum);
                reset(pos);
                localMax = 0;
                //we keep track of the position of the max value over each Kandane's execution
                // notice that we do not keep track of the max value, but only its position
                sum[0] = ps[k][0] - (i==0 ? 0 : ps[i-1][0]);
                for (int j = 1; j < dim; j++) {                    
                    if (sum[j-1] > 0){
                        sum[j] = sum[j-1] + ps[k][j] - (i==0 ? 0 : ps[i-1][j]);
                        pos[j] = pos[j-1];
                    }else{
                        sum[j] = ps[k][j] - (i==0 ? 0 : ps[i-1][j]);
                        pos[j] = j;
                    }
                    if (sum[j] > sum[localMax]){
                        localMax = j;
                    }
                }//Kandane ends here

                if (sum[localMax] > maxSum){
                    /* sum[localMax] is the new max value
                       the corresponding submatrix goes from rows i..k.
                       and from columns pos[localMax]..localMax
                    */
                    maxSum = sum[localMax];
                    top = i;
                    left = pos[localMax];
                    bottom = k;
                    right = localMax;
                }      
            }
        }
        System.err.println("Max SubMatrix determinant = " + maxSum);
        //composing the required matrix
        int[][] output = new int[bottom - top + 1][right - left + 1];
        for(int i = top, k = 0; i <= bottom; i++, k++){
            for(int j = left, l = 0; j <= right ; j++, l++){                
                output[k][l] = matrix[i][j];
            }
        }
        return maxSum;
    }

    private void reset(int[] a) {
        for (int index = 0; index < a.length; index++) {
            a[index] = 0;
        }
    }  

}

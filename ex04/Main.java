/* Hints:
 * - https://en.wikipedia.org/wiki/Maximum_subarray_problem
 * - http://stackoverflow.com/questions/2643908/getting-the-submatrix-with-maximum-sum
 */

import java.io.*;
import java.util.*; /* Scanner, Arraylist */

class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();

        while (s.hasNextLine()){
            list.add(s.nextLine());
        }
        /* Do not create any list if there was no input */
        if(list.size() == 0) System.exit(0);

        Exercise e1 = new Exercise(list);

        e1.run();
        System.exit(0);
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

    public void run()
    {
        /* number of tests */
        this.numTests = Integer.parseInt(this.inputlist.get(this.current_index)); /* array[0] */

        /* Prevent errors in case number of tests is 0 */
        if(this.numTests <= 0) return;

        this.current_index = 1; /* first matrix dimension */

        while (this.current_index < this.inputlist.size()) { /* go through all matrices */
            int maximum;

            if(!createIntMatrix()) { /* skip zero dimension matrices */
                System.out.println("0");
                current_index++;
                continue;
            }

            createPrefixMatrix();
            maximum = maxMatrixSum();

            System.out.println("" + maximum);
        }
    }

    private boolean createIntMatrix() {
        /* Dimension only used locally - length used to derive in other methods */
        int dimension = Integer.parseInt(this.inputlist.get(this.current_index));

        if(dimension <= 0) {
            return false;
        }

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
        return true;
    }

    /* Create Matrix with Prefix values, used in Exercise 4 */
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
    }

    private int maxMatrixSum()
    {
        int dimension = this.matrix.length;
        int bestSolution;

        if(matrix[0][0] > 0)
            bestSolution = matrix[0][0];
        else
            bestSolution = 0;

        for (int i = 0; i < dimension; i++) {              /* ok */
            for(int j = i; j < dimension; j++) {           /* ok */
                int [] maxSubSeq       = new int[dimension];
                int localMax           = 0;
                int z;

                /* Take the previous row, if possible, else take 0th row */
                if (i == 0) {
                    z = 0;
                } else {
                    z = this.prefix_matrix[i-1][0];
                }

                /* Kandane */
                maxSubSeq[0] = this.prefix_matrix[j][0] - z;
                for(int k = 1; k < dimension; k++) {
                    int z2;
                    if (i == 0) {
                        z2 = 0;
                    } else {
                        z2 = this.prefix_matrix[i-1][k];
                    }

                    /* This one is added in any case */
                    maxSubSeq[k] = this.prefix_matrix[j][k] - z2;

                    if(maxSubSeq[k-1] > 0) {
                        maxSubSeq[k] += maxSubSeq[k-1]; /* add previous sum */
                    } else {
                        maxSubSeq[k] += 0; /* do not add anything from before */
                    }

                    if(maxSubSeq[k] > maxSubSeq[localMax])
                        localMax = k;
                }
                if(maxSubSeq[localMax] > bestSolution)
                    bestSolution = maxSubSeq[localMax];
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
}

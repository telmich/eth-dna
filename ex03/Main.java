/* Hints:
 * - https://en.wikipedia.org/wiki/Maximum_subarray_problem
 * - http://stackoverflow.com/questions/2643908/getting-the-submatrix-with-maximum-sum
 */

import java.io.*;
import java.util.*; /* Scanner, Arraylist */

class Main {
    public static void main(String[] args) throws IOException {
        //        Exercise.cli();

        int zahlen[] = {1,2,2,2,2,3,4,18,22,33,44};
        int zahlen_c[] = {1,2,2,11,15,18,22,33,44};


        Aufgabe32 a = new Aufgabe32();


        a.aufgabe32a(zahlen, 8); /* should fail */
        a.aufgabe32b(zahlen, 8); /* should be faster */

        a.aufgabe32a(zahlen, 35); /* should succeed */
        a.aufgabe32b(zahlen, 35); /* should be faster */

        a.aufgabe32c(zahlen_c);
    }
}

class Exercise {
    private int []values;

    private int [][]fiverGroups;
    private int numGroups;
    private int sizeLastGroup;
    private int targetElement;


    public Exercise(int [] values) {
        this.values        = values;
        this.numGroups     = values.length/5 + 1;
        this.sizeLastGroup = values.length % 5;
        this.targetElement = (int) Math.ceil(values.length/2);
    }

    private void median()
    {
        int i = 0;

        while(i < values.length) {
            int []median = new int[5];



            /* create median group */
            for(int j=0; j < 5; j++) {


            }
        }
    }

    private void groupsOfFive()
    {
        System.err.println("Creating groups: " + this.numGroups + " lastgroup: " + this.sizeLastGroup);

        fiverGroups = new int[numGroups][5];

        for(int i=0; i < numGroups; i++) {

            ;
        }
    }


    public static void cli () throws IOException
    {
        Scanner s = new Scanner(System.in);

        int numTests = s.nextInt();
        for(int i=0; i < numTests; i++) {
            int numValues = s.nextInt();
            int []values;

            System.err.println("Test " + i + " values: " + numValues);

            values = new int[numValues];
            for(int j=0; j < numValues; j++) {
                values[j] = s.nextInt();
            }

            Exercise e1 = new Exercise(values);
            e1.run();
        }
    }

    public void run()
    {
        groupsOfFive();
    }
}

class Aufgabe32 {

    boolean found = false;
    int a, b;
    int i;
    int j;
    int steps;

    public Aufgabe32 ()
    {

    }

    private void report(boolean found, int a, int b, int c, int steps)
    {
        if(found)
            System.out.println("a = " + a + " b = " + b + " c = " + c + " steps: " + steps );
        else
            System.out.println("c = " + c + " nicht gefunden"  + " steps: " + steps);
    }

    public boolean aufgabe32a(int []zahlen, int summeZuSuchen)
    {
        found = false;
        a = b = i = j = steps = 0;

        /* Naive solution: try to pair every number with every number and exit
           in case the sum was found
        */

        for(i=0; i < zahlen.length; i++) {
            for(j=i+1; j < zahlen.length; j++) {
                int summe = zahlen[i] + zahlen[j];
                steps++;

                if(summe == summeZuSuchen) {
                    found = true;
                    break;
                }
            }
            if(found) {
                a = zahlen[i];
                b = zahlen[j];
                break;
            }
        }
        report(found, a, b, summeZuSuchen, steps);

        return found;
    }


    public boolean aufgabe32b(int []zahlen, int summeZuSuchen)
    {
        found = false;
        a = b = i = j = steps = 0;

        /* Idea:
           - search the first item less than the search sum from backwards
           - search from the beginning a matching item to create the sum, abort if found
             the previously marked element
           - repeat the previous steps with one element shifted to the left until it is the 2nd element
        */


        /* try all numbers from the right as possible a */
        for(i=zahlen.length-1; i >= 0; i--) {
            if(zahlen[i] > summeZuSuchen) continue;

            /* Try to build a sum by using the first numbers */
            for(j=0; j < i; j++) {
                int summe = zahlen[i] + zahlen[j];
                steps++;

                if(summe == summeZuSuchen) {
                    found = true;
                    break;
                }
                steps++;
            }
            if(found) {
                a = zahlen[i];
                b = zahlen[j];
                break;
            }

        }
        report(found, a, b, summeZuSuchen, steps);
        return found;

    }

    public void aufgabe32c(int []zahlen) {
        boolean found_c = false;

        /* Try all values as a possible c */
        for(int i = 0; i < zahlen.length; i++) {
            found_c = aufgabe32a(zahlen, zahlen[i]);
            // found_c = aufgabe32b(zahlen, zahlen[i]);

            if(found_c) break;
        }
    }
}

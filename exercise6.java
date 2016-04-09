/* Generic boilerplate */
import java.io.*;
import java.util.*; /* Scanner, Arraylist */

/* TODO: Prinzip dynamischer Programmierung auf Marssondenweg anwenden */


public class exercise6 {
    public static void main(String[] args) throws IOException {

        /* DP tests */
        DP dp1 = new DP();
        int fib = Integer.parseInt(args[0]);

        System.out.println("" + dp1.fib_dict(fib));
        System.out.println("" + dp1.fib_iter(fib));

        int [][] steine0 = { /* max = 8 */
            { 0, 1, 1 },
            { 1, 3, 4 },
            { 4, 2, 0 }
        };

        int [][] steine0_2 = { /* max = 12 */
            { 0, 1, 1 },
            { 5, 3, 4 },
            { 4, 2, 0 }
        };

        int [][] steine1 = {   /* max = 25 */
            { 0, 1, 1, 1, 1 },
            { 4, 1, 1, 1, 1 },
            { 1, 9, 1, 1, 1 },
            { 1, 1, 4, 1, 1 },
            { 1, 1, 1, 5, 0 }
        };

        int [][] steine2 = {
            { 0,   9,  2,  5, 11, 8 },
            { 17, 21, 32,  5, 15, 3 },
            {  2,  2,  3,  8,  1, 5 },
            {  8,  2,  8, 11, 15, 9 },
            {  0,  5,  3, 10, 4,  0 }
        };

        int [][] steine3 = { /* optimal: O O S S O S S */
            { 0, 2, 2, 2, 2 },
            { 1, 1, 1, 1, 2 },
            { 1, 1, 99, 1, 2 },
            { 1, 1, 1, 1, 2 },
            { 1, 1, 1, 1, 0 }
        };

        MarsMission m0 = new MarsMission(steine0);
        m0.findAndPrint();

        MarsMission m02 = new MarsMission(steine0_2);
        m02.findAndPrint();

        MarsMission m1 = new MarsMission(steine1);
        m1.findAndPrint();
        MarsMission m2 = new MarsMission(steine2);
        m2.findAndPrint();

    }
}

class DP {
    int max;
    int []fibnumber;

    public DP() {
        max = 1000;
        fibnumber = new int[max];
        for(int i = 0; i < fibnumber.length; i++) {
            fibnumber[i] = -1;
        }
        fibnumber[0] = 0;
        fibnumber[1] = 1;
    }

    public int fib_iter(int num)
    {
        int i, x1, x2, result = 0;

        switch(num) {
        case 0: return 0;
        case 1: return 1;
        }

        x1 = 0;
        x2 = 1;
        for(i=2; i <= num; i++) {
            result = x1 + x2;
            x1 = x2;
            x2 = result;
        }
        return result;
    }

    public int fib_dict(int num)
    {
        int result = -1;

        /* still neeeds to be calculated ? */
        if(fibnumber[num] == -1) {
            fibnumber[num] = fib_dict(num - 1) + fib_dict(num -2);
        }

        return fibnumber[num];
    }
}


/* Infos
 * 7.4 Längste gemeinsam Teilfolge
 * 10.2 Editierdistanz
 */

/* Dynamische Programmierung: 4-Damen-Problem */


/* Aufgabe 6.1: Marsmission

 * Ost/Süd Bewegungen möglich
 * ges.: Weg mit maximalen Wert
 * Methode: odynamischen Programmierung
 * Eingabe: mxn Matrix, A[1,1] = A[m,n] = 0


Beispiel:

S 9 2 5 11 8
17 21 32 5 15 3
2 2 3 8 1 5
8 2 8 11 15 9
0 5 3 10 4 Z

 (1, 1) → (2, 1) → (2, 2) → (2, 3) → (2, 4) → · · · → (5, 6).


- Need to save / store the way (x,y coordinates)
- There are multiple optimal paths possible
- There are at maximum two choices per path possible
-


1) Definition der DP-Tabelle: Welche Dimensionen hat die Tabelle? Was ist die Bedeutung
jedes Eintrags?

Tabelle enthält Werte für den zurückgelegten Weg, kommen von einem spezifischen Feld
Beispiel (Tabelle Aufgabe)

  Start -> S -> S  = 0 + 17 + 2 = 19
  Start -> S -> O -> O = 0 + 17 + 21 + 32 = ...

Wir müssen nur sämtliche möglichen Weg abbilden können, somit alle S/O-Kombinationen:


   O------------------->
   S     9
   | 17 26
   | 19
   |
   v


O -> S ist das gleiche wie S -> O!

S S O = 17 + 2 + 2 = 21
O S S = 9 + 21 + 2 = 32

Müssen uns merken, von welcher Richtung das Maximum erreicht wurde:

S S O = 17 + 2 + 2 = 21 (O)
O S S = 9 + 21 + 2 = 32 (S)

Können das Maximum immer überschreiben
In jedem Feld 2 Arme aufmachen für S/O.


Tabelle der Grösse m * n * 2 [Maximum + Richtung]


2) Berechnung eines Eintrags: Wie berechnet sich ein Eintrag aus den Werten von anderen
Einträgen? Welche Einträge hängen nicht von anderen Einträgen ab?

Der Eintrag in der Tabelle benötigt den Wert des bisherigen Weges.

3) Berechnungsreihenfolge: In welcher Reihenfolge kann man die Einträge berechnen, so dass
die jeweils benötigten anderen Einträge bereits vorher berechnet wurden?



4) Auslesen der Lösung: Wie lässt sich die Lösung am Ende aus der Tabelle auslesen?



*/

class MarsMission {
    int [][] input;
    int [][][] solution;

    static final int dirundef = -1;
    static final int east = 0;
    static final int south = 1;

    /* aliases for easier handling */
    int maxeast;
    int maxsouth;


    public MarsMission(int [][]input) {
        this.input = input;


        maxeast = input.length;
        maxsouth = input[0].length;

        solution = new int[maxeast][maxsouth][3];

        for(int i=0; i < solution.length; i++) {
            for(int j=0; j < solution[0].length; j++) {
                solution[i][j][0] = -1; /* the maximum value */
                solution[i][j][1] = -1; /* previous horizontal */
                solution[i][j][2] = -1; /* previous vertical */

            }
        }
        solution[0][0][0] = 0;
        solution[maxeast-1][maxsouth-1][0] = 0;

    }

    public void findAndPrint()
    {
        System.out.println("------------");
        findSolution();
        printInput();
        printSolution();
    }

    /* returns matrix with coordinates */
    public void findSolution () {
        for(int i=0; i < solution.length; i++) {
            for(int j=0; j < solution[0].length; j++) {
                go(i,j, i+1, j);
                go(i,j, i, j+1);
            }
        }
    }

    private void go(int horiz_old, int vert_old, int horiz_new, int vert_new)
    {
        int new_field_current_value, new_field_new_value, this_field_value;

        /* Don't go any further */
        if(horiz_new == maxeast || vert_new == maxsouth) return;


        this_field_value = solution[horiz_old][vert_old][0];

        new_field_current_value = solution[horiz_new][vert_new][0];

        new_field_new_value = this_field_value + input[horiz_new][vert_new];

        /* Only update if new path is better */
        if(new_field_new_value > new_field_current_value) {
            solution[horiz_new][vert_new][0] = new_field_new_value;
            solution[horiz_new][vert_new][1] = horiz_old;
            solution[horiz_new][vert_new][2] = vert_old;
        }
    }

    public void printSolution()
    {
        System.out.println("Values:");
        for(int i=0; i < solution.length; i++) {
            for(int j=0; j < solution[0].length; j++) {
                System.out.print("" + solution[i][j][0] + " ");
            }
            System.out.println("");
        }

        System.out.println("Reverse path:");
        int i = maxeast-1, j = maxsouth-1;
        int i_previous, j_previous;

        while(i != 0 || j != 0) {
            i_previous = solution[i][j][1];
            j_previous = solution[i][j][2];

            System.out.print("(" + i_previous + ", ");
            System.out.println("" + j_previous + ") ");

            i = i_previous;
            j = j_previous;
        }
    }

    public void printInput()
    {
        System.out.println("Input: ");
        for(int i=0; i < input.length; i++) {
            for(int j=0; j < input[0].length; j++) {
                System.out.print("" + input[i][j] + " ");
            }
            System.out.println("");
        }
    }

}




/* Aufgabe 6.2: Selbstanordnende Listen.

Gesucht: #Schlüsselvergleiche

A −→ L −→ G −→ O −→ R −→ I −→ T −→ H −→ M −→ U −→ S

Zugriff: ’R’, ’A’, ’I’, ’S’, ’T’, ’A’, ’A’, ’G’

’R’: 5
  R -> A −→ L −→ G −→ O  −→ I −→ T −→ H −→ M −→ U −→ S
’A’: 2
  A-> R -> L −→ G −→ O  −→ I −→ T −→ H −→ M −→ U −→ S
’I’: 6
  I -> A-> R -> L −→ G −→ O −→ T −→ H −→ M −→ U −→ S
’S’: 11
  S -> I -> A-> R -> L −→ G −→ O −→ T −→ H −→ M −→ U
’T’: 8
  T ->  S -> I -> A-> R -> L −→ G −→ O  −→ H −→ M −→ U
’A’: 4
  A -> T ->  S -> I -> R -> L −→ G −→ O  −→ H −→ M −→ U
’A’: 1
  A -> T ->  S -> I -> R -> L −→ G −→ O  −→ H −→ M −→ U
’G’: 7
  G -> A -> T ->  S -> I -> R -> L −→ O  −→ H −→ M −→ U

Total: 44

*/

/* 6.3: 3 verschiedene Gegenstände: 1 CHF

erste Zahl enthält die Anzahl v erschiedener Arten von Gegenständen n ≤ 5000
n Ganzzahlen ai ≤ 2000 mit 1 ≤ i ≤ n,

 */

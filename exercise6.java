/* Generic boilerplate */
import java.io.*;
import java.util.*; /* Scanner, Arraylist */

/* TODO: Prinzip dynamischer Programmierung auf Marssondenweg anwenden */


class exercise6 {
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

        Main mymain = new Main();
        mymain.main(args);

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
 * 7.4 Laengste gemeinsam Teilfolge
 * 10.2 Editierdistanz
 */

/* Dynamische Programmierung: 4-Damen-Problem */


/* Aufgabe 6.1: Marsmission

 * Ost/Sued Bewegungen moeglich
 * ges.: Weg mit maximalen Wert
 * Methode: odynamischen Programmierung
 * Eingabe: mxn Matrix, A[1,1] = A[m,n] = 0


Beispiel:

S 9 2 5 11 8
17 21 32 5 15 3
2 2 3 8 1 5
8 2 8 11 15 9
0 5 3 10 4 Z

 (1, 1) -> (2, 1) -> (2, 2) -> (2, 3) -> (2, 4) -> 54 -> (5, 6).


- Need to save / store the way (x,y coordinates)
- There are multiple optimal paths possible
- There are at maximum two choices per path possible
-


1) Definition der DP-Tabelle: Welche Dimensionen hat die Tabelle? Was ist die Bedeutung
jedes Eintrags?

Tabelle enthaelt Werte fuer den zurueckgelegten Weg, kommen von einem spezifischen Feld
Beispiel (Tabelle Aufgabe)

  Start -> S -> S  = 0 + 17 + 2 = 19
  Start -> S -> O -> O = 0 + 17 + 21 + 32 = ...

Wir muessen nur saemtliche moeglichen Weg abbilden koennen, somit alle S/O-Kombinationen:


   O------------------->
   S     9
   | 17 26
   | 19
   |
   v


O -> S ist das gleiche wie S -> O!

S S O = 17 + 2 + 2 = 21
O S S = 9 + 21 + 2 = 32

Muessen uns merken, von welcher Richtung das Maximum erreicht wurde:

S S O = 17 + 2 + 2 = 21 (O)
O S S = 9 + 21 + 2 = 32 (S)

Koennen das Maximum immer ueberschreiben
In jedem Feld 2 Arme aufmachen fuer S/O.


Tabelle der Groesse m * n * 2 [Maximum + Richtung]


2) Berechnung eines Eintrags: Wie berechnet sich ein Eintrag aus den Werten von anderen
Eintraegen? Welche Eintraege haengen nicht von anderen Eintraegen ab?

Der Eintrag in der Tabelle benoetigt den Wert des bisherigen Weges.

3) Berechnungsreihenfolge: In welcher Reihenfolge kann man die Eintraege berechnen, so dass
die jeweils benoetigten anderen Eintraege bereits vorher berechnet wurden?



4) Auslesen der Loesung: Wie laesst sich die Loesung am Ende aus der Tabelle auslesen?



- Algorithmus: See MarsMission, findSolution.
- Laufzeit: m*n*2


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

        System.out.print("(" + i + ", ");
        System.out.println("" + j + ") ");

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

Gesucht: #Schluesselvergleiche

A --> L --> G --> O --> R --> I --> T --> H --> M --> U --> S

Zugriff: 'R', 'A', 'I', 'S', 'T', 'A', 'A', 'G'

'R': 5
  R -> A --> L --> G --> O  --> I --> T --> H --> M --> U --> S
'A': 2
  A-> R -> L --> G --> O  --> I --> T --> H --> M --> U --> S
'I': 6
  I -> A-> R -> L --> G --> O --> T --> H --> M --> U --> S
'S': 11
  S -> I -> A-> R -> L --> G --> O --> T --> H --> M --> U
'T': 8
  T ->  S -> I -> A-> R -> L --> G --> O  --> H --> M --> U
'A': 4
  A -> T ->  S -> I -> R -> L --> G --> O  --> H --> M --> U
'A': 1
  A -> T ->  S -> I -> R -> L --> G --> O  --> H --> M --> U
'G': 7
  G -> A -> T ->  S -> I -> R -> L --> O  --> H --> M --> U

Total: 44

*/

/* 6.3: 3 verschiedene Gegenstaende: 1 CHF

erste Zahl enthaelt die Anzahl v erschiedener Arten von Gegenstaenden n 5000
n Ganzzahlen ai <= 2000 mit 1 <= i <= n,

Array Bucket[] benutzen, Bucket[k], wie viele verschiedene Gegenstände Arten
mit k Gegenständen verfügbar

-> Liste mit Anzahl Arten mit gleicher Anzahl

 */


class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);

        int testCases = s.nextInt();

        while(s.hasNextInt())
            {
                int num_items = s.nextInt();
                int []items = new int[num_items];
                for(int i = 0; i < num_items; i++) {
                    items[i] = s.nextInt();
                }
                Exercise06 ex = new Exercise06(items);
                ex.run();
            }

        System.exit(0);
    }
}

/* Gesucht: Anzahl uniquer 3er Gruppen??

        n!
      -------
      (n-k)!

      k = 3

Nein!

4 verschieden Arten von Gegenstaenden:
1 1 1 20 = 1

1 Artikel vom Typ 1
1 Artikel vom Typ 2
1 Artikel vom Typ 3
20 Artikel vom Typ 4

Maximalbetrag beim Umtauschen gesucht!!!!

 */

class Exercise06
{
    int []items;
    int result;

    int firstTime;
    int highest;

    int count;
    int []lastThree;

    boolean somethingLeft;

    MySort sort;

    HashMap<Integer, Integer> map;

    public Exercise06(int []items)
    {
        this.items = items;
        result = 0;
        highest = 0;

        sort = new MySort();

        somethingLeft = true;

        map = new HashMap<Integer, Integer>();

        count = 0;
        lastThree = new int[3];
    }

    public void run()
    {
        //   calculate_by_sort();
        calculateWithMap();
        printSolution();
    }

    void printDebug()
    {
        System.err.print("[ ... ");
        for(int i=0; i < items.length; i++) {
            System.err.print(" " + items[i]);
        }
        System.err.println(" ... ]");

    }

    void printDebugMap()
    {
        Iterator<Integer> keySetIterator = map.keySet().iterator();
        while(keySetIterator.hasNext()){
            Integer key = keySetIterator.next();
            System.err.println("key: " + key + " value: " + map.get(key));
        }
    }

    /* Take the three highest, add +1, decrement the item

     */
    void calculateWithMap()
    {
        createNumberMap();
        takeThreeHighest();
        printDebugMap();
    }

    /* This can be a bit smarter - maybe storing the possible values outside? */
    /* Collection values(): It returns a collection of values of map. */
    boolean findNextHighest()
    {
        boolean moreThanZero = true;

        while(highest > 0) {
            if(map.containsKey(highest)) {
                break;
            } else {
                highest--;
            }
        }
        if(highest == 0) moreThanZero = false;

        return moreThanZero;
    }

    /* take the highest available element count and take it out of the map */
    void takeHighest()
    {
        int countHigh;

        /* get the number of items at the highest count */
        countHigh = map.get(highest);

        lastThree[count] = highest - 1; /* save the next position */

        /* Update map:
         * Either reduce amount or delete key, if it was the last one
         */
        if(countHigh == 1) {
            map.remove(highest);
        } else {
            map.put(highest, countHigh-1);
        }
        count++;

        /* insert values back into map after three have been taken */
        if(count == 3)
            {
                result++;
                count = 0;

                insertNumInMap(lastThree[0]);
                insertNumInMap(lastThree[1]);
                insertNumInMap(lastThree[2]);
            }

    }

    void takeThreeHighest()
    {
        /* As long as there are more elements than zero, continue */
        while(highest > 0) {
            /* Stop if highest is zero */
            if(!findNextHighest()) break;

            /* take highest */
            takeHighest();
        }
    }

    /* Store number of items w/ same number of items in array (?)
     * Maybe use a hashlist to store item?
     */
    void createNumberMap()
    {
        for(int i=0; i < items.length; i++) {
            if(items[i] > highest) highest = items[i];
            insertNumInMap(items[i]);
        }
    }

    void insertNumInMap(int num)
    {
        int value = 1;


        if(map.containsKey(num)) {
            value += map.get(num);
        }
        System.err.println("Insert in map: " + num + " -> " + value);
        map.put(num, value);
    }

    void calculate_by_sort()
    {
        /* no need to sort anything with less than 3 items */
        if(items.length < 3) return;

        int first  = items.length -1;
        int second = items.length -2;
        int third  = items.length -3;

        do {
            items = sort.run(items);

            if(items[third] > 0) { /* if at least three have a chance -> change them! */
                items[first]--;
                items[second]--;
                items[third]--;
                result++;
            } else {
                somethingLeft = false;
            }

            //            printDebug();

        } while(somethingLeft);
    }

    void printSolution()
    {
        System.out.println("" + result);
    }

    void printInput()
    {
        for(int i = 0; i < items.length; i++)
            {
                System.err.print(" " + items[i]);
            }
        System.err.println("");
    }
}

class MySort {

    public int [] to_sort;
    boolean firstTime;

    public MySort() {
        firstTime = true;
    }


    public int [] run(int []to_sort)
    {
        this.to_sort = to_sort;

        if(firstTime) {
            run_qs();
            firstTime = false;
        } else {
            run_insertionSort();
        }

        return this.to_sort;
    }

    public void run_qs()
    {
		int low = 0;
		int high = to_sort.length - 1;

        quickSort(to_sort, low, high);
    }

    public void run_insertionSort()
    {
        for(int i=1; i < to_sort.length; i++) {
            int temp = to_sort[i];
            int j;
            for(j = i-1; j >= 0 && temp < to_sort[j]; j--)
                to_sort[j+1] = to_sort[j];
            to_sort[j+1] = temp;
        }
    }


	public static void quickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;

		if (low >= high)
			return;

		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}

			while (arr[j] > pivot) {
				j--;
			}

			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);

		if (high > i)
			quickSort(arr, i, high);
	}


}

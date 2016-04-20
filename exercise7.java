/* Generic boilerplate */
import java.io.*;
import java.util.*;



/* Aufgabe 7.1: Kodierungsbaum */
/*

(a)

 5 verschiedene Buchstaben

 ges.: Kodierungsbaum
 ges.: kodierte Wort AGGLGSK

Key           A G L K S
Freq.         9 8 8 7 7  total = 39
Rel. Weight   ---------
                 39

1. Create nodes: A:9, G:8, ...

2. Create / join trees to generate tree:

0 = left, 1 = right


   14         16
 /    \     /   \
K:7   S:7  L:8 G:8



      23
    /    \
   14   9:A        16
 /    \           /   \
K:7   S:7        L:8 G:8


Final tree:
            39
        /          \
      23           16
    /    \        /   \
   14   9:A      L:8 G:8
 /    \
K:7   S:7

Code of AGGLGSK:

01 11 11 10 11 001 000


-------------> it seems that the exercise CHANGED and K&L are different now...
-------------> so let's do (a) again....

(a)

 5 verschiedene Buchstaben

 ges.: Kodierungsbaum
 ges.: kodierte Wort AGGLGSK

Key           A G K L S
Freq.         9 8 8 7 7  total = 39
Rel. Weight   ---------
                 39

1. Create nodes: A:9, G:8, ...

2. Create / join trees to generate tree:

0 = left, 1 = right


   14         16
 /    \     /   \
L:7   S:7  G:8 K:8



      23
    /    \
   14   A:9      16
 /    \         /   \
L:7   S:7      G:8 K:8


           39
       /       \
      23        \
    /    \       \
   14   A:9      16
 /    \         /   \
L:7   S:7      G:8 K:8


Code of AGGLGSK:

01 10 10 000 10 001 11


(b)
  ges.: optimaler Suchbaum
  ges.: gewichtete Pfadlänge

  geg.:
  alphabetischen Ordnung der Schlüssel
  % Zugriffe zwischen Schlüsseln

  Schlüsselintervall (_,A) (A,G) (G,K) (K,L) (L,S) (S,_)
  Häufigkeit            0    18     7     0    19    17





*/


/* Aufgabe 7.2

(a)

Palindrom via DP

Ausgabe: alle Paare (i,j) mit Palindromen

ges.: Algorithmus
ges.: Laufzeit




*/


/* Aufgabe 7.3 : längste gemeinsame Teilfolge */

class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);

        int testCases = s.nextInt();
        s.nextLine();

        while(testCases > 0)
            {
                String line1 = s.nextLine();
                String line2 = s.nextLine();

                Exercise ex = new Exercise(line1, line2);
                ex.run();

                testCases--;
            }

        System.exit(0);
    }
}


/*
1) Definition der DP-Tabelle: Welche Dimensionen hat die Tabelle? Was ist die Bedeutung
jedes Eintrags?

Dimension: m * n
Eintrag: Anzahl gleicher Zeichen bis zu dieser Stelle

2) Berechnung eines Eintrags: Wie berechnet sich ein Eintrag aus den Werten von anderen
Einträgen? Welche Einträge hängen nicht von anderen Einträgen ab?


3) Berechnungsreihenfolge: In welcher Reihenfolge kann man die Einträge berechnen, so dass
die jeweils benötigten anderen Einträge bereits vorher berechnet wurden?


4) Auslesen der Lösung: Wie lässt sich die Lösung am Ende aus der Tabelle auslesen?

*/


class Exercise {

    String s1, s2;
    int    l1, l2;
    int [][]dpTable;

    public Exercise(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;

        l1 = s1.length();
        l2 = s2.length();

        dpTable = new int[l1+1][l2+1];
    }

    void run()
    {
        // Debug
        // // System.err.println(s1);
        // // System.err.println(s2);

        computeTable();
        // Debug
        //        printTable();
        printResult();
    }

    //takes as input two strings and computes and returns
    //the table needed for the dynamic programming
    void computeTable() {

        /* Zero length strings are all zero length in matching */
        for(int i=0; i <= l1; i++)
            dpTable[i][0] = 0;

        /* Zero length strings are all zero length in matching */
        for(int i=0; i <= l2; i++)
            dpTable[0][i] = 0;

        for(int i = 1; i <= l1; i++) {
            for(int j = 1; j <= l2; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dpTable[i][j] = dpTable[i-1][j-1] + 1;
                } else {
                    int a = dpTable[i-1][j];
                    int b = dpTable[i][j-1];

                    dpTable[i][j] = Math.max(a,b);
                }
            }
        }
    }


    void printTable()
    {
        for(int i = 0; i<=l1; i++) {
            for(int j=0; j <= l2; j++) {
                System.err.print("" + dpTable[i][j] + " ");
            }
            System.err.println("");
        }
    }

    void printResult() {
        System.out.println("" + dpTable[l1][l2]);
    }


}

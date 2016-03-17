/* Hints / Questions:
 * - stderr printing?
 */

import java.io.*;
import java.util.*; /* Scanner, Arraylist */


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        String []lines;
        int i = 0;


        int nTests = Integer.parseInt(in.readLine());
        System.out.println("Tests: " + Integer.toString(nTests));

        lines = new String[nTests];

        /* Read & run tests */
        while ((s = in.readLine()) != null && s.length() != 0) {
            Exercise e1 = new Exercise();
            System.out.println("Test " + i++);
            System.out.println(e1.blum_median(s));
        }        
    }

}

class Exercise {

    private int maximum;
    private int [] inputlist;
    private int elements;
    
    public Exercise() {
        maximum = 1000;
        inputlist = new int[maximum];
        elements = 0;
    }

    String blum_median(String input) {
        
        Scanner scanner = new Scanner(input);

        int []real_list;

        /* Skip the first element as it is only the number of numbers */
        scanner.nextInt();
        
        while (scanner.hasNextInt()) {
            inputlist[elements] = scanner.nextInt();
            elements++;
        }
        real_list = new int[elements];
        for(int i=0; i< elements; i++) {
            real_list[i] = inputlist[i];
        }
                    
        test_listof5(listof5(real_list));

        /*
        for(int i=0; i < elements; i++) {
            System.out.println("Noch ein Element " +inputlist[i]);
            } */

        return "";
    }

    int [][] listof5 (int []list) {
        int [][]lists;
        
        int no_lists = (list.length)/5;
        int cnt = 0;

        /* create another list for the leftover elements */
        if (list.length % 5 != 0) no_lists++;
        
        lists = new int[no_lists][5];

        for(int i = 0; i < no_lists; i++) {
            for(int j = 0; j < 5; j++) {
                if(cnt < list.length) { /* Elements available */
                    lists[i][j] = list[cnt];
                    cnt++;
                }
            }
        }

        return lists;
    }
    void test_listof5(int [][]partitioned_lists)
    {
        for(int i=0; i < partitioned_lists.length; i++) {
            System.out.println("List " + i);
            for(int j=0; j < partitioned_lists[i].length; j++) {
                System.out.println("List[" + i + "][" + j + "] = " + partitioned_lists[i][j]);
            }
        }
    }

    int median_5(int []liste) {
        return 2;
    }
}

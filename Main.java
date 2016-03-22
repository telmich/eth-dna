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

        for(int i = 1; i < list.size(); i++) {
            System.out.println("Line " + i + ": " + list.get(i));
        }
        Exercise e1 = new Exercise(list);

        /* Read & run tests */
        /*        while ((s = in.readLine()) != null && s.length() != 0) {
            Exercise e1 = new Exercise(s);
            System.out.println("Test " + i++);
            System.out.println(e1.blum_median());
            }*/
    }

}

class Exercise {

    private int maximum;

    //    private int [] inputlist;
    private int elements;
    
    private String input;

    ArrayList<ArrayList<Integer>> list_of_int_lists;

    ArrayList <String> inputlist;
    
    public Exercise(ArrayList <String> list) {
        // this.input = input;      /* input given to the program          */
        this.inputlist = list;      /* input given to the program          */
        maximum = 1000;          /* Maximum number of elements per line */
    }

    /*    privat readMatrix()
    {

    }*/

//     private void input_to_integer_array()
//     {
//         Scanner scanner = new Scanner(input);
//         elements = scanner.nextInt();
// 
//         System.out.println("Elements: " + elements);
//         
//         inputlist = new int[elements];
// 
//         /* Could loop over elements as well */
//         for(int i = 0; i < elements; i++) {
//             inputlist[i] = scanner.nextInt();
//         }
// 
//         /* Need to track index for array anyway - better suited for Arraylist:
//         List<Integer> list = new ArrayList<Integer>();
//         while (scanner.hasNextInt()) {
//             list.add(scanner.nextInt());
//         }
//         */        
//     }
// 
//    String blum_median() {
        
//        input_to_integer_array();
        
        //    test_listof5(listof5(inputlist));

    //        return "";
    //    }

    void listsof5 () {
        //         List<List<Int>> lists = new ArrayList<List<Individual>>(4);

        //return lists;


 //        List<Integer> list = new ArrayList<Integer>();        
 //        int [][]lists;
 //        
 //        int no_lists = (list.length)/5;
 //        int cnt = 0;
 // 
 //        /* create another list for the leftover elements */
 //        if (list.length % 5 != 0) no_lists++;
 //        
 //        lists = new int[no_lists][5];
 // 
 //        for(int i = 0; i < no_lists; i++) {
 //            for(int j = 0; j < 5; j++) {
 //                if(cnt < list.length) { /* Elements available */
 //                    lists[i][j] = list[cnt];
 //                    cnt++;
 //                }
 //            }
 //        }
 // 
 //        return lists;*/
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

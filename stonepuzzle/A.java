package stonepuzzle;

public class A {
    public static void main(String[] args) {
        int[][] data ={
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}
        };

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.println(i+"..."+j);
            }
        }
    }
}

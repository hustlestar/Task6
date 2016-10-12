/**
 * Created by Hustler on 11.10.2016.
 */
public class Multiply2Thread extends Thread {
    private volatile static Matrix matrix;
    private static double[][] m1;
    private static double[][] m2;
    private int r1;
    private int c1;
    private int r2;
    private int c2;
    public int r1c1;
    public static int count;
    private static int countcell;


    public Multiply2Thread(Matrix result, double[][] first, double[][] second, int firstCellRow,
                           int firstCellCol, int lastCellRow, int lastCellCol) {
        matrix = result;
        m1 = first;
        m2 = second;
        this.r1 = firstCellRow;
        this.c1 = firstCellCol;
        this.r2 = lastCellRow;
        this.c2 = lastCellCol;
        //System.out.println("Создан поток" + ++count);
        ++count;
    }

    @Override
    public void run() {
        //when 1 matrix has 1 row
        for(;;) {
            //System.out.println(++countcell);
            r1c1=0;//refreshing value
            if (r1 < r2 && c1 < matrix.getColumns() - 1) {
                multiplyAlgorithm();
                //System.out.println(1 + ": " + r1 + ", " + c1 + " == " + r1c1);
                matrix.setValue(r1, c1, r1c1);
                c1++;
            } else if (r1 < r2 && c1 == matrix.getColumns() - 1) {
                multiplyAlgorithm();
                //System.out.println(2 + ": " + r1 + ", " + c1 + " == " + r1c1);
                matrix.setValue(r1, c1, r1c1);
                c1 = 0;
                r1++;
            } else if (r1 == r2 && c1 <= c2) {
                multiplyAlgorithm();
                //System.out.println(3 + ": " + r1 + ", " + c1 + " == " + r1c1);
                matrix.setValue(r1, c1, r1c1);
                c1++;
            } else break;
        }
    }
    //algorithm of multiplication
    private void multiplyAlgorithm() {
        //1row first matrix, 1 column second matrix
        if (m1[0].length == 1) {
            r1c1 += m1[r1][0] * m2[0][c1];

        } else if (m1.length >= m2[0].length || m1.length < m2[0].length) {
            //when matix is like [3, 4] * [4, 2] or [3, 4] * [4, 5]
            for (int i = 0; i < m1[0].length; i++) {
                for (int j = 0; j < m2.length; j++) {
                    if (i == j) {
                        r1c1 += m1[r1][j] * m2[i][c1];
                    }
                }
            }
        } else {
            //when rows and columns number is equal. square matrix
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m2[0].length; j++) {
                    if (i == j && j < m1[0].length && i < m2.length) {
                        r1c1 += m1[r1][j] * m2[i][c1];
                    }
                }
            }
        }
    }
}

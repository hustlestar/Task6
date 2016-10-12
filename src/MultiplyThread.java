/**
 * Created by Hustler on 07.10.2016.
 */
public class MultiplyThread extends Thread {
    private static Matrix matrix;
    private static double[][] m1;
    private static double[][] m2;
    private int r1;
    private int c1;
    private int r2;
    private int c2;
    public int r1c1;
    public int r2c2;
    public static int count;
    private static int countcell;


    public MultiplyThread(Matrix result, double[][] first, double[][] second, int r1, int c1, int r2, int c2) {
        matrix = result;
        m1 = first;
        m2 = second;
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        //System.out.println("Создан поток" + ++count);
        ++count;
    }

    public MultiplyThread(Matrix result, double[][] first, double[][] second, int r1, int c1) {
        matrix = result;
        m1 = first;
        m2 = second;
        this.r1 = r1;
        this.c1 = c1;
        ++count;
        //System.out.println("Создан поток " + ++count);

    }

    @Override
    public void run() {
        //when 1 matrix has 1 row
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
        matrix.setValue(r1, c1, r1c1);

        if (m2.length == 1) {
            r2c2 += m1[r2][0] * m2[0][c2];
        } else if (m1.length >= m2[0].length || m1.length < m2[0].length) {
            //варик для массивов типа [3, 4] * [4, 2]
            for (int i = 0; i < m1[0].length; i++) {
                for (int j = 0; j < m2.length; j++) {
                    if (i == j) {
                        r2c2 += m1[r2][j] * m2[i][c2];
                    }

                }
            }
        } else {
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m2[0].length; j++) {
                    if (i == j && j < m1[0].length && i < m2.length) {
                        r2c2 += m1[r2][j] * m2[i][c2];
                    }
                }
            }
        }
        if (r2 < matrix.getRows() && c2 < matrix.getColumns()) {
            matrix.setValue(r2, c2, r2c2);
        }
    }
}

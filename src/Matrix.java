import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hustler on 07.10.2016.
 */
public class Matrix {
    protected volatile double[][] matrix;


    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    protected volatile static Matrix mult;

    //method, which sets values in result array
    public synchronized void setValue(int row, int col, double value) {
        mult.getMatrix()[row][col] = value;
    }


    public static void main(String[] args) throws MatrixMultiplyException {
        //matrix generation
        //multiplying NON-SQUARE matrix is NOT A PROBLEM for this app
        //Date date1 = new Date();
        double[][] mas = getArray(30, 50);
        double[][] mas3 = getArray(50, 70);
        Matrix m1 = new Matrix(mas);
        Matrix m2 = new Matrix(mas3);

        Matrix mama = m1.multiply(m2);
        System.out.println("------------------------------------------------------------");
        System.out.println("This app works with square and NON-SQUARE matrixes with ease");
        System.out.println("Result matrix:");
        printMatrix(mama);
        System.out.println("Size of result matrix is (" + mama.getRows() + " , " + mama.getColumns() + ") for multiplying used "
                + MultiplyThread.count + " threads");
        //Date date2 = new Date();
        //long time = date2.getTime() - date1.getTime();
        //System.out.println("Время " + time);
    }

    protected static void printMatrix(Matrix mama) {
        for (double[] ints : mama.getMatrix()) {
            for (double i : ints) {
                System.out.print(String.format("%.0f ", i));
            }
            System.out.println();
        }
    }

    protected Matrix multiply(Matrix m2) throws MatrixMultiplyException {
        if (checkIfPossible(this, m2)) {

            printM1M2(m2);//printing array 1 and 2;

            int resultRows = this.getRows();
            int resultCols = m2.getColumns();
            int resultAll = resultCols * resultRows;

            double[][] result = new double[resultRows][resultCols];
            mult = new Matrix(result);

            int rR = 0;//this variable is used to check row number
            int rC = 0;//this variable is used to check column number
            List<Thread> threadList = new ArrayList<>();
            for (int rA = 0; rA < resultAll; ) {
                //variable rA is used to represent number of current cell
                //System.out.println("222222222222");
                if (rR < resultRows && rC < resultCols - 2) {
                    //checks if current 2 cells are in the begining of the row
                    int r1 = rR;
                    int c1 = rC++;
                    int r2 = rR;
                    int c2 = rC++;
                    MultiplyThread thread = new MultiplyThread(mult, this.getMatrix(), m2.getMatrix(), r1, c1, r2, c2);
                    threadList.add(thread);
                    thread.start();
                    rA++;
                    rA++;
                } else if (rR < resultRows && rC < resultCols - 1) {
                    //checks if current cells are two last cells in the row
                    int r1 = rR;
                    int c1 = rC++;
                    int r2 = rR;
                    int c2 = rC++;
                    rC = 0;
                    rR++;
                    MultiplyThread thread = new MultiplyThread(mult, this.getMatrix(), m2.getMatrix(), r1, c1, r2, c2);
                    threadList.add(thread);
                    thread.start();
                    rA++;
                    rA++;
                } else {
                    //when one cell is last on one row, and next cell is on the other row
                    int r1 = rR;
                    int c1 = rC++;
                    rR++;
                    if (rR < resultRows) {
                        rC = 0;
                        int r2 = rR;
                        int c2 = rC++;
                        rA++;
                        rA++;
                        MultiplyThread thread = new MultiplyThread(mult, this.getMatrix(), m2.getMatrix(), r1, c1, r2, c2);
                        threadList.add(thread);
                        thread.start();
                    } else {
                        //when we reached last cell
                        MultiplyThread thread = new MultiplyThread(mult, this.getMatrix(), m2.getMatrix(), r1, c1);
                        threadList.add(thread);
                        thread.start();
                        rA++;
                        //System.out.println("ETAAAAAAAAAAAAAAAAAAAAAAAAA POSLEDNIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
                    }
                }
            }
            //waiting for all threads to finish
            for (Thread thread : threadList) {
                if (thread.isAlive()) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted thread");
                    }
                }
            }
        } else throw new MatrixMultiplyException();
        return mult;
    }

    protected void printM1M2(Matrix m2) {
        for (double[] ints : this.getMatrix()) {
            for (double anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------------");
        for (double[] ints : m2.getMatrix()) {
            for (double anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    protected static boolean checkIfPossible(Matrix m1, Matrix m2) {
        if (m1 == null) return false;
        else if (m2 == null) return false;
        else if (m1.getRows() == 0) return false;
        else if (m2.getColumns() == 0) return false;
        return m1.getColumns() == m2.getRows();
    }

    //array generator
    protected static double[][] getArray(int r, int c) {
        double[][] mas = new double[r][c];
        int d = 0;
        for (int i = 0; i < mas.length; i++) {
            for (int i1 = 0; i1 < mas[i].length; i1++) {
                //mas[i][i1] = d++;
                mas[i][i1] = Math.round(Math.random() * 10);//random0-10 values
            }
        }
        return mas;
    }

    public int getColumns() {
        return matrix[0].length;
    }

    public int getRows() {
        return matrix.length;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
}

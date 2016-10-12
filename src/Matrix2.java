import java.util.Date;

/**
 * Created by Hustler on 08.10.2016.
 */
public class Matrix2 extends Matrix {

    public Matrix2(double[][] matrix) {
        super(matrix);
    }

    public static void main(String[] args) throws MatrixMultiplyException {
        //matrix generation
        //multiplying NON-SQUARE matrix is NOT A PROBLEM for this app
        //Date date1 = new Date();
        double[][] mas = getArray(3, 15);
        double[][] mas3 = getArray(15, 8);
        Matrix2 m1 = new Matrix2(mas);
        Matrix2 m2 = new Matrix2(mas3);
        Matrix mama = m1.multiply(m2);
        System.out.println("------------------------------------------------------------");
        System.out.println("This app works with square and non-square matrixes with ease");
        System.out.println("Result matrix:");
        printMatrix(mama);
        System.out.println("Size of result matrix is (" + mama.getRows() + " , " + mama.getColumns() + ") for multiplying used "
                + Multiply2Thread.count + " threads");
        //Date date2 = new Date();
        //long time = date2.getTime() - date1.getTime();
        //System.out.println("Время " + time);
    }

    @Override
    protected Matrix multiply(Matrix m2) throws MatrixMultiplyException {
        if (checkIfPossible(this, m2)) {
            printM1M2(m2); //printing array 1 and 2;

            int resultRows = this.getRows();
            int resultCols = m2.getColumns();
            int resultAll = resultCols * resultRows;
            double[][] result = new double[resultRows][resultCols];
            mult = new Matrix2(result);
            int cellsNumber1;//number of cells which are given to 1 thread
            if (resultRows * resultCols % 2 == 0) {
                cellsNumber1 = resultRows * resultCols / 2;
            } else {
                cellsNumber1 = resultRows * resultCols / 2 + 1;
            }
            int rR = 0;//this variable is used to check row number
            int rC = 0;//this variable is used to check column number

            for (int rA = 0; rA < resultAll; ) {
                //variable rA is used to represent number of current cell
                int r2;
                int c2;

                if (rR < resultRows && rC < resultCols) {
                    if (rA == cellsNumber1 - 1) {
                        r2 = rR;
                        c2 = rC;
                        //System.out.println("МЫ ТУТА");
                        //System.out.println(r2 + " " + c2);
                        //creating the first thread
                        new Multiply2Thread(mult, this.getMatrix(), m2.getMatrix(), 0, 0, r2, c2).start();
                        Multiply2Thread thread;
                        //creating the second thread
                        if (resultRows % 2 != 0) {
                            thread = new Multiply2Thread(mult, this.getMatrix(), m2.getMatrix(), r2, c2 + 1, resultRows - 1, resultCols - 1);
                        } else {
                            thread = new Multiply2Thread(mult, this.getMatrix(), m2.getMatrix(), r2 + 1, 0, resultRows - 1, resultCols - 1);
                        }
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted");
                        }

                        break;
                    }
                    rC++;
                    rA++;
                    if (rC == resultCols) {
                        rC = 0;
                        rR++;
                    }
                }

            }
        } else throw new MatrixMultiplyException();
        return mult;
    }
}

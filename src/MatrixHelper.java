/**
 * Created by Hustler on 31.10.2016.
 */
public class MatrixHelper {

    public static void printMatrix(Matrix mama) {
        for (double[] ints : mama.getMatrix()) {
            for (double i : ints) {
                System.out.print(String.format("%.0f ", i));
            }
            System.out.println();
        }
    }

    public static void printM1M2(Matrix m1, Matrix m2) {
        for (double[] ints : m1.getMatrix()) {
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

}

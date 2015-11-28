import com.analyze.GlobalMaximum;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Sergey on 23.11.2015.
 */
public class TestGlobalMaximum {
    /**
     * Test method of GlobalMax
     *
     * @throws Exception
     */
    final double e = 0.001;//constant need to compare two double numbers

    @Test
    public void testGlobalMax() throws Exception {
        GlobalMaximum tester = new GlobalMaximum();
        double[] X_array = new double[1001];
        double[] Y_array = new double[1001];

        //generate y=x
        for (int i = 0; i < 1001; i++) {
            X_array[i] = 4 - i * 0.008;
            Y_array[i] = X_array[i];

        }
        //test y=x
        assertTrue("Global maximum of y=x is wrong", Math.abs(4.0 - Y_array[tester.Calc(X_array, Y_array)]) < e);

        //generate y=x^2
        for (int i = 0; i < 1001; i++) {
            X_array[i] = 4 - i * 0.008;
            Y_array[i] = X_array[i] * X_array[i];

        }
        // test y=x^2
        assertTrue("Global maximum of y=x^2 is wrong", Math.abs(16 - Y_array[tester.Calc(X_array, Y_array)]) < e);

        //generate y=sin(x^2)
        for (int i = 0; i < 1001; i++) {
            X_array[i] = 4 - i * 0.008;
            Y_array[i] = Math.sin(X_array[i] * X_array[i]);

        }
        //test x=sin(x^2)
        assertTrue("Global maximum of y=sin(x^2) is wrong", Math.abs(1 - Y_array[tester.Calc(X_array, Y_array)]) < e);


    }
}

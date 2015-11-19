import com.analyze.Analize;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * test-class of class Analyze
 * Created by Sergey on 18.11.2015.
 */
public class testAnalize {
    /**
     * Test method of GlobalMax
     * @throws Exception
     */
    final double e=0.001;//constant need to compare two double numbers
    @Test
    public void testGlobalMax() throws Exception{
        Analize tester = new Analize();
        double[] X_array= new double[1001];
        double[] Y_array= new double[1001];
        //test y=x
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i];

        }
        assertTrue(Math.abs(4.0 - Y_array[tester.GlobalMax(X_array,Y_array,1001)])<e);
        // test y=x^2
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i]*X_array[i];

        }
        assertTrue(Math.abs(16 - Y_array[tester.GlobalMax(X_array,Y_array,1001)])<e);

        //test x=sin(x^2)
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=Math.sin(X_array[i]*X_array[i]);

        }
        assertTrue(Math.abs(1 - Y_array[tester.GlobalMax(X_array,Y_array,1001)])<e);



    }
    /**
     * Test method of GlobalMin
     * @throws Exception
     */
    @Test
    public void testGlobalMin() throws Exception{
        Analize tester = new Analize();
        double[] X_array= new double[1001];
        double[] Y_array= new double[1001];
        //test y=x
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i];

        }
        assertTrue(Math.abs(-4.0 - Y_array[tester.GlobalMin(X_array,Y_array,1001)])<e);
        // test y=x^2
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i]*X_array[i];

        }
        assertTrue(Math.abs(0 - Y_array[tester.GlobalMin(X_array,Y_array,1001)])<e);

        //test x=sin(x^2)
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=Math.sin(X_array[i]*X_array[i]);

        }
        assertTrue(Math.abs(-1 - Y_array[tester.GlobalMin(X_array,Y_array,1001)])<e);


        // assertEquals(4,4);

    }
}

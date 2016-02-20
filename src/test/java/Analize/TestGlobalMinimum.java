package Analize;

import com.analyzeme.analyze.GlobalMinimum;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Sergey on 23.11.2015.
 */

public class TestGlobalMinimum {
    final double e=0.001;//constant need to compare two double numbers

    @Test
    public void TestGlobalMin() throws Exception{
        GlobalMinimum tester = new GlobalMinimum();
        double[] X_array= new double[1001];
        double[] Y_array= new double[1001];

        //generate array of y=x
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i];

        }
        //Compare -4 and minimum of y=x
        assertTrue("Global minimum of y=x is  wrong",Math.abs(-4.0 - Y_array[tester.Calc(X_array,Y_array)])<e);

        //generate array of  y=x^2
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=X_array[i]*X_array[i];

        }
        // Compare 0 and minimum of y=x^2
        assertTrue("Global minimum of y=x^2 is  wrong",Math.abs(0 - Y_array[tester.Calc(X_array,Y_array)])<e);


        //generate array of  y=sin(x^2)
        for(int i=0;i<1001;i++){
            X_array[i]=4-i*0.008;
            Y_array[i]=Math.sin(X_array[i]*X_array[i]);

        }
        //Compare -1 and minimum of y=sin(x^2)
        assertTrue("Global minimum of y=sin(x^2)is wrong",Math.abs(-1 - Y_array[tester.Calc(X_array,Y_array)])<e);

    }
}


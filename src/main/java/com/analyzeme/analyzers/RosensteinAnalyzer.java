package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;

import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.apache.commons.math.transform.FastFourierTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static java.lang.Math.*;

/**
 * Created by ankarenko on 06.09.16.
 */
public class RosensteinAnalyzer implements IAnalyzer {
    private static final Logger LOGGER;
    private static final int NUMBER_OF_PARAMS = 2; // ???
    private static final double EPS = 10e-10;


    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.RosensteinAnalyzer");
    }

    private static class Neibours {
        int i;
        int j;
        double distance;

        Neibours(int _i, int _j, double _distance) {
            i = _i;
            j = _j;
            distance = _distance;
        }
    }

    public static class OptimalDimensionFinder {
        private Double[] data;
        private int N;
        private int delay;
        private final Double EPS = 10e-8;
        private final double TRESHOLD_FIRST_CRITERION = 15;
        private final double TRESHOLD_SECOND_CRITERION = 2;

        OptimalDimensionFinder(int optimalDelay, Double[] dataSet) {
            delay = optimalDelay;
            data = dataSet.clone();
            N = data.length;
        }

        private double normSubstraction(double[] x, double[] y, int dim) {
            double norm = 0;
            for (int i = 0; i < dim; ++i) {
                norm += pow(x[i] - y[i], 2);
            }
            return sqrt(norm);
        }

        private Neibours getNNinfo(double[][] attractor, int i, int len, int dim) {

            Neibours info = new Neibours(0, 0, Double.MAX_VALUE);
            info.i = i;

            for (int j = 0; j < len; ++j) {
                if (i != j) {
                    double norm = normSubstraction(attractor[i], attractor[j], dim);
                    if (norm < info.distance) {
                        info.distance = norm;
                        info.j = j;
                    }
                }
            }
            return info;
        }

        private boolean isNNfirstCriterion(Neibours infoNN, int dim, int delay) {

            int i = infoNN.i;
            int j = infoNN.j;
            double oldDimNorm = pow(infoNN.distance, 2);
            int shift = dim * delay;

            if (i + shift >= N || j + shift >= N) {
                return false;
            }

            double newDimNorm = oldDimNorm + pow(data[i + shift] - data[j + shift], 2);
            double val = sqrt((newDimNorm - oldDimNorm) / oldDimNorm);
            return  val > TRESHOLD_FIRST_CRITERION;
        }

        private void improveAttractor(double[][] attractor, int dim, int delay) {
            int newLen = N - (dim - 1) * delay;
            int shift = (dim - 1) * delay;

            for (int i = 0; i < newLen; ++i) {
                attractor[i][dim - 1] = data[i + shift];
            }
        }

        public int FNN() {
            //double[] values = new double[20];
            //double[] dimen = new double[20];
            //for (int i = 0; i < 20; ++i)
            //    dimen[i] = i + 1;

            //long timer = -System.currentTimeMillis();

            double fnnFreq = EPS;
            int dim = 0;
            /*100 should be presented as constant*/
            double[][] attractor = new double[N][100];

            while (fnnFreq >= EPS) {
                //System.out.println("dim : " + dim + 1);
                ++dim;
                improveAttractor(attractor, dim, delay);
                int newLen = N - (dim - 1) * delay;
                int amountFN = 0;

                for (int i = 0; i < newLen; ++i) {
                    Neibours infoNN = getNNinfo(attractor, i, newLen, dim);

                    if (isNNfirstCriterion(infoNN, dim, delay)) {
                        amountFN++;
                    }

                    if (((double)(amountFN) / newLen) >= EPS) {
                        break;
                    }
                }
                fnnFreq = (double)(amountFN) / newLen;
                //values[dim - 1] = fnnFreq;
            }
            //timer += System.currentTimeMillis();
            //System.out.println("\nFNN time : " + timer);


            //Plot2DPanel plot = new Plot2DPanel();
            //plot.addLinePlot("Lorenz", dimen, values);
            //JFrame frame = new JFrame("Distances");
            //frame.setSize(600, 600);
            //frame.setContentPane(plot);

            //frame.setVisible(true);

            return dim;
        }

    }

    /*private*/
    public static class OptimalDelayFinder {
        private Double[] data;
        private Double[] pA;
        private Double[] intervals;
        private int N;
        private int L;
        private Vector<Vector<Integer>> preimage;
        private Integer[] image;
        private Double[] pB;
        private int delay;
        private int stepsAmount;

        OptimalDelayFinder(Double[] dataSet, int steps) {
            data = dataSet.clone();
            N = data.length;
            L = (int)floor(log(N) / log(2)) + 1;
            stepsAmount = steps;

            intervals = computeIntervals(data, L);
            image = computeImage(data, intervals);
            preimage = computePreimage(data, intervals);
            pA = computeFrequancy(data);
            pB = pA.clone();
        }

        private Integer[] computeImage(Double data[], Double[] intervals) {
            Integer[] image = new Integer[N];

            for (int i = 0; i < N; ++i) {
                image[i] = getIntervalNumber(data[i], intervals);
            }

            return image;
        }

        private void computeMaxMin(Double[] minmax, Double[] data) {
            minmax[1] = Double.MIN_VALUE;
            minmax[0] = Double.MAX_VALUE;

            for (int i = 0; i < N; ++i) {
                if (data[i] > minmax[1]) {
                    minmax[1] = data[i];
                }
                if (data[i] < minmax[0]) {
                    minmax[0] = data[i];
                }
            }
        }

        private Double[] computeIntervals(Double[] data, int amount) {
            Double[] minmax = new Double[2];
            computeMaxMin(minmax, data);
            minmax[0] -= 10e-6;
            minmax[1] += 10e-6;

            double h = (minmax[1] - minmax[0]) / amount;
            Double[] intervals = new Double[amount + 1];

            for (int i = 0; i < amount + 1; ++i) {
                intervals[i] = minmax[0] + i * h;
            }

            return intervals;
        }

        private int getIntervalNumber(double val, Double[] intervals) {
            for (int i = 0; i < L; ++i) {
                if (val >= intervals[i] && val < intervals[i + 1]) {
                    return i;
                }
            }
            return -1;
        }

        private Vector<Vector<Integer>> computePreimage(Double[] data, Double[] intervals) {
            int size = data.length;
            Vector<Vector<Integer>> preimage = new Vector<Vector<Integer>>(L);

            for (int i = 0; i < L; ++i) {
                preimage.add(new Vector<Integer>());
            }

            for (int i = 0; i < size; ++i) {
                int num = getIntervalNumber(data[i], intervals);
                if (num >= 0 && num < L) {
                    preimage.get(num).add(i);
                }
            }

            return preimage;
        }

        private Double[] computeFrequancy(Double[] data) {
            Double[] freq = new Double[L];
            preimage = computePreimage(data, intervals);

            for (int i = 0; i < L; ++i) {
                double pointsInInterval = (double)preimage.get(i).size();
                freq[i] = pointsInInterval / N;
            }

            return freq;
        }

        private Double[] computeFrequancyShifted(int delay, Double[] lastFreq) {
            int intervalNum = image[delay - 1];
            lastFreq[intervalNum] -= 1.0 / N;
            return lastFreq;
        }

        private Double[] computeJointFrequancy(int delay, int intervalNum) {

            Double[] pAB = new Double[L];
            for (int i = 0; i < L; ++i) {
                pAB[i] = 0.0;
            }

            Vector<Integer> times = preimage.get(intervalNum);
            for (int i = 0; i < times.size(); ++i) {
                int shifted = times.get(i) + delay;

                if (shifted < N) {
                    int index = image[shifted];
                    pAB[index] += 1.0 / N;
                }
            }
            return pAB;
        }

        private double I(int delay) {
            double res = 0;
            pB = computeFrequancyShifted(delay, pB);
            Double[][] pAB = new Double[L][L];

            for (int i = 0; i < L; ++i) {
                pAB[i] = computeJointFrequancy(delay, i);
            }

            for (int i = 0; i < L; ++i) {
                for (int j = 0; j < L; ++j) {
                    res += pow(pAB[i][j] - pA[i] * pB[j], 2);
                }
            }
            return res;
        }

        public int AMI()
        {
            //long timer = -System.currentTimeMillis();

            double[] arrAMI = new double [stepsAmount];
            for (int i = 0; i < stepsAmount; ++i) {
                arrAMI[i] = I(i + 1);
            }

            //timer += System.currentTimeMillis();
            //System.out.println("\nAMI time : " + timer);

        /**/
        //    double[] del = new double[stepsAmount];
        //    for (int i = 0; i < stepsAmount; ++i)
        //        del[i] = i + 1;
        /**/


        /*
            Plot2DPanel plot = new Plot2DPanel();
            plot.setAxisLabels("delay", "AMI value");

            plot.addLinePlot("Average mutual information. Lorenz", del, arrAMI);
            JFrame frame = new JFrame("");
            frame.setSize(600, 600);
            frame.setContentPane(plot);

            frame.setVisible(true);
        */
            delay = analyze(arrAMI);
            return delay;
        }

        private int analyze(double[] arr) {
            for (int i = 1; i < arr.length; ++i) {
                if (arr[i - 1] < arr[i] && arr[i] < arr[i + 1])
                    return i;
            }

        /*if monotone*/
            double val = 1 / Math.E;
            for (int i = 1; i < arr.length; ++i) {
                if (arr[i] / arr[0] < val)
                    return i;
            }
            return -1;
        }
    }

    @Override
    /*Rosenstein*/
    public IResult analyze(Map<String, List<DataEntry>> data) throws Exception {
        List<DataEntry> parameters = data.get("parameters");
        List<DataEntry> inputData = data.get("information");

        int delay = parameters.get(0).getDoubleValue().intValue();
        int dimension = parameters.get(1).getDoubleValue().intValue();
        int stepsAmount = parameters.get(2).getDoubleValue().intValue();
        double interval = parameters.get(3).getDoubleValue();

        Double[] information = new Double[inputData.size()];

        for (int i = 0; i < inputData.size(); ++i) {
            information[i] = inputData.get(i).getDoubleValue();
        }

        double lambda = Rosenstein(delay, dimension, stepsAmount, information, interval);
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, lambda));
    }

    private double[][] createAttractor(int delay, int dimension, Double[] data) {
        int len = data.length - (dimension - 1) * delay;
        double[][] attr = new double[len][dimension];
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < dimension; ++j) {
                attr[i][j] = data[i + j * delay];
            }
        }
        return attr;
    }

    /*Если размер данных не является степенью двойки, то дополнить нулями*/
    private double[] prepareForFourier(Double[] data) {
        /*Плохо! Надо переписать*/
        int m = (int)floor(log(data.length) / log(2));
        if (data.length != 1 << m) {
            ++m;
        }
        int len = (int) pow(2, m);
        double[] res = new double[len];
        for (int i = 0; i < len; ++i) {
            res[i] = (i < data.length)? data[i] : 0;
        }
        return res;
    }

    private double meanFrequancy(Double[] data) {
        double T = data.length;
        FastFourierTransformer fft = new FastFourierTransformer(/*DftNormalization.STANDARD*/);
        Complex[] res = fft.transform(prepareForFourier(data)/*, TransformType.FORWARD*/);

        double a = 0;
        double b = 0;
        for (int i = 0; i < data.length; ++i) {
            double I = pow(res[i].abs(), 2);
            a += (i / T) * I;
            b += I;
        }
        return a / b;
    }

    private double d(int i, Neibours neibours, double[][] attractor) {
        int first = neibours.i + i;
        int second = neibours.j + i;
        return (first < attractor.length && second < attractor.length) ?
                sqrt(SqrNormEuclid(attractor[first], attractor[second], attractor[0].length)) : -1;
    }

    private Neibours getNeibours(int i, double[][] attractor, int meanPeriod) {
        Neibours neibours = new Neibours(i, -1, Double.MAX_VALUE);
        for (int j = 0; j < attractor.length; ++j) {
            if (abs(i - j) > meanPeriod) {
                double norm = sqrt(SqrNormEuclid(attractor[i], attractor[j], attractor[0].length));
                if (norm < neibours.distance) {
                    neibours.distance = norm;
                    neibours.j = j;
                }
            }
        }
        return neibours;
    }

    private double SqrNormEuclid(double[] x, double[] y, int dim) {
        double norm = 0;
        for (int i = 0; i < dim; ++i) {
            norm += pow(x[i] - y[i], 2);
        }
        return norm;
    }

    private double Rosenstein(int delay, int dimension, int stepsAmount, Double[] data, double delta) {
        int N = data.length;
        int meanPeriod = (int) (floor(1.0 / meanFrequancy(data)) + 1);
        //System.out.println("Mean period : " + meanPeriod);

        double[][] attr = createAttractor(delay, dimension, data);
        int M = N - (dimension - 1) * delay;
        double[] distances = new double[stepsAmount];

        for (int i = 0; i < stepsAmount; ++i) {
            distances[i] = 0;
        }

        Vector<Neibours> nearests = new Vector<Neibours>(M / 2);

        /*Find nearests*/
        for (int j = 0; j < M; ++j) {
            Neibours neibours = getNeibours(j, attr, meanPeriod);
            nearests.add(neibours);

            if (neibours.distance >= EPS) {
                distances[0] += log(neibours.distance);
            }
        }

     // int amount = nearests.size();
        distances[0] /= M;

        for (int i = 1; i < stepsAmount; ++i) {
            int count = 0;
            for (int j = 0; j < M; ++j) {
                double d = d(i, nearests.get(j), attr);
                if (d <= EPS) {
                    count++;
                } else {
                    distances[i] += log(d);
                }
            }
            distances[i] /= M - count;
        }
        return leastSquares(distances, delta) / delta;
    }

    public double leastSquares(double[] distances, double delta) {
        SimpleRegression regression = new SimpleRegression();

        double[] x = new double[distances.length];
        for (int i = 0; i < distances.length; ++i) {
            x[i] = i * delta;
            regression.addData(i, distances[i]);
        }
        double slope = regression.getSlope();

        /*графики*/

        /*
        Plot2DPanel plot = new Plot2DPanel();
        plot.setAxisLabels("t", "<ln(d)>");
        plot.addLinePlot("Rosenstein", x, distances);
        JFrame frame = new JFrame("Distances");
        frame.setSize(600, 600);
        frame.setContentPane(plot);

        frame.setVisible(true);

        slope = regression.getSlope();
        regression.getIntercept();

        double[] y = new double[distances.length];
        for (int i = 0; i < distances.length; ++i)
            y[i] = regression.getIntercept() + x[i] * regression.getSlope() / delta;

        plot.addLinePlot("ss", x, y);*/
        return slope;
    }

    @Override
    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }
}






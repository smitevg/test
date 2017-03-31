package st.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


/**
 1   close past 1 min
 2              2
 3              3
 4              4
 5   close past 5 min
 6    delta     1
 7    delta     2
 8    delta     3
 9    delta     4
 10   delta     5


 */
public class Nero implements Serializable {


    public static void main(String [] s) throws IOException, ClassNotFoundException {

        NeuroModel.full();
        NeuroModel.save();
        Random r = new Random();
        int x = 100000;
        ArrayList<Bar> _bars = new ArrayList<>();
        for(int i=0; i<30; i++) {
            Bar b = new Bar();
            x = x + r.nextInt(700) - r.nextInt(700);
            b.close = x;
            _bars.add(b);
        }
        System.out.println( fun(0) );
        System.out.println( N_2(_bars) );

    }



    public double N_1(ArrayList<Bar> bars) {


        System.out.println(bars.get(4).close);
        System.out.println(bars.get(1).close);
        double [] input = new double[200];
        input[0] = bars.get(4).close;
        input[1] = bars.get(3).close;
        input[2] = bars.get(2).close;
        input[3] = bars.get(1).close;
        input[5] = bars.get(0).close;

        input[6]  = bars.get(4).max - bars.get(4).min;
        input[7]  = bars.get(3).max - bars.get(3).min;
        input[8]  = bars.get(2).max - bars.get(2).min;
        input[9]  = bars.get(1).max - bars.get(1).min;
        input[10] = bars.get(0).max - bars.get(0).min;

        return N(input);


    }

    //15 минуток
    public static double N_2(ArrayList<Bar> bars) {
        double [] input = new double[200];

        int baza = bars.get(0).close;
        for (int i=1; i<15; i++) {
            input[i-1] = ((input[i] - input[i-1]) / input[i]) * 10;
        }



        return N(input);


    }


    public static double N(double[] f) {

        double[] v1 = new double[NeuroModel.RAZM];
        double[] vr = new double[NeuroModel.RAZM];


        for (int i = 0; i<NeuroModel.RAZM; i++) {
            v1[i] = f[i];
        }


        for (int i = 0; i<NeuroModel.RAZM; i++) {
            v1[i] = fun(v1[i]);
        }

        //1
        for (int x1 = 0; x1<NeuroModel.RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<NeuroModel.RAZM; y1++) {
                d = d + v1[y1]*NeuroModel.l1[x1][y1];
            }
            vr[x1] = fun(d);
        }

        //2
        for (int x1 = 0; x1 < NeuroModel.RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<NeuroModel.RAZM; y1++) {
                d = d + vr[y1]*NeuroModel.l2[x1][y1];
            }
            v1[x1] = fun(d);
        }

        //3
        for (int x1 = 0; x1 < NeuroModel.RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<NeuroModel.RAZM; y1++) {
                d = d + v1[y1]*NeuroModel.l3[x1][y1];
            }
            vr[x1] = fun(d);
        }
        //4
        for (int x1 = 0; x1 < NeuroModel.RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<NeuroModel.RAZM; y1++) {
                d = d + vr[y1]*NeuroModel.l4[x1][y1];
            }
            v1[x1] = d;
        }
        return v1[1];
    }

    public static double fun(double f) {
        return tan(f);
    }

    public static double sig(double f) {
        return (1/( 1 + Math.pow(Math.E,(-1*f))));
    }

    public static double tan(double f) {
        //return Math.pow(Math.E,(2*f) - 1) / (Math.pow(Math.E,(2*f)) + 1);
        return (Math.pow(Math.E, f)  - Math.pow(Math.E, -f) ) / (Math.pow(Math.E, f)  + Math.pow(Math.E, -f) );
    }

}

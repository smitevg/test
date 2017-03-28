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
    public final static int RAZM = 200;



    private float[][] l1 = new float[RAZM][RAZM];
    private float[][] l2 = new float[RAZM][RAZM];
    private float[][] l3 = new float[RAZM][RAZM];
    private float[][] l4 = new float[RAZM][RAZM];

    public static void main(String [] s) throws IOException, ClassNotFoundException {
        Nero nero = Nero.load();
        Nero.save(nero);
    }

    public static void save(Nero n) throws IOException {
        FileOutputStream fos = new FileOutputStream("d:\\nero.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(n);
        oos.flush();
        oos.close();
    }

    public static Nero load() throws IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream("d:\\nero.out");
        ObjectInputStream oos = new ObjectInputStream(fos);
        Nero n = (Nero) oos.readObject();
        oos.close();
        return n;
    }

    public static void full(Nero n) {
        Random r = new Random();
        for (int x1= 0; x1<RAZM; x1++) {
            for (int y1 = 0; y1 < RAZM; y1++) {
                n.l1[x1][y1] = r.nextInt(400);
                n.l2[x1][y1] = r.nextInt(400);
                n.l3[x1][y1] = r.nextInt(400);
                n.l4[x1][y1] = r.nextInt(400);
            }
        }
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


    public double N(double[] f) {

        double[] v1 = new double[RAZM];
        double[] vr = new double[RAZM];

        Random r = new Random();
        for (int i = 0; i<RAZM; i++) {
            v1[i] = (double) r.nextInt();
        }


        for (int i = 0; i<RAZM; i++) {
            v1[i] = sig(v1[i]);
        }

        //1
        for (int x1 = 0; x1<RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<RAZM; y1++) {
                d = d + v1[y1]*l1[x1][y1];
            }
            vr[x1] = sig(d);
        }

        //2
        for (int x1 = 0; x1 < RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<RAZM; y1++) {
                d = d + vr[y1]*l2[x1][y1];
            }
            v1[x1] = sig(d);
        }

        //3
        for (int x1 = 0; x1 < RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<RAZM; y1++) {
                d = d + v1[y1]*l3[x1][y1];
            }
            vr[x1] = sig(d);
        }
        //4
        for (int x1 = 0; x1 < RAZM; x1++) {

            double d = 0;
            for (int y1 = 0; y1<RAZM; y1++) {
                d = d + vr[y1]*l4[x1][y1];
            }
            v1[x1] = d;
        }
        return v1[1];
    }


    public static double sig(double f) {

        return (1/( 1 + Math.pow(Math.E,(-1*f))));
    }

}

package st.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sbt-gubarev-ea on 30/03/2017.
 */
public class NeuroModel {
    public final static int RAZM = 200;



    public static float[][] l1 = new float[RAZM][RAZM];
    public static float[][] l2 = new float[RAZM][RAZM];
    public static float[][] l3 = new float[RAZM][RAZM];
    public static float[][] l4 = new float[RAZM][RAZM];



    public static void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("d:\\nero.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        float[][][] d = new float[4][RAZM][RAZM];
        d[0] = l1;
        d[1] = l2;
        d[2] = l3;
        d[3] = l4;

        oos.writeObject(d);
        oos.flush();
        oos.close();
    }

    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream("d:\\nero.out");
        ObjectInputStream oos = new ObjectInputStream(fos);

        float[][][] d = new float[4][RAZM][RAZM];

        d = (float[][][]) oos.readObject();
        oos.close();
        l1 = d[0];
        l2 = d[1];
        l3 = d[2];
        l4 = d[3];


    }

    public static void full( ) {
        Random r = new Random();

        for (int x1= 0; x1< NeuroModel.RAZM; x1++) {
            for (int y1 = 0; y1 < NeuroModel.RAZM; y1++) {
                NeuroModel.l1[x1][y1] = r.nextFloat();
                NeuroModel.l2[x1][y1] = r.nextFloat();
                NeuroModel.l3[x1][y1] = r.nextFloat();
                NeuroModel.l4[x1][y1] = r.nextFloat();
            }
        }
    }
}

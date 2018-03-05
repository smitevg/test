import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;


@SpringBootApplication
public class Main {

    static Connection conn = null;

    public static void main(String[] args) {


        try {
            Class.forName ("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        try {
            conn = DriverManager.getConnection ("jdbc:h2:D:/test", "sa", "");
            //conn.prepareStatement("create table ta (wa VARCHAR , wr varchar, b_amer blob, b_uk, s number)").execute();
            //conn.prepareStatement("alter table ta drop b").execute();
            //conn.prepareStatement("alter table ta add b_amer blob").execute();
            //conn.prepareStatement("alter table ta add b_uk blob").execute();

           /* File file = new File("d:/w2.txt");
            FileInputStream f = new FileInputStream(file);

            InputStreamReader ir = new InputStreamReader(f);
            BufferedReader in = new BufferedReader(ir);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.length() > 5) {


                    System.out.println(inputLine);
                    PreparedStatement ps = conn.prepareStatement("insert into ta(wa, wr) values (?, ?)");
                    String[] ar = inputLine.split("-");
                    ps.setString(1, ar[0].trim());
                    ps.setString(2, ar[1].trim());
                    ps.execute();
                }
            }
            conn.commit();
            in.close();*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            load();
         //   getOggBytes();
//            getFile();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static String get(String word) throws IOException {
        URL oracle = new URL("http://dictionary.cambridge.org/dictionary/english-russian/"+word);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.120", 8080));

        BufferedReader in = new BufferedReader(
            new InputStreamReader(oracle.openConnection(proxy).getInputStream()));

        String inputLine;
        String res = null;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains(word)) {
                if (inputLine.contains("data-src-ogg")) {
                    res = inputLine;
                    System.out.println(res);

                    int i1 = res.lastIndexOf("data-src-ogg=\"https:");
                    int i2 = res.lastIndexOf(".ogg\"");
                    String res2 = res.substring(i1+19, i2+4);
                    res2 = "http"+res2;
                    System.out.println(res2);

                    if (res.contains("listen to British English pronunciation")) {
                        byte [] b = getOggBytes(res2);
                        PreparedStatement st = null;
                        try {
                            st = conn.prepareStatement("update ta set b_uk = ? , s = 1 where wa = ?");
                            st.setBytes(1, b);
                            st.setString(2, word);
                            st.execute();
                            conn.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (res.contains("listen to American pronunciation")) {
                        byte [] b = getOggBytes(res2);
                        PreparedStatement st = null;
                        try {
                            st = conn.prepareStatement("update ta set b_amer = ? , s = 1 where wa = ?");
                            st.setBytes(1, b);
                            st.setString(2, word);
                            st.execute();
                            conn.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

        in.close();
        return res;
    }


    public static void load() throws IOException, SQLException {

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select wa from ta where s is null");
        rs.first();
        int i = 0;
        while (rs.next()) {
            String str = rs.getString(1);
            if (!str.contains(" ")) {
                get(str);
                i++;
                if (i > 1500) {
                    System.exit(0);
                }
            }
        }

    }

    public static void getFile() throws IOException, SQLException {

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select wa from ta where s is null");
        rs.first();

        Blob b = rs.getBlob(1);

        byte [] by = new byte[1024*500];

        int l = b.getBinaryStream().read(by);

        File f1 = new File("d:/by2.ogg");
        OutputStream out = new FileOutputStream(f1);
        out.write(by,0, l);

        out.close();

    }

    // получение байтов
    public static byte[] getOggBytes(String u) throws IOException {
        byte[] b = new byte[1024*500];
        //URL oracle = new URL("http://dictionary.cambridge.org/media/english-russian/us_pron_ogg/h/hom/home_/home.ogg");
        URL oracle = new URL(u);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.120", 8080));

        InputStream in = oracle.openConnection(proxy).getInputStream();

        new File("d:/x").delete();
        Files.copy(in, Paths.get("d:/x"));
        File f1 = new File("d:/x");
        InputStream out = new FileInputStream(f1);
        int l = out.read(b);
        b = Arrays.copyOf(b, l);
        out.close();
        in.close();
        return b;
        /*PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into ta(w, b) values(?, ?)");
            st.setString(1, "home2");
            st.setBytes(2, b);
            st.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    }


}

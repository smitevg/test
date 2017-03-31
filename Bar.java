package st.model;

import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smit on 26.03.2017.
 */
public class Bar implements Serializable{

    public static final SimpleDateFormat SIMPLE_DATE_BAR_FORMAT = new SimpleDateFormat("YYYY.MM.dd HH:mm");

    public String date = null;
    public int open = 0;
    public int close = 0;
    public int min = 0;
    public int max = 0;
    public int volumn = 0;



}

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


    public void addTick(Tick tick) {
        if (this.date == null) {
            this.date = tick.dateMinutes;
            this.open = tick.last;
            this.close = tick.last;
            this.min = tick.last;
            this.max = tick.last;
            this.volumn = tick.volume;
        } else {
            if (this.date.equals( tick.dateMinutes )) {
                this.volumn = this.volumn + tick.volume;
                this.close = tick.last;
                if (this.min > tick.last) this.min = tick.last;
                if (this.max < tick.last) this.min = tick.last;
            } else {
                throw new RuntimeException("BAR DATA TICK");
            }
        }
    }
}

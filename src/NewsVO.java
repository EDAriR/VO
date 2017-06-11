import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Java on 2017/6/8.
 */
public class NewsVO implements Serializable {
    private String new_no;       //Not Null (PK)
    private Timestamp new_date;  //Not Null
    private String new_title;    //Not Null
    private String new_cnt;      //Not Null

    public NewsVO() {
        super();
    }

    public String getNew_no() {
        return new_no;
    }

    public void setNew_no(String new_no) {
        this.new_no = new_no;
    }

    public Timestamp getNew_date() {
        return new_date;
    }

    public void setNew_date(Timestamp new_date) {
        this.new_date = new_date;
    }

    public String getNew_title() {
        return new_title;
    }

    public void setNew_title(String new_title) {
        this.new_title = new_title;
    }

    public String getNew_cnt() {
        return new_cnt;
    }

    public void setNew_cnt(String new_cnt) {
        this.new_cnt = new_cnt;
    }
}

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Java on 2017/6/8.
 */
public class User_ReportVO implements Serializable {

    private String mem_no_ed;     //	Not Null (PK) (FK)
    private String mem_no_ing;    //	Not Null (PK) (FK)
    private String urpt_cnt;      //	Not Null
    private Timestamp urpt_date;  //	Not Null
    private String urpt_rsn;      //	Not Null
    private String urpt_is_cert;  //	"Not Null0¡G§_1¡G¬O"
    private String urpt_unrsn;    //

    public User_ReportVO() {
        super();
    }

    public String getMem_no_ed() {
        return mem_no_ed;
    }

    public void setMem_no_ed(String mem_no_ed) {
        this.mem_no_ed = mem_no_ed;
    }

    public String getMem_no_ing() {
        return mem_no_ing;
    }

    public void setMem_no_ing(String mem_no_ing) {
        this.mem_no_ing = mem_no_ing;
    }

    public String getUrpt_cnt() {
        return urpt_cnt;
    }

    public void setUrpt_cnt(String urpt_cnt) {
        this.urpt_cnt = urpt_cnt;
    }

    public Timestamp getUrpt_date() {
        return urpt_date;
    }

    public void setUrpt_date(Timestamp urpt_date) {
        this.urpt_date = urpt_date;
    }

    public String getUrpt_rsn() {
        return urpt_rsn;
    }

    public void setUrpt_rsn(String urpt_rsn) {
        this.urpt_rsn = urpt_rsn;
    }

    public String getUrpt_is_cert() {
        return urpt_is_cert;
    }

    public void setUrpt_is_cert(String urpt_is_cert) {
        this.urpt_is_cert = urpt_is_cert;
    }

    public String getUrpt_unrsn() {
        return urpt_unrsn;
    }

    public void setUrpt_unrsn(String urpt_unrsn) {
        this.urpt_unrsn = urpt_unrsn;
    }
}

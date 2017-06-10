import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Java on 2017/6/8.
 */
public class NewsVO implements Serializable {
    private String new_No;       //Not Null (PK)
    private Timestamp new_Date;  //Not Null
    private String new_Title;    //Not Null
    private String new_Cnt;      //Not Null

    public NewsVO(Integer new_No, Date new_Date, String new_Title, String new_Cnt) {
        super();
    }

    public String getNew_No() {
        return new_No;
    }

    public void setNew_No(String new_No) {
        this.new_No = new_No;
    }

    public Timestamp getNew_Date() {
        return new_Date;
    }

    public void setNew_Date(Timestamp new_Date) {
        this.new_Date = new_Date;
    }

    public String getNew_Title() {
        return new_Title;
    }

    public void setNew_Title(String new_Title) {
        this.new_Title = new_Title;
    }

    public String getNew_Cnt() {
        return new_Cnt;
    }

    public void setNew_Cnt(String new_Cnt) {
        this.new_Cnt = new_Cnt;
    }
}

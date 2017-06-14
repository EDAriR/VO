package bbq.chat.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Java on 2017/6/8.
 */
public class Chat_RecordVO implements Serializable {

    private String cr_no;    //Not Null (PK)
    private String cf_no;    //(FK)
    private String cg_no;    //(FK)
    private Date cr_date;    //Not Null
    private String cr_cnt;   //Not Null

    public Chat_RecordVO() {
        super();
    }

    public String getCr_no() {
        return cr_no;
    }

    public void setCr_no(String cr_no) {
        this.cr_no = cr_no;
    }

    public String getCf_no() {
        return cf_no;
    }

    public void setCf_no(String cf_no) {
        this.cf_no = cf_no;
    }

    public String getCg_no() {
        return cg_no;
    }

    public void setCg_no(String cg_no) {
        this.cg_no = cg_no;
    }

    public Date getCr_date() {
        return cr_date;
    }

    public void setCr_date(Date cr_date) {
        this.cr_date = cr_date;
    }

    public String getCr_cnt() {
        return cr_cnt;
    }

    public void setCr_cnt(String cr_cnt) {
        this.cr_cnt = cr_cnt;
    }
}

package bbq.chat.model;

import java.io.Serializable;

/**
 * Created by Java on 2017/6/8.
 */
public class Chat_NotebookVO implements Serializable {
    private String cnb_no;    //	Not Null (PK)
    private String cf_no;     //	(FK)
    private String cg_no;     //	(FK)
    private String cnb_cnt;   //
//    private byte[] cnb_pic;   //

    public Chat_NotebookVO() {
        super();
    }

    public String getCnb_no() {
        return cnb_no;
    }

    public void setCnb_no(String cnb_no) {
        this.cnb_no = cnb_no;
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

    public String getCnb_cnt() {
        return cnb_cnt;
    }

    public void setCnb_cnt(String cnb_cnt) {
        this.cnb_cnt = cnb_cnt;
    }
}

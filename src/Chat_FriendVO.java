import java.io.Serializable;

/**
 * Created by Java on 2017/6/8.
 */
public class Chat_FriendVO implements Serializable {
    private String cf_no;       //	Not Null (PK)
    private String mem_no_s;    //	(FK)
    private String mem_no_o;    //	(FK)
    private String cf_is_del;   //	"Not Null0：否 (預設)1：是"

    public Chat_FriendVO() {
        super();
    }

    public String getCf_no() {
        return cf_no;
    }

    public void setCf_no(String cf_no) {
        this.cf_no = cf_no;
    }

    public String getMem_no_s() {
        return mem_no_s;
    }

    public void setMem_no_s(String mem_no_s) {
        this.mem_no_s = mem_no_s;
    }

    public String getMem_no_o() {
        return mem_no_o;
    }

    public void setMem_no_o(String mem_no_o) {
        this.mem_no_o = mem_no_o;
    }

    public String getCf_is_del() {
        return cf_is_del;
    }

    public void setCf_is_del(String cf_is_del) {
        this.cf_is_del = cf_is_del;
    }
}

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Java on 2017/6/8.
 */
public class Chat_GroupVO implements Serializable {
    private String cg_no;       //	Not Null (PK)
    private String cg_name;     //	Not Null
    private Date cg_year;       //	Not Null
    private String cg_is_aa;    //	"Not Null0：否1：是"
    private String cg_is_ar;    //	"Not Null0：否1：是"
    private String cg_is_ab;    //	"Not Null0：否1：是"
    private String cg_is_ac;    //	"Not Null0：否1：是"
    private String cg_is_sf;    //	"Not Null1：是"
    private String cg_is_ad;    //	"Not Null0：否1：是"
    private String baby_rd;    //	"Not Null0：否1：是"

    public Chat_GroupVO() {
        super();
    }

    public String getCg_no() {
        return cg_no;
    }

    public void setCg_no(String cg_no) {
        this.cg_no = cg_no;
    }

    public String getCg_name() {
        return cg_name;
    }

    public void setCg_name(String cg_name) {
        this.cg_name = cg_name;
    }

    public Date getCg_year() {
        return cg_year;
    }

    public void setCg_year(Date cg_year) {
        this.cg_year = cg_year;
    }

    public String getCg_is_aa() {
        return cg_is_aa;
    }

    public void setCg_is_aa(String cg_is_aa) {
        this.cg_is_aa = cg_is_aa;
    }

    public String getCg_is_ab() {
        return cg_is_ab;
    }

    public void setCg_is_ab(String cg_is_ab) {
        this.cg_is_ab = cg_is_ab;
    }

    public String getCg_is_ac() {
        return cg_is_ac;
    }

    public void setCg_is_ac(String cg_is_ac) {
        this.cg_is_ac = cg_is_ac;
    }

    public String getCg_is_sf() {
        return cg_is_sf;
    }

    public void setCg_is_sf(String cg_is_sf) {
        this.cg_is_sf = cg_is_sf;
    }

    public String getCg_is_ad() {
        return cg_is_ad;
    }

    public void setCg_is_ad(String cg_is_ad) {
        this.cg_is_ad = cg_is_ad;
    }

    public String getCg_is_ar() {
        return cg_is_ar;
    }

    public void setCg_is_ar(String cg_is_ar) {
        this.cg_is_ar = cg_is_ar;
    }

    public String getBaby_rd() {
        return baby_rd;
    }

    public void setBaby_rd(String baby_rd) {
        this.baby_rd = baby_rd;
    }
}
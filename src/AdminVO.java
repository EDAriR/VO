import java.io.Serializable;
import java.util.List;

/**
 * Created by Java on 2017/6/8.
 */
public class AdminVO implements Serializable {
    private String adm_no;     //	Not Null (PK)
    private String adm_acct;   //	Not Null
    private String adm_pwd;    //	Not Null
    private String adm_name;   //	Not Null
    private String adm_mail;   //	Not Null

    public AdminVO() {
        super();
    }

    public String getAdm_no() {
        return adm_no;
    }

    public void setAdm_no(String adm_no) {
        this.adm_no = adm_no;
    }

    public String getAdm_acct() {
        return adm_acct;
    }

    public void setAdm_acct(String adm_acct) {
        this.adm_acct = adm_acct;
    }

    public String getAdm_pwd() {
        return adm_pwd;
    }

    public void setAdm_pwd(String adm_pwd) {
        this.adm_pwd = adm_pwd;
    }

    public String getAdm_name() {
        return adm_name;
    }

    public void setAdm_name(String adm_name) {
        this.adm_name = adm_name;
    }

    public String getAdm_mail() {
        return adm_mail;
    }

    public void setAdm_mail(String adm_mail) {
        this.adm_mail = adm_mail;
    }

}

package bbq.admin.model;

import java.io.Serializable;

/**
 * Created by Java on 2017/6/8.
 */
public class Admin_AuthorityVO implements Serializable {
    private String adm_no;     //	Not Null (PK) (FK)
    private String auth_no;    //	Not Null (PK) (FK)

    public Admin_AuthorityVO() {
        super();
    }

    public String getAdm_no() {
        return adm_no;
    }

    public void setAdm_no(String adm_no) {
        this.adm_no = adm_no;
    }

    public String getAuth_no() {
        return auth_no;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }
}

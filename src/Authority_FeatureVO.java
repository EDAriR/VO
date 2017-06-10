import java.io.Serializable;

/**
 * Created by Java on 2017/6/8.
 */
public class Authority_FeatureVO implements Serializable {

    private String auth_no;      //	Not Null (PK)
    private String auth_name;    //	Not Null

    public Authority_FeatureVO() {
        super();
    }

    public String getAuth_no() {
        return auth_no;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }
}

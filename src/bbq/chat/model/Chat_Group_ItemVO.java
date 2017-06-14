package bbq.chat.model;

import java.io.Serializable;

/**
 * Created by Java on 2017/6/8.
 */
public class Chat_Group_ItemVO implements Serializable {
    private String cg_no;    //	Not Null (PK) (FK)
    private String mem_no;   //	Not Null (PK) (FK)

    public Chat_Group_ItemVO() {
        super();
    }

    public String getCg_no() {
        return cg_no;
    }

    public void setCg_no(String cg_no) {
        this.cg_no = cg_no;
    }

    public String getMem_no() {
        return mem_no;
    }

    public void setMem_no(String mem_no) {
        this.mem_no = mem_no;
    }
}

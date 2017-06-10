import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface Chat_Group_ItemDAO_interface {

    public void insert(Chat_Group_ItemVO Chat_Group_ItemVO);

    public void update(Chat_Group_ItemVO Chat_Group_ItemVO);

    public void delete(String cg_no,String mem_no);

    public Chat_Group_ItemVO findByPrimaryKey(String cg_no,String mem_no);

    public List<Chat_Group_ItemVO> getAll();

}

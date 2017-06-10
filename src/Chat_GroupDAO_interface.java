import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface Chat_GroupDAO_interface {

    public void insert(Chat_GroupVO Chat_GroupVO);

    public void update(Chat_GroupVO Chat_GroupVO);

    public void delete(String cg_no);

    public Chat_GroupVO findByPrimaryKey(String cg_no);

    public List<Chat_GroupVO> getAll();

}

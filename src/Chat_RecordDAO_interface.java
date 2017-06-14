import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface Chat_RecordDAO_interface {

    public void insert(Chat_RecordVO Chat_RecordVO);
    public void update(Chat_RecordVO Chat_RecordVO);
    public void delete(String cr_no);
    public Chat_RecordVO findByPrimaryKey(String cr_no);
    public List<Chat_RecordVO> getAll();

}

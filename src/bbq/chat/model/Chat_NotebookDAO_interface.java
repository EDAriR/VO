package bbq.chat.model;

import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface Chat_NotebookDAO_interface {

    public void insert(Chat_NotebookVO Chat_NotebookVO);

    public void update(Chat_NotebookVO Chat_NotebookVO);

    public void delete(String cnb_no);

    public Chat_NotebookVO findByPrimaryKey(String cnb_no);

    public List<Chat_NotebookVO> getAll();

}

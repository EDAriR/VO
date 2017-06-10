import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface AdminDAO_interface {

    public void insert(Admin_AuthorityVO Admin_AuthorityVO);

    public void update(Admin_AuthorityVO Admin_AuthorityVO);

    public void delete(String adm_no);

    public AdminDAO_interface findByPrimaryKey(String adm_no);

    public List<Admin_AuthorityVO> getAll();

}

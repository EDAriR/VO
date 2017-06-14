package bbq.admin.model;

import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface AdminDAO_interface {

    public void insert(AdminVO AdminVO);

    public void update(AdminVO AdminVO);

    public void delete(String adm_no);

    public AdminVO findByPrimaryKey(String adm_no);

    public List<AdminVO> getAll();

}

package bbq.admin.model;

import java.util.List;

/**
 * Created by Java on 2017/6/10.
 */
public interface Authority_FeatureDAO_interface {

    public void insert(Authority_FeatureVO Authority_FeatureVO);

    public void update(Authority_FeatureVO Authority_FeatureVO);

    public void delete(String proc_no);

    public Authority_FeatureVO findByPrimaryKey(String auth_no);

    public List<Authority_FeatureVO> getAll();

}

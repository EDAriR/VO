import java.sql.Timestamp;
import java.util.List;


public class NewsService {

    private NewsDAO_interface dao;

    public NewsService() {
        dao = new NewsDAO();
    }

    public NewsVO addNews(String new_no, Timestamp new_date, String new_title,
                                 String new_cnt) {

        NewsVO newsVO = new NewsVO();

        newsVO.setNew_no(new_no);
        newsVO.setNew_date(new_date);
        newsVO.setNew_title(new_title);
        newsVO.setNew_cnt(new_cnt);
        dao.insert(newsVO);

        return newsVO;
    }

    public NewsVO updateNews(String new_no, String new_title, String new_cnt) {

        NewsVO newsVO = new NewsVO();

        newsVO.setNew_no(new_no);
        newsVO.setNew_title(new_title);
        newsVO.setNew_cnt(new_cnt);

        dao.update(newsVO);

        return newsVO;
    }

    public void deleteNews(String new_no) {
        dao.delete(new_no);
    }

    public NewsVO getOneNews(String new_no) {
        return dao.findByPrimaryKey(new_no);
    }

    public List<NewsVO> getAll() {
        return dao.getAll();
    }
}

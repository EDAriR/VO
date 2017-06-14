import java.util.*;
import java.sql.*;


public class NewsJDBCDAO implements NewsDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO News (NEW_NO, NEW_DATE, NEW_TITLE, NEW_CNT) "
            + "VALUES ('n'||LPAD(to_char(NEW_NO_SEQ.nextval),4,'0'),"
            + " CURRENT_TIMESTAMP, ?, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT new_no , new_title FROM News";
    private static final String GET_ONE_STMT = "SELECT new_no, new_title FROM News where new_no = ?";
    // �R�����
    private static final String DELETE_NEWS = "DELETE FROM News where new_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE News set new_title=? where new_no = ?";


    @Override
    public void insert(NewsVO newsVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] news = {"new_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, news); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�

            pstmt.setString(1, newsVO.getNew_title());
            pstmt.setString(2, newsVO.getNew_cnt());
            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void update(NewsVO newsVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);
            pstmt.setString(1, newsVO.getNew_title());
            pstmt.setString(2, newsVO.getNew_no());
            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void delete(String new_no) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 �]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_NEWS);
            pstmt.setString(1, new_no);
            pstmt.executeUpdate();

            // 2 �]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete NEWS" + new_no);

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3���]�w���exception�o�ͮɤ�catch�϶���
                    con.rollback();
                } catch (SQLException excep) {
                    throw new RuntimeException("rollback error occured. "
                            + excep.getMessage());
                }
            }
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public NewsVO findByPrimaryKey(String new_no) {

        NewsVO newsVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, new_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNew_no(rs.getString("new_no"));
                newsVO.setNew_title(rs.getString("new_title"));
            }

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return newsVO;
    }

    @Override
    public List<NewsVO> getAll() {

        List<NewsVO> list = new ArrayList<NewsVO>();
        NewsVO newsVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNew_no(rs.getString("new_no"));
                newsVO.setNew_title(rs.getString("new_title"));
                list.add(newsVO); // Store the row in the list
            }

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {

        NewsJDBCDAO dao = new NewsJDBCDAO();

        // �s�W
//        NewsVO newsVO1 = new NewsVO();
//        newsVO1.setNew_date(new Timestamp(System.currentTimeMillis()));
//        newsVO1.setNew_title("�ڭ̨Ӥ��Ϊ��j���_�_���^���P");
//        newsVO1.setNew_cnt("�]�ȳ��^���P");
//        dao.insert(newsVO1);
//        System.out.print("insert success");

        // �ק�
//        NewsVO newsVO2 = new NewsVO();
//        newsVO2.setNew_no("n0001");
//        newsVO2.setNew_title("�ק�ݬ�");
//        dao.update(newsVO2);
//        System.out.println("update");

        // �R��
//		dao.delete("n0001");
//		System.out.println("delete");

        // �d��
        NewsVO newsVO3 = dao.findByPrimaryKey("n0002");
        System.out.print(newsVO3.getNew_no() + ",");
        System.out.println(newsVO3.getNew_title());
        System.out.println("---------------------");

        // �d�߳���
        List<NewsVO> list = dao.getAll();
        for (NewsVO news : list) {
            System.out.print(news.getNew_no() + ",");
            System.out.print(news.getNew_title());
            System.out.println();
        }
    }
}
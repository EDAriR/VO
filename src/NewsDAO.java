import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NewsDAO implements NewsDAO_interface {
    // �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
    private static DataSource ds = null;

    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }// �s�W���
    private static final String INSERT_STMT = "INSERT INTO news (new_no, new_date, new_title, new_cnt) "
            + "VALUES ('n'||LPAD(TO_CHAR(new_no_seq.NEXTVAL),4,'0'),"
            + " CURRENT_TIMESTAMP, ?, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT new_no , new_title FROM News";
    private static final String GET_ONE_STMT = "SELECT new_no, new_title FROM News WHERE new_no = ?";
    // �R�����
    private static final String DELETE_NEWS = "DELETE FROM News WHERE new_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE News SET new_title=? WHERE new_no = ?";


    @Override
    public void insert(NewsVO newsVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            con = ds.getConnection();
            String[] news = {"new_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, news); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�

            pstmt.setString(1, newsVO.getNew_title());
            pstmt.setString(2, newsVO.getNew_cnt());
            pstmt.executeUpdate();

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

            con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE);
            pstmt.setString(1, newsVO.getNew_title());
            pstmt.setString(2, newsVO.getNew_no());
            pstmt.executeUpdate();

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

            con = ds.getConnection();

            // 1 �]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_NEWS);
            pstmt.setString(1, new_no);
            pstmt.executeUpdate();

            // 2 �]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete NEWS" + new_no);

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

            con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, new_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNew_no(rs.getString("new_no"));
                newsVO.setNew_title(rs.getString("new_title"));
            }
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
            con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNew_no(rs.getString("new_no"));
                newsVO.setNew_title(rs.getString("new_title"));
                list.add(newsVO); // Store the row in the list
            }
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
}
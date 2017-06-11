import java.util.*;
import java.sql.*;


public class NewsJDBCDAO implements NewsDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO News (NEW_NO, NEW_DATE, NEW_TITLE, NEW_CNT) " 
    		 + "VALUES ('n'||LPAD(to_char(NEW_NO_SEQ.nextval),4,'0'),"
    		 + " CURRENT_TIMESTAMP, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT new_no , new_title FROM News";
    private static final String GET_ONE_STMT = "SELECT new_no, new_title FROM News where new_no = ?";
    // 刪除資料
    private static final String DELETE_NEWS = "DELETE FROM News where new_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE News set new_title=? where new_no = ?";


    @Override
    public void insert(NewsVO newsVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"new_no"}; // 有使用sequence產生編號的話才要寫
            java.util.Date today = new java.util.Date();
        	
            pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
            
            pstmt.setTimestamp(0, newsVO.getNew_date());
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
    public void delete(String new_no){

        int updateCount_PRODUCTs = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1●設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            // 先刪除商品(多) --->尚未建product，因此先註解
//			pstmt = con.prepareStatement(DELETE_PRODUCTs);
//			pstmt.setString(1, new_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // 再刪除商品類別(一)
            pstmt = con.prepareStatement(DELETE_NEWS);
            pstmt.setString(1, new_no);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除商品類別編號" + new_no + "時,共有商品" + updateCount_PRODUCTs
                    + "個同時被刪除");

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3●設定於當有exception發生時之catch區塊內
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

    public NewsVO findByPrimaryKey(String new_no){

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
                newsVO.setNew_title(rs.getString("proc_name"));
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

    public List<NewsVO> getAll(){

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
                newsVO.setNew_title(rs.getString("proc_name"));
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
        // 測試看看每個指令是否可以使用
        // 新增
        NewsVO newsVO1 = new NewsVO();
//        newsVO1.setNew_date(new Timestamp(System.currentTimeMillis()));
//        newsVO1.setNew_title("財務部回來嚕");
//        newsVO1.setNew_cnt("財務部回來嚕");
//        dao.insert(newsVO1);

        // 修改
        NewsVO newsVO2 = new NewsVO();
        newsVO2.setNew_no("n0007");
        newsVO2.setNew_title("修改看看");
		dao.update(newsVO2);

        // 刪除
//		dao.delete("1");

        // 查詢
//        NewsVO newsVO3 = dao.findByPrimaryKey("1");
//		System.out.print(newsVO3.getNew_no() + ",");
//		System.out.println(newsVO3.getNew_title());
//		System.out.println("---------------------");

        // 查詢部門
//		List<NewsVO> list = dao.getAll();
//		for (NewsVO proc : list) {
//			System.out.print(proc.getNew_no() + ",");
//			System.out.print(proc.getNew_title());
//			System.out.println();
//		}

    }
}
import java.util.*;
import java.sql.*;


public class User_ReportJDBCDAO implements User_ReportDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO User_Report (mem_no_ed, mem_no_ing, NEW_TITLE, NEW_CNT,mem_no_ing," +
            " urpt_cnt, urpt_date, urpt_rsn, urpt_is_cert, urpt_unrsn)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT mem_no_ed , new_title FROM User_Report";
    private static final String GET_ONE_STMT = "SELECT mem_no_ed, new_title FROM User_Report where mem_no_ed = ? and mem_no_ing =?";
    // 刪除資料
    private static final String DELETE_NEWS = "DELETE FROM User_Report where mem_no_ed = ? and mem_no_ing =?";
    // 修改資料
    private static final String UPDATE = "UPDATE User_Report set new_title=? where mem_no_ed = ? and mem_no_ing =?";


    @Override
    public void insert(User_ReportVO user_ReportVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
//            String[] cols = {"new_no"}; // 有使用sequence產生編號的話才要寫
            java.util.Date today = new java.util.Date();

//            pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數

            pstmt.setTimestamp(0, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(1, user_ReportVO.getMem_no_ed());
            pstmt.setString(2, user_ReportVO.getUrpt_cnt());

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
    public void update(User_ReportVO user_ReportVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, user_ReportVO.getMem_no_ed());
            pstmt.setString(2, user_ReportVO.getUrpt_cnt());

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
    public void delete(String mem_no_ed){

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
//			pstmt.setString(1, mem_no_ed);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // 再刪除商品類別(一)
            pstmt = con.prepareStatement(DELETE_NEWS);
            pstmt.setString(1, mem_no_ed);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除商品類別編號" + mem_no_ed + "時,共有商品" + updateCount_PRODUCTs
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

    @Override
    public User_ReportVO findByPrimaryKey(String mem_no_ed){

        User_ReportVO user_ReportVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, mem_no_ed);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                user_ReportVO = new User_ReportVO();
                user_ReportVO.setMem_no_ed(rs.getString("mem_no_ed"));
                user_ReportVO.setUrpt_cnt(rs.getString("urpt_cnt"));
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
        return user_ReportVO;
    }

    @Override
    public List<User_ReportVO> getAll(){

        List<User_ReportVO> list = new ArrayList<User_ReportVO>();
        User_ReportVO user_ReportVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                user_ReportVO = new User_ReportVO();
                user_ReportVO.setMem_no_ed(rs.getString("mem_no_ed"));
                user_ReportVO.setMem_no_ing(rs.getString("mem_no_ing"));
                user_ReportVO.setUrpt_cnt(rs.getString("urpt_cnt"));
                list.add(user_ReportVO); // Store the row in the list
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

        User_ReportJDBCDAO dao = new User_ReportJDBCDAO();
        // 測試看看每個指令是否可以使用
        // 新增
        NewsVO user_ReportVO1 = new NewsVO();
//        user_ReportVO1.setNew_date(new Timestamp(System.currentTimeMillis()));
//        user_ReportVO1.setNew_title("財務部回來嚕");
//        user_ReportVO1.setNew_cnt("財務部回來嚕");
//        dao.insert(newsVO1);

        // 修改
        User_ReportVO user_ReportVO2 = new User_ReportVO();
        user_ReportVO2.setMem_no_ing("n0007");
        user_ReportVO2.setUrpt_cnt("修改看看");
        dao.update(user_ReportVO2);

        // 刪除
//		dao.delete("1");

        // 查詢
//        User_ReportVO user_ReportVO3 = dao.findByPrimaryKey("1");
//		System.out.print(user_ReportVO3.getNew_no() + ",");
//		System.out.println(user_ReportVO3.getNew_title());
//		System.out.println("---------------------");

        // 查詢部門
//		List<User_ReportVO> list = dao.getAll();
//		for (User_ReportVO proc : list) {
//			System.out.print(proc.getNew_no() + ",");
//			System.out.print(proc.getNew_title());
//			System.out.println();
//		}

    }
}
import java.util.*;
import java.sql.*;

public class User_ReportJDBCDAO implements User_ReportDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    // private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO User_Report "
            + "(MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE, URPT_RSN, URPT_IS_CERT, URPT_UNRSN) "
            + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT mem_no_ed, URPT_CNT, URPT_RSN, URPT_IS_CERT FROM User_Report";
    private static final String GET_ONE_STMT = "SELECT mem_no_ed, URPT_CNT, URPT_RSN, URPT_IS_CERT "
            + "FROM User_Report where mem_no_ed = ?";
    // 刪除資料
    private static final String DELETE_NEWS = "DELETE FROM User_Report where mem_no_ed = ? and mem_no_ing =?";
    // 修改資料
    private static final String UPDATE = "UPDATE User_Report set URPT_IS_CERT=? where mem_no_ed = ? and mem_no_ing =?";

    @Override
    public void insert(User_ReportVO user_ReportVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(INSERT_STMT);

            // (MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE, URPT_RSN,
            // URPT_IS_CERT, URPT_UNRSN)
            pstmt.setString(1, user_ReportVO.getMem_no_ed());
            pstmt.setString(2, user_ReportVO.getMem_no_ing());
            pstmt.setString(3, user_ReportVO.getUrpt_cnt());
            pstmt.setString(4, user_ReportVO.getUrpt_rsn());
            pstmt.setString(5, user_ReportVO.getUrpt_is_cert());
            pstmt.setString(6, user_ReportVO.getUrpt_unrsn());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
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
            pstmt.setString(2, user_ReportVO.getMem_no_ing());
            pstmt.setString(3, user_ReportVO.getUrpt_is_cert());
            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
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
    public void delete(String mem_no_ed, String mem_no_ing) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_NEWS);
            pstmt.setString(1, mem_no_ed);
            pstmt.setString(1, mem_no_ing);
            pstmt.executeUpdate();

            // 2 設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete User Report :" + mem_no_ed);

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3 設定於當有exception發生時之catch區塊內
                    con.rollback();
                } catch (SQLException excep) {
                    throw new RuntimeException("rollback error occured. " + excep.getMessage());
                }
            }
            throw new RuntimeException("A database error occured. " + se.getMessage());
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
    public User_ReportVO findByPrimaryKey(String mem_no_ed) {

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
            throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
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
    public List<User_ReportVO> getAll() {

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
                user_ReportVO.setUrpt_cnt(rs.getString("urpt_cnt"));
                user_ReportVO.setUrpt_rsn(rs.getString("urpt_rsn"));
                list.add(user_ReportVO); // Store the row in the list
            }
//			 mem_no_ed, URPT_CNT, URPT_RSN, 

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
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

        // 新增(OK)
        // (MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE,
        // URPT_RSN, URPT_IS_CERT,URPT_UNRSN)

//		User_ReportVO user_ReportVO1 = new User_ReportVO();
//		user_ReportVO1.setMem_no_ed("M0000001");
//		user_ReportVO1.setMem_no_ing("M0000009");
//		user_ReportVO1.setUrpt_cnt("787846sas回");
//		user_ReportVO1.setUrpt_date(new Timestamp(System.currentTimeMillis()));
//		user_ReportVO1.setUrpt_rsn("亂碼留言");
//		user_ReportVO1.setUrpt_is_cert("0");
//		user_ReportVO1.setUrpt_unrsn("不要問你會怕");
//		dao.insert(user_ReportVO1);
//		System.out.println("insert OK");

        // 修改
        User_ReportVO user_ReportVO2 = new User_ReportVO();
        user_ReportVO2.setMem_no_ed("M0000001");
        user_ReportVO2.setMem_no_ing("M0000009");
        user_ReportVO2.setUrpt_is_cert("1");
        dao.update(user_ReportVO2);

        // 刪除
//         dao.delete("M0000001","M0000009");
//         System.out.println("delete");

        // 查詢
//		 User_ReportVO user_ReportVO3 = dao.findByPrimaryKey("M0000001");
//		 System.out.print(user_ReportVO3.getMem_no_ed() + ",");
//		 System.out.println(user_ReportVO3.getUrpt_is_cert());
//		 System.out.println("---------------------");

        // 查詢部門
//		 List<User_ReportVO> list = dao.getAll();
//		 for (User_ReportVO up : list) {
//		 System.out.print(up.getMem_no_ed() + ",");
//		 System.out.print(up.getUrpt_cnt());
//		 System.out.print(up.getUrpt_rsn());
//		 System.out.println();
//		 }
    }
}
import java.util.*;
import java.sql.*;


public class Chat_RecordJDBCDAO implements Chat_RecordDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Record " +
            "(cr_no, CR_DATE, CF_NO, CG_NO, cr_cnt) " +
            "VALUES ('cr'||LPAD(to_char(ADM_NO_SEQ.nextval),4,'0'), CURRENT_TIMESTAMP, ?, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT cr_no, CR_DATE, cr_cnt FROM Chat_Record";
    private static final String GET_ONE_STMT = "SELECT cr_no, CR_DATE, cr_cnt FROM Chat_Record where cr_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Chat_Record where cr_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE Chat_Record set cr_cnt=? where cr_no = ?";


    @Override
    public void insert(Chat_RecordVO chat_RecordVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cr = {"cr_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cr); // 有使用sequence產生編號的話才要寫第二個參數
            pstmt.setString(1, chat_RecordVO.getCf_no());
            pstmt.setString(2, chat_RecordVO.getCg_no());
            pstmt.setString(3, chat_RecordVO.getCr_cnt());
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
    public void update(Chat_RecordVO chat_RecordVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);
            pstmt.setString(1, chat_RecordVO.getCr_cnt());
            pstmt.setString(2, chat_RecordVO.getCr_no());
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
    public void delete(String cr_no) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cr_no);
            pstmt.executeUpdate();

            // 2 設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete Chat_Record : " + cr_no);

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3 設定於當有exception發生時之catch區塊內
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
    public Chat_RecordVO findByPrimaryKey(String cr_no) {

        Chat_RecordVO chat_RecordVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, cr_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_RecordVO = new Chat_RecordVO();
                chat_RecordVO.setCr_no(rs.getString("cr_no"));
                chat_RecordVO.setCr_cnt(rs.getString("cr_cnt"));
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
        return chat_RecordVO;
    }

    @Override
    public List<Chat_RecordVO> getAll() {

        List<Chat_RecordVO> list = new ArrayList<Chat_RecordVO>();
        Chat_RecordVO chat_RecordVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_RecordVO = new Chat_RecordVO();
                chat_RecordVO.setCr_no(rs.getString("cr_no"));
                chat_RecordVO.setCr_cnt(rs.getString("cr_cnt"));
                list.add(chat_RecordVO); // Store the row in the list
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

        Chat_RecordJDBCDAO dao = new Chat_RecordJDBCDAO();

        // 新增(OK)
//        Chat_RecordVO chat_RecordVO1 = new Chat_RecordVO();
//        chat_RecordVO1.setCr_cnt("null");
//        chat_RecordVO1.setCr_cnt("null");
//        chat_RecordVO1.setCr_cnt("聊天記錄測試*");
//        dao.insert(chat_RecordVO1);
//        System.out.println("新增成功");

        // 修改
//		Chat_RecordVO chat_RecordVO2 = new Chat_RecordVO();
//		chat_RecordVO2.setCr_no("cr0002");
//		chat_RecordVO2.setCr_cnt("修改看看");
//		dao.update(chat_RecordVO2);
//		System.out.println("update");

        // 刪除
//		dao.delete("cr0001");
//		System.out.println("delete");

        // 查詢
//		Chat_RecordVO chat_RecordVO3 = dao.findByPrimaryKey("cr0002");
//		System.out.print(chat_RecordVO3.getCr_no() + ",");
//		System.out.println(chat_RecordVO3.getCr_cnt());
//		System.out.println("---------------------");

        // 查詢部門
//		List<Chat_RecordVO> list = dao.getAll();
//		for (Chat_RecordVO cr : list) {
//			System.out.print(cr.getCr_no() + ",");
//			System.out.print(cr.getCr_cnt());
//			System.out.println();
//		}

    }
}
import java.util.*;
import java.sql.*;


public class Chat_GroupJDBCDAO implements Chat_GroupDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Group (cg_no, proc_name) VALUES (cg_no_seq.NEXTVAL, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT cg_no , cg_name FROM Chat_Group";
    private static final String GET_ONE_STMT = "SELECT cg_no, cg_name FROM Chat_Group where cg_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Chat_Group where cg_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE Chat_Group set cg_name=? where cg_no = ?";

    @Override
    public void insert(Chat_GroupVO chat_GroupVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = { "cg_no" }; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
            pstmt.setString(1, chat_GroupVO.getCg_name());

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
    public void update(Chat_GroupVO chat_GroupVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, chat_GroupVO.getCg_name());
            pstmt.setString(2, chat_GroupVO.getCg_no());

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
    public void delete(String cg_no) {
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
//			pstmt.setString(1, cg_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // 再刪除商品類別(一)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cg_no);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除商品類別編號" + cg_no + "時,共有商品" + updateCount_PRODUCTs
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
    public Chat_GroupVO findByPrimaryKey(String cg_no) {

        Chat_GroupVO chat_GroupVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, cg_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_GroupVO = new Chat_GroupVO();
                chat_GroupVO.setCg_no(rs.getString("cg_no"));
                chat_GroupVO.setCg_name(rs.getString("cg_name"));
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
        return chat_GroupVO;
    }

    @Override
    public List<Chat_GroupVO> getAll() {
        List<Chat_GroupVO> list = new ArrayList<Chat_GroupVO>();
        Chat_GroupVO chat_GroupVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_GroupVO = new Chat_GroupVO();
                chat_GroupVO.setCg_no(rs.getString("cg_no"));
                chat_GroupVO.setCg_name(rs.getString("cg_name"));
                list.add(chat_GroupVO); // Store the row in the list
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

        Chat_GroupJDBCDAO dao = new Chat_GroupJDBCDAO();
        // 測試看看每個指令是否可以使用
        // 新增
        Chat_GroupVO chat_GroupVO1 = new Chat_GroupVO();
        chat_GroupVO1.setCg_name("財務部回來嚕");
        dao.insert(chat_GroupVO1);

        // 修改
//		Chat_GroupVO chat_GroupVO2 = new Chat_GroupVO();
//		chat_GroupVO2.setCg_no("2");
//		chat_GroupVO2.setCg_name("修改看看");
//		dao.update(chat_GroupVO2);

        // 刪除
//		dao.delete("1");

        // 查詢
//		Chat_GroupVO chat_GroupVO3 = dao.findByPrimaryKey("1");
//		System.out.print(chat_GroupVO3.getCg_no() + ",");
//		System.out.println(chat_GroupVO3.getCg_name());
//		System.out.println("---------------------");

        // 查詢部門
//		List<Chat_GroupVO> list = dao.getAll();
//		for (Chat_GroupVO proc : list) {
//			System.out.print(proc.getCg_no() + ",");
//			System.out.print(proc.getCg_name());
//			System.out.println();
//		}

    }
}
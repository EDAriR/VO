import java.util.*;
import java.sql.*;


public class Chat_NotebookJDBCDAO implements Chat_NotebookDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Notebook (cnb_no, cnb_cnt) VALUES (cnb_no_seq.NEXTVAL, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT cnb_no , cnb_cnt FROM Chat_Notebook";
    private static final String GET_ONE_STMT = "SELECT cnb_no, cnb_cnt FROM Chat_Notebook where cnb_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Chat_Notebook where cnb_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE Chat_Notebook set proc_name=? where cnb_no = ?";


    @Override
    public void insert(Chat_NotebookVO chat_NotebookVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"cnb_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
            pstmt.setString(1, chat_NotebookVO.getCnb_no());

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
    public void update(Chat_NotebookVO chat_NotebookVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, chat_NotebookVO.getCnb_cnt());
            pstmt.setString(2, chat_NotebookVO.getCnb_no());

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
    public void delete(String cnb_no){

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
//			pstmt.setString(1, cnb_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // 再刪除商品類別(一)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cnb_no);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除商品類別編號" + cnb_no + "時,共有商品" + updateCount_PRODUCTs
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
    public Chat_NotebookVO findByPrimaryKey(String cnb_no){

        Chat_NotebookVO chat_NotebookVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, cnb_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_NotebookVO = new Chat_NotebookVO();
                chat_NotebookVO.setCnb_no(rs.getString("cnb_no"));
                chat_NotebookVO.setCnb_cnt(rs.getString("cnb_cnt"));
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
        return chat_NotebookVO;
    }

    @Override
    public List<Chat_NotebookVO> getAll(){

        List<Chat_NotebookVO> list = new ArrayList<Chat_NotebookVO>();
        Chat_NotebookVO chat_NotebookVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_NotebookVO = new Chat_NotebookVO();
                chat_NotebookVO.setCnb_no(rs.getString("cnb_no"));
                chat_NotebookVO.setCnb_cnt(rs.getString("cnb_cnt"));
                list.add(chat_NotebookVO); // Store the row in the list
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

        Chat_NotebookJDBCDAO dao = new Chat_NotebookJDBCDAO();
        // 測試看看每個指令是否可以使用
        // 新增
        Chat_NotebookVO chat_NotebookVO1 = new Chat_NotebookVO();
        chat_NotebookVO1.setCnb_cnt("財務部回來嚕");
        dao.insert(chat_NotebookVO1);

        // 修改
//		Chat_NotebookVO chat_NotebookVO2 = new Chat_NotebookVO();
//		chat_NotebookVO2.setProc_no("2");
//		chat_NotebookVO2.setProc_name("修改看看");
//		dao.update(chat_NotebookVO2);

        // 刪除
//		dao.delete("1");

        // 查詢
//		Chat_NotebookVO chat_NotebookVO3 = dao.findByPrimaryKey("1");
//		System.out.print(chat_NotebookVO3.getProc_no() + ",");
//		System.out.println(chat_NotebookVO3.getProc_name());
//		System.out.println("---------------------");

        // 查詢部門
//		List<Chat_NotebookVO> list = dao.getAll();
//		for (Chat_NotebookVO proc : list) {
//			System.out.print(proc.getCnb_no() + ",");
//			System.out.print(proc.getCnb_cnt());
//			System.out.println();
//		}

    }
}
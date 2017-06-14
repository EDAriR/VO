package bbq.chat.model;

import java.util.*;
import java.sql.*;


public class Chat_Group_ItemJDBCDAO implements Chat_Group_ItemDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Group_Item (cg_no, mem_no) " +
            "VALUES (?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT cg_no , mem_no FROM Chat_Group_Item";
    private static final String GET_ONE_STMT = "SELECT cg_no, mem_no FROM Chat_Group_Item where cg_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Chat_Group_Item where cg_no = ?";


    @Override
    public void insert(Chat_Group_ItemVO chat_Group_ItemVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            pstmt = con.prepareStatement(INSERT_STMT);
            pstmt.setString(1, chat_Group_ItemVO.getCg_no());
            pstmt.setString(2, chat_Group_ItemVO.getMem_no());
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
    public void delete(String cg_no, String mem_no) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cg_no);
            pstmt.executeUpdate();

            // 2 設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete Chat Group Item" + cg_no);

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
    public Chat_Group_ItemVO findByPrimaryKey(String cg_no, String mem_no) {

        Chat_Group_ItemVO chat_Group_ItemVO = null;
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
                chat_Group_ItemVO = new Chat_Group_ItemVO();
                chat_Group_ItemVO.setCg_no(rs.getString("cg_no"));
                chat_Group_ItemVO.setMem_no(rs.getString("mem_no"));
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
        return chat_Group_ItemVO;
    }

    public List<Chat_Group_ItemVO> getAll() {

        List<Chat_Group_ItemVO> list = new ArrayList<Chat_Group_ItemVO>();
        Chat_Group_ItemVO chat_Group_ItemVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_Group_ItemVO = new Chat_Group_ItemVO();
                chat_Group_ItemVO.setCg_no(rs.getString("cg_no"));
                chat_Group_ItemVO.setMem_no(rs.getString("mem_no"));
                list.add(chat_Group_ItemVO); // Store the row in the list
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

        Chat_Group_ItemJDBCDAO dao = new Chat_Group_ItemJDBCDAO();

        // 新增
//        bbq.chat.model.Chat_Group_ItemVO chat_Group_ItemVO1 = new bbq.chat.model.Chat_Group_ItemVO();
//        chat_Group_ItemVO1.setCg_no("cg002");
//        chat_Group_ItemVO1.setMem_no("M0000003");
//        dao.insert(chat_Group_ItemVO1);
//        System.out.println("新增成功");

        // 無法修改

        // 刪除
//		dao.delete("cg002","M0000003");
//		System.out.println("delete");

        // 查詢
        Chat_Group_ItemVO chat_Group_ItemVO3 = dao.findByPrimaryKey("cg002", "M0000003");
        System.out.print(chat_Group_ItemVO3.getCg_no() + ",");
        System.out.println(chat_Group_ItemVO3.getMem_no());
        System.out.println("---------------------");

        // 查詢部門
//		List<bbq.chat.model.Chat_Group_ItemVO> list = dao.getAll();
//		for (bbq.chat.model.Chat_Group_ItemVO cgi : list) {
//			System.out.print(cgi.getCg_no() + ",");
//			System.out.print(cgi.getMem_no());
//			System.out.println();
//		}

    }
}
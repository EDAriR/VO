package bbq.chat.model;

import java.util.*;
import java.sql.*;


public class Chat_FriendJDBCDAO implements Chat_FriendDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Friend (CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL) VALUES ('cf'||LPAD(to_char(CF_NO_SEQ.nextval),3,'0'), ?, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL FROM Chat_Friend";
    private static final String GET_ONE_STMT = "SELECT CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL FROM Chat_Friend where cf_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Chat_Friend where cf_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE Chat_Friend set cf_is_del=? where cf_no = ? ";


    @Override
    public void insert(Chat_FriendVO chat_FriendVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cf = {"cf_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cf); // 有使用sequence產生編號的話才要寫第二個參數
            pstmt.setString(1, chat_FriendVO.getMem_no_s());
            pstmt.setString(2, chat_FriendVO.getMem_no_o());
            pstmt.setString(3, chat_FriendVO.getCf_is_del());
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
    public void update(Chat_FriendVO chat_FriendVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);
            pstmt.setString(1, chat_FriendVO.getCf_is_del());
            pstmt.setString(2, chat_FriendVO.getCf_no());
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
    public void delete(String cf_no) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cf_no);
            pstmt.executeUpdate();

            // 2 設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete Chat Friend :" + cf_no);

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
    public Chat_FriendVO findByPrimaryKey(String cf_no) {

        Chat_FriendVO chat_FriendVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, cf_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_FriendVO = new Chat_FriendVO();
                chat_FriendVO.setCf_no(rs.getString("cf_no"));
                chat_FriendVO.setMem_no_o(rs.getString("mem_no_o"));
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
        return chat_FriendVO;
    }

    @Override
    public List<Chat_FriendVO> getAll() {

        List<Chat_FriendVO> list = new ArrayList<Chat_FriendVO>();
        Chat_FriendVO chat_FriendVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chat_FriendVO = new Chat_FriendVO();
                chat_FriendVO.setCf_no(rs.getString("cf_no"));
                chat_FriendVO.setMem_no_o(rs.getString("mem_no_o"));
                list.add(chat_FriendVO); // Store the row in the list
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

        Chat_FriendJDBCDAO dao = new Chat_FriendJDBCDAO();
        // 測試看看每個指令是否可以使用
        // 新增(OK)
//        bbq.chat.model.Chat_FriendVO chat_FriendVO1 = new bbq.chat.model.Chat_FriendVO();
//        chat_FriendVO1.setMem_no_s("M0000001");
//        chat_FriendVO1.setMem_no_o("M0000007");
//        chat_FriendVO1.setCf_is_del("0");
//        dao.insert(chat_FriendVO1);
//        System.out.println("新增成功");

        // 修改
        Chat_FriendVO chat_FriendVO2 = new Chat_FriendVO();
        chat_FriendVO2.setCf_no("cf002");
        chat_FriendVO2.setCf_is_del("1");
        dao.update(chat_FriendVO2);
        
        System.out.println("update");

        // 刪除
//        dao.delete("cf003");
//        System.out.println("delete");

        // 查詢
        Chat_FriendVO chat_FriendVO3 = dao.findByPrimaryKey("cf001");
        System.out.print(chat_FriendVO3.getCf_no() + ",");
        System.out.println(chat_FriendVO3.getMem_no_o());
        System.out.println("---------------------");

        // 查詢部門
        List<Chat_FriendVO> list = dao.getAll();
        for (Chat_FriendVO cf : list) {
            System.out.print(cf.getCf_no() + ",");
            System.out.print(cf.getMem_no_o());
            System.out.println();
        }
    }
}
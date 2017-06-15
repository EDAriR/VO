package bbq.chat.model;

import java.util.*;
import java.sql.*;


public class Chat_GroupJDBCDAO implements Chat_GroupDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Chat_Group " +
            "(cg_no, cg_name, cg_year, cg_is_ar, cg_is_ab, cg_is_ac, cg_is_sf, cg_is_ad, cg_baby_rd) " +
            "VALUES ('cg'||LPAD(to_char(cg_no_seq.NEXTVAL),3,'0'), ?, ?, ?, ?, ?, ?, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT cg_no , cg_name FROM chat_group";
    private static final String GET_ONE_STMT = "SELECT cg_no, cg_name FROM chat_group WHERE cg_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM chat_group WHERE cg_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE chat_group SET cg_name=? WHERE cg_no = ?";

    @Override
    public void insert(Chat_GroupVO chat_GroupVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
//        	(cg_no, CG_NAME, CG_YEAR, CG_IS_AR, CG_IS_AB, CG_IS_AC, CG_IS_SF, CG_IS_AD, BABY_RD
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cg = {"cg_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cg); // 有使用sequence產生編號的話才要寫第二個參數
            pstmt.setString(1, chat_GroupVO.getCg_name());
            pstmt.setDate(2, chat_GroupVO.getCg_year());
            pstmt.setString(3, chat_GroupVO.getCg_is_ar());
            pstmt.setString(4, chat_GroupVO.getCg_is_ab());
            pstmt.setString(5, chat_GroupVO.getCg_is_ac());
            pstmt.setString(6, chat_GroupVO.getCg_is_sf());
            pstmt.setString(7, chat_GroupVO.getCg_is_ad());
            pstmt.setString(8, chat_GroupVO.getBaby_rd());
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
            System.out.println("Delete Chat_Group: " + cg_no);

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

        // 新增(OK)
//        bbq.chat.model.Chat_GroupVO chat_GroupVO1 = new bbq.chat.model.Chat_GroupVO();
//        chat_GroupVO1.setCg_name("群組測試1");
//        chat_GroupVO1.setCg_year(java.sql.Date.valueOf("2002-02-01"));
//        chat_GroupVO1.setCg_is_ab("0");
//        chat_GroupVO1.setCg_is_ac("1");
//        chat_GroupVO1.setCg_is_sf("0");
//        chat_GroupVO1.setCg_is_ad("1");
//        chat_GroupVO1.setCg_is_ar("1");
//        chat_GroupVO1.setBaby_rd("沒時間睡覺症");
//        dao.insert(chat_GroupVO1);
//        System.out.println("insert");

        // 修改
//		bbq.chat.model.Chat_GroupVO chat_GroupVO2 = new bbq.chat.model.Chat_GroupVO();
//		chat_GroupVO2.setCg_no("cg002");
//		chat_GroupVO2.setCg_name("11修改看看");
//		dao.update(chat_GroupVO2);
//		System.out.println("update");

        // 刪除
//		dao.delete("cg001");

        // 查詢
        Chat_GroupVO chat_GroupVO3 = dao.findByPrimaryKey("cg002");
        System.out.print(chat_GroupVO3.getCg_no() + ",");
        System.out.println(chat_GroupVO3.getCg_name());
        System.out.println("---------------------");

        // 查詢部門
        List<Chat_GroupVO> list = dao.getAll();
        for (Chat_GroupVO cg : list) {
            System.out.print(cg.getCg_no() + ",");
            System.out.print(cg.getCg_name());
            System.out.println();
        }

    }
}
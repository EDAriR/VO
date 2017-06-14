import java.util.*;
import java.sql.*;


public class AdminJDBCDAO implements AdminDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //  private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Admin (adm_no, adm_acct,adm_pwd, adm_name, adm_mail) " +
            "VALUES ('ad'||LPAD(to_char(ADM_NO_SEQ.nextval),3,'0'), ?, ?, ?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT adm_no , adm_name FROM Admin";
    private static final String GET_ONE_STMT = "SELECT adm_no, adm_name FROM Admin where adm_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Admin where adm_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE Admin set adm_name=? where adm_no = ?";


    @Override
    public void insert(AdminVO adminVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] seq = {"adm_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, seq); // 有使用sequence產生編號的話才要寫第二個參數

            pstmt.setString(1, adminVO.getAdm_acct());
            pstmt.setString(2, adminVO.getAdm_pwd());
            pstmt.setString(3, adminVO.getAdm_name());
            pstmt.setString(4, adminVO.getAdm_mail());

            pstmt.executeUpdate();

        } catch (Exception se) {
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
    public void update(AdminVO adminVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, adminVO.getAdm_name());
            pstmt.setString(2, adminVO.getAdm_no());

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
    public void delete(String adm_no) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, adm_no);
            pstmt.executeUpdate();

            // 2 設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete admin" + adm_no );

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
    public AdminVO findByPrimaryKey(String adm_no) {

        AdminVO adminVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, adm_no);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                adminVO = new AdminVO();
                adminVO.setAdm_no(rs.getString("adm_no"));
                adminVO.setAdm_name(rs.getString("adm_name"));
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
        return adminVO;
    }

    @Override
    public List<AdminVO> getAll() {

        List<AdminVO> list = new ArrayList<AdminVO>();
        AdminVO adminVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                adminVO = new AdminVO();
                adminVO.setAdm_no(rs.getString("adm_no"));
                adminVO.setAdm_name(rs.getString("adm_name"));
                list.add(adminVO); // Store the row in the list
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

        AdminJDBCDAO dao = new AdminJDBCDAO();

        // 新增
//        AdminVO adminVO1 = new AdminVO();
//        adminVO1.setAdm_acct("adtestacct1");
//        adminVO1.setAdm_pwd("adtestpwd1");
//        adminVO1.setAdm_name("adtestname1");
//        adminVO1.setAdm_mail("adtestmail1");        
//        dao.insert(adminVO1);
//        System.out.println("新增OK");
       
        // 修改
//        AdminVO adminVO2 = new AdminVO();
//        adminVO2.setAdm_no("ad006");
//        adminVO2.setAdm_name("nametest2");
//       adminVO2.setAdm_mail("mail222"); 
//        dao.update(adminVO2);
//        System.out.println("------修改---------");

        // 刪除
        dao.delete("ad007");
        System.out.println("------刪除---------");

        // 查詢
//        AdminVO adminVO3 = dao.findByPrimaryKey("ad004");
//        System.out.print(adminVO3.getAdm_no() + ",");
//        System.out.println(adminVO3.getAdm_name());
//        System.out.println("---------------------");

        // 查詢部門
        List<AdminVO> list = dao.getAll();
        for (AdminVO ad : list) {
            System.out.print(ad.getAdm_no() + ",");
            System.out.print(ad.getAdm_name());
            System.out.println();
        }

    }
}
package bbq.admin.model;

import java.util.*;
import java.sql.*;


public class Admin_AuthorityJDBCDAO implements Admin_AuthorityDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO Admin_Authority (adm_no, auth_no) VALUES (?, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT adm_no , auth_no FROM Admin_Authority";
    private static final String GET_ONE_STMT = "SELECT adm_no, auth_no FROM Admin_Authority where adm_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM Admin_Authority WHERE auth_no = ? AND adm_no = ?";


    @Override
    public void insert(Admin_AuthorityVO admin_AuthorityVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            pstmt = con.prepareStatement(INSERT_STMT);
            pstmt.setString(1, admin_AuthorityVO.getAdm_no());
            pstmt.setString(2, admin_AuthorityVO.getAuth_no());
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
    public void delete(String adm_no,String auth_no){

        int updateCount_PRODUCTs = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, auth_no);
            pstmt.setString(2, adm_no);
            pstmt.executeUpdate();

            System.out.println("Delete Admin Authority:" + adm_no +" and "+ auth_no);

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
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

    public Admin_AuthorityVO findByPrimaryKey(String adm_no){

        Admin_AuthorityVO admin_AuthorityVO = null;
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
                admin_AuthorityVO = new Admin_AuthorityVO();
                admin_AuthorityVO.setAdm_no(rs.getString("adm_no"));
                admin_AuthorityVO.setAuth_no(rs.getString("auth_no"));
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
        return admin_AuthorityVO;
    }

    public List<Admin_AuthorityVO> getAll(){

        List<Admin_AuthorityVO> list = new ArrayList<Admin_AuthorityVO>();
        Admin_AuthorityVO admin_AuthorityVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                admin_AuthorityVO = new Admin_AuthorityVO();
                admin_AuthorityVO.setAdm_no(rs.getString("adm_no"));
                admin_AuthorityVO.setAuth_no(rs.getString("auth_no"));
                list.add(admin_AuthorityVO); // Store the row in the list
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

        Admin_AuthorityJDBCDAO dao = new Admin_AuthorityJDBCDAO();

        // 新增
//        bbq.admin.model.Admin_AuthorityVO admin_AuthorityVO1 = new bbq.admin.model.Admin_AuthorityVO();
//        admin_AuthorityVO1.setAuth_no("an1");
//        admin_AuthorityVO1.setAdm_no("ad006");
//        dao.insert(admin_AuthorityVO1);
//        System.out.println("新增!");

//         刪除(OK)
//		dao.delete("ad007","an4");
//		System.out.println("delete");

//         查詢
		Admin_AuthorityVO admin_AuthorityVO3 = dao.findByPrimaryKey("ad005");
		System.out.print(admin_AuthorityVO3.getAdm_no() + ",");
		System.out.println(admin_AuthorityVO3.getAdm_no());
		System.out.println("---------------------");

//         查詢部門
		List<Admin_AuthorityVO> list = dao.getAll();
		for (Admin_AuthorityVO aa : list) {
			System.out.print(aa.getAdm_no() + ",");
			System.out.print(aa.getAuth_no());
			System.out.println();
		}

    }
}
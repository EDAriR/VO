import java.util.*;
import java.sql.*;


public class Admin_AuthorityJDBCDAO implements Admin_AuthorityDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO Admin_Authority (adm_no, auth_no) VALUES (?, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT adm_no , auth_no FROM Admin_Authority";
    private static final String GET_ONE_STMT = "SELECT adm_no, auth_no FROM Admin_Authority where adm_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM Admin_Authority WHERE auth_no = ? AND adm_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE Admin_Authority set proc_name=? where adm_no = ?";


    @Override
    public void insert(Admin_AuthorityVO admin_AuthorityVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
//            String[] cols = {"adm_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
//            pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
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
    public void update(Admin_AuthorityVO admin_AuthorityVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, admin_AuthorityVO.getAuth_no());
            pstmt.setString(2, admin_AuthorityVO.getAdm_no());

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

            // 1���]�w�� pstm.executeUpdate()���e
//            con.setAutoCommit(false);

            // ���R���ӫ~(�h) --->�|����product�A�]��������
//			pstmt = con.prepareStatement(DELETE_PRODUCTs);
//			pstmt.setString(1, adm_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // �A�R���ӫ~���O(�@)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, auth_no);
            pstmt.setString(2, adm_no);
            pstmt.executeUpdate();

            // 2���]�w�� pstm.executeUpdate()����
//            con.commit();
//            con.setAutoCommit(true);
            System.out.println("�R���ӫ~���O�s��" + adm_no + "��,�@���ӫ~" + updateCount_PRODUCTs
                    + "�ӦP�ɳQ�R��");

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3���]�w���exception�o�ͮɤ�catch�϶���
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
        // ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
        // �s�W
//        Admin_AuthorityVO admin_AuthorityVO1 = new Admin_AuthorityVO();
//        admin_AuthorityVO1.setAuth_no("an1");
//        admin_AuthorityVO1.setAdm_no("ad006");
//        dao.insert(admin_AuthorityVO1);
//        System.out.println("�s�W!");

        // �L�k�ק�


//         �R��(OK)
//		dao.delete("ad007","an4");
//		System.out.println("delete");

//         �d��
		Admin_AuthorityVO admin_AuthorityVO3 = dao.findByPrimaryKey("ad005");
		System.out.print(admin_AuthorityVO3.getAdm_no() + ",");
		System.out.println(admin_AuthorityVO3.getAdm_no());
		System.out.println("---------------------");

//         �d�߳���
		List<Admin_AuthorityVO> list = dao.getAll();
		for (Admin_AuthorityVO proc : list) {
			System.out.print(proc.getAdm_no() + ",");
			System.out.print(proc.getAuth_no());
			System.out.println();
		}

    }
}
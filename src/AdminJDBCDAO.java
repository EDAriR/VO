import java.util.*;
import java.sql.*;


public class AdminJDBCDAO implements AdminDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //  private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO Admin (adm_no, adm_acct,adm_pwd, adm_name, adm_mail) " +
            "VALUES ('ad'||LPAD(to_char(ADM_NO_SEQ.nextval),3,'0'), ?, ?, ?, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT adm_no , adm_name FROM Admin";
    private static final String GET_ONE_STMT = "SELECT adm_no, adm_name FROM Admin where adm_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM Admin where adm_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE Admin set adm_name=? where adm_no = ?";


    @Override
    public void insert(AdminVO adminVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] seq = {"adm_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, seq); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�

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

            // 1 �]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, adm_no);
            pstmt.executeUpdate();

            // 2 �]�w�� pstm.executeUpdate()����
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
                    // 3 �]�w���exception�o�ͮɤ�catch�϶���
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

        // �s�W
//        AdminVO adminVO1 = new AdminVO();
//        adminVO1.setAdm_acct("adtestacct1");
//        adminVO1.setAdm_pwd("adtestpwd1");
//        adminVO1.setAdm_name("adtestname1");
//        adminVO1.setAdm_mail("adtestmail1");        
//        dao.insert(adminVO1);
//        System.out.println("�s�WOK");
       
        // �ק�
//        AdminVO adminVO2 = new AdminVO();
//        adminVO2.setAdm_no("ad006");
//        adminVO2.setAdm_name("nametest2");
//       adminVO2.setAdm_mail("mail222"); 
//        dao.update(adminVO2);
//        System.out.println("------�ק�---------");

        // �R��
        dao.delete("ad007");
        System.out.println("------�R��---------");

        // �d��
//        AdminVO adminVO3 = dao.findByPrimaryKey("ad004");
//        System.out.print(adminVO3.getAdm_no() + ",");
//        System.out.println(adminVO3.getAdm_name());
//        System.out.println("---------------------");

        // �d�߳���
        List<AdminVO> list = dao.getAll();
        for (AdminVO ad : list) {
            System.out.print(ad.getAdm_no() + ",");
            System.out.print(ad.getAdm_name());
            System.out.println();
        }

    }
}
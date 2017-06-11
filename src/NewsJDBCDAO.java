import java.util.*;
import java.sql.*;


public class NewsJDBCDAO implements Admin_AuthorityDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO product_classification (proc_no, proc_name) VALUES (product_classification_seq.NEXTVAL, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT proc_no , proc_name FROM product_classification";
    private static final String GET_ONE_STMT = "SELECT proc_no, proc_name FROM product_classification where proc_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM product_classification where proc_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE product_classification set proc_name=? where proc_no = ?";


    @Override
    public void insert(Admin_AuthorityVO Admin_AuthorityVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"proc_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
            pstmt.setString(1, product_classificationVO.getProc_name());

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
    public void update(Admin_AuthorityVO Admin_AuthorityVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, product_classificationVO.getProc_name());
            pstmt.setString(2, product_classificationVO.getProc_no());

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
    public void delete(String adm_no){

        int updateCount_PRODUCTs = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1���]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            // ���R���ӫ~(�h) --->�|����product�A�]��������
//			pstmt = con.prepareStatement(DELETE_PRODUCTs);
//			pstmt.setString(1, proc_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // �A�R���ӫ~���O(�@)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, proc_no);
            pstmt.executeUpdate();

            // 2���]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("�R���ӫ~���O�s��" + proc_no + "��,�@���ӫ~" + updateCount_PRODUCTs
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

        Product_ClassificationVO product_classificationVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, proc_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                product_classificationVO = new Product_ClassificationVO();
                product_classificationVO.setProc_no(rs.getString("proc_no"));
                product_classificationVO.setProc_name(rs.getString("proc_name"));
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
        return product_classificationVO;
    }

    public List<Admin_AuthorityVO> getAll(){

        List<Product_ClassificationVO> list = new ArrayList<Product_ClassificationVO>();
        Product_ClassificationVO product_classificationVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                product_classificationVO = new Product_ClassificationVO();
                product_classificationVO.setProc_no(rs.getString("proc_no"));
                product_classificationVO.setProc_name(rs.getString("proc_name"));
                list.add(product_classificationVO); // Store the row in the list
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

        Product_ClassificationJDBCDAO dao = new Product_ClassificationJDBCDAO();
        // ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
        // �s�W
        Product_ClassificationVO product_classificationVO1 = new Product_ClassificationVO();
        product_classificationVO1.setProc_name("�]�ȳ��^���P");
        dao.insert(product_classificationVO1);

        // �ק�
//		Product_ClassificationVO product_classificationVO2 = new Product_ClassificationVO();
//		product_classificationVO2.setProc_no("2");
//		product_classificationVO2.setProc_name("�ק�ݬ�");
//		dao.update(product_classificationVO2);

        // �R��
//		dao.delete("1");

        // �d��
//		Product_ClassificationVO product_classificationVO3 = dao.findByPrimaryKey("1");
//		System.out.print(product_classificationVO3.getProc_no() + ",");
//		System.out.println(product_classificationVO3.getProc_name());
//		System.out.println("---------------------");

        // �d�߳���
//		List<Product_ClassificationVO> list = dao.getAll();
//		for (Product_ClassificationVO proc : list) {
//			System.out.print(proc.getProc_no() + ",");
//			System.out.print(proc.getProc_name());
//			System.out.println();
//		}

    }
}
package bbq.admin.model;

import java.util.*;
import java.sql.*;


public class Authority_FeatureJDBCDAO implements Authority_FeatureDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO authority_feature (auth_no, auth_name) " +
            "VALUES ('an'||LPAD(TO_CHAR(auth_no_seq.NEXTVAL),1,'0'), ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT auth_no , auth_name FROM authority_feature";
    private static final String GET_ONE_STMT = "SELECT auth_no, auth_name FROM authority_feature " +
            "where auth_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM authority_feature WHERE auth_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE authority_feature SET auth_name=? WHERE auth_no = ?";


    @Override
    public void insert(Authority_FeatureVO authority_FeatureVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] seq = {"auth_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, seq); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
            
            pstmt.setString(1, authority_FeatureVO.getAuth_name());
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
    public void update(Authority_FeatureVO authority_FeatureVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, authority_FeatureVO.getAuth_name());
            pstmt.setString(2, authority_FeatureVO.getAuth_no());
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
    public void delete(String auth_no){

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 �]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, auth_no);
            pstmt.executeUpdate();

            // 2 �]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete Authority Feature" + auth_no);

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
    public Authority_FeatureVO findByPrimaryKey(String auth_no){

        Authority_FeatureVO authority_featureVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, auth_no);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_featureVO = new Authority_FeatureVO();
                authority_featureVO.setAuth_no(rs.getString("auth_no"));
                authority_featureVO.setAuth_name(rs.getString("auth_name"));
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
        return authority_featureVO;
    }

    @Override
    public List<Authority_FeatureVO> getAll(){

        List<Authority_FeatureVO> list = new ArrayList<Authority_FeatureVO>();
        Authority_FeatureVO authority_FeatureVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_FeatureVO = new Authority_FeatureVO();
                authority_FeatureVO.setAuth_no(rs.getString("auth_no"));
                authority_FeatureVO.setAuth_name(rs.getString("auth_name"));
                list.add(authority_FeatureVO); // Store the row in the list
            }

        } catch (Exception se) {
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

        Authority_FeatureJDBCDAO dao = new Authority_FeatureJDBCDAO();
        // ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
        // �s�W(OK)
//        bbq.admin.model.Authority_FeatureVO authority_FeatureVO1 = new bbq.admin.model.Authority_FeatureVO();
//        authority_FeatureVO1.setAuth_name("T^Q");
//        dao.insert(authority_FeatureVO1);
//        System.out.println("�s�W");

        // �ק�
//        bbq.admin.model.Authority_FeatureVO authority_FeatureVO2 = new bbq.admin.model.Authority_FeatureVO();
//        authority_FeatureVO2.setAuth_no("an1");
//        authority_FeatureVO2.setAuth_name("hi");
//		dao.update(authority_FeatureVO2);
//		System.out.println("update");

        // �R��
//		dao.delete("an2");
//		System.out.println("delete");

        // �d��
        Authority_FeatureVO authority_FeatureVO3 = dao.findByPrimaryKey("an1");
		System.out.print(authority_FeatureVO3.getAuth_no() + ",");
		System.out.println(authority_FeatureVO3.getAuth_name());
		System.out.println("---------------------");

        // �d�߳���
		List<Authority_FeatureVO> list = dao.getAll();
		for (Authority_FeatureVO af : list) {
			System.out.print(af.getAuth_no() + ",");
			System.out.print(af.getAuth_name());
			System.out.println();
		}

    }

}
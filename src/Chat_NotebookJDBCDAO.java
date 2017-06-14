import java.util.*;
import java.sql.*;


public class Chat_NotebookJDBCDAO implements Chat_NotebookDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO Chat_Notebook (cnb_no, cnb_cnt) " +
            "VALUES ('cnb'||LPAD(to_char(cnb_no_seq.NEXTVAL),3,'0'), ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT cnb_no , cnb_cnt FROM Chat_Notebook";
    private static final String GET_ONE_STMT = "SELECT cnb_no, cnb_cnt FROM Chat_Notebook where cnb_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM Chat_Notebook where cnb_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE Chat_Notebook set Cnb_CNT=? where cnb_no = ?";


    @Override
    public void insert(Chat_NotebookVO chat_NotebookVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cnb = {"cnb_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, cnb); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
            pstmt.setString(1, chat_NotebookVO.getCnb_cnt());
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

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1 �]�w�� pstm.executeUpdate()���e
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cnb_no);
            pstmt.executeUpdate();

            // 2���]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Delete" + cnb_no);

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

        // �s�W
//        Chat_NotebookVO chat_NotebookVO1 = new Chat_NotebookVO();
//        chat_NotebookVO1.setCnb_cnt("�O�ƥ�����https://img.kekeke.cc/t/x8JrPHoAAr.png");
//        dao.insert(chat_NotebookVO1);
//        System.out.println("�s�W����");

        // �ק�
//		Chat_NotebookVO chat_NotebookVO2 = new Chat_NotebookVO();
//		chat_NotebookVO2.setCnb_no("cnb003");
//		chat_NotebookVO2.setCnb_cnt("--------");
//		System.out.println("�ק粒��");
//		dao.update(chat_NotebookVO2);
//		System.out.println("�ק粒��--");

        // �R��
//		dao.delete("cnb003");
//		System.out.println("�R������");

        // �d��
		Chat_NotebookVO chat_NotebookVO3 = dao.findByPrimaryKey("cnb002");
		System.out.print(chat_NotebookVO3.getCnb_no() + ",");
		System.out.println(chat_NotebookVO3.getCnb_cnt());
		System.out.println("---------------------");

        // �d�߳���
		List<Chat_NotebookVO> list = dao.getAll();
		for (Chat_NotebookVO cnb : list) {
			System.out.print(cnb.getCnb_no() + ",");
			System.out.print(cnb.getCnb_cnt());
			System.out.println();
		}

    }
}
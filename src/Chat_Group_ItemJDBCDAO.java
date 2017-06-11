import java.util.*;
import java.sql.*;


public class Chat_Group_ItemJDBCDAO implements Chat_Group_ItemDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO Chat_Group_Item (cg_no, mem_no) VALUES (cg_no_seq.NEXTVAL, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT cg_no , mem_no FROM Chat_Group_Item";
    private static final String GET_ONE_STMT = "SELECT cg_no, mem_no FROM Chat_Group_Item where cg_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM Chat_Group_Item where cg_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE Chat_Group_Item set mem_no=? where cg_no = ?";


    @Override
    public void insert(Chat_Group_ItemVO chat_Group_ItemVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"proc_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
            pstmt.setString(1, chat_Group_ItemVO.getCg_no());

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
    public void update(Chat_Group_ItemVO chat_Group_ItemVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = con.prepareStatement(UPDATE);

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
    public void delete(String cg_no,String mem_no){

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
//			pstmt.setString(1, cg_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // �A�R���ӫ~���O(�@)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cg_no);
            pstmt.executeUpdate();

            // 2���]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("�R���ӫ~���O�s��" + cg_no + "��,�@���ӫ~" + updateCount_PRODUCTs
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

    @Override
    public Chat_Group_ItemVO findByPrimaryKey(String cg_no,String mem_no){

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

    public List<Chat_Group_ItemVO> getAll(){

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
        // ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
        // �s�W
        Chat_Group_ItemVO chat_Group_ItemVO1 = new Chat_Group_ItemVO();
        chat_Group_ItemVO1.setMem_no("�]�ȳ��^���P");
        dao.insert(chat_Group_ItemVO1);

        // �ק�
//		Chat_Group_ItemVO chat_Group_ItemVO2 = new Chat_Group_ItemVO();
//		chat_Group_ItemVO2.setCg_no("2");
//		chat_Group_ItemVO2.setMem_no("�ק�ݬ�");
//		dao.update(chat_Group_ItemVO2);

        // �R��
//		dao.delete("1");

        // �d��
//		Chat_Group_ItemVO chat_Group_ItemVO3 = dao.findByPrimaryKey("1");
//		System.out.print(chat_Group_ItemVO3.getCg_no() + ",");
//		System.out.println(chat_Group_ItemVO3.getMem_no());
//		System.out.println("---------------------");

        // �d�߳���
//		List<Chat_Group_ItemVO> list = dao.getAll();
//		for (Chat_Group_ItemVO proc : list) {
//			System.out.print(proc.getCg_no() + ",");
//			System.out.print(proc.getMem_no());
//			System.out.println();
//		}

    }
}
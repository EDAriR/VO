import java.util.*;
import java.sql.*;


public class Chat_FriendJDBCDAO implements Chat_FriendDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    //    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // �s�W���
    private static final String INSERT_STMT = "INSERT INTO Chat_Friend (CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL) VALUES ('cf'||LPAD(to_char(CF_NO_SEQ.nextval),3,'0'), ?, ?, ?)";
    // �d�߸��
    private static final String GET_ALL_STMT = "SELECT CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL FROM Chat_Friend";
    private static final String GET_ONE_STMT = "SELECT CF_NO, MEM_NO_S, MEM_NO_O, CF_IS_DEL FROM Chat_Friend where cf_no = ?";
    // �R�����
    private static final String DELETE_PROC = "DELETE FROM Chat_Friend where cf_no = ?";
    // �ק���
    private static final String UPDATE = "UPDATE Chat_Friend set cf_is_del=? where cf_no = ?";


    @Override
    public void insert(Chat_FriendVO chat_FriendVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"cf_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
            pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
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

            pstmt.setString(1, chat_FriendVO.getCf_no());
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
//			pstmt.setString(1, cf_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // �A�R���ӫ~���O(�@)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, cf_no);
            pstmt.executeUpdate();

            // 2���]�w�� pstm.executeUpdate()����
            con.commit();
            con.setAutoCommit(true);
            System.out.println("�R���ӫ~���O�s��" + cf_no + "��,�@���ӫ~" + updateCount_PRODUCTs
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
        // ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
        // �s�W(OK)
        Chat_FriendVO chat_FriendVO1 = new Chat_FriendVO();
        chat_FriendVO1.setMem_no_s("M0000001");
        chat_FriendVO1.setMem_no_o("M0000005");
        chat_FriendVO1.setCf_is_del("0");
        dao.insert(chat_FriendVO1);
        System.out.println("�s�W���\");

        // �ק�
//        Chat_FriendVO chat_FriendVO2 = new Chat_FriendVO();
//        chat_FriendVO2.setCf_no("2");
//        chat_FriendVO2.setMem_no_o("�ק�ݬ�");
//        dao.update(chat_FriendVO2);

        // �R��
//        dao.delete("1");

        // �d��
//        Chat_FriendVO chat_FriendVO3 = dao.findByPrimaryKey("1");
//        System.out.print(chat_FriendVO3.getCf_no() + ",");
//        System.out.println(chat_FriendVO3.getMem_no_o());
//        System.out.println("---------------------");

        // �d�߳���
//        List<Chat_FriendVO> list = dao.getAll();
//        for (Chat_FriendVO proc : list) {
//            System.out.print(proc.getCf_no() + ",");
//            System.out.print(proc.getMem_no_o());
//            System.out.println();
//        }
    }
}
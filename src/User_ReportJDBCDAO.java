import java.util.*;
import java.sql.*;

public class User_ReportJDBCDAO implements User_ReportDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
	// private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// �s�W���
	private static final String INSERT_STMT = "INSERT INTO User_Report "
			+ "(MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE, URPT_RSN, URPT_IS_CERT, URPT_UNRSN) "
			+ "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";
	// �d�߸��
	private static final String GET_ALL_STMT = "SELECT mem_no_ed, URPT_CNT, URPT_RSN, URPT_IS_CERT FROM User_Report";
	private static final String GET_ONE_STMT = "SELECT mem_no_ed, URPT_CNT, URPT_RSN, URPT_IS_CERT "
			+ "FROM User_Report where mem_no_ed = ?";
	// �R�����
	private static final String DELETE_NEWS = "DELETE FROM User_Report where mem_no_ed = ? and mem_no_ing =?";
	// �ק���
	private static final String UPDATE = "UPDATE User_Report set URPT_IS_CERT=? where mem_no_ed = ? and mem_no_ing =?";

	@Override
	public void insert(User_ReportVO user_ReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			// String[] cols = {"new_no"}; // ���ϥ�sequence���ͽs�����ܤ~�n�g
			java.util.Date today = new java.util.Date();

			pstmt = con.prepareStatement(INSERT_STMT); //
			// ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
			// (MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE, URPT_RSN,
			// URPT_IS_CERT, URPT_UNRSN)
			
			pstmt.setString(1, user_ReportVO.getMem_no_ed());
			pstmt.setString(2, user_ReportVO.getMem_no_ing());
			pstmt.setString(3, user_ReportVO.getUrpt_cnt());
			pstmt.setString(4, user_ReportVO.getUrpt_rsn());
			pstmt.setString(5, user_ReportVO.getUrpt_is_cert());
			pstmt.setString(6, user_ReportVO.getUrpt_unrsn());

			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(User_ReportVO user_ReportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, user_ReportVO.getMem_no_ed());
			pstmt.setString(2, user_ReportVO.getUrpt_cnt());

			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String mem_no_ed) {

		int updateCount_PRODUCTs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			// ���R���ӫ~(�h) --->�|����product�A�]��������
			// pstmt = con.prepareStatement(DELETE_PRODUCTs);
			// pstmt.setString(1, mem_no_ed);
			// updateCount_PRODUCTs = pstmt.executeUpdate();
			// �A�R���ӫ~���O(�@)
			pstmt = con.prepareStatement(DELETE_NEWS);
			pstmt.setString(1, mem_no_ed);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���ӫ~���O�s��" + mem_no_ed + "��,�@���ӫ~" + updateCount_PRODUCTs + "�ӦP�ɳQ�R��");

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public User_ReportVO findByPrimaryKey(String mem_no_ed) {

		User_ReportVO user_ReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_no_ed);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				user_ReportVO = new User_ReportVO();
				user_ReportVO.setMem_no_ed(rs.getString("mem_no_ed"));
				user_ReportVO.setUrpt_cnt(rs.getString("urpt_cnt"));
			}

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return user_ReportVO;
	}

	@Override
	public List<User_ReportVO> getAll() {

		List<User_ReportVO> list = new ArrayList<User_ReportVO>();
		User_ReportVO user_ReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				user_ReportVO = new User_ReportVO();
				user_ReportVO.setMem_no_ed(rs.getString("mem_no_ed"));
				user_ReportVO.setMem_no_ing(rs.getString("mem_no_ing"));
				user_ReportVO.setUrpt_cnt(rs.getString("urpt_cnt"));
				list.add(user_ReportVO); // Store the row in the list
			}

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		User_ReportJDBCDAO dao = new User_ReportJDBCDAO();
		// ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
		// �s�W(OK)
		// (MEM_NO_ED, MEM_NO_ING, URPT_CNT, URPT_DATE, URPT_RSN, URPT_IS_CERT,
		// URPT_UNRSN)
		User_ReportVO user_ReportVO1 = new User_ReportVO();
		user_ReportVO1.setMem_no_ed("M0000001");
		user_ReportVO1.setMem_no_ing("M0000002");
		user_ReportVO1.setUrpt_cnt("787846sas�^");
		user_ReportVO1.setUrpt_date(new Timestamp(System.currentTimeMillis()));
		user_ReportVO1.setUrpt_rsn("�]�ȳ��^���P");
		user_ReportVO1.setUrpt_is_cert("1");
		user_ReportVO1.setUrpt_unrsn("�]�ȳ��^���P");
		dao.insert(user_ReportVO1);

		// �ק�
		// User_ReportVO user_ReportVO2 = new User_ReportVO();
		// user_ReportVO2.setMem_no_ing("n0007");
		// user_ReportVO2.setUrpt_cnt("�ק�ݬ�");
		// dao.update(user_ReportVO2);

		// �R��
		// dao.delete("1");

		// �d��
		// User_ReportVO user_ReportVO3 = dao.findByPrimaryKey("1");
		// System.out.print(user_ReportVO3.getMem_no_ed() + ",");
		// System.out.println(user_ReportVO3.getUrpt_is_cert());
		// System.out.println("---------------------");

		// �d�߳���
		// List<User_ReportVO> list = dao.getAll();
		// for (User_ReportVO proc : list) {
		// System.out.print(proc.getMem_no_ing() + ",");
		// System.out.print(proc.getUrpt_cnt());
		// System.out.println();

	}
}
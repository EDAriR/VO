import java.util.*;
import java.sql.*;


public class NewsJDBCDAO implements Admin_AuthorityDAO_interface {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
//    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ba101g3";
    private static final String PASSWORD = "baby";
    // 新增資料
    private static final String INSERT_STMT = "INSERT INTO product_classification (proc_no, proc_name) VALUES (product_classification_seq.NEXTVAL, ?)";
    // 查詢資料
    private static final String GET_ALL_STMT = "SELECT proc_no , proc_name FROM product_classification";
    private static final String GET_ONE_STMT = "SELECT proc_no, proc_name FROM product_classification where proc_no = ?";
    // 刪除資料
    private static final String DELETE_PROC = "DELETE FROM product_classification where proc_no = ?";
    // 修改資料
    private static final String UPDATE = "UPDATE product_classification set proc_name=? where proc_no = ?";


    @Override
    public void insert(Admin_AuthorityVO Admin_AuthorityVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            String[] cols = {"proc_no"}; // 有使用sequence產生編號的話才要寫
            pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
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

            // 1●設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            // 先刪除商品(多) --->尚未建product，因此先註解
//			pstmt = con.prepareStatement(DELETE_PRODUCTs);
//			pstmt.setString(1, proc_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
            // 再刪除商品類別(一)
            pstmt = con.prepareStatement(DELETE_PROC);
            pstmt.setString(1, proc_no);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除商品類別編號" + proc_no + "時,共有商品" + updateCount_PRODUCTs
                    + "個同時被刪除");

            // Handle any DRIVER errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3●設定於當有exception發生時之catch區塊內
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
        // 測試看看每個指令是否可以使用
        // 新增
        Product_ClassificationVO product_classificationVO1 = new Product_ClassificationVO();
        product_classificationVO1.setProc_name("財務部回來嚕");
        dao.insert(product_classificationVO1);

        // 修改
//		Product_ClassificationVO product_classificationVO2 = new Product_ClassificationVO();
//		product_classificationVO2.setProc_no("2");
//		product_classificationVO2.setProc_name("修改看看");
//		dao.update(product_classificationVO2);

        // 刪除
//		dao.delete("1");

        // 查詢
//		Product_ClassificationVO product_classificationVO3 = dao.findByPrimaryKey("1");
//		System.out.print(product_classificationVO3.getProc_no() + ",");
//		System.out.println(product_classificationVO3.getProc_name());
//		System.out.println("---------------------");

        // 查詢部門
//		List<Product_ClassificationVO> list = dao.getAll();
//		for (Product_ClassificationVO proc : list) {
//			System.out.print(proc.getProc_no() + ",");
//			System.out.print(proc.getProc_name());
//			System.out.println();
//		}

    }
}
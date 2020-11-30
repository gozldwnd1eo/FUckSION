package MovieSysServer.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MemberDAO {

	boolean insertResult = false;
	boolean deleteResult = false;
	PreparedStatement pstmt = null;
	Connection conn = null;

	public boolean insertCustomer(CustomerDTO dto) { // 고객 추가

		String SQL = "INSERT INTO MEMBERS(MEM_ID,MEM_PASSWORD,MEM_NAME,MEM_PHONENUM,MEM_ACCOUNT,MEM_GENDER,MEM_MONEY,MEM_EMAIL,MEM_BIRTHDAY,MEM_FLAG)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getCus_id());
			pstmt.setString(2, dto.getCus_password());
			pstmt.setString(3, dto.getCus_name());
			pstmt.setString(4, dto.getCus_phoneNum());
			pstmt.setString(5, dto.getCus_account());
			pstmt.setString(6, dto.getCus_gender());
			pstmt.setInt(7, dto.getCus_money());
			pstmt.setString(8, dto.getEmail());
			pstmt.setString(9, dto.getBirthday());
			pstmt.setString(10, dto.getFlag());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
		}
		insertResult = true;
		return insertResult;
	}

	public String loginRequest(String inputID, String inputPWD) { // 로그인 요청
		// PreparedStatement adstmt = null;
		PreparedStatement custmt = null;
		// ResultSet rsAdmin = null;
		ResultSet rsCusto = null;
		// boolean loginResult = false;
		String loginResult = "false";

		// String SQLad = "SELECT AD_ID FROM ADMINS WHERE AD_ID=\'" + inputID + "\' AND
		// AD_PASSWORD=\'" + inputPWD + "\'";
		// String SQLcu = "SELECT MEM_ID FROM MEMBERS WHERE MEM_ID = \'" + inputID + "\'
		// AND MEM_PASSWORD = \'" + inputPWD + "\'";
		String SQLcu = "SELECT MEM_FLAG FROM MEMBERS WHERE MEM_ID = \'" + inputID + "\' AND MEM_PASSWORD = \'"
				+ inputPWD + "\'";
		try {
			conn = getConnection();
			// adstmt = conn.prepareStatement(SQLad);
			custmt = conn.prepareStatement(SQLcu);
			// rsAdmin = adstmt.executeQuery();
			rsCusto = custmt.executeQuery();
			// if (rsAdmin.next())
			// loginResult = true;
			if (rsCusto.next()) {
				loginResult = rsCusto.getString("MEM_FLAG"); // 안되면 getString 1
			}

		} catch (SQLException sqle) {
			System.out.println("SQL문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			// if(rsAdmin!=null) try{rsAdmin.close();} catch(Exception e){throw new
			// RuntimeException(e.getMessage());}
			if (rsCusto != null)
				try {
					rsCusto.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			// if(adstmt!=null) try{adstmt.close();} catch(Exception e){throw new
			// RuntimeException(e.getMessage());}
			if (custmt != null)
				try {
					custmt.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
		}
		return loginResult;
	}

	public String selectMemberID(String name, String email) {
		PreparedStatement custmt = null;
		ResultSet rsCusto = null;
		String loginResult = "false";

		String SQLcu = "SELECT MEM_ID FROM MEMBERS WHERE MEM_NAME = \'" + name + "\' AND MEM_EMAIL = \'" + email + "\'";
		try {
			conn = getConnection();
			custmt = conn.prepareStatement(SQLcu);
			rsCusto = custmt.executeQuery();

			if (rsCusto.next()) {
				loginResult = rsCusto.getString("MEM_ID"); // 안되면 getString 1
			}

		} catch (SQLException sqle) {
			System.out.println("SQL문에서 예외 발생");
			sqle.printStackTrace();
		} finally {

			if (rsCusto != null)
				try {
					rsCusto.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			if (custmt != null)
				try {
					custmt.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
		}
		return loginResult;
	}

	public boolean deleteCustomer(String id) { // 고객삭제
		String SQL = "DELETE FROM CUSTOMERS WHERE CUS_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외 발생");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
		}
		deleteResult = true;
		return deleteResult;
	}

	public static Connection getConnection() {
		Connection conn = null;

		try {
			String user = "test1";
			String pw = "1234";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
}

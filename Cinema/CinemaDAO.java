package Cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CinemaDAO {
	private boolean insertResult = false;
	private boolean deleteResult = false;
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private ResultSet rs = null;

	public boolean insertScreen(ScreenDTO dto) {
		
		String SQL = "INSERT INTO SCREENS(AUDI_ID,FILM_ID,SCREEN_RESIDUALSEAT,SCREEN_STARTTIME,SCREEN_FINALTIME)" + "VALUES (?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getAudi_id());
			pstmt.setString(2, dto.getFilm_id());
			pstmt.setInt(3, dto.getScreen_residualSeat());
			pstmt.setString(4, dto.getScreen_startTime());
			pstmt.setString(5, dto.getScreen_finalTime());
			pstmt.executeQuery();
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}

	public ArrayList<ScreenDTO> displayScreen(String movid, String theater_id) {

		ArrayList<ScreenDTO> arr = new ArrayList<ScreenDTO>();

		try {
			conn = getConnection();
			String SQL = "SELECT * FROM SCREENS WHERE (\"AUDI_ID\" IN " + "(SELECT AUDI_ID FROM AUDITORIUMS WHERE THEATER_ID=" + theater_id
					+ "))" + " AND FILM_ID=" + movid + " AND SCREEN_STARTTIME>SYSDATE ORDER BY SCREEN_STARTTIME";
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenDTO dto = new ScreenDTO();
				dto.setFilm_id(rs.getString("FILM_ID"));
				dto.setScreen_id(rs.getString("SCREENS_ID"));
				dto.setAudi_id(rs.getString("AUDI_ID"));
				dto.setScreen_residualSeat(rs.getInt("SCREEN_RESIDUALSEAT"));
				dto.setScreen_startTime(rs.getString("SCREEN_STARTTIME"));
				dto.setScreen_finalTime(rs.getString("SCREEN_FINALTIME"));
				arr.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}

	//
	public boolean insertTheater(TheaterDTO dto) {
		String SQL = "INSERT INTO THEATERS(THEATER_NAME,THEATER_AREA,THEATER_ADDRESS,AD_ID)" + "VALUES (?,?,?,?)";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getTheater_name());
			pstmt.setString(2, dto.getTheater_area());
			pstmt.setString(3, dto.getTheater_address());
			pstmt.setNString(4, dto.getAd_id());
			pstmt.executeQuery();
		} catch (SQLException sqle) {
			System.out.println("INSERT������ ���� �߻�");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}

	public ArrayList<TheaterDTO> displayTheater() {
		ArrayList<TheaterDTO> arr = new ArrayList<TheaterDTO>();
		TheaterDTO dto = new TheaterDTO();
		String SQL = "SELECT * FROM THEATERS";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setTheater_id(rs.getString("THEATER_ID"));
				dto.setTheater_name(rs.getString("THEATER_NAME"));
				dto.setTheater_area(rs.getString("THEATER_AREA"));
				dto.setTheater_address(rs.getString("THEATER_ADDRESS"));
				dto.setAd_id(rs.getString("AD_ID"));
				arr.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}

	//

	public boolean insertAuditorium(AuditoriumDTO dto) {
		String SQL = "INSERT INTO AUDITORIUMS(THEATER_ID, AUDI_NUM, AUDI_SEATCNT)" + "VALUES (?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getTheater_id());
			pstmt.setInt(2, dto.getAudi_num());
			pstmt.setInt(3, dto.getAudi_seatCnt());

			pstmt.executeQuery();
		} catch (SQLException sqle) {
			System.out.println("INSERT������ ���� �߻�");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}

	public ArrayList<AuditoriumDTO> displayAuditorium(String inputID) {
		ArrayList<AuditoriumDTO> arr = new ArrayList<AuditoriumDTO>();
		AuditoriumDTO dto = new AuditoriumDTO();
		String SQL = "SELECT * FROM AUDITORUMS WHERE " + inputID + " = AUDITORUMS.THEATER_ID";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setAudi_id(rs.getString("AUDI_ID"));
				dto.setAudi_num(rs.getInt("AUDI_NUM"));
				dto.setTheater_id(rs.getString("THEATER_ID"));
				dto.setAudi_seatCnt(rs.getInt("AUDI_SEATCNT"));
				arr.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}

		}
		return arr;
	}

	public boolean deleteAuditorium(String audi_id) {
		String SQL = "DELETE FROM AUDITORUMS WHERE AUDI_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, audi_id);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE������ ���� �߻�");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}

		}
		deleteResult = true;
		return deleteResult;
	}

//SCREENS ����
	public boolean deleteScreen(String id) {
		String SQL = "DELETE FROM SCREENS WHERE SCREENS_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			pstmt.executeQuery();
		} catch (SQLException sqle) {
			System.out.println("DELETE������ ���� �߻�");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}

		}
		deleteResult = true;
		return deleteResult;
	}

	public boolean deleteTheater(String id) {
		String SQL = "DELETE FROM THEATERS WHERE THEATER_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			pstmt.executeQuery();
		} catch (SQLException sqle) {
			System.out.println("DELETE������ ���� �߻�");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		deleteResult = true;
		return deleteResult;
	}

	//
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// String user = "movieAdmin";
			// String pw = "movieadmin";
			String user = "test1";
			String pw = "1234";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB ����̹� �ε� ���� :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB ���ӽ��� : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
}
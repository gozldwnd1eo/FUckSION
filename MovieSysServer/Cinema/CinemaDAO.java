package MovieSysServer.Cinema;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CinemaDAO {
	private boolean insertResult = false;
	private boolean deleteResult = false;
	private boolean updateResult = false;
	private PreparedStatement pstmt = null;  //
	private CallableStatement cstmt = null;  //프로시저, 함수 쿼리를 사용할 때 쓰는 용
	private Connection conn = null;
	private ResultSet rs = null;

	public boolean insertResvation(ResvDTO dto) { // 예매 실행
		
		String SQL = "{call RESERV_EXEC(?,?,?,?,?)}";
		try {
			conn = getConnection();
			cstmt = conn.prepareCall(SQL);
			cstmt.setString(1, dto.getScreen_id());
			cstmt.setString(2, dto.getCus_id());
			cstmt.setInt(3, dto.getResv_peopleNum());
			cstmt.setString(4, dto.getResv_seatNum());
			cstmt.setInt(5, dto.getResv_depositAmount());
			cstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("프로시저문에서 예외 발생");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if(cstmt!=null) try{cstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}
	
	public boolean insertScreen(ScreenDTO dto) { //상영영화 추가
		
		String SQL = "INSERT INTO SCREENS(AUDI_ID,FILM_ID,SCREEN_RESIDUALSEAT,SCREEN_STARTTIME,SCREEN_FINALTIME)" + "VALUES (?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getAudi_id());
			pstmt.setString(2, dto.getFilm_id());
			pstmt.setInt(3, dto.getScreen_residualSeat());
			pstmt.setString(4, dto.getScreen_startTime());
			pstmt.setString(5, dto.getScreen_finalTime());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
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

	public ArrayList<ScreenDTO> displayScreen(String movid, String theater_id) { //상영영화 조회

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
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}

	public boolean insertTheater(TheaterDTO dto) { //영화관 추가
		String SQL = "INSERT INTO THEATERS(THEATER_NAME,THEATER_AREA,THEATER_ADDRESS,AD_ID)" + "VALUES (?,?,?,?)";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getTheater_name());
			pstmt.setString(2, dto.getTheater_area());
			pstmt.setString(3, dto.getTheater_address());
			pstmt.setNString(4, dto.getAd_id());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
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

	public ArrayList<TheaterDTO> displayTheater() {  //영화관 조회
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
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}


	public boolean insertAuditorium(AuditoriumDTO dto) { //상영관 추가
		String SQL = "INSERT INTO AUDITORIUMS(THEATER_ID, AUDI_NUM, AUDI_SEATCNT)" + "VALUES (?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getTheater_id());
			pstmt.setInt(2, dto.getAudi_num());
			pstmt.setInt(3, dto.getAudi_seatCnt());

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
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

	public ArrayList<AuditoriumDTO> displayAuditorium(String inputID) {//상영관 조회
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
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}

		}
		return arr;
	}

	public boolean updateTheater(TheaterDTO dto){//영화관 수정
		boolean updateResult;
		String SQL = "UPDATE THEATERS SET THEATER_NAME=?, THEATER_AREA=?, THEATER_ADDRESS=? WHERE THEATER_ID=? AND AD_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getTheater_name());
			pstmt.setString(2, dto.getTheater_area());
			pstmt.setString(3, dto.getTheater_address());
			pstmt.setString(4, dto.getTtheater_id());
			pstmt.setString(5, dto.getAd_id());
		
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("UPDATE문에서 예외 발생");
			sqle.printStackTrace();
			updateResult = false;
			return updateResult;
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
		updateResult = true;
		return updateResult;
	}

	public boolean deleteResv(String id, String resvnum){//예매 취소
		String SQL = "DELETE FROM RESERVATIONS WHERE AUDI_ID = \'" + id + "\' AND RESV_NUM = \'" + resvnum + "\'";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, resvnum);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외 발생");
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

	public boolean deleteAuditorium(String audi_id) { //상영관 삭제
		String SQL = "DELETE FROM AUDITORUMS WHERE AUDI_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, audi_id);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외 발생");
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

	public boolean deleteScreen(String id) { //상영영화 삭제
		String SQL = "DELETE FROM SCREENS WHERE SCREENS_ID = ?";

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
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}

		}
		deleteResult = true;
		return deleteResult;
	}

	public boolean deleteTheater(String id) { //영화관 삭제
		String SQL = "DELETE FROM THEATERS WHERE THEATER_ID = ?";

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
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		deleteResult = true;
		return deleteResult;
	}
	public boolean updateFilm(ScreenDTO dto) { //상영영화 수정

		String SQL = "UPDATE SCREENS SET AUDI_ID=?,FILM_ID=?, SCREEN_RESIDUALSEAT=?, SCREEN_STARTTIME=?, SCREEN_FINALTIME=? WHERE SCREEN_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getAudi_id());
			pstmt.setString(2, dto.getFilm_id());
			pstmt.setInt(3, dto.getScreen_residualSeat());
			pstmt.setString(4, dto.getScreen_startTime());
			pstmt.setString(5, dto.getScreen_finalTime());
			pstmt.setString(6, dto.getScreen_id());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("UPDATE문에서 예외 발생");
			sqle.printStackTrace();
			updateResult = false;
			return updateResult;
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
		updateResult = true;
		return updateResult;
	}
	public String displayMovie() { //상영영화 조회
		String result="";
		String SQL = "SELECT * FROM SCREENS";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=result+rs.getString("SCREEN_ID")+"\\";
				result=result+rs.getString("AUDI_ID")+"\\";
				result=result+rs.getString("FILM_ID")+"\\";
				result=result+rs.getString("SCREEN_RESIDUALSEAT")+"\\";
				result=result+rs.getString("SCREEN_STARTTIME")+"\\";
				result=result+rs.getString("SCREEN_FINALTIME")+"|";
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
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
		return result;
	}
	public boolean updateScreen(AuditoriumDTO dto) { ////상영관 수정

		String SQL = "UPDATE AUDITORIUMS SET AUDI_NUM=?,THEATER_ID=?, AUDI_SEATCNT=? WHERE AUDI_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, dto.getAudi_num());
			pstmt.setString(2, dto.getTheater_id());
			pstmt.setInt(3, dto.getAudi_seatCnt());
			pstmt.setString(4, dto.getAudi_id());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("UPDATE문에서 예외 발생");
			sqle.printStackTrace();
			updateResult = false;
			return updateResult;
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
		updateResult = true;
		return updateResult;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "movieAdmin";
			String pw = "movieadmin";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 : " + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
}
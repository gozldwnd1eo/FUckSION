package MovieSysServer.Cinema;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CinemaDAO {
	private boolean insertResult = false;
	private boolean deleteResult = false;
	private boolean updateResult = false;
	private PreparedStatement pstmt = null; //
	private CallableStatement cstmt = null; // 프로시저, 함수 쿼리를 사용할 때 쓰는 용
	private Connection conn = null;
	private ResultSet rs = null;
	String result = "";

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
			if (cstmt != null)
				try {
					cstmt.close();
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

	public boolean insertScreen(ScreenDTO dto) { // 상영영화 추가

		String SQL = "INSERT INTO SCREENS(AUDI_ID,FILM_ID,SCREEN_RESIDUALSEAT,SCREEN_STARTTIME,SCREEN_FINALTIME)"
				+ "VALUES (?,?,?,?,?)";
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

	public String displayScreen(String movid, String theater_id) { // 상영영화 조회

		try {
			conn = getConnection();
			String SQL = "SELECT * FROM SCREENS WHERE AUDI_ID IN (SELECT AUDI_ID FROM AUDITORIUMS WHERE THEATER_ID= ? ) AND FILM_ID= ? AND SCREEN_STARTTIME>SYSDATE ORDER BY SCREEN_STARTTIME";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, theater_id);
			pstmt.setString(2, movid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("FILM_ID") + "\\";
				result = result + rs.getString("SCREENS_ID") + "\\";
				result = result + rs.getString("AUDI_ID") + "\\";
				result = result + rs.getString("SCREEN_RESIDUALSEAT") + "\\";
				result = result + rs.getString("SCREEN_STARTTIME") + "\\";
				result = result + rs.getString("SCREEN_FINALTIME") + "|";
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

	public boolean insertTheater(TheaterDTO dto) { // 영화관 추가
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

	public String displayTheater() { // 모든 영화관 조회
		String SQL = "SELECT * FROM THEATERS";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("THEATER_ID") + "\\";
				result = result + rs.getString("THEATER_NAME") + "\\";
				result = result + rs.getString("THEATER_AREA") + "\\";
				result = result + rs.getString("THEATER_ADDRESS") + "\\";
				result = result + rs.getString("MEM_ID") + "|";
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

	public boolean insertAuditorium(AuditoriumDTO dto) { // 상영관 추가
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

	public String displayAuditorium(String inputID) {// 상영관 조회

		String SQL = "SELECT * FROM AUDITORUMS WHERE AUDITORUMS.THEATER_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("AUDI_ID") + "\\";
				result = result + rs.getString("AUDI_NUM") + "\\";
				result = result + rs.getString("THEATER_ID") + "\\";
				result = result + rs.getString("AUDI_SEATCNT") + "|";
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

	public boolean updateTheater(TheaterDTO dto) {// 영화관 수정
		boolean updateResult;
		String SQL = "UPDATE THEATERS SET THEATER_NAME=?, THEATER_AREA=?, THEATER_ADDRESS=? WHERE THEATER_ID=? AND MEM_ID=?";
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

	public boolean deleteResv(String id, String resvnum) {// 예매 취소
		String SQL = "EXEC RESERV_CNACEL_EXEC (?,?)";

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

	public boolean deleteAuditorium(String audi_id) { // 상영관 삭제
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

	public boolean deleteScreen(String id) { // 상영영화 삭제
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

	public boolean deleteTheater(String id) { // 영화관 삭제
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

	public boolean updateScreen(ScreenDTO dto) { // 상영영화 수정

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

	public String displayScreen() { // 상영영화 조회

		String SQL = "SELECT * FROM SCREENS";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("SCREEN_ID") + "\\";
				result = result + rs.getString("AUDI_ID") + "\\";
				result = result + rs.getString("FILM_ID") + "\\";
				result = result + rs.getString("SCREEN_RESIDUALSEAT") + "\\";
				result = result + rs.getString("SCREEN_STARTTIME") + "\\";
				result = result + rs.getString("SCREEN_FINALTIME") + "|";
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

	public boolean updateAudi(AuditoriumDTO dto) { //// 상영관 수정

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

	public String displayTheaterByArea(String inputArea, String inputFilmId) { // 영화관(지역으로) 조회 요청
		String SQL = "SELECT * FROM THEATERS WHERE THEATER_AREA= ? AND THEATER_ID IN (SELECT THEATER_ID FROM AUDITORIUMS WHERE AUDI_ID IN(SELECT AUDI_ID FROM SCREENS WHERE FILM_ID=?)) ORDER BY THEATER_AREA";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputArea);
			pstmt.setString(2, inputFilmId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("THEATER_ID") + "\\";
				result = result + rs.getString("THEATER_NAME") + "\\";
				result = result + rs.getString("THEATER_AREA") + "\\";
				result = result + rs.getString("THEATER_ADDRESS") + "\\";
				result = result + rs.getString("MEM_ID") + "|";
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

	public String displayAudiRuntime(String inputTheaterId, String inputFilmId) { // 상영시간 조회 요청
		String SQL = "SELECT SCREEN_ID ,AUDI_NUM,SCREEN_STARTTIME,SCREEN_FINALTIME FROM SCREENS, AUDITORIUMS WHERE FILM_ID=? AND AUDITORIUMS.THEATER_ID=? AND SCREENS.AUDI_ID=AUDITORIUMS.AUDI_ID AND SCREEN_STARTTIME>SYSDATE ORDER BY SCREEN_STARTTIME";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputFilmId);
			pstmt.setString(2, inputTheaterId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("SCREEN_ID") + "\\";
				result = result + rs.getString("AUDI_NUM") + "\\";
				result = result + rs.getString("SCREEN_STARTTIME") + "\\";
				result = result + rs.getString("SCREEN_FINALTIME") + "|";
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

	public String displayScreenTable(String inputTheaterId) { // 해당 영화관의 상영시간표 조회 요청
		String SQL = "SELECT * FROM SCREENS WHERE AUDI_ID IN(SELECT AUDI_ID FROM AUDITORIUMS WHERE THEATER_ID=?) AND SCREEN_STARTTIME>SYSDATE ORDER BY SCREEN_STARTTIME";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputTheaterId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("SCREEN_ID") + "\\";
				result = result + rs.getString("AUDI_ID") + "\\";
				result = result + rs.getString("FILM_ID") + "\\";
				result = result + rs.getString("SCREEN_RESIDUALSEAT") + "\\";
				result = result + rs.getString("SCREEN_STARTTIME") + "\\";
				result = result + rs.getString("SCREEN_FINALTIME") + "|";
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

	public String displaySeatSituation(String inputScreenId) { // 현재 좌석 상황 조회 요청
		String SQL = "SELECT RESV_SEATNUM FROM RESERVATIONS WHERE SCREEN_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputScreenId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("RESV_SEATNUM") + "~";
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

	public String displayResvList(String inputMemId) { // 예매 내역 조회 요청
		String SQL = "SELECT RESV_NUM,FILM_NAME,RESV_PEOPLENUM,RESV_SEATNUM,RESV_DEPOSITAMOUNT,RESV_DEPOSITDATE FROM RESERVATIONS,SCREENS,FILMS WHERE SCREENS.FILM_ID=FILMS.FILM_ID AND RESERVATIONS.SCREEN_ID=SCREENS.SCREEN_ID AND MEM_ID= ? AND RESV_CANCELDATE IS NULL";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputMemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("RESV_NUM") + "\\";
				result = result + rs.getString("FILM_NAME") + "\\";
				result = result + rs.getString("RESV_PEOPLENUM") + "\\";
				result = result + rs.getString("RESV_SEATNUM") + "\\";
				result = result + rs.getString("RESV_DEPOSITAMOUNT") + "\\";
				result = result + rs.getString("RESV_DEPOSITDATE") + "|";
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

	public String displayTheaterForAdmin(String inputMemId) { // 담당자용 영화관 조회 요청
		String SQL = "SELECT * FROM THEATERS WHERE MEM_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputMemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("THEATER_ID") + "\\";
				result = result + rs.getString("THEATER_NAME") + "\\";
				result = result + rs.getString("THEATER_AREA") + "\\";
				result = result + rs.getString("THEATER_ADDRESS") + "|";
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

	public String displayAudi(String inputFilmId) { // 상영관 조회 요청
		String SQL = "SELECT * FROM AUDITORIUMS WHERE THEATER_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inputFilmId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("AUDI_ID") + "\\";
				result = result + rs.getString("AUDI_NUM") + "\\";
				result = result + rs.getString("THEATER_ID") + "\\";
				result = result + rs.getString("AUDI_SEATCNT") + "|";
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

	public String displaySalesPerMovie() { // 영화별 매출 조회 요청
		String SQL = "SELECT * FROM TABLE(PRINT_SALES_PER_MOVIE)";
		try {
			conn = getConnection();
			cstmt = conn.prepareCall(SQL);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("영화ID") + "\\";
				result = result + rs.getString("영화별매출") + "|";
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
			if (cstmt != null)
				try {
					cstmt.close();
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

	public String displayAllSales() { // 총매출 조회 요청
		String SQL = "SELECT PRINT_TOTAL_SALES FROM DUAL";
		try {
			conn = getConnection();
			cstmt = conn.prepareCall(SQL);
			rs = cstmt.executeQuery();
			if (rs.next()) {
				result = result + rs.getString("total");
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
			if (cstmt != null)
				try {
					cstmt.close();
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

	public String displayCancelRatePerMovie() { // 영화별 취소율 조회 요청
		String SQL = "SELECT * FROM TABLE(PRINT_CANCELRATE_PER_MOVIE)";
		try {
			conn = getConnection();
			cstmt = conn.prepareCall(SQL);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("영화ID") + "\\";
				result = result + rs.getString("영화별취소율") + "|";
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
			if (cstmt != null)
				try {
					cstmt.close();
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

	public String displayResvRatePerMovie() { // 영화별 예매율 조회 요청
		String SQL = "SELECT FILM_ID, FILM_RESVRATE FROM FILMS WHERE SYSDATE-FILM_OPENINGDATE <30";
		try {
			conn = getConnection();
			cstmt = conn.prepareCall(SQL);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				result = result + rs.getString("FILM_ID") + "\\";
				result = result + rs.getString("FILM_RESVRATE") + "|";
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
			if (cstmt != null)
				try {
					cstmt.close();
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

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "test1";
			String pw = "1234";
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
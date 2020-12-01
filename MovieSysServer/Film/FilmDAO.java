package MovieSysServer.Film;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class FilmDAO {
	boolean insertResult = false;
	boolean deleteResult = false;
	boolean updateResult = false;

	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private ResultSet rs = null;

	public ArrayList<String> displayArea(String filmId) { // 지역 조회
		ArrayList<String> arr = new ArrayList<String>();
		String SQL = "SELECT DISTINCT THEATER_AREA FROM THEATERS WHERE THEATER_ID IN (SELECT THEATER_ID FROM AUDITORIUMS WHERE AUDI_ID IN(SELECT AUDI_ID FROM SCREENS WHERE FILM_ID='?')) ORDER BY THEATER_AREA ;";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, filmId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString("THEATER_AREA"));
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
		return arr;
	}

	public boolean insertFilm(FilmDTO dto) { // 영화 추가

		String SQL = "INSERT INTO FILMS(FILM_NAME,FILM_TEASER,FILM_INFO,FILM_GENRE,FILM_OPENINGDATE,FILM_SUMMARY,FILM_POSTER)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getFilm_name());
			pstmt.setString(2, dto.getFilm_teaser());
			pstmt.setString(3, dto.getFilm_info());
			pstmt.setString(4, dto.getFilm_genre());
			pstmt.setString(5, dto.getFilm_openingDate());
			pstmt.setString(6, dto.getFilm_summary());
			pstmt.setByte(7, dto.getFilm_poster());
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
	public boolean updateFilm(FilmDTO dto) { //영화 수정

		String SQL = "UPDATE FILMS SET FILM_NAME=?,FILM_TEASER=?, FILM_INFO=?, FILM_GENRE=?, FILM_OPENINGDATE=?, FILM_SUMMARY=?, FILM_POSTER=? WHERE FILM_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getFilm_name());
			pstmt.setString(2, dto.getFilm_teaser());
			pstmt.setString(3, dto.getFilm_info());
			pstmt.setString(4, dto.getFilm_genre());
			pstmt.setString(5, dto.getFilm_openingDate());
			pstmt.setString(6, dto.getFilm_summary());
			pstmt.setByte(7, dto.getFilm_poster());
			pstmt.setString(8, dto.getFilm_id());
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

	public String displayScreenList() { // 상영영화리스트 조회
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		String SQLcu = "select *,avg(rev_starpoint) from FILMS where film_id=(select distinct film_id from screen)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQLcu);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result += rs.getString("film_name") + "\\"; // 안되면 getString 1
				result += rs.getByte("film_poster") + "\\";
				result += rs.getString("film_resvrate") + "\\";
				result += rs.getString("rev_starpoint") + "|";
			}
		} catch (SQLException sqle) {
			System.out.println("SQL문에서 예외 발생");
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

	public ArrayList<FilmDTO> displayMovie() { // 영화 조회
		ArrayList<FilmDTO> arr = new ArrayList<FilmDTO>();
		FilmDTO dto = new FilmDTO();
		String SQL = "SELECT * FROM FILMS WHERE (FILM_OPENINGDATE<SYSDATE) AND (FILM_OPENINGDATE+30>SYSDATE) ORDER BY FILM_OPENINGDATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setFilm_name(rs.getString("FILM_NAME"));
				dto.setFilm_teaser(rs.getString("FILM_TEASER"));
				dto.setFilm_info(rs.getString("FILM_INFO"));
				dto.setFilm_genre(rs.getString("FILM_GENRE"));
				dto.setFilm_openingDate(rs.getString("FILM_OPENINGDATE"));
				dto.setFilm_summary(rs.getString("FILM_SUMMARY"));
				dto.setFilm_poster(rs.getByte("FILM_POSTER"));
				arr.add(dto);
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
		return arr;
	}

	public FilmDTO displayMovieDetail(String inputFilmId) { // 영화의 상세정보 조회
		FilmDTO dto = new FilmDTO();
		String SQL = "SELECT * FROM FILMS WHERE (FILM_OPENINGDATE<SYSDATE) AND (FILM_OPENINGDATE+30>SYSDATE) AND FILM_ID=? ORDER BY FILM_OPENINGDATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getFilm_id());
			rs = pstmt.executeQuery();
			
			dto.setFilm_name(rs.getString("FILM_NAME"));
			dto.setFilm_teaser(rs.getString("FILM_TEASER"));
			dto.setFilm_info(rs.getString("FILM_INFO"));
			dto.setFilm_genre(rs.getString("FILM_GENRE"));
			dto.setFilm_openingDate(rs.getString("FILM_OPENINGDATE"));
			dto.setFilm_summary(rs.getString("FILM_SUMMARY"));
			dto.setFilm_poster(rs.getByte("FILM_POSTER"));
				
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
		return dto;
	}


	public boolean deleteFilm(String title) { // 영화 삭제
		String SQL = "DELETE FROM FILMS WHERE FILM_NAME = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, title);
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

	public boolean insertReview(ReviewDTO dto) { // 리뷰 추가

		String SQL = "INSERT INTO REVIEWS(CUS_ID,FILM_ID,REV_STARPOINT,REV_CONTENT,REV_DATE)" + "VALUES (?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getCus_id());
			pstmt.setString(2, dto.getFilm_id());
			pstmt.setInt(3, dto.getRev_starPoint());
			pstmt.setString(4, dto.getRev_content());
			pstmt.setString(5, dto.getRev_date());
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
	public boolean updateReview(ReviewDTO dto) { //리뷰 수정

		String SQL = "UPDATE REVIEWS SET REV_CONTENT=?, REV_STARPOINT=? WHERE REV_ID=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getRev_content());
			pstmt.setInt(2, dto.getRev_starPoint());
			pstmt.setString(3, dto.getRev_id());
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

	public ArrayList<ReviewDTO> displayReview(String movid) { // 리뷰 조회

		ArrayList<ReviewDTO> arr = new ArrayList<ReviewDTO>();
		ReviewDTO dto = new ReviewDTO();
		String SQL = "SELECT * FROM REVIEWS WHERE FILM_ID=" + movid + " ORDER BY REV_DATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setCus_id(rs.getString("MEM_ID"));
				dto.setFilm_id(rs.getString("FILM_ID"));
				dto.setRev_date(rs.getString("REV_DATE"));
				dto.setRev_content(rs.getString("REV_CONTENT"));
				dto.setRev_starPoint(rs.getInt("REV_rev_STARPOINT"));
				arr.add(dto);
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
		return arr;
	}

	public ReviewDTO displayReviewDetail(String cusId) { // 내가 작성한 리뷰 조회

		ReviewDTO dto = new ReviewDTO();
		String SQL = "SELECT * FROM REVIEWS WHERE MEM_ID=" + cusId + " ORDER BY REV_DATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			dto.setCus_id(rs.getString("MEM_ID"));
			dto.setFilm_id(rs.getString("FILM_ID"));
			dto.setRev_date(rs.getString("REV_DATE"));
			dto.setRev_content(rs.getString("REV_CONTENT"));
			dto.setRev_starPoint(rs.getInt("REV_rev_STARPOINT"));
				
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
		return dto;
	}

	public boolean deleteReview(String id) { // 리뷰 삭제
		String SQL = "DELETE FROM REVIEWS WHERE REV_ID = ?";

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
			String user = "movieAdmin";
			String pw = "movieadmin";
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

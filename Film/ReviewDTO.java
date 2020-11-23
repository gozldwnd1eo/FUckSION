package Film;

public class ReviewDTO {
	private String rev_id; //리뷰번호 varchar2
	private String cus_id; //고객id varchar2
	private String film_id; //영화id varchar2
	private int rev_starPoint; //별점 number
	private String rev_content; //리뷰내용 varchar2
	private String rev_date; //리뷰날짜 date
	
	public String getRev_id() {
		return rev_id;
	}
	public void setRev_id(String rev_id) {
		this.rev_id = rev_id;
	}
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public String getFilm_id() {
		return film_id;
	}
	public void setFilm_id(String film_id) {
		this.film_id = film_id;
	}
	public int getRev_starPoint() {
		return rev_starPoint;
	}
	public void setRev_starPoint(int rev_starPoint) {
		this.rev_starPoint = rev_starPoint;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public String getRev_date() {
		return rev_date;
	}
	public void setRev_date(String rev_date) {
		this.rev_date = rev_date;
	}
}

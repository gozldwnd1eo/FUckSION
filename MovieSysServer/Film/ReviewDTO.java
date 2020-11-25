package MovieSysServer.Film;

public class ReviewDTO {
	private String rev_id; // varchar2			����ID
	private String cus_id; // varchar2			����ID
	private String film_id; // varchar2			��ȭID
	private int rev_starPoint; // number		����
	private String rev_content; // varchar2		���䳻��
	private String rev_date; // date			����Խó�¥
	
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

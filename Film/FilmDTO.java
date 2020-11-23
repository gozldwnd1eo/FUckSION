package Film;

public class FilmDTO {
	private String film_id; // varchar2(5)
	private String film_name; // varchar2(50)
	private String film_teaser; // varchar2(100)
	private String film_info; // varchar2(50)
	// (��ȭ����~�⿬��1~�⿬��2~�⿬��3~�󿵽ð�)
	private String film_genre; // varchar2(20)
	private String film_openingDate; // date
	private String film_summary; // clob
	private byte film_poster; // blob
	private double film_resvRate;
	
	public String getFilm_id() {
		return film_id;
	}
	public void setFilm_id(String film_id) {
		this.film_id = film_id;
	}
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}
	public String getFilm_teaser() {
		return film_teaser;
	}
	public void setFilm_teaser(String film_teaser) {
		this.film_teaser = film_teaser;
	}
	public String getFilm_info() {
		return film_info;
	}
	public void setFilm_info(String film_info) {
		this.film_info = film_info;
	}
	public String getFilm_genre() {
		return film_genre;
	}
	public void setFilm_genre(String film_genre) {
		this.film_genre = film_genre;
	}
	public String getFilm_openingDate() {
		return film_openingDate;
	}
	public void setFilm_openingDate(String film_openingDate) {
		this.film_openingDate = film_openingDate;
	}
	public String getFilm_summary() {
		return film_summary;
	}
	public void setFilm_summary(String film_summary) {
		this.film_summary = film_summary;
	}
	public byte getFilm_poster() {
		return film_poster;
	}
	public void setFilm_poster(byte film_poster) {
		this.film_poster = film_poster;
	}

	public double getFilm_resvRate() {
		return film_resvRate;
	}

	public void setFilm_resvRate(double film_resvRate) {
		this.film_resvRate = film_resvRate;
	}
	
}
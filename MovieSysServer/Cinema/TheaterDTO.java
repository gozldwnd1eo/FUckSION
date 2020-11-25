package Cinema;

public class TheaterDTO {
	private String theater_id; // varchar2(5)	영화관ID
	private String theater_name; // varchar2(50)	영화관명칭
	private String theater_area; // varchar2(10)	지역       구미  구미역
	private String theater_address; // varchar2(100) 	주소
	private String ad_id;			// varchar2(10) 	담당자ID
	
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id=ad_id;
	}
	public String getTtheater_id() {
		return theater_id;
	}
	public void setTheater_id(String theater_id) {
		this.theater_id = theater_id;
	}
	public String getTheater_name() {
		return theater_name;
	}
	public void setTheater_name(String theater_name) {
		this.theater_name = theater_name;
	}
	public String getTheater_area() {
		return theater_area;
	}
	public void setTheater_area(String theater_area) {
		this.theater_area = theater_area;
	}
	public String getTheater_address() {
		return theater_address;
	}
	public void setTheater_address(String theater_address) {
		this.theater_address = theater_address;
	}
}
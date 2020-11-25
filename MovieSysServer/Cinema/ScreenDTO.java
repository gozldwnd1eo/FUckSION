package MovieSysServer.Cinema;

public class ScreenDTO {
	private String screen_id; // varchar2(5)    상영영화ID
	private String audi_id; // varchar2(5)		상영관ID
	private String film_id; // varchar2(5)		영화ID
	private int screen_residualSeat; // number(4,0) 잔여좌석수
	private String screen_startTime; // time	시작시각
	private String screen_finalTime; // time	종료시각
	
	public String getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}
	public String getAudi_id() {
		return audi_id;
	}
	public void setAudi_id(String audi_id) {
		this.audi_id = audi_id;
	}
	public String getFilm_id() {
		return film_id;
	}
	public void setFilm_id(String film_id) {
		this.film_id = film_id;
	}
	public int getScreen_residualSeat() {
		return screen_residualSeat;
	}
	public void setScreen_residualSeat(int screen_residualSeat) {
		this.screen_residualSeat = screen_residualSeat;
	}
	public String getScreen_startTime() {
		return screen_startTime;
	}
	public void setScreen_startTime(String screen_startTime) {
		this.screen_startTime = screen_startTime;
	}
	public String getScreen_finalTime() {
		return screen_finalTime;
	}
	public void setScreen_finalTime(String screen_finalTime) {
		this.screen_finalTime = screen_finalTime;
	}

}

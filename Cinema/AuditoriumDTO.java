package Cinema;

public class AuditoriumDTO {
	private String audi_id; // varchar2(5)
	private int audi_num; // number(2,0)
	private String theater_id; // varchar2(5)
	private int audi_seatCnt; // number(4,0)
	
	public String getAudi_id() {
		return audi_id;
	}
	public void setAudi_id(String audi_id) {
		this.audi_id = audi_id;
	}
	public int getAudi_num() {
		return audi_num;
	}
	public void setAudi_num(int audi_num) {
		this.audi_num = audi_num;
	}
	public String getTheater_id() {
		return theater_id;
	}
	public void setTheater_id(String theater_id) {
		this.theater_id = theater_id;
	}
	public int getAudi_seatCnt() {
		return audi_seatCnt;
	}
	public void setAudi_seatCnt(int audi_seatCnt) {
		this.audi_seatCnt = audi_seatCnt;
	}
}
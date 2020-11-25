package MovieSysServer.Cinema;

public class ResvDTO {
	private String resv_num; // varchar2  예약번호
	private String screen_id; // id varchar2 영화ID
	private String cus_id; // varchar2  고객ID
	private int resv_peopleNum; //      number 인원수
	private String resv_seatNum; // varchar2   좌석번호     B12~B13~B14
	private String resv_depositDate; // date  입금일
	private int resv_depositAmount; // number  입금액
	private String resv_cancelDate; // date   취소일
	
	public String getResv_num() {
		return resv_num;
	}
	public void setResv_num(String resv_num) {
		this.resv_num = resv_num;
	}
	public String getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public int getResv_peopleNum() {
		return resv_peopleNum;
	}
	public void setResv_peopleNum(int resv_peopleNum) {
		this.resv_peopleNum = resv_peopleNum;
	}
	public String getResv_seatNum() {
		return resv_seatNum;
	}
	public void setResv_seatNum(String resv_seatNum) {
		this.resv_seatNum = resv_seatNum;
	}
	public String getResv_depositDate() {
		return resv_depositDate;
	}
	public void setResv_depositDate(String resv_depositDate) {
		this.resv_depositDate = resv_depositDate;
	}
	public int getResv_depositAmount() {
		return resv_depositAmount;
	}
	public void setResv_depositAmount(int resv_depositAmount) {
		this.resv_depositAmount = resv_depositAmount;
	}
	public String getResv_cancelDate() {
		return resv_cancelDate;
	}
	public void setResv_cancelDate(String resv_cancelDate) {
		this.resv_cancelDate = resv_cancelDate;
	}
	
}

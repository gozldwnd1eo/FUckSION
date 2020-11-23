package Cinema;

public class ResvDTO {
	private String resv_num; //���Ź�ȣ varchar2
	private String screen_id; //�󿵿�ȭ id varchar2
	private String cus_id; //����id varchar2
	private int resv_peopleNum; //�ο���       number
	private String resv_seatNum; //�¼���ȣ varchar2
	private String resv_depositDate; //�Ա��� date
	private int resv_depositAmount; //�Աݾ� number
	private String resv_cancelDate; //����� date
	
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

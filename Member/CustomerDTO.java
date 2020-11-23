package Member;

public class CustomerDTO {
	private String cus_id; // varchar2(10)
	private String cus_password; // varchar2(20)
	private String cus_name; // varchar2(20)
	private String cus_account; // varchar2(50)
	private String cus_phoneNum; // varchar2(20)
	private String cus_gender; // boolean
	private int cus_money; // number
	
	public int getCus_money() {
		return cus_money;
	}
	public void setCus_money(int cus_money) {
		this.cus_money = cus_money;
	}	
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public String getCus_password() {
		return cus_password;
	}
	public void setCus_password(String cus_password) {
		this.cus_password = cus_password;
	}
	public String getCus_name() {
		return cus_name;
	}
	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}
	public String getCus_account() {
		return cus_account;
	}
	public void setCus_account(String cus_account) {
		this.cus_account = cus_account;
	}
	public String getCus_phoneNum() {
		return cus_phoneNum;
	}
	public void setCus_phoneNum(String cus_phoneNum) {
		this.cus_phoneNum = cus_phoneNum;
	}
	public String getCus_gender() {
		return cus_gender;
	}
	public void setCus_gender(String cus_gender) {
		this.cus_gender = cus_gender;
	}
	
}
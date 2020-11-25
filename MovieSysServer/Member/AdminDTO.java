package MovieSysServer.Member;

public class AdminDTO {
	private String ad_id; // varchar2(10)			담당자ID
	private String ad_password; // varchar2(20)		담당자비밀번호
	private String ad_name; // varchar2(20)			담당자이름
	private String ad_account; // varchar2(50)		담당자계좌번호
	private String ad_phoneNum; // varchar2(20)		담당자핸드폰번호
	private int ad_money; // number					담당자잔액
	
	public int getAd_money() {
		return ad_money;
	}
	public void setAd_money(int ad_money) {
		this.ad_money = ad_money;
	}	
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public String getAd_password() {
		return ad_password;
	}
	public void setAd_password(String ad_password) {
		this.ad_password = ad_password;
	}
	public String getAd_name() {
		return ad_name;
	}
	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}
	public String getAd_account() {
		return ad_account;
	}
	public void setAd_account(String ad_account) {
		this.ad_account = ad_account;
	}
	public String getAd_phoneNum() {
		return ad_phoneNum;
	}
	public void setAd_phoneNum(String ad_phoneNum) {
		this.ad_phoneNum = ad_phoneNum;
	}
}
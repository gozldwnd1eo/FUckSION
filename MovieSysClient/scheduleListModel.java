package MovieSysClient;

public class scheduleListModel {
    public String screenID;
    public String audiNum;
    public String startTime;
    public String endTime;

    public String getListDisplay(){
        return audiNum+"관 / "+startTime+" ~ "+endTime;
    }

    public String getScreenID() {
        return screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public String getAudiNum() {
        return audiNum;
    }

    public void setAudiNum(String audiNum) {
        this.audiNum = audiNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

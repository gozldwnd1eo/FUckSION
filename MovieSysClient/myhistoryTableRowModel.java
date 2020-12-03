package MovieSysClient;

import javafx.beans.property.StringProperty;

public class myhistoryTableRowModel {
    private StringProperty filmname;
    private StringProperty theatername;
    private StringProperty audi_time;
    private StringProperty seatnum;
    private StringProperty price;
    private StringProperty paydate;
    private StringProperty refund;
    private StringProperty resv_num;

    public myhistoryTableRowModel(StringProperty filmname, StringProperty theatername, StringProperty audi_time,
            StringProperty seatnum, StringProperty price, StringProperty paydate, StringProperty refund,
            StringProperty resv_num) {
        this.filmname = filmname;
        this.theatername = theatername;
        this.audi_time = audi_time;
        this.seatnum = seatnum;
        this.price = price;
        this.paydate = paydate;
        this.refund = refund;
        this.resv_num = resv_num;
    }

    public StringProperty filmnameProperty() {
        return filmname;
    }

    public StringProperty theaternameProperty() {
        return theatername;
    }

    public StringProperty audi_timeProperty() {
        return audi_time;
    }

    public StringProperty seatnumProperty() {
        return seatnum;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty paydateProperty() {
        return paydate;
    }

    public StringProperty refundProperty() {
        return refund;
    }

    public StringProperty resv_numProperty() {
        return resv_num;
    }
}

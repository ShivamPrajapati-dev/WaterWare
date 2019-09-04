package com.shivamprajapati.waterware;

public class PreviousHistory {

    private String date,bill,name,address,phone,fromTime,toTime,cleaningCharges,visitingCharges,totalCharges;
    private String[] days;

    String getCleaningCharges() {
        return cleaningCharges;
    }

    void setCleaningCharges(String cleaningCharges) {
        this.cleaningCharges = cleaningCharges;
    }

    String getVisitingCharges() {
        return visitingCharges;
    }

    void setVisitingCharges(String visitingCharges) {
        this.visitingCharges = visitingCharges;
    }

    String getTotalCharges() {
        return totalCharges;
    }

    void setTotalCharges(String totalCharges) {
        this.totalCharges = totalCharges;
    }

    String getFromTime() {
        return fromTime;
    }

    void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    String getToTime() {
        return toTime;
    }

    void setToTime(String toTime) {
        this.toTime = toTime;
    }

    String[] getDays() {
        return days;
    }

    void setDays(String[] days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }





    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBill() {
        return bill;
    }

    void setBill(String bill) {
        this.bill = bill;
    }
}

package com.i200513FinalProject.FinalProject1.Person;

public class PersonBookingRecyclerModel
{
    String hotelName,
    from,
    to,
    status;

    public PersonBookingRecyclerModel(String hotelName, String from, String to, String status) {
        this.hotelName = hotelName;
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

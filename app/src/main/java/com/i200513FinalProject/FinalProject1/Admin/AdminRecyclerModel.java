package com.i200513FinalProject.FinalProject1.Admin;

public class AdminRecyclerModel {
    String person_Username,
            hotel_Name,
            fromDateTime,
            toDateTime,
            status;

    public AdminRecyclerModel(String person_Username, String hotel_Name, String fromDateTime, String toDateTime, String status) {
        this.person_Username = person_Username;
        this.hotel_Name = hotel_Name;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.status = status;
    }

    public String getPerson_Username() {
        return person_Username;
    }

    public void setPerson_Username(String person_Username) {
        this.person_Username = person_Username;
    }

    public String getHotel_Name() {
        return hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        this.hotel_Name = hotel_Name;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(String toDateTime) {
        this.toDateTime = toDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String printString()
    {
        return person_Username.concat(" ")
                .concat(hotel_Name).concat(" ")
                .concat(fromDateTime).concat(" ")
                .concat(toDateTime).concat(" ")
                .concat(status);
    }
}

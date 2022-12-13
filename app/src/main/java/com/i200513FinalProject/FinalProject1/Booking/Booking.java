package com.i200513FinalProject.FinalProject1.Booking;

public class Booking {

    String booking_PersonID, booking_PersonUsername;
    String booking_HotelID, booking_HotelName;
    String booking_StartDateTime, booking_EndDateTime;
    String booking_Status;

    public Booking(String booking_PersonID, String booking_PersonUsername, String booking_HotelID, String booking_HotelName, String booking_StartDateTime, String booking_EndDateTime,String booking_Status) {
        this.booking_PersonID = booking_PersonID;
        this.booking_PersonUsername = booking_PersonUsername;
        this.booking_HotelID = booking_HotelID;
        this.booking_HotelName = booking_HotelName;
        this.booking_StartDateTime = booking_StartDateTime;
        this.booking_EndDateTime = booking_EndDateTime;
        this.booking_Status=booking_Status;
    }

    public String getBooking_PersonID() {
        return booking_PersonID;
    }

    public void setBooking_PersonID(String booking_PersonID) {
        this.booking_PersonID = booking_PersonID;
    }

    public String getBooking_PersonUsername() {
        return booking_PersonUsername;
    }

    public void setBooking_PersonUsername(String booking_PersonUsername) {
        this.booking_PersonUsername = booking_PersonUsername;
    }

    public String getBooking_HotelID() {
        return booking_HotelID;
    }

    public void setBooking_HotelID(String booking_HotelID) {
        this.booking_HotelID = booking_HotelID;
    }

    public String getBooking_HotelName() {
        return booking_HotelName;
    }

    public void setBooking_HotelName(String booking_HotelName) {
        this.booking_HotelName = booking_HotelName;
    }

    public String getBooking_StartDateTime() {
        return booking_StartDateTime;
    }

    public void setBooking_StartDateTime(String booking_StartDateTime) {
        this.booking_StartDateTime = booking_StartDateTime;
    }

    public String getBooking_EndDateTime() {
        return booking_EndDateTime;
    }

    public void setBooking_EndDateTime(String booking_EndDateTime) {
        this.booking_EndDateTime = booking_EndDateTime;
    }

    public String getBooking_Status() {
        return booking_Status;
    }

    public void setBooking_Status(String booking_Status) {
        this.booking_Status = booking_Status;
    }
}

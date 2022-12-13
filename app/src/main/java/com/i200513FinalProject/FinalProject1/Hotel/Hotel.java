package com.i200513FinalProject.FinalProject1.Hotel;

public class Hotel {
    String hotel_ID,hotel_Name,hotel_Address,hotel_City,hotel_PhoneNo;
    String hotel_Description,hotel_Rating,hotel_Pricing;
    String hotel_Tags;
    String hotel_Image;

    public Hotel(String hotel_ID, String hotel_Name, String hotel_Address, String hotel_City, String hotel_PhoneNo, String hotel_Description, String hotel_Rating, String hotel_Pricing, String hotel_Tags, String hotel_img) {
        this.hotel_ID = hotel_ID;
        this.hotel_Name = hotel_Name;
        this.hotel_Address = hotel_Address;
        this.hotel_City = hotel_City;
        this.hotel_PhoneNo = hotel_PhoneNo;
        this.hotel_Description = hotel_Description;
        this.hotel_Rating = hotel_Rating;
        this.hotel_Pricing = hotel_Pricing;
        this.hotel_Tags = hotel_Tags;
        this.hotel_Image=hotel_img;
    }

    public Hotel(Hotel hotel) {
        this.hotel_ID = hotel.hotel_ID;
        this.hotel_Name = hotel.hotel_Name;
        this.hotel_Address = hotel.hotel_Address;
        this.hotel_City = hotel.hotel_City;
        this.hotel_PhoneNo = hotel.hotel_PhoneNo;
        this.hotel_Description = hotel.hotel_Description;
        this.hotel_Rating = hotel.hotel_Rating;
        this.hotel_Pricing = hotel.hotel_Pricing;
        this.hotel_Tags = hotel.hotel_Tags;
        this.hotel_Image=hotel.hotel_Image;
    }

    public String getHotel_ID() {
        return hotel_ID;
    }

    public void setHotel_ID(String hotel_ID) {
        this.hotel_ID = hotel_ID;
    }

    public String getHotel_Name() {
        return hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        this.hotel_Name = hotel_Name;
    }

    public String getHotel_Address() {
        return hotel_Address;
    }

    public void setHotel_Address(String hotel_Address) {
        this.hotel_Address = hotel_Address;
    }

    public String getHotel_City() {
        return hotel_City;
    }

    public void setHotel_City(String hotel_City) {
        this.hotel_City = hotel_City;
    }

    public String getHotel_PhoneNo() {
        return hotel_PhoneNo;
    }

    public void setHotel_PhoneNo(String hotel_PhoneNo) {
        this.hotel_PhoneNo = hotel_PhoneNo;
    }

    public String getHotel_Description() {
        return hotel_Description;
    }

    public void setHotel_Description(String hotel_Description) {
        this.hotel_Description = hotel_Description;
    }

    public String getHotel_Rating() {
        return hotel_Rating;
    }

    public void setHotel_Rating(String hotel_Rating) {
        this.hotel_Rating = hotel_Rating;
    }

    public String getHotel_Pricing() {
        return hotel_Pricing;
    }

    public void setHotel_Pricing(String hotel_Pricing) {
        this.hotel_Pricing = hotel_Pricing;
    }

    public String getHotel_Tags() {
        return hotel_Tags;
    }

    public void setHotel_Tags(String hotel_Tags) {
        this.hotel_Tags = hotel_Tags;
    }

    public String getHotel_Image() {
        return hotel_Image;
    }

    public void setHotel_Image(String hotel_img) {
        this.hotel_Image = hotel_img;
    }
    public String printString()
    {
       return getHotel_ID()+" "+getHotel_Name()+" "+getHotel_Image();
    }
}



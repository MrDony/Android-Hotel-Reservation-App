package com.i200513FinalProject.FinalProject1.NestedRecycler;

public class HorizontalRecyclerViewModel {

    String hotelID;
    String imgUrl;
    String title;
    String description;

    public HorizontalRecyclerViewModel(String hotelID,String imgUrl, String title, String description) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.hotelID=hotelID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

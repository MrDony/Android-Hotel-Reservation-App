package com.i200513FinalProject.FinalProject1.NestedRecycler;

import java.util.List;

public class VerticalRecyclerViewModel {
    String title;
    String description;

    List<HorizontalRecyclerViewModel> horizontalList;
    public VerticalRecyclerViewModel(String t, String d)
    {
        this.title=t; this.description=d;
    }

    public VerticalRecyclerViewModel(String t,String d,List<HorizontalRecyclerViewModel> list)
    {
        this.title=t;
        this.description=d;
        this.horizontalList=list;
    }

    public List<HorizontalRecyclerViewModel> getHorizontalList() {
        return horizontalList;
    }

    public void setHorizontalList(List<HorizontalRecyclerViewModel> horizontalList) {
        this.horizontalList = horizontalList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

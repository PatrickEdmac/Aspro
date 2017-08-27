package com.example.patrick.aspro;

/**
 * Created by Patrick on 01/08/2017.
 */

public class ProListElement {
    private String name;
    private String profession;
    private String wage;
    private int ImageId;

    public ProListElement(String name, String profession, String wage, int imageId) {
        this.name = name;
        this.profession = profession;
        this.wage = wage;
        ImageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }
}

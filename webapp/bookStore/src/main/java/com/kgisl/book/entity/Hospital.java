package com.kgisl.book.entity;

public class Hospital {
    public int hospital_id;
    public String hospital_name;
    public String location;
    public String contno;
    public String website;
    public Hospital(String hospital_name, String location, String contno, String website) {
        this.hospital_id = hospital_id;
        this.hospital_name = hospital_name;
        this.location = location;
        this.contno = contno;
        this.website = website;
    }
    public int gethospital_id() {
        return hospital_id;
    }
    public void sethospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }
    public String gethospital_name() {
        return hospital_name;
    }
    public void sethospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getContno() {
        return contno;
    }
    public void setContno(String contno) {
        this.contno = contno;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    @Override
    public String toString() {
        return "Hospital [contno=" + contno + ", hospital_id=" + hospital_id + ", hospital_name=" + hospital_name
                + ", location=" + location + ", website=" + website + "]";
    }
}

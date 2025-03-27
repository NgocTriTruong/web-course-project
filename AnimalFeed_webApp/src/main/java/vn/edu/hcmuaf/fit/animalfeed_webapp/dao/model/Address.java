package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class Address implements Serializable {
    private int id;
    private int userId;
    private String detail;
    private String ward;
    private String district;
    private String province;
    private String note;

    public Address() {}

    public Address(int id, int userId, String detail, String ward, String district, String province, String note) {
        this.id = id;
        this.userId = userId;
        this.detail = detail;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", detail='" + detail + '\'' +
                ", ward='" + ward + '\'' +
                ", district='" + district + '\'' +
                ", province='" + province + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

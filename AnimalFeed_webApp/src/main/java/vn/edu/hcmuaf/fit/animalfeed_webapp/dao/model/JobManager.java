package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class JobManager implements Serializable {
    private int id;
    private String location;
    private String phone;
    private String position;

    public JobManager() {}

    public JobManager(int id, String location, String phone, String position) {
        this.id = id;
        this.location = location;
        this.phone = phone;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "JobManager{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

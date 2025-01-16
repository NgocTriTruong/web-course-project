package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private int userId;
    private String contact_user;
    private String phone;
    private String email;
    private String address;
    private String content;

    public Contact() {}

    public Contact(int id, int userId, String contact_user, String phone, String email, String address, String content) {
        this.id = id;
        this.userId = userId;
        this.contact_user = contact_user;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.content = content;
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

    public String getContact_user() {
        return contact_user;
    }

    public void setContact_user(String contact_user) {
        this.contact_user = contact_user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", userId=" + userId +
                ", contact_user='" + contact_user + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

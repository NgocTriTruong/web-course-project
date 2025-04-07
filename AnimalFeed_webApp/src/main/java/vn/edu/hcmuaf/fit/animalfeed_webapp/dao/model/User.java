package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String fullName;
    private String password;
    private String phone;
    private String email;
    private int status;
    private Date createDate;
    private Date updateDate;
    private int role; // 0: User, 1: Admin
    private int sub_role; // 0: Super Admin, 1: User Admin, 2: Product Admin, 3: Order Admin, 4: Shipper Admin, 5: News Admin

    public User(int id, String fullName, String password, String phone, String email, int status, Date createDate, Date updateDate, int role, int sub_role) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.role = role;
        this.sub_role = sub_role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getSub_role() {
        return sub_role;
    }

    public void setSub_role(int sub_role) {
        this.sub_role = sub_role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", role=" + role +
                ", sub_role=" + sub_role +
                '}';
    }
}
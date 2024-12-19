package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.util.Date;

public class Discount implements Serializable {
    private int id;
    private int percentage;
    private Date updateDate;

    public Discount() {}

    public Discount(int id, int percentage, Date updateDate) {
        this.id = id;
        this.percentage = percentage;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", percentage=" + percentage +
                ", updateDate=" + updateDate +
                '}';
    }
}

package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.util.Date;

public class ActionLog implements Serializable {
    private int id;
    private int user_id;
    private String action_type;     //Loại hành động (create, update, delete, etc.)
    private String entity_type;     //Loại đối tượng (product, user, order, etc.)
    private int entity_id;
    private Date created_at;        //Thời điểm thực hiện hành động
    private String description;     //Mô tả hành động
    private String before_data;   //Dữ liệu trước khi thực hiện hành động
    private String after_data;    //Dữ liệu sau khi thực hiện hành động

    public ActionLog(int user_id, String action_type, String entity_type, int entity_id, String description, String before_data, String after_data) {
        this.user_id = user_id;
        this.action_type = action_type;
        this.entity_type = entity_type;
        this.entity_id = entity_id;
        this.created_at = new Date();;
        this.description = description;
        this.before_data = before_data;
        this.after_data = after_data;
    }

    public ActionLog() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBefore_data() {
        return before_data;
    }

    public void setBefore_data(String before_data) {
        this.before_data = before_data;
    }

    public String getAfter_data() {
        return after_data;
    }

    public void setAfter_data(String after_data) {
        this.after_data = after_data;
    }

    @Override
    public String toString() {
        return "ActionLog{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", action_type='" + action_type + '\'' +
                ", entity_type='" + entity_type + '\'' +
                ", entity_id=" + entity_id +
                ", created_at=" + created_at +
                ", description='" + description + '\'' +
                ", before_data='" + before_data + '\'' +
                ", after_data='" + after_data + '\'' +
                '}';
    }
}

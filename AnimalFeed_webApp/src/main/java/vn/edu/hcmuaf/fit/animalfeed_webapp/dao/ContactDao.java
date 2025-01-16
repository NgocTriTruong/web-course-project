package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.statement.StatementContext;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Contact;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Create a new contact
    public void addContact(Contact contact) {
        try {
            jdbi.useHandle(handle -> {
                handle.createUpdate("INSERT INTO contacts (user_id, contact_user, phone, email, address, content) " +
                                "VALUES (:userId, :contact_user, :phone, :email, :address, :content)")
                        .bindBean(contact)
                        .execute();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM contacts")
                        .map(new ContactMapper())
                        .list());
//        System.out.println("Contacts: " + contacts); // Debug dữ liệu trả về
        return contacts;
    }

    // Delete a contact by ID
    public void deleteContact(int id) {
        jdbi.useHandle(handle -> {
            handle.createUpdate("DELETE FROM contacts WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }


    public static class ContactMapper implements RowMapper<Contact> {
        @Override
        public Contact map(ResultSet rs, StatementContext ctx) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getInt("id")); // Cột `id` ánh xạ vào thuộc tính `id`
            contact.setUserId(rs.getInt("user_id")); // Cột `user_id` ánh xạ vào `userId`
            contact.setContact_user(rs.getString("contact_user")); // Cột `name_contact` ánh xạ vào `nameContact`
            contact.setPhone(rs.getString("phone")); // Cột `phone` ánh xạ vào `phone`
            contact.setEmail(rs.getString("email")); // Cột `email` ánh xạ vào `email`
            contact.setAddress(rs.getString("address")); // Cột `address` ánh xạ vào `address`
            contact.setContent(rs.getString("content")); // Cột `content` ánh xạ vào `content`
            return contact;
        }
    }
}

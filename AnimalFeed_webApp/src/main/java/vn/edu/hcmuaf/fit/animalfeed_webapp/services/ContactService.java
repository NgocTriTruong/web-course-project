package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ContactDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Contact;

import java.util.List;

public class ContactService {
    private ContactDao contactDao;

    public ContactService() {
        contactDao = new ContactDao();
    }

    public void addContact(Contact contact) throws IllegalArgumentException {
        // Kiểm tra số điện thoại
        if (contact.getPhone() == null || !contact.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số.");
        }

        // Kiểm tra email
        if (contact.getEmail() != null && !contact.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email không hợp lệ. Phải chứa ký tự '@'.");
        }

        // Lưu contact sau khi kiểm tra
        contactDao.addContact(contact);
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts() ;
    }

    public void deleteContact(int id) {
        contactDao.deleteContact(id);
    }

//    public List<Contact> getContactsByUserId(int userId) {
//        // Giả định mỗi contact có userId
//        return contactDao.getContactsByUserId(userId);
//    }

}

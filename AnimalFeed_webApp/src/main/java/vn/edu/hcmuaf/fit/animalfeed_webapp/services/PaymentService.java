package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.PaymentDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Payment;

import java.util.List;

public class PaymentService {
    private PaymentDao paymentDao = new PaymentDao();

    // Get all payments
    public List<Payment> getAll() {
        return paymentDao.getAll();
    }

    public void addPayment(Payment payment) {
        paymentDao.addPayment(payment);
    }

    public Payment getPaymentById(int id) {
        return paymentDao.getPaymentById(id);
    }

    // Get payment by order id
    public Payment getPaymentByOrderId(int orderId) {
        return paymentDao.getPaymentByOrderId(orderId);
    }

    //update payment
    public void updatePayment(Payment payment) {
        paymentDao.updatePayment(payment);
    }

    //update payment status
    public void updatePaymentStatus(int orderId, int status) {
        paymentDao.updatePaymentStatus(orderId, status);
    }

}

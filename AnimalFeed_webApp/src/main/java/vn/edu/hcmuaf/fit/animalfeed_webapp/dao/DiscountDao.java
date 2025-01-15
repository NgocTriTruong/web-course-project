package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;

import java.util.List;

public class DiscountDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Lấy tất cả các discount
    public List<Discount> getAll() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * FROM discounts")
                .mapToBean(Discount.class).list());
    }

    public double calculateDiscountedPrice(double originalPrice, int discountId) {
        Discount discount = getById(discountId);
        if (discount == null) {
            return originalPrice;
        }
        return originalPrice * (100 - discount.getPercentage()) / 100.0;
    }

    private Discount getById(int discountId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM discounts WHERE id = :id")
                        .bind("id", discountId)
                        .mapToBean(Discount.class)
                        .findOne()
                        .orElse(null)
        );
    }
}

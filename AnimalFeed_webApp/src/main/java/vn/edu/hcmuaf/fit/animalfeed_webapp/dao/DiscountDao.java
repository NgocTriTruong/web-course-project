package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;

import java.util.List;

public class DiscountDao {

    // Lấy tất cả các discount
    public List<Discount> getAll() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * FROM discounts")
                .mapToBean(Discount.class).list());
    }

    //lay discount theo id
    public Discount getDiscountById(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from discounts where id = :id")
                        .bind("id", id)
                        .mapToBean(Discount.class).findOne().orElse(null));
    }
}

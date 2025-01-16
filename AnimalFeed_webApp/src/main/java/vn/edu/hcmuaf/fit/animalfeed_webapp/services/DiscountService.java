package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;

import java.util.List;

public class DiscountService {
    static DiscountDao discountDao = new DiscountDao();

    //Lay tat ca discount
    public List<Discount> getAll() {
        return discountDao.getAll();
    }

    //lay discount theo id
    public Discount getDiscountById(int id) {
        return discountDao.getDiscountById(id);
    }
}

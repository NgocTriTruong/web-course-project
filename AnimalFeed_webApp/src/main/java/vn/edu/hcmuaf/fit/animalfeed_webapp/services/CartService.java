package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;

public class CartService {
    static CartDetailDao cartDetailDao = new CartDetailDao();

    public void insertCD(CartDetail cartDetail) {
        cartDetailDao.insertCD(cartDetail);
    }

    public void deleteCD(int productId, int id) {
        cartDetailDao.deleteCD(productId, id);
    }

    public void updateQuantity(int productId, int id, int quantity) {
        cartDetailDao.updateQuantity(productId, id, quantity);
    }
}

package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ProductDetail;

import java.util.List;

public class ProductDetailService {
    ProductDetailDao productDetailDao = new ProductDetailDao();

    public ProductDetail getDetail(int id) {
        return productDetailDao.getById(id);
    }

    public List<ProductDetail> getAll() {
        return productDetailDao.getAll();
    }


    public List<Product> getRelatedProducts(int categoryId, int excludeProductId) {
        return productDetailDao.getRelatedProducts(categoryId, excludeProductId);
    }
}

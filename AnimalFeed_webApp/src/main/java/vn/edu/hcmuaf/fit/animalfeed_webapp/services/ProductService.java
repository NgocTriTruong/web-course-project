package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.*;

public class ProductService {

    static ProductDao productDao = new ProductDao();

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public Product getDetail(String in) {
        try {
            int id = Integer.parseInt(in);
            return productDao.getById(id);
        }catch (NumberFormatException e) {
            return null;
        }

    }

    public int getTotalProduct(){
        return productDao.getTotalProduct();
    }

    public List<Product> getProductByPage(int page, int id){
        return productDao.getProductByPage(page, id);
    }

}

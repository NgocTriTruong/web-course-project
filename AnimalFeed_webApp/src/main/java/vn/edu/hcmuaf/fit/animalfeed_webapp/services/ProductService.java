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

    //Lay product theo id danh muc
    public List<Product> getByCatId(int categoryId) {
        return productDao.getByCatId(categoryId);
    }

    //Dem so luong product trong db
    public int getTotalProduct(){
        return productDao.getTotalProduct();
    }

    //phan trang product
    public List<Product> getProductByPage(int page, int id){
        return productDao.getProductByPage(page, id);
    }

    //Lay danh sach san pham co discount
    public List<Product> discountedProducts() {
        return productDao.discountedProducts();
    }

    //Them product
    public void insertProduct(Product product) {
        productDao.insertProduct(product);
    }

    //xoa product
    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }

    //sua product
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

}

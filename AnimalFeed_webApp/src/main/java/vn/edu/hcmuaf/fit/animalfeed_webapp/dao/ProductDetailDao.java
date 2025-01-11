package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ProductDetail;

import java.util.List;

public class ProductDetailDao {
    Jdbi jdbi = JdbiConnect.getJdbi();

    public List<ProductDetail> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("select * from product_details")
                .mapToBean(ProductDetail.class).list());
    }

    public ProductDetail getById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from product_details where id = :id")
                .bind("id", id)
                .mapToBean(ProductDetail.class).findOne().orElse(null));
    }

    public List<Product> getRelatedProducts(int categoryId, int excludeProductId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                        SELECT p.id, p.img, p.name, p.description, p.price
                        FROM product_details pd
                        JOIN products p ON pd.product_id = p.id
                        WHERE product_id IN (SELECT id FROM products WHERE cat_id = :categoryId) 
                        AND product_id != :excludeProductId
                        """)
                        .bind("categoryId", categoryId)
                        .bind("excludeProductId", excludeProductId)
                        .mapToBean(Product.class).list());
    }

    public static void main(String[] args) {
        List<Product> productDetails = new ProductDetailDao().getRelatedProducts(1, 90);
        for (Product productDetail : productDetails) {
            System.out.println(productDetail);
        }
    }
}

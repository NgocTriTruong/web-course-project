package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.List;

public class ProductWithDiscountDao {

    //Lay danh sach san pham co discount
    public List<ProductWithDiscountDTO> discountedProducts() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                            SELECT
                                 p.id,
                                 p.name,
                                 p.description,
                                 p.price,
                                 CASE
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 30 THEN 50
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 90 THEN 25
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 150 THEN 15
                                    ELSE 0
                                 END AS percentage,
                                 (p.price * (100 -
                                     CASE
                                        WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 30 THEN 50
                                        WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 90 THEN 25
                                        WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 150 THEN 15
                                        ELSE 0
                                     END
                                 ) / 100) AS discounted_price,
                                 DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) AS days_left,
                                 p.img
                             FROM products p
                             WHERE
                                 DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 150
                             ORDER BY
                                 CASE
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 30 THEN 50
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 90 THEN 25
                                    WHEN DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), CURDATE()) <= 150 THEN 15
                                    ELSE 0
                                 END DESC, p.price ASC
                        """)
                        .mapToBean(ProductWithDiscountDTO.class) // Ánh xạ kết quả vào DTO
                        .list()
        );
    }

    public static void main(String[] args) {
        ProductWithDiscountDao productWithDiscountDao = new ProductWithDiscountDao();
        List<ProductWithDiscountDTO> productWithDiscount = productWithDiscountDao.discountedProducts();
        for (ProductWithDiscountDTO product : productWithDiscount) {
            System.out.println(product);
        }
        System.out.println("Total discounted products: " + productWithDiscount.size());
    }
}

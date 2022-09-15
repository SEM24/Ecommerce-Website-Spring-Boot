package com.khomsi.site_project;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.Vendor;
import com.khomsi.site_project.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class ProductTest {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateProduct() {
        Category category = entityManager.find(Category.class, 1);
        Vendor vendor = entityManager.find(Vendor.class, 2);

        Product product = new Product();
        product.setTitle("Samsung Galaxy S22 Ultra");
        product.setAlias("samsung_galaxy_s22_ultra");
        product.setDescription("Full hd screen, 512 gb, green, red, blue colors");
        product.setPrice(36000);
        product.setImageURL("https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_91200147/fee_786_587_png");
        product.setCategory(category);
        product.setVendor(vendor);

        Product saveProduct = productRepo.save(product);
        assertThat(saveProduct).isNotNull();
        assertThat(saveProduct.getId()).isPositive();
    }

    @Test
    void testListAllProducts() {
        Iterable<Product> iterableProd = productRepo.findAll();
        iterableProd.forEach(System.out::println);
    }

    @Test
    void testGetProduct() {
        Integer id = 2;
        Product findById = productRepo.getReferenceById(id);
        System.out.println(findById);
        assertThat(findById).isNotNull();
    }

    @Test
    void testUpdateProduct() {
        Integer id = 12;
        Product product = productRepo.getReferenceById(id);

        product.setPrice(39000);
        productRepo.save(product);
        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(39000);
    }

    @Test
    void testDeleteProduct() {
        Integer id = 12;
        productRepo.deleteById(id);
        Optional<Product> result = productRepo.findById(id);

        assertThat(result.isEmpty());
    }

    @Test
    public void testSearchProduct(){
        String keyword = "Apple";
        int pageNum =  0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Product> page = productRepo.findAll(keyword, pageable);

        List<Product> productList = page.getContent();

        productList.forEach(product -> System.out.println(product));

        assertThat(productList.size()).isGreaterThan(0);
    }
}

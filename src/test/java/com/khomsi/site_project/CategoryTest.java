package com.khomsi.site_project;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.Vendor;
import com.khomsi.site_project.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRep;

    @Test
    void testCreateCategory() {

        Category testCategory = new Category();
        testCategory.setTitle("Samsung Galaxy S22 Ultra");
        testCategory.setAlias("samsung_galaxy_s22_ultra");
        testCategory.setImageURL("https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_91200147/fee_786_587_png");
        testCategory.setEnabled(true);

        Category saveCategory = categoryRep.save(testCategory);
        assertThat(saveCategory).isNotNull();
        assertThat(saveCategory.getId()).isPositive();
    }

    @Test
    public void testListEnabledCategories() {
        List<Category> categories = categoryRep.findAllEnabled();

        categories.forEach(category -> {
            System.out.println(category.getTitle() + " (" + category.getEnabled() + ")");
        });
    }
}

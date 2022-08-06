package com.khomsi.site_project;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRep;

    @Test
    public void testListEnabledCategories() {
        List<Category> categories = categoryRep.findAllEnabled();

        categories.forEach(category -> {
            System.out.println(category.getTitle() + " (" + category.getEnabled() + ")");
        });
    }
}

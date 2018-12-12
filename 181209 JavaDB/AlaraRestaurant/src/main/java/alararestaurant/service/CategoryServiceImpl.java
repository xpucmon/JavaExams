package alararestaurant.service;

import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder result = new StringBuilder();

        this.categoryRepository.findAll().stream()
                .sorted((c1, c2) -> c2.getItems().size() - c1.getItems().size())
                .forEach(c -> {
                    result.append(String.format("Category: %s", c.getName())).append(System.lineSeparator());
                    c.getItems().forEach(item -> {
                        result.append(String.format("--- Item Name: %s", item.getName())).append(System.lineSeparator());
                        result.append(String.format("--- Item Price: %.2f", item.getPrice())).append(System.lineSeparator());
                        result.append(System.lineSeparator());
                    });
                }
        );

        return result.toString();
    }
}

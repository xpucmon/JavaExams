package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.domain.dtos.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String FILES_ITEMS_JSON = System.getProperty("user.dir") + "/src/main/resources/files/items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(FILES_ITEMS_JSON);
    }

    @Override
    public String importItems(String items) {
        StringBuilder importResult = new StringBuilder();

        ItemImportDto[] itemImportDtos = this.gson
                .fromJson(items, ItemImportDto[].class);

        for (ItemImportDto itemImportDto : itemImportDtos) {
            Item item = this.itemRepository.findByName(itemImportDto.getName()).orElse(null);

            if (item != null){
                continue;
            }

            Category category = this.categoryRepository.findByName(itemImportDto.getCategory()).orElse(null);

            if (!this.validationUtil.isValid(itemImportDto)){
                importResult.append(Constants.INVALID_DATA_FORMAT).append(System.lineSeparator());

                continue;
            }

            if (category == null){
                category = new Category();
                category.setName(itemImportDto.getCategory());
                this.categoryRepository.saveAndFlush(category);
            }

            item = this.mapper.map(itemImportDto, Item.class);
            item.setCategory(category);
            this.itemRepository.saveAndFlush(item);

            importResult.append(String.format(Constants.SUCCESSFULLY_IMPORTED_JSON,
                    item.getName())).append(System.lineSeparator());

        }

        return importResult.toString().trim();
    }
}

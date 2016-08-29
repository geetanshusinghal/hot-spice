package com.hotspice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotspice.dao.CategoryDao;
import com.hotspice.dao.DishDao;
import com.hotspice.entities.Category;
import com.hotspice.entities.CategoryAttributes;
import com.hotspice.entities.Dish;
import com.hotspice.entities.DishAttributes;
import com.hotspice.es.ESHelper;
import com.hotspice.objects.Attribute;
import com.hotspice.objects.CategoryRequest;
import com.hotspice.objects.DishAttributeRequest;
import com.hotspice.objects.DishRequest;
import com.hotspice.redis.RedisHelper;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CatalogueService {

    Logger logger = LoggerFactory.getLogger(CatalogueService.class);

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    DishDao dishDao;

    @Autowired
    RedisHelper redisHelper;

    ObjectMapper om = new ObjectMapper();

    public List<Category> getAllCategories() {
        return categoryDao.fetchAll();
    }

    public Category getCategoryByName(String categoryName) {
        return categoryDao.getCategoryByName(categoryName);
    }

    public Category createCategory(CategoryRequest categoryRequest) throws Exception {
        Category category = categoryDao.getCategoryByName(categoryRequest.getCategoryName());
        if(category != null) {
            throw  new Exception("Category all ready exist with name:  " + categoryRequest.getCategoryName());
        }
        category = createCategoryObject(categoryRequest);
        categoryDao.createCategory(category);
        return category;
    }

    public Dish createDish(DishRequest dishRequest) throws Exception {
        Dish dish = dishDao.getDishByName(dishRequest.getDishName());
        if(dish != null) {
            throw  new Exception("Dish all ready exist with name:  " + dishRequest.getDishName());
        }

        Category category = categoryDao.getCategoryByName(dishRequest.getCategoryName());
        if(category == null) {
            throw  new Exception("Category not exist with name:  " + dishRequest.getCategoryName());
        }

        dish = createDishObject(dishRequest, category);
        dishDao.createDish(dish);
        return dish;
    }

    public List<Dish> searchDish(String searchType, String searchValue) throws Exception {
        try {
            List<Dish> dishList = new ArrayList<Dish>();
            if("category".equalsIgnoreCase(searchType)) {
                if(StringUtils.isEmpty(searchValue)) {
                    throw new Exception("Search value param can't be empty for search type category");
                }
                Category category = getCategoryByName(searchValue);
                dishList = dishDao.getDishByCategory(category);
            } else if("dish_name".equalsIgnoreCase(searchType)) {
                if(StringUtils.isEmpty(searchValue)) {
                    throw new Exception("Search value param can't be empty for search type dish_name");
                }
                Dish dish = dishDao.getDishByName(searchValue);
                dishList.add(0, dish);
            } else if("last_order".equalsIgnoreCase(searchType)) {
                String result = redisHelper.getValueByKey("hotfix.recent.order.dish");
                if(!StringUtils.isEmpty(result)) {
                    Dish dish = om.readValue(result, Dish.class);
                    dishList.add(0, dish);
                }
            } else if ("most_orders".equalsIgnoreCase(searchType)) {
                String result = redisHelper.getMaxScoreElement("hotfix.max.order.dish");
                Dish dish = dishDao.getDish(Long.valueOf(result));
                dishList.add(0, dish);
            }
            return dishList;
        } catch (Exception e) {
            logger.error("error while searching....... "  + e);
            throw new Exception(e.getMessage());
        }
    }

    private Category createCategoryObject(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());

        List<CategoryAttributes> list = new ArrayList<CategoryAttributes>();
        if(categoryRequest.getAttributes() != null) {
            for(Attribute attribute : categoryRequest.getAttributes()) {
                CategoryAttributes categoryAttributes = new CategoryAttributes();
                categoryAttributes.setDescription(attribute.getDescription());
                categoryAttributes.setAttributeName(attribute.getAttributeName());
                categoryAttributes.setDataType(attribute.getDataType());
                categoryAttributes.setMandatory(attribute.isMandatory());
                categoryAttributes.setValidation(attribute.getValidation());
                categoryAttributes.setCategory(category);
                categoryAttributes.setActive(true);
                list.add(categoryAttributes);
            }
        }
        category.setCategoryAttributes(list);
        category.setActive(true);
        return category;
    }

    private Dish createDishObject(DishRequest dishRequest, Category category) {
        Dish dish = new Dish();
        dish.setCategory(category);
        dish.setDishName(dishRequest.getDishName());
        dish.setDescription(dishRequest.getDescription());
        dish.setImage(dishRequest.getImage());
        dish.setPrice(dishRequest.getPrice());
        dish.setUnit(dishRequest.getUnit());

        List<DishAttributes> list = new ArrayList<DishAttributes>();
        if(dishRequest.getAttributes() != null) {
            for(DishAttributeRequest attribute : dishRequest.getAttributes()) {
                DishAttributes dishAttributes = new DishAttributes();
                dishAttributes.setAttributeName(attribute.getName());
                dishAttributes.setAttributeValue(attribute.getValue());
                dishAttributes.setDish(dish);
                dishAttributes.setActive(true);
                list.add(dishAttributes);
            }
        }
        dish.setDishAttributes(list);
        dish.setActive(true);
        return dish;
    }
}

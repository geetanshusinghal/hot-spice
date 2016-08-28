package com.hotspice.dao;

import com.hotspice.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Geetanshu on 27/08/16.
 */
@Repository
public class CategoryDao extends BaseDao<Category> {

    Logger logger = LoggerFactory.getLogger(CategoryDao.class);

    @Transactional
    public void createCategory(Category category)  {
        try {
            persist(category);
        } catch (Exception ex) {
            logger.error("Error in persisting category: " + ex + " ; "+ ex.getMessage());
        }
    }

    @Transactional
    public void updateCategory(Category category)  {
        try {
            update(category);
        } catch (Exception ex) {
            logger.error("Error in updateing category: " + ex + " ; "+ ex.getMessage());
        }
    }

    public Category getCategory(long categoryId) {
        try {
            Category category = entityManager.find(Category.class, categoryId);
            return category;
        } catch (Exception ex) {
            logger.error("Error in fetching Category: " + ex + " ; "+ ex.getMessage());
            return null;
        }
    }

    public Category getCategoryByName(String categoryName) {
        try {
            Query query = entityManager.createQuery("SELECT c from Category c where c.categoryName=:categoryName and c.isActive is true");
            query.setParameter("categoryName", categoryName);
            Category category = (Category) query.getSingleResult();
            return category;
        } catch (Exception ex) {
            logger.error("Error in fetching Category: " + ex + " ; "+ ex.getMessage());
            return null;
        }
    }

    public List<Category> fetchAll() {
        logger.debug("Entering fetchAll");
        try {
            Query query = entityManager.createQuery("SELECT c from Category c where c.isActive is true");
            List<Category> list = query.getResultList();
            logger.info("Category list size  " + list.size());
            return list;
        } catch (Exception ex) {
            logger.error("Error in fetching all Categories: " + ex + " ; " + ex.getMessage());
            return null;
        }
    }
}

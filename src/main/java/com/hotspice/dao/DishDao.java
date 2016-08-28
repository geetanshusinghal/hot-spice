package com.hotspice.dao;

import com.hotspice.entities.Category;
import com.hotspice.entities.Dish;
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
@Transactional(readOnly = false)
public class DishDao extends BaseDao<Dish> {
    Logger logger = LoggerFactory.getLogger(DishDao.class);

    @Transactional
    public void createDish(Dish dish)  {
        try {
            persist(dish);
        } catch (Exception ex) {
            logger.error("Error in persisting playlist: " + ex + " ; "+ ex.getMessage());
        }
    }

    @Transactional
    public void updateDish(Dish dish)  {
        try {
            update(dish);
        } catch (Exception ex) {
            logger.error("Error in persisting playlist: " + ex + " ; "+ ex.getMessage());
        }
    }

    public Dish getDish(long dishId) {
        try {
            Dish dish = entityManager.find(Dish.class, dishId);
            return dish;
        } catch (Exception ex) {
            logger.error("Error in fetching DishAttributes: " + ex + " ; "+ ex.getMessage());
            return null;
        }
    }

    public Dish getDishByName(String dishName) {
        try {
            Query query = entityManager.createQuery("SELECT d from Dish d where d.dishName=:dishName and d.isActive is true");
            query.setParameter("dishName", dishName);
            Dish dish = (Dish) query.getSingleResult();
            return dish;
        } catch (Exception ex) {
            logger.error("Error in fetching Category: " + ex + " ; "+ ex.getMessage());
            return null;
        }
    }

    public List<Dish> getDishByCategory(Category category) {
        try {
            Query query = entityManager.createQuery("SELECT d from Dish d where d.category=:category and d.isActive is true");
            query.setParameter("category", category);

            List<Dish> dishList =  query.getResultList();
            return dishList;
        } catch (Exception ex) {
            logger.error("Error in fetching Category: " + ex + " ; "+ ex.getMessage());
            return null;
        }
    }
}

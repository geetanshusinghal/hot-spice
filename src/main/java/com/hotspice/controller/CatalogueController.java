package com.hotspice.controller;

import com.hotspice.entities.Category;
import com.hotspice.entities.Dish;
import com.hotspice.objects.CategoryRequest;
import com.hotspice.objects.DishRequest;
import com.hotspice.objects.Request;
import com.hotspice.objects.Response;
import com.hotspice.service.CatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Geetanshu on 25/08/16.
 */
@RestController
public class CatalogueController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);

    @Autowired
    CatalogueService catalogueService;


    @RequestMapping(path = "/categories" , method = RequestMethod.GET)
    public Response<List<Category>> getAllCategories(){
        logger.info("Fetching all categories");
        try {
            Response<List<Category>> response = new Response<List<Category>>();
            List<Category> categoryList = catalogueService.getAllCategories();
            response.setPayload(categoryList);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting all categories" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/createCategory" , method = RequestMethod.POST)
    public Response<Category> createCategory(@RequestBody Request<CategoryRequest> request) {
        logger.info("Creating category: " + request);
        try {
            Response<Category> response = new Response<Category>();
            Category category = catalogueService.createCategory(request.getPayload());
            response.setPayload(category);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in creating category" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/categories/{categoryName}" , method = RequestMethod.GET)
    public Response<Category> getCategoryByName(@PathVariable String categoryName){
        logger.info("Fetching category by name");
        try {
            Response<Category> response = new Response<Category>();
            Category category = catalogueService.getCategoryByName(categoryName);
            response.setPayload(category);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting category" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/createDish" , method = RequestMethod.POST)
    public Response<Dish> createDish(@RequestBody Request<DishRequest> request) {
        logger.info("Creating dish: " + request);
        try {
            Response<Dish> response = new Response<Dish>();
            Dish dish = catalogueService.createDish(request.getPayload());
            response.setPayload(dish);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in creating Dish" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    @RequestMapping(path = "/search" , method = RequestMethod.GET)
    public Response<List<Dish>> searchDish(@RequestParam String searchType, @RequestParam(required=false) String searchValue){
        logger.info("Searching Dish by: " + searchType + " , " + searchValue );
        try {
            Response<List<Dish>> response = new Response<List<Dish>>();
            List<Dish> dishList = catalogueService.searchDish(searchType, searchValue);
            response.setPayload(dishList);
            response.setStatus("200");
            return response;
        }catch (Throwable th){
            logger.error("Exception occurred in getting Dish" + th);
            return createErrorResponse("500", th.getMessage());
        }
    }

    private Response createErrorResponse(String respCode, String message) {
        Response errorResponse = new Response();
        errorResponse.setStatus(respCode);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
package net.breezeware.service.api;

import java.util.List;

import net.breezeware.dto.food.item.CreateFoodItemDto;
import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.dto.food.item.UpdateFoodItemDto;
import net.breezeware.exception.FoodItemAlreadyExistException;
import net.breezeware.exception.FoodItemException;

/**
 * Service interface for managing food items.
 */
public interface FoodItemService {

    /**
     * Retrieves all food items.
     * @return                   a list of food item DTOs
     * @throws FoodItemException if an error occurs while retrieving the food items
     */
    List<FoodItemDto> retrieveFoodItems() throws FoodItemException;

    /**
     * Retrieves a food item by its ID.
     * @param  id                the ID of the food item to retrieve
     * @return                   the food item DTO
     * @throws FoodItemException if the food item with the given ID is not found
     */
    FoodItemDto retrieveFoodItem(Long id) throws FoodItemException;

    /**
     * Retrieves a food item by its name.
     * @param  name              the name of the food item to retrieve
     * @return                   the food item DTO
     * @throws FoodItemException if the food item with the given name is not found
     */
    FoodItemDto retrieveFoodItemByName(String name) throws FoodItemException;

    /**
     * Creates a new food item.
     * @param  createFoodItemDto the DTO representing the food item to create
     * @return                   the created food item DTO
     */
    FoodItemDto createFoodItem(CreateFoodItemDto createFoodItemDto) throws FoodItemAlreadyExistException;

    /**
     * Updates an existing food item.
     * @param  id                the ID of the food item to update
     * @param  updateFoodItemDto the DTO representing the updated food item
     * @return                   the updated food item DTO
     * @throws FoodItemException if the food item with the given ID is not found
     */
    FoodItemDto updateFoodItem(Long id, UpdateFoodItemDto updateFoodItemDto) throws FoodItemException;

    /**
     * Deletes a food item by its ID.
     * @param  id                the ID of the food item to delete
     * @throws FoodItemException if the food item with the given ID is not found
     */
    void deleteFoodItem(Long id) throws FoodItemException;
}

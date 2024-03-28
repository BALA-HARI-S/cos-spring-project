package net.breezeware.service.api;

import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.exception.FoodItemException;

import java.util.List;

/**
 * Service interface for managing food items.
 */
public interface FoodItemService {

    /**
     * Retrieves all food items.
     *
     * @return a list of food item DTOs
     * @throws FoodItemException if an error occurs while retrieving the food items
     */
    List<FoodItemDto> retrieveFoodItems() throws FoodItemException;

    /**
     * Retrieves a food item by its ID.
     *
     * @param id the ID of the food item to retrieve
     * @return the food item DTO
     * @throws FoodItemException if the food item with the given ID is not found
     */
    FoodItemDto retrieveFoodItem(Long id) throws FoodItemException;

    /**
     * Retrieves a food item by its name.
     *
     * @param name the name of the food item to retrieve
     * @return the food item DTO
     * @throws FoodItemException if the food item with the given name is not found
     */
    FoodItemDto retrieveFoodItemByName(String name) throws FoodItemException;

    /**
     * Creates a new food item.
     *
     * @param foodItemDto the DTO representing the food item to create
     * @return the created food item DTO
     */
    FoodItemDto createFoodItem(FoodItemDto foodItemDto);

    /**
     * Updates an existing food item.
     *
     * @param id the ID of the food item to update
     * @param foodItemDto the DTO representing the updated food item
     * @return the updated food item DTO
     * @throws FoodItemException if the food item with the given ID is not found
     */
    FoodItemDto updateFoodItem(Long id, FoodItemDto foodItemDto) throws FoodItemException;

    /**
     * Deletes a food item by its ID.
     *
     * @param id the ID of the food item to delete
     * @throws FoodItemException if the food item with the given ID is not found
     */
    void deleteFoodItem(Long id) throws FoodItemException;
}


package net.breezeware.service.api;

import net.breezeware.dto.FoodItemDto;
import net.breezeware.exception.FoodItemException;

import java.util.List;

public interface FoodItemService {
    List<FoodItemDto> retrieveFoodItems() throws FoodItemException;
    FoodItemDto retrieveFoodItem(Long id) throws FoodItemException;
    FoodItemDto retrieveFoodItemByName(String name) throws FoodItemException;
    FoodItemDto createFoodItem(FoodItemDto foodItemDto);
    FoodItemDto updateFoodItem(Long id, FoodItemDto foodItemDto) throws FoodItemException;
    void deleteFoodItem(Long id) throws FoodItemException;
}

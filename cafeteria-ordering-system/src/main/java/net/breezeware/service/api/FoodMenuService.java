package net.breezeware.service.api;

import net.breezeware.dto.foodMenuDto.*;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;

import java.util.List;

public interface FoodMenuService {
    List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException;

    FoodMenuItemsDto retrieveFoodMenu(Long id) throws FoodMenuException, FoodItemException;

    List<FoodMenuItemsQuantityDto> retrieveFoodMenuOfTheDay() throws FoodMenuException, FoodItemException;

    FoodMenuDto createFoodMenu(FoodMenuCreateDto foodMenuCreateDto);

    FoodMenuDto updateFoodMenu(Long id, FoodMenuUpdateDto foodMenuUpdateDto) throws FoodMenuException;

    void deleteFoodMenu(Long id) throws FoodMenuException;

    FoodMenuItemsDto addFoodItemToMenu(Long menuId, Long foodItemId) throws FoodMenuException, FoodItemException;

    FoodMenuItemsQuantityDto updateFoodMenuItemQuantity(Long menuId, Long foodItemId, Integer quantity) throws FoodMenuException, FoodItemException;

    void deleteFoodMenuItem(Long foodMenuId, Long foodItemId) throws FoodMenuException;
}

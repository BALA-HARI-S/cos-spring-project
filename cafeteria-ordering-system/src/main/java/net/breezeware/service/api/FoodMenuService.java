package net.breezeware.service.api;

import net.breezeware.dto.*;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;

import java.util.List;

public interface FoodMenuService {
    List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException;

    FoodMenuItemsDto retrieveFoodMenu(Long id) throws FoodMenuException, FoodItemException;

    FoodMenuItemsQuantityDto retrieveFoodMenuOfTheDay(Long Id) throws FoodMenuException, FoodItemException;

    FoodMenuDto createFoodMenu(FoodMenuCreateDto foodMenuCreateDto);

    FoodMenuDto updateFoodMenu(Long id, FoodMenuUpdateDto foodMenuUpdateDto) throws FoodMenuException;

    void deleteFoodMenu(Long id) throws FoodMenuException;

    FoodMenuItemsDto updateFoodMenuItems(Long id, FoodMenuItemsUpdateDto foodMenuItemsUpdateDto) throws FoodMenuException;

    FoodMenuItemsQuantityDto updateFoodMenuItemQuantity(Long menuId, Long foodItemId, Integer quantity) throws FoodMenuException, FoodItemException;

    void deleteFoodMenuItem(Long id) throws FoodMenuException;
}

package net.breezeware.service.api;

import net.breezeware.dto.FoodItemDto;
import net.breezeware.dto.FoodMenuDto;
import net.breezeware.exception.FoodMenuException;

import java.util.List;

public interface FoodMenuService {
    List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException;

    FoodMenuDto retrieveFoodMenu(Long id) throws FoodMenuException;
    FoodMenuDto retrieveFoodMenu(String name) throws FoodMenuException;

    FoodMenuDto createFoodMenu(FoodMenuDto foodMenuDto);

    FoodMenuDto updateFoodMenu(Long id, FoodMenuDto foodMenuDto) throws FoodMenuException;

    void deleteFoodMenu(Long id) throws FoodMenuException;
}

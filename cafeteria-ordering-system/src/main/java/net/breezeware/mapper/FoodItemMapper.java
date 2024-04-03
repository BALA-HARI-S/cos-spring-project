package net.breezeware.mapper;

import org.mapstruct.Mapper;

import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.entity.FoodItem;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {
    FoodItemDto foodItemToFoodItemDto(FoodItem foodItem);

    FoodItem foodItemDtoToFoodItem(FoodItemDto foodItemDto);
}

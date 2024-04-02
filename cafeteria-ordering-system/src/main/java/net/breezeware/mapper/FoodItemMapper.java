package net.breezeware.mapper;

import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.entity.FoodItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {
    FoodItemDto foodItemToFoodItemDto(FoodItem foodItem);

    FoodItem foodItemDtoToFoodItem(FoodItemDto foodItemDto);
}

package net.breezeware.mapper;

import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.entity.FoodItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {
    FoodItemMapper INSTANCE = Mappers.getMapper(FoodItemMapper.class);

    FoodItemDto foodItemToFoodItemDto(FoodItem foodItem);

    FoodItem foodItemDtoToFoodItem(FoodItemDto foodItemDto);
}

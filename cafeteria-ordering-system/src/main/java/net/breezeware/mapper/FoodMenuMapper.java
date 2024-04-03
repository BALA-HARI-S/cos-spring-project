package net.breezeware.mapper;

import org.mapstruct.Mapper;

import net.breezeware.dto.food.menu.FoodMenuDto;
import net.breezeware.entity.FoodMenu;

@Mapper(componentModel = "spring")
public interface FoodMenuMapper {
    FoodMenuDto foodMenuToFoodMenuDto(FoodMenu foodMenu);

    FoodMenu foodMenuDtoToFoodMenu(FoodMenuDto foodMenuDto);
}

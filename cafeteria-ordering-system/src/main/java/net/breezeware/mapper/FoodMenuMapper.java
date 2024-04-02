package net.breezeware.mapper;

import net.breezeware.dto.foodMenuDto.FoodMenuDto;
import net.breezeware.entity.FoodMenu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMenuMapper {
    FoodMenuDto foodMenuToFoodMenuDto(FoodMenu foodMenu);

    FoodMenu foodMenuDtoToFoodMenu(FoodMenuDto foodMenuDto);
}

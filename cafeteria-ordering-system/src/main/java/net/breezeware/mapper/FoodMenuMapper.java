package net.breezeware.mapper;

import net.breezeware.dto.FoodMenuDto;
import net.breezeware.entity.FoodMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FoodMenuMapper {
    FoodMenuMapper INSTANCE = Mappers.getMapper(FoodMenuMapper.class);

    FoodMenuDto foodMenuToFoodMenuDto(FoodMenu foodMenu);

    FoodMenu foodMenuDtoToFoodMenu(FoodMenuDto foodMenuDto);
}

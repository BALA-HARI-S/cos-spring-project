package net.breezeware.mapper;

import net.breezeware.dto.foodOrder.FoodOrderDto;
import net.breezeware.entity.FoodOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FoodOrderMapper {
    FoodOrderMapper INSTANCE = Mappers.getMapper(FoodOrderMapper.class);

    FoodOrderDto foodOrderToFoodOrderDto(FoodOrder foodOrder);

    FoodOrder foodOrderDtoToFoodOrder(FoodOrderDto foodOrderDto);

}

package net.breezeware.dto.food.order;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("CreateFoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodOrderDto {
    @Schema(description = "The ID of the customer placing the food order.")
    @NotNull(message = "Customer id must not be empty")
    private Long customerId;

    @Schema(description = "The ID of the menu in which the food item is picked from to place order.")
    @NotNull(message = "Menu id must not be empty")
    private Long menuId;

    @Schema(description = "A map representing the food items and their corresponding quantities.")
    @NotNull(message = "Food item id and its corresponding quantity must not be empty")
    private Map<Long, Integer> foodItemsQuantityMap;
}

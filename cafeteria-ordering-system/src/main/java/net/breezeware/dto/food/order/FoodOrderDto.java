package net.breezeware.dto.food.order;

import java.time.Instant;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@JsonRootName("FoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderDto {
    @Schema(description = "The unique identifier of the food order")
    @NotNull(message = "Food order id must not be empty")
    private Long id;

    @Schema(description = "The identifier of the customer who placed the order")
    @NotNull(message = "Customer id must not be empty")
    private Long customerId;

    @Schema(description = "A mapping of food items to their respective quantities in the order")
    @NotNull(message = "Food item dto and its corresponding item quantity must not be empty")
    private Map<FoodItemDto, Integer> foodItemsQuantityMap;

    @Schema(description = "The total cost of the food order")
    @NotNull(message = "Total cost of the ordered food items must not be empty")
    private Double totalCost;

    @Schema(description = "The status of the food order")
    @NotNull(message = "Order status must not be empty")
    private OrderStatus orderStatus;

    @Schema(description = "The timestamp when the food order was created")
    @NotNull(message = "Order created date and time must not be empty")
    private Instant created;
}

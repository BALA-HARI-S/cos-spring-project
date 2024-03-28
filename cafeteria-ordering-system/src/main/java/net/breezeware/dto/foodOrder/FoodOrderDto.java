package net.breezeware.dto.foodOrder;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.dto.fooditemdto.FoodItemDto;
import net.breezeware.entity.OrderStatus;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

@Data
@JsonRootName("FoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderDto {
    @NotNull
    @Schema(description = "The unique identifier of the food order")
    private Long id;

    @Schema(description = "The identifier of the customer who placed the order")
    private Long customerId;

    @NotNull
    @Schema(description = "A mapping of food items to their respective quantities in the order")
    private Map<FoodItemDto, Integer> foodItemsQuantityMap;

    @NotNull
    @Schema(description = "The total cost of the food order")
    private Double totalCost;

    @NotNull
    @Schema(description = "The status of the food order")
    private OrderStatus orderStatus;

    @NotNull
    @Schema(description = "The timestamp when the food order was created")
    private Instant created;
}

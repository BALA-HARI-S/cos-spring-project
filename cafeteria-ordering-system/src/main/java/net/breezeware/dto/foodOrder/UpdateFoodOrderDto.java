package net.breezeware.dto.foodOrder;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.OrderStatus;

import javax.validation.constraints.Min;

@Data
@JsonRootName("UpdateFoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodOrderDto {
    @Min(0)
    @Schema(description = "The total cost of the food order. Must be non-negative.")
    private Double totalCost;

    @Schema(description = "The status of the food order.")
    private OrderStatus orderStatus;
}

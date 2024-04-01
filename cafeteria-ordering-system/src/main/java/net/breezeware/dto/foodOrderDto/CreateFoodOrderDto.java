package net.breezeware.dto.foodOrderDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@JsonRootName("CreateFoodOrder")
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodOrderDto {
    @Schema(description = "The ID of the customer placing the food order.")
    @NotNull(message = "Customer id must not be empty")
    private Long customerId;

    @Schema(
            description = "A map representing the food items and their corresponding quantities.",
            example = "{1: 2, 2: 1}"
    )
    @NotNull(message = "Food item id and its corresponding quantity must not be empty")
    private Map<Long, Integer> foodItemsQuantityMap;
}

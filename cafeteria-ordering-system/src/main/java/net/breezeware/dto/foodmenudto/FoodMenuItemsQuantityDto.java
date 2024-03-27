package net.breezeware.dto.foodmenudto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.dto.fooditemdto.FoodItemDto;
import net.breezeware.entity.Availability;

import java.util.Map;
import java.util.Set;

@Data
@JsonRootName("FoodMenuItemsQuantity")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemsQuantityDto {
    @Schema(description = "The name of the food menu")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;

    @Schema(description = "The list food items in the menu")
    private Map<FoodItemDto, Integer> foodMenuItemsQuantity;
}

package net.breezeware.dto.foodMenuDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.dto.foodItemDto.FoodItemDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonRootName("FoodMenuItems")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodMenuItemsDto {
    @Schema(description = "The list food items in the menu")
    @JsonProperty("foodMenuItems")
    @NotNull(message = "Food items must not be empty")
    private List<FoodItemDto> foodMenuItemsDto;
}

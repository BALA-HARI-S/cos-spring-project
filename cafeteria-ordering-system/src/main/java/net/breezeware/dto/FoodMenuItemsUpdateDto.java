package net.breezeware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@JsonRootName("FoodMenuItems")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemsUpdateDto {
    @Schema(description = "The list food items in the menu")
    @JsonProperty("foodMenuItems")
    private List<FoodItemDto> foodMenuItemsDto;
}

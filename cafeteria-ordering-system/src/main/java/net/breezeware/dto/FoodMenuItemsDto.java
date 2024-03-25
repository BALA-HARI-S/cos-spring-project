package net.breezeware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.Availability;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@JsonRootName("FoodMenuItems")
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuItemsDto {
    @Schema(description = "The name of the food menu")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;

    @Schema(description = "The list food items in the menu")
    @JsonProperty("foodMenuItems")
    private List<FoodItemDto> foodMenuItemsDto;

    @Schema(description = "The timestamp when the food menu was created")
    private Instant created;

    @Schema(description = "The timestamp when the food menu was last modified")
    private Instant modified;
}

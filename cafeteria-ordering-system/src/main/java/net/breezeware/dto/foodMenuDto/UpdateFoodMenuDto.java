package net.breezeware.dto.foodMenuDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.Availability;

import java.util.Set;

@Data
@JsonRootName("FoodMenu")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodMenuDto {
    @Schema(description = "The name of the food menu")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    private Set<Availability> menuAvailability;
}

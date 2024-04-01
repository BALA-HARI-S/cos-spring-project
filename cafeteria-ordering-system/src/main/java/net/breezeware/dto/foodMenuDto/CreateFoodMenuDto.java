package net.breezeware.dto.foodMenuDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.entity.Availability;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonRootName("FoodMenu")
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodMenuDto {
    @Schema(description = "The name of the food menu")
    @NotBlank(message = "Menu name must not be empty")
    private String name;

    @Schema(description = "The availability of the food menu on different days")
    @NotNull(message = "Menu availability must not be empty")
    private Set<Availability> menuAvailability;
}

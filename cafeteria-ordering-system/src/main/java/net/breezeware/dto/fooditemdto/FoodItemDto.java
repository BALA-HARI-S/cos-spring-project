package net.breezeware.dto.fooditemdto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@JsonRootName("FoodItem")
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    @Schema(description = "The ID of the food item")
    private Long id;

    @Schema(description = "The name of the food item")
    private String name;

    @Schema(description = "The price of the food item")
    private Double price;

    @Schema(description = "The instant when the food item was created")
    private Instant created;

    @Schema(description = "The instant when the food item was last modified")
    private Instant modified;
}

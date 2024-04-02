package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food_menu_item_quantity_map")
public class FoodMenuItemQuantityMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private FoodMenuItemMap foodMenuItemMap;

    @NotNull(message = "The food menu item quantity map quantity must not be empty")
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull(message = "The food menu item quantity map created date and time must not be empty")
    @Column(name = "created")
    private Instant created;

    @NotNull(message = "The food menu item quantity map modified date and time must not be empty")
    @Column(name = "modified")
    private Instant modified;
}

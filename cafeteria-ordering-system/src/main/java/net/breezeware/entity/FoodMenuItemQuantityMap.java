package net.breezeware.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Min(value = 0,message = "Quantity must not be negative value")
    private Integer quantity;

    @NotNull(message = "The food menu item quantity map created date and time must not be empty")
    @Column(name = "created")
    private Instant created;

    @NotNull(message = "The food menu item quantity map modified date and time must not be empty")
    @Column(name = "modified")
    private Instant modified;
}

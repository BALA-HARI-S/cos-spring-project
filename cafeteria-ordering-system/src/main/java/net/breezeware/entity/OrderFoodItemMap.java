package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_food_item_quantity_map")
public class OrderFoodItemMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @Column(name = "food_order_id")
    private FoodOrder foodOrder;

    @OneToOne
    @Column(name = "food_menu_id")
    private FoodMenu foodMenu;

    @OneToOne
    @Column(name = "food_item_id")
    private FoodItem foodItem;

    @NotNull(message = "The ordered food item quantity must not be empty")
    @Column(name = "quantity")
    private Integer quantity;
}

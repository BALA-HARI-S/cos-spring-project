package net.breezeware.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private FoodOrder foodOrder;

    @OneToOne
    private FoodMenu foodMenu;

    @OneToOne
    private FoodItem foodItem;

    @NotNull(message = "The ordered food item quantity must not be empty")
    @Column(name = "quantity")
    private Integer quantity;
}

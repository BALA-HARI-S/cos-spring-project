package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_food_item_map")
public class FoodMenuItemMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @Column(name = "food_item_id")
    private FoodItem foodItem;

    @OneToOne
    @Column(name = "food_menu_id")
    private FoodMenu foodMenu;
}

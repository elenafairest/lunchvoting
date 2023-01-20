package com.github.elenafairest.lunchvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "menu_date", "name"}, name = "menu_item_unique_restaurant_name_menu_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuItem extends NamedEntity {
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "menu_date", nullable = false)
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, Integer price, LocalDate menuDate) {
        super(id, name);
        this.price = price;
        this.menuDate = menuDate;
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.name, menuItem.price, menuItem.menuDate);
    }
}
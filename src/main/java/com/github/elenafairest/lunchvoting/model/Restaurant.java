package com.github.elenafairest.lunchvoting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.elenafairest.lunchvoting.util.validation.NoHtml;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"contact_info", "name"}, name = "restaurant_unique_contact_info_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restaurant extends NamedEntity {
    @Column(name = "contact_info", nullable = false)
    @NotBlank
    @Size(min = 10, max = 250)
    @NoHtml
    private String contactInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("menuDate DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(hidden = true)
    //@JsonIgnore
    private List<MenuItem> menu;

    public Restaurant(Integer id, String name, String contactInfo) {
        super(id, name);
        this.contactInfo = contactInfo;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.contactInfo);
    }
}

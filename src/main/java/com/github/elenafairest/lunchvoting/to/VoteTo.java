package com.github.elenafairest.lunchvoting.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    @NotNull
    LocalDate voteDate;

    @NotNull
    Integer restaurantId;

    public VoteTo(Integer id, LocalDate voteDate, Integer restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.restaurantId = restaurantId;
    }
}

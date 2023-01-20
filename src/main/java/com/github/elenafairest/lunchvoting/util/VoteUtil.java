package com.github.elenafairest.lunchvoting.util;

import com.github.elenafairest.lunchvoting.model.Vote;
import com.github.elenafairest.lunchvoting.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {
    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getVoteDate(), vote.getRestaurant().getId());
    }

    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createTo)
                .collect(Collectors.toList());
    }
}

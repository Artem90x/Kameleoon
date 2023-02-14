package com.example.kameleoon.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Embeddable
@NoArgsConstructor
public class Vote {

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User voter;

    @Column(name = "date_of_voting", updatable = false)
    private Instant dateOfVote;

    public Vote(VoteType voteType) {
        this.voteType = voteType;
        dateOfVote = Instant.now();
    }
    public enum VoteType {
        DOWN_VOTE,
        UP_VOTE
    }
}

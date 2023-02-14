package com.example.kameleoon.model.entity.dto;

import com.example.kameleoon.model.entity.Quote;
import com.example.kameleoon.model.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class QuoteDTO {

    private Long id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private long ownerId;
    private List<Vote> votes;
    private int score;

    public static QuoteDTO toDto(Quote quote) {
        return new QuoteDTO(quote.getId(), quote.getContent(), quote.getCreatedAt(), quote.getUpdatedAt(),
                quote.getOwner().getId(), quote.getVotes(), quote.getScore());
    }
}

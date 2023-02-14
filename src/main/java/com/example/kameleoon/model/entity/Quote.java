package com.example.kameleoon.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ElementCollection
    @CollectionTable(name = "votes")
    private List<Vote> votes = new ArrayList<>();

    @Column(name = "score")
    private int score;

    public boolean addVote(Vote vote) {
        return votes.add(vote);
    }

    public void upVote() {
        score++;
    }

    public void downVote() {
        score--;
    }
}

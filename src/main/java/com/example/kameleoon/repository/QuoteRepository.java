package com.example.kameleoon.repository;

import com.example.kameleoon.model.entity.Quote;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT * FROM quotes WHERE user_id = :user_id", nativeQuery = true)
    List<Quote> findAllByUserId(@Param("user_id") Long UserId);

    @Query(value = "SELECT * FROM quotes ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> getRandom();
}

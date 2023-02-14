package com.example.kameleoon.service;

import com.example.kameleoon.exception.QuoteNotFoundException;
import com.example.kameleoon.exception.UserNotFoundException;
import com.example.kameleoon.model.entity.Quote;
import com.example.kameleoon.model.entity.dto.QuoteDTO;

import java.util.List;

public interface QuoteService {

    QuoteDTO save(Quote quote, Long userId) throws UserNotFoundException;
    void delete(Long quoteId);
    QuoteDTO modify(Long quoteId, Quote patchedQuote) throws QuoteNotFoundException;
    QuoteDTO vote(Long quoteId, Long userId, boolean upvote) throws QuoteNotFoundException, UserNotFoundException;
    List<QuoteDTO> getAll();
    List<QuoteDTO> getAllUserQuotes(Long userId) throws UserNotFoundException;
    QuoteDTO getOneById(Long quoteId) throws QuoteNotFoundException;
    QuoteDTO getRandom() throws QuoteNotFoundException;
    List<QuoteDTO> getTopTen();
    List<QuoteDTO> getBottomTen();
}

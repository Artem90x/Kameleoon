package com.example.kameleoon.service.impl;

import com.example.kameleoon.exception.QuoteNotFoundException;
import com.example.kameleoon.exception.UserNotFoundException;
import com.example.kameleoon.model.entity.Quote;
import com.example.kameleoon.model.entity.User;
import com.example.kameleoon.model.entity.Vote;
import com.example.kameleoon.model.entity.dto.QuoteDTO;
import com.example.kameleoon.repository.QuoteRepository;
import com.example.kameleoon.repository.UserRepository;
import com.example.kameleoon.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;

    @Override
    public QuoteDTO save(Quote quote, Long userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("Requested user not found");
        }
        quote.setOwner(user.get());
        return QuoteDTO.toDto(quoteRepository.save(quote));
    }

    @Override
    public void delete(Long quoteId) {

        if (quoteRepository.existsById(quoteId)) {
            try {
                quoteRepository.deleteById(quoteId);
            } catch (EmptyResultDataAccessException e) {
            }
        }
    }

    @Override
    public QuoteDTO modify(Long quoteId, Quote patchedQuote) throws QuoteNotFoundException {

        Optional<Quote> quote = quoteRepository.findById(quoteId);

        if (quote.isEmpty()) {
            throw new QuoteNotFoundException("Requested quote not found");
        }
        Quote patchable = quote.get();

        if (patchedQuote.getContent() != null) {
            patchable.setContent(patchedQuote.getContent());
        }
        return QuoteDTO.toDto(quoteRepository.save(patchable));
    }

    @Override
    public QuoteDTO vote(Long quoteId, Long userId, boolean upvote) throws QuoteNotFoundException, UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("Requested user not found");
        }

        Optional<Quote> quoteOptional = quoteRepository.findById(quoteId);

        if (quoteOptional.isEmpty()) {
            throw new QuoteNotFoundException("Requested quote not found");
        }

        Quote quote = quoteOptional.get();
        Vote vote;

        if (upvote) {
            vote = new Vote(Vote.VoteType.UP_VOTE);
            quote.upVote();
        } else {
            vote = new Vote(Vote.VoteType.DOWN_VOTE);
            quote.downVote();
        }
        vote.setVoter(user.get());
        quote.addVote(vote);
        return QuoteDTO.toDto(quoteRepository.save(quote));
    }

    @Override
    public List<QuoteDTO> getAll() {

        return quoteRepository.findAll().stream()
                .map(QuoteDTO::toDto)
                .toList();
    }

    @Override
    public List<QuoteDTO> getAllUserQuotes(Long userId) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("Requested user not found");
        }
        return quoteRepository.findAllByUserId(userId).stream()
                .map(QuoteDTO::toDto)
                .toList();
    }

    @Override
    public QuoteDTO getOneById(Long quoteId) throws QuoteNotFoundException {

        Optional<Quote> quote = quoteRepository.findById(quoteId);

        if (quote.isEmpty()) {
            throw new QuoteNotFoundException("Requested quote not found");
        }
        return QuoteDTO.toDto(quote.get());
    }

    @Override
    public QuoteDTO getRandom() throws QuoteNotFoundException {

        Optional<Quote> quote = quoteRepository.getRandom();

        if (quote.isEmpty()) {
            throw new QuoteNotFoundException("Requested quote not found");
        }
        return QuoteDTO.toDto(quote.get());
    }

    @Override
    public List<QuoteDTO> getTopTen() {
        return quoteRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Quote::getScore).reversed())
                .limit(10)
                .map(QuoteDTO::toDto)
                .toList();
    }

    @Override
    public List<QuoteDTO> getBottomTen() {
        return quoteRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Quote::getScore))
                .limit(10)
                .map(QuoteDTO::toDto)
                .toList();
    }
}

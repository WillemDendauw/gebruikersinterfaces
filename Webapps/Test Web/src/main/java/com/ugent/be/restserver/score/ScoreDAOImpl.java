package com.ugent.be.restserver.score;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("!test")
public class ScoreDAOImpl implements ScoreDAO{
    private final ScoreRepository repository;

    public ScoreDAOImpl(ScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addScore(Score score) {
        repository.save(score);
    }

    @Override
    public void updateScore(UUID uuid, Score score) {
        repository.save(score);
    }

    @Override
    public List<Score> getAllScores() {
        return repository.findAll();
    }

    @Override
    public Optional<Score> getScore(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public void deleteScore(UUID uuid) {
        repository.deleteById(uuid);
    }
}

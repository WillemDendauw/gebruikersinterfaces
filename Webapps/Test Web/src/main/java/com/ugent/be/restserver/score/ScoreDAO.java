package com.ugent.be.restserver.score;

import com.ugent.be.restserver.score.Score;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScoreDAO {
    void addScore(Score score);
    void updateScore(UUID uuid, Score score);
    List<Score> getAllScores();
    Optional<Score> getScore(UUID uuid);
    void deleteScore(UUID uuid);
}

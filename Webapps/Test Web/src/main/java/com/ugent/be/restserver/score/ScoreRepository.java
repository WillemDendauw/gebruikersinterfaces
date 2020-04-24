package com.ugent.be.restserver.score;

import com.ugent.be.restserver.score.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {

}

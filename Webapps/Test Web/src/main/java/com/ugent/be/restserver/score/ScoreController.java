package com.ugent.be.restserver.score;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ScoreController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ScoreDAO scoreDAO;

    public ScoreController(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    /**
     * Provide a list of all scores.
     */
    @GetMapping("/scores")
    public List<Score> getScore() {
        return scoreDAO.getAllScores();
    }

    /**
     * Provide the details of a blogPost with the given id. Throw IllegalArgumentException if id doesn't exist.
     */
    @GetMapping("/scores/{id}")
    public Score getScore(@PathVariable("id") UUID uuid) {
        return scoreDAO.getScore(uuid).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Removes the blogPost with the given id.
     */
    @DeleteMapping("/scores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteScore(@PathVariable("id") UUID uuid) {
        scoreDAO.deleteScore(uuid);
    }

    /**
     * Update the blogPost with the given id.
     */
    @PutMapping("/scores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestBody Score score, @PathVariable("id") UUID uuid) {
        scoreDAO.updateScore(uuid, score);
    }

    /**
     * Creates a new BlogPost, setting its URL as the Location header on the
     * response.
     */
    @PostMapping("/scores")
    public ResponseEntity<Void> addPost(@RequestBody Score score) {
        scoreDAO.addScore(score);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(score.getUuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Maps IllegalArgumentExceptions to a 404 Not Found HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleNotFound(Exception ex) {
        logger.error("Exception is: ", ex);
        // return empty 404
    }
}

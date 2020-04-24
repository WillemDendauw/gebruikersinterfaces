package com.ugent.be.restserver.score;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Score implements Serializable {
    @Id
    private UUID uuid;
    private String name;
    private int score;

    // default constructor needed for (un)marshalling with restTemplate
    public Score() {
        this.uuid = UUID.randomUUID();
    }

    public Score(final String name, final int score) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.score = score;
    }

    public Score(final UUID uuid, final String name, final int score) {
        this.uuid = uuid;
        this.name = name;
        this.score = score;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.ugent.be.restserver.score.Score score_obj = (com.ugent.be.restserver.score.Score) o;
        return uuid.equals(score_obj.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

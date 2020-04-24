package com.ugent.be.restserver.blogpost;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BlogPost implements Serializable {
    @Id
    private UUID uuid;
    private String title;
    private String content;

    // default constructor needed for (un)marshalling with restTemplate
    public BlogPost() {
        this.uuid = UUID.randomUUID();
    }

    public BlogPost(final String title, final String content) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.content = content;
    }

    public BlogPost(final UUID uuid, final String title, final String content) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return uuid.equals(blogPost.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

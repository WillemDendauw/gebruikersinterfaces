package com.ugent.be.restserver.blogpost;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@Profile("test")
public class BlogPostDAOTest implements BlogPostDAO{
    private final Map<UUID, BlogPost> blogPosts = new HashMap<>();

    final static BlogPost defaultPost1 = new BlogPost(new UUID(0, 0), "APIs", "Are great because..");
    final static BlogPost defaultPost2 = new BlogPost(new UUID(0, 1),"Spring", "Saves you lot of time..");

    public BlogPostDAOTest() {
        List<BlogPost> defaultPosts = Arrays.asList(defaultPost1, defaultPost2);
        defaultPosts.forEach(this::addPost);
    }

    public void addPost(final BlogPost blogPost) {
        blogPosts.putIfAbsent(blogPost.getUuid(), blogPost);
    }

    public void updatePost(final UUID uuid, final BlogPost blogPost) {
        blogPosts.put(uuid, blogPost);
    }

    public List<BlogPost> getAllPosts() {
        return new ArrayList<>(blogPosts.values());
    }

    public Optional<BlogPost> getPost(final UUID uuid) {
        return Optional.ofNullable(blogPosts.get(uuid));
    }

    public void deletePost(final UUID uuid) {
        blogPosts.remove(uuid);
    }
}

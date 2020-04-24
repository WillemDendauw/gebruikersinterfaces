package com.ugent.be.restserver.blogpost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogPostDAO {
    void addPost(BlogPost post);
    void updatePost(UUID uuid, BlogPost blogPost);
    List<BlogPost> getAllPosts();
    Optional<BlogPost> getPost(UUID uuid);
    void deletePost(UUID uuid);
}

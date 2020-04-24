package com.ugent.be.restserver.blogpost;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("!test")
public class BlogPostDAOImpl implements BlogPostDAO {
    private final BlogPostRepository repository;

    public BlogPostDAOImpl(BlogPostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addPost(BlogPost post) {
        repository.save(post);
    }

    @Override
    public void updatePost(UUID uuid, BlogPost blogPost) {
        repository.save(blogPost);
    }

    @Override
    public List<BlogPost> getAllPosts() {
        return repository.findAll();
    }

    @Override
    public Optional<BlogPost> getPost(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public void deletePost(UUID uuid) {
        repository.deleteById(uuid);
    }
}

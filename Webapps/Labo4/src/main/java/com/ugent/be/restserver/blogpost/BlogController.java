package com.ugent.be.restserver.blogpost;

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

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BlogPostDAO blogPostDAO;

    public BlogController(BlogPostDAO blogPostDAO) {
        this.blogPostDAO = blogPostDAO;
    }

    /**
     * Provide a list of all blogPosts.
     */
    @GetMapping("/posts")
    public List<BlogPost> getPosts() {
        return blogPostDAO.getAllPosts();
    }

    /**
     * Provide the details of a blogPost with the given id. Throw IllegalArgumentException if id doesn't exist.
     */
    @GetMapping("/posts/{id}")
    public BlogPost getPost(@PathVariable("id") UUID uuid) {
        return blogPostDAO.getPost(uuid).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Removes the blogPost with the given id.
     */
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") UUID uuid) {
        blogPostDAO.deletePost(uuid);
    }

    /**
     * Update the blogPost with the given id.
     */
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestBody BlogPost post, @PathVariable("id") UUID uuid) {
        blogPostDAO.updatePost(uuid, post);
    }

    /**
     * Creates a new BlogPost, setting its URL as the Location header on the
     * response.
     */
    @PostMapping("/posts")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> addPost(@RequestBody BlogPost post) {
        blogPostDAO.addPost(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(post.getUuid())
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

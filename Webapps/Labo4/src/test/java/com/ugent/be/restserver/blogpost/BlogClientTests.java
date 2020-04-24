package com.ugent.be.restserver.blogpost;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogClientTests {

    /**
     * Server URL ending with the servlet mapping on which the application can be reached.
     * Make sure to manually start the server locally before running these tests.
     */
    private static final String BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void listBlogPosts() {
        String url = BASE_URL + "/posts";
        BlogPost[] posts = restTemplate.getForObject(url, BlogPost[].class);
        Assert.assertNotNull(posts);
        Assert.assertTrue(posts.length >= 2);
    }

    @Test
    public void getBlogPosts() {
        String url = BASE_URL + "/posts/{id}";
        BlogPost post = restTemplate.getForObject(url, BlogPost.class, BlogPostDAOTest.defaultPost1.getUuid());
        Assert.assertNotNull(post);
        Assert.assertEquals(BlogPostDAOTest.defaultPost1, post);
    }

    @Test
    public void createBlogPosts() {
        String url = BASE_URL + "/posts";
        BlogPost post = new BlogPost("title", "content");
        URI newPostLocation = restTemplate.postForLocation(url, post);

        BlogPost retrievedPost = restTemplate.getForObject(newPostLocation, BlogPost.class);
        Assert.assertNotNull(post);
        Assert.assertEquals(post, retrievedPost);
        Assert.assertEquals("title", post.getTitle());
        Assert.assertEquals("content", post.getContent());
    }

    @Test
    public void getUnExistingPost() {
        try {
            restTemplate.getForObject(BASE_URL + "/posts/{id}", BlogPost.class, UUID.randomUUID());
            Assert.fail("Should have thrown error.");
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    public void updatePost() {
        // add new blog post
        String url = BASE_URL + "/posts";
        BlogPost post = new BlogPost("title", "content");
        URI blogPostLocation = restTemplate.postForLocation(url, post);

        // retrieve the newly created blog post
        BlogPost retrievedPost = restTemplate.getForObject(blogPostLocation, BlogPost.class);
        Assert.assertNotNull(retrievedPost);
        Assert.assertEquals(post.getUuid(), retrievedPost.getUuid());
        Assert.assertEquals("title", retrievedPost.getTitle());
        Assert.assertEquals("content", retrievedPost.getContent());

        // update the blog post
        retrievedPost.setTitle("New Title");
        retrievedPost.setContent("New Content");
        restTemplate.put(blogPostLocation, retrievedPost);

        // retrieve the updated blog post
        BlogPost updatedPost = restTemplate.getForObject(blogPostLocation, BlogPost.class);
        Assert.assertNotNull(updatedPost);
        Assert.assertEquals(post.getUuid(), updatedPost.getUuid());
        Assert.assertEquals("New Title", updatedPost.getTitle());
        Assert.assertEquals("New Content", updatedPost.getContent());
    }

    @Test
    public void deletePost() {
        // add new blog post
        String url = BASE_URL + "/posts";
        BlogPost post = new BlogPost("title", "content");
        URI blogPostLocation = restTemplate.postForLocation(url, post);

        // retrieve the newly created blog post
        BlogPost retrievedPost = restTemplate.getForObject(blogPostLocation, BlogPost.class);
        Assert.assertNotNull(retrievedPost);
        Assert.assertEquals(post.getUuid(), retrievedPost.getUuid());
        Assert.assertEquals("title", retrievedPost.getTitle());
        Assert.assertEquals("content", retrievedPost.getContent());

        // delete the blog post
        restTemplate.delete(blogPostLocation);

        // should throw error accessing deleted post
        try {
            BlogPost updatedPost = restTemplate.getForObject(blogPostLocation, BlogPost.class);
            Assert.fail("Should have thrown error.");
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}

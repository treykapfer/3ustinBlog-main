package com.treykapfer.ustinblog.services;


import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.treykapfer.ustinblog.models.*;
import com.treykapfer.ustinblog.repositories.PostRepository;

@Service
public class PostService {
    // adding the post repository as a dependency
    private final PostRepository PostRepository;
    private final UserService userService;
    
    public PostService(PostRepository postRepository, UserService userService) {
        this.PostRepository = postRepository;
        this.userService = userService;
    }
    // returns all the posts
    public List<Post> allPosts() {
        return PostRepository.findAll();
    }
    //returns all the posts in reverse order
    public List<Post> allPostsDesc() {
        return PostRepository.findAllReverse();
    }
    // creates a post
    public Post createPost(Post b) {
        return PostRepository.save(b);
    }
    // retrieves a post
    public Post findOneByID(Long id) {
        Optional<Post> optionalPost = PostRepository.findById(id);
        if(optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            return null;
        }
    }

	public Post updatePost(Post post) {
			return PostRepository.save(post);
		}
	
	public void deletePost(Long id) {
		System.out.println(id + " post deleted");
		PostRepository.deleteById(id);
    }
    
    //Like Service for Post
    public void addLike(Long post_id, Long user_id){
        Post post = findOneByID(post_id);
        User user = userService.findUserById(user_id);

        if(!post.getLikers().contains(user)) {
            post.getLikers().add(user);
            this.PostRepository.save(post);
        }
    }

    public void unLike(Long post_id, Long user_id) {
        Post post = findOneByID(post_id);
        User user = userService.findUserById(user_id);

        post.getLikers().remove(user);
        this.PostRepository.save(post);
    }
}
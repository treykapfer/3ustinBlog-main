package com.treykapfer.ustinblog.services;


import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.treykapfer.ustinblog.models.*;
import com.treykapfer.ustinblog.repositories.CommentRepository;
import com.treykapfer.ustinblog.repositories.UserRepository;

@Service
public class CommentService {
    // adding the comment repository as a dependency
    private final CommentRepository CommentRepository;
    private final UserService userService;
    
    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.CommentRepository = commentRepository;
        this.userService = userService;
    }
    // returns all the comments
    public List<Comment> allComments() {
        return CommentRepository.findAll();
    }
    // creates a comment
    public Comment createComment(Comment b) {
        return CommentRepository.save(b);
    }
    // retrieves a comment
    public Comment findOneByID(Long id) {
        Optional<Comment> optionalComment = CommentRepository.findById(id);
        if(optionalComment.isPresent()) {
            return optionalComment.get();
        } else {
            return null;
        }
    }

	public Comment updateComment(Comment comment) {
			return CommentRepository.save(comment);
		}
	
	public void deleteComment(Long id) {
		System.out.println(id + " comment deleted");
		CommentRepository.deleteById(id);
    }
    
    //Like Service for Post
    public void addLike(Long comment_id, Long user_id){
        Comment comment = findOneByID(comment_id);
        User user = userService.findUserById(user_id);

        if(!comment.getLikers().contains(user)) {
            comment.getLikers().add(user);
            this.CommentRepository.save(comment);
        }
    }

    public void unLike(Long comment_id, Long user_id) {
        Comment comment = findOneByID(comment_id);
        User user = userService.findUserById(user_id);

        comment.getLikers().remove(user);
        this.CommentRepository.save(comment);
    }
}
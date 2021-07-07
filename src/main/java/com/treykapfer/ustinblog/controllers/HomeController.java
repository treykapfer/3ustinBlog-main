package com.treykapfer.ustinblog.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.treykapfer.ustinblog.models.*;
import com.treykapfer.ustinblog.services.CommentService;
import com.treykapfer.ustinblog.services.PostService;
import com.treykapfer.ustinblog.services.UserService;
import com.treykapfer.ustinblog.validations.UserValidator;

@Controller
public class HomeController {
	
	private UserService userServ;
	private UserValidator userVal;
	private PostService postService;
	private CommentService commentService;

	public HomeController(UserService userServ, UserValidator userVal, PostService postService, CommentService commentService) {
		this.userServ = userServ;
		this.userVal = userVal;
		this.postService = postService;
		this.commentService = commentService;
	}
	
	//ALL LANDING PAGES
	@GetMapping("/")
	public String login(@ModelAttribute("user") User user, Model model) {
		List<Post> posts = postService.allPosts();
		model.addAttribute("posts",posts);
		return "login.jsp";
	}
	//DEV PAGE
	@RequestMapping("/about")
    public String about(HttpSession session, Model model) {
		User user = (User) session.getAttribute("sesUser");
		model.addAttribute("sesUser",user);
		//
    	return "about.jsp";
	}

	//HOME/DASHBOARD
	@RequestMapping("/home")
    public String home(Model model, HttpSession session) {
		User user = (User) session.getAttribute("sesUser");
		model.addAttribute("sesUser",user);
		//
		List<Post> posts = postService.allPostsDesc();
		model.addAttribute("posts",posts);
		return "home.jsp";
	}

	//POST PAGE
	@GetMapping("/post/{id}")
	public String post(@PathVariable("id") Long id, @ModelAttribute("comment") Comment comment, Model model, HttpSession session){
		User u = (User) session.getAttribute("sesUser");
		model.addAttribute("sesUser",u);
		//
		Post post = postService.findOneByID(id);
		List<Comment> comments = commentService.allComments();
		User user = (User) session.getAttribute("sesUser");
		User userInDB = this.userServ.findUserById(user.getId());
		model.addAttribute("userInDB", userInDB);
		model.addAttribute("post",post);
		model.addAttribute("comments", comments);
		return "post.jsp";
	}

	//CREATE NEW
	@GetMapping("/post/new")
	public String addPost(Model model, HttpSession session) {
		Long id = (Long) session.getAttribute("userID");
		User u = userServ.findUserById(id);
		model.addAttribute("sesUser",u);
		
		model.addAttribute("newPost", new Post());
		model.addAttribute("allPosts", postService.allPosts());
		return "newpost.jsp";
	}

	//PROFILE PAGE
	@GetMapping("/user/{id}")
	public String profile(@PathVariable("id") Long id, Model model, HttpSession session){
		User user = (User) session.getAttribute("sesUser");
		model.addAttribute("sesUser",user);
		User u = userServ.findUserById(id);
		model.addAttribute("profUser",u);
		return "profile.jsp";
	}

	//ALL POST REQUESTS
	//LOGIN REG POST MAPPING
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User newUser, BindingResult result, HttpSession session) {
		//add validator
		userVal.validate(newUser, result);
		if(userServ.findByEmail(newUser.getEmail()) != null){
			result.rejectValue("email", null, "Email already used.");
		}
		//regular checks
		if(result.hasErrors()) {
			System.out.println(result);
			return "login.jsp";
		} else {
			User u = userServ.registerUser(newUser);
			session.setAttribute("sesUser", u);
			session.setAttribute("userID", u.getId());
			return "redirect:/home";
		}
	}
	
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	boolean isAuth = userServ.authenticateUser(email, password);
    	if(isAuth) {
    		User sesUser = userServ.findByEmail(email);
    		session.setAttribute("sesUser", sesUser);
    		session.setAttribute("userID", sesUser.getId());
    		return "redirect:/home";
    	}
    	redirectAttributes.addFlashAttribute("error", "Invalid credientials. Please try again.");
	    return "redirect:/";
    }

	@RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
	}

	//CREATE NEW POST
	@PostMapping("/post/add")
	public String createPost(@Valid @ModelAttribute("newPost") Post newPost, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			System.out.println(result);
			return "newpost.jsp";
		}
		postService.createPost(newPost);
		return "redirect:/home";
	}

	//CREATE NEW COMMENT
	@RequestMapping(value="/post/{id}/newComment", method=RequestMethod.POST)
	public String postNewComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult result, @PathVariable("id") Long id, Model model, HttpSession session){
		//Grabbing Post, User, and Comment Content for a new Comment.
		Post post = postService.findOneByID(id);
		User user = userServ.findUserById(Long.valueOf(session.getAttribute("userID").toString()));
		Comment newComment= new Comment();
		//Storing all the values into a new comment.
		newComment.setContent(comment.getContent());
		newComment.setPost(post);
		newComment.setUser(user);
		//Creating a new comment.
		commentService.createComment(newComment);
		return "redirect:/post/" + id;
		
	}

	//LIKE POSTS
	@PostMapping("post/{pID}/like")
	public String likePost(@PathVariable("pID") Long pID, HttpSession session) {
		User user = (User) session.getAttribute("sesUser");
		this.postService.addLike(pID, user.getId());
		return "redirect:/post/" + pID;
	}

	@PostMapping("post/{pID}/unLike")
	public String unLikePost(@PathVariable("pID") Long pID, HttpSession session) {
		User user = (User) session.getAttribute("sesUser");
		this.postService.unLike(pID, user.getId());
		return "redirect:/post/" + pID;
	}

	//LIKE/UNLIKE COMMENTS
	@PostMapping("comment/{cID}/{pID}/like")
	public String likeComment(@PathVariable("cID") Long cID, @PathVariable("pID") Long pID, HttpSession session) {
		User user = (User) session.getAttribute("sesUser");
		this.commentService.addLike(cID, user.getId());
		return "redirect:/post/" + pID;
	}

	@PostMapping("comment/{cID}/{pID}/unLike")
	public String unLikeComment(@PathVariable("cID") Long cID, @PathVariable("pID") Long pID, HttpSession session) {
		User user = (User) session.getAttribute("sesUser");
		this.commentService.unLike(cID, user.getId());
		return "redirect:/post/" + pID;
	}

}

package com.treykapfer.ustinblog.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="Title field cannot be empty")
    private String title;
    
    @Size(max = 255, message="Content cannot be greater than 255 characters")
    @NotEmpty(message="Content field cannot be empty")
    private String content;

    @Size(max = 255, message="The image URL cannot be greater than 255 characters")
    @NotEmpty(message="Image URL field cannot be empty")
    private String imageURL;
    
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    //To create the relationship between User and Liking a Post
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "post_likers",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likers;

    @OneToMany(mappedBy="post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    //>>>likes (many to many users, many to one post, many to one comment)

    public Post() {
    }

	//getters/setters
    //for relationships
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

    //for self

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<User> getLikers(){
        return this.likers;
    }

    public void setLikers(List<User> likers){
        this.likers = likers;
    }

    //add extra
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

	@PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

}

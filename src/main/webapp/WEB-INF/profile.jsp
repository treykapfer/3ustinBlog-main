<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<link rel="stylesheet" type="text/css" href="../css/home.css">
	<link rel="stylesheet" type="text/css" href="../css/post.css">
	<link rel="stylesheet" type="text/css" href="../css/profile.css">
	<link rel="icon" type="image/png" href="../img/favicon.png">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Alegreya+Sans+SC&family=Audiowide&family=Bebas+Neue&family=Bowlby+One+SC&family=Bungee&family=Dela+Gothic+One&family=Open+Sans&family=Roboto&family=Roboto+Slab&display=swap" rel="stylesheet">
	<title>WoW:Blog — Profile</title>
</head>
<body>
	<div class="container">
		<header>
			<h1>${profUser.username} Profile</h1>
			<nav>
				<a href="/home">Back Home</a>
				<a href="/post/new">Create Post</a>
				<a href="/user/${sesUser.id}">Your Profile</a>
				<a href="/about">Meet the Devs</a>
				<a href="/logout">Logout</a>
			</nav>
		</header>
		<main>
			<div class="user_container">
				<h1>${profUser.username}'s Activity</h1>
				<div class="post_container">
					<h2>Posts</h2>
					<c:forEach items="${profUser.posts}" var="post">
						<a href="/post/${post.id}" class="post_row">
							<div class="post_img">
								<img src="${post.imageURL}" alt="Image Failed to Load">
							</div>
							<div class="post_content">
								<h3>${post.title}</h3>
								<small>Posted by ${post.user.username} at ${post.createdAt} — ${post.likers.size()} Like(s)</small>
								<p>${post.content}</p>
							</div>
						</a>
					</c:forEach>
				</div>
				<div class="comment_container">
					<h2>Comments</h2>
					<c:forEach items="${profUser.comments}" var="comment">
						<div class="comment_card">
							<div class="comment_header">
								<p>${profUser.username} says:</p>
							</div>
							<p class="high_font">${comment.content}</p>
							<small>Posted at ${comment.createdAt}</small>
						</div>
					</c:forEach>
				</div>
			</div>
		</main>
	</div>
</body>
</html>
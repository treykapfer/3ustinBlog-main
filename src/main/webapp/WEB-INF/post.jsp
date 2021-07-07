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
	<link rel="stylesheet" type="text/css" href="../css/post.css">
	<link rel="icon" type="image/png" href="../img/favicon.png">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Alegreya+Sans+SC&family=Audiowide&family=Bebas+Neue&family=Bowlby+One+SC&family=Bungee&family=Dela+Gothic+One&family=Open+Sans&family=Roboto&family=Roboto+Slab&display=swap" rel="stylesheet">
	<title>WoW:Blog — ${post.title}</title>
</head>
<body>
	<div class="container">
		<header>
			<h1></h1>
			<nav>
				<a href="/home">Back Home</a>
				<a href="/user/${sesUser.id}">Your Profile</a>
				<a href="/post/new">Create Post</a>
				<a href="/about">Meet the Devs</a>
				<a href="/logout">Logout</a>
			</nav>
		</header>
		<main>
			<div class="post_wrap">
				<div class="post_img">
					<img src="${post.imageURL}" alt="Image Failed to Load">
				</div>
				<div class="post_content">
					<h1>${post.title}</h1>
					<h6>Posted by <a href="/user/${post.user.id}">${post.user.username}</a> at ${post.createdAt}</h6>
					<p>${post.content}</p>
					<div class="likes">
						<small>${post.likers.size()} Like(s)</small>
						<c:choose>
							<c:when test="${post.likers.contains(userInDB) }">
								<form action = "/post/${post.id}/unLike" method="post">
								<button type="submit" class="btnblue">UnLike</button>
								</form>
							</c:when>
							<c:otherwise>
								<form action = "/post/${post.id}/like" method="post">
								<button type="submit" class="btnblue">Like</button>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="comment_container">
				<h2>Comments</h2>
				<c:forEach items="${post.comments}" var="comment">
					<div class="comment_card">
						<div class="comment_header">
							<p><a href="/user/${comment.user.id}">${comment.user.username}</a> says:</p>
							<div class="likes">
								<small>${comment.likers.size()} Like(s)</small>
								<c:choose>
									<c:when test="${comment.likers.contains(userInDB) }">
										<form action = "/comment/${comment.id}/${post.id}/unLike" method="post">
										<button type="submit" class="btnblue">UnLike</button>
										</form>
									</c:when>

									<c:otherwise>
										<form action = "/comment/${comment.id}/${post.id}/like" method="post">
										<button type="submit" class="btnblue">Like</button>
										</form>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<p class="high_font">${comment.content}</p>
						<small>Posted at ${comment.createdAt}</small>
					</div>
				</c:forEach>
				<form:form method="POST" action="/post/${post.id}/newComment" modelAttribute="comment" id="comment_form">
					<div class="form">
						<form:textarea type="text" path="content" rows="4" cols="50"/>
						<input class="btn" type="submit" value="Comment"/>
					</div>
				</form:form>
			</div>
		</main>
	</div>
</body>
</html>
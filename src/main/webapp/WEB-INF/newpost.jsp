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
	<link rel="stylesheet" type="text/css" href="../css/new.css">
	<link rel="icon" type="image/png" href="../img/favicon.png">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Alegreya+Sans+SC&family=Audiowide&family=Bebas+Neue&family=Bowlby+One+SC&family=Bungee&family=Dela+Gothic+One&family=Open+Sans&family=Roboto&family=Roboto+Slab&display=swap" rel="stylesheet">
    <title>WoW:Blog — New Post</title>
</head>
<body>
  <div class="container">
    	<header>
			<h1>Create Post</h1>
			<nav>
				<a href="/home">Back Home</a>
				<a href="/user/${sesUser.id}">Your Profile</a>
				<a href="/about">Meet the Devs</a>
				<a href="/logout">Logout</a>
			</nav>
		</header>
		<main>
		<div class="post_container">
				<form:form action="/post/add" method="post" modelAttribute="newPost" class="form">
					<h1>New Post</h1>

					<form:input path="user" type="hidden" value="${sesUser.id}"/>

					<form:label path="title">Post Title</form:label>
					<form:input path="title" type="text" placeholder="Post Title"/>
					<small><p><form:errors path="title" type="text"/></p></small>

					<form:label path="content">Content</form:label>
					<form:textarea path="content" type="text" rows="4" cols="50" placeholder="Your text here"/>
					<small><p><form:errors path="content" type="text"/></p></small>

					<form:label path="imageURL">Image URL</form:label>
					<form:input path="imageURL" type="text" placeholder="Paste Image URL"/>
					<small><p><form:errors path="imageURL" type="text"/></p></small>
					
					<input type="submit" value="Create" class="btn"/>
					
				</form:form>
			</div>
		</main>
  </div>
</body>
</html>
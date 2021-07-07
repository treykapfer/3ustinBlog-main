<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>    

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="icon" type="image/png" href="img/favicon.png">
    <script type="text/javascript" src="js/script.js"></script>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Alegreya+Sans+SC&family=Audiowide&family=Bebas+Neue&family=Bowlby+One+SC&family=Bungee&family=Dela+Gothic+One&family=Open+Sans&family=Roboto&family=Roboto+Slab&display=swap" rel="stylesheet">
    <title>WoW:Blog — Login & Registration</title>
</head>
<body>
	<div class="container">
        <header>
			<h1></h1>
            <nav>
                <a></a>
            </nav>
        </header>
        <main>
            <div class="left_container">
                <!-- here is a preview of the blog -->
				<img src="/img/Wow_blog4.png" id="blog_logo_big">
            </div>
            <div class="right_container">
                <form method="POST" action="/login">
                    <div class="form">
                        <h2>Login</h2>
                        
                        <label>Email:</label>
                        <input type="text" id="email" name="email"/>

						<small><p></p></small>

                        <label>Password:</label>
                        <input type="password" id="password" name="password"/>
                        
                        <small><p>${error}</p></small>

                        <input class="btn" type="submit" value="Login"/>
                    </div>
                </form>
				<form:form method="POST" action="/registration" modelAttribute="user">
					<div class="form">
						<h2>Register</h2>
						
							<form:label path="username">Username:</form:label>
							<form:input type="text" path="username"/>
							<small><p><form:errors path="username"/></p></small>

							<form:label path="email">Email:</form:label>
							<form:input type="email" path="email"/>
							<small><p><form:errors path="email"/></p></small>
							
							<form:label path="password">Password:</form:label>
							<form:password path="password"/>
							<small><p><form:errors path="password"/></p></small>
							
							<form:label path="passwordConfirmation">Password Confirmation:</form:label>
							<form:password path="passwordConfirmation"/>
							<small><p><form:errors path="passwordConfirmation"/></p></small>
							
						<input class="btn" type="submit" value="Register"/>
					</div>
				</form:form>
			</div>
		</main>
		<footer>
            <c:forEach items="${posts}" var="post">
                <div class="blog_card" onclick = "javaAlert()">
                    <img src="${post.imageURL}">
                    <h4>${post.title}</h4>
                    <p>${post.content}</p>
                </div>
            </c:forEach>
        </footer>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Contact Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card">
        <div class="auth-header">
            <h1>📋 Contact Manager</h1>
            <p>Create a new account</p>
        </div>

        <c:if test="${not empty errorMsg}">
            <div class="alert alert-error">${errorMsg}</div>
        </c:if>
        <c:if test="${not empty emailError}">
            <div class="alert alert-error">${emailError}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/register"
                   method="post" modelAttribute="user">

            <div class="form-group">
                <label>Full Name</label>
                <form:input path="name" placeholder="Enter your name"/>
                <form:errors path="name" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label>Email</label>
                <form:input path="email" type="email" placeholder="Enter your email"/>
                <form:errors path="email" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label>Password</label>
                <form:password path="password" placeholder="Min 6 characters"/>
                <form:errors path="password" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Register</button>
        </form:form>

        <p class="auth-footer">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
        </p>
    </div>
</div>
</body>
</html>

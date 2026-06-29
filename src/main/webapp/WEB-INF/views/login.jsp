<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Contact Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card">
        <div class="auth-header">
            <h1>📋 Contact Manager</h1>
            <p>Sign in to your account</p>
        </div>

        <!-- Success / Error Messages -->
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">${successMsg}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-error">${errorMsg}</div>
        </c:if>
        <c:if test="${not empty logoutMsg}">
            <div class="alert alert-info">${logoutMsg}</div>
        </c:if>

        <!-- Login Form -->
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="username" placeholder="Enter your email" required/>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter your password" required/>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Login</button>
        </form>

        <p class="auth-footer">
            Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
        </p>
    </div>
</div>
</body>
</html>

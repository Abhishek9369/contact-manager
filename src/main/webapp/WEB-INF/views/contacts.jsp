<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Contacts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>

<!-- ─── Navbar ─────────────────────────────────────────────────── -->
<nav class="navbar">
    <div class="nav-brand">📋 Contact Manager</div>
    <div class="nav-right">
        <span>👤 <sec:authentication property="name"/></span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
    </div>
</nav>

<!-- ─── Main Content ────────────────────────────────────────────── -->
<div class="container">

    <!-- Flash Messages -->
    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>

    <!-- Header Row -->
    <div class="page-header">
        <div>
            <h2>My Contacts <span class="badge">${totalContacts}</span></h2>
        </div>
        <a href="${pageContext.request.contextPath}/contacts/add" class="btn btn-primary">
            + Add Contact
        </a>
    </div>

    <!-- Search Bar -->
    <form action="${pageContext.request.contextPath}/contacts" method="get" class="search-form">
        <input type="text" name="keyword" value="${keyword}"
               placeholder="Search by name or phone..."/>
        <button type="submit" class="btn btn-secondary">Search</button>
        <c:if test="${not empty keyword}">
            <a href="${pageContext.request.contextPath}/contacts" class="btn btn-outline">Clear</a>
        </c:if>
    </form>

    <!-- Contacts Table -->
    <c:choose>
        <c:when test="${empty contacts}">
            <div class="empty-state">
                <p>🔍 No contacts found.</p>
                <a href="${pageContext.request.contextPath}/contacts/add" class="btn btn-primary">
                    Add your first contact
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Category</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="contact" items="${contacts}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${contact.name}</td>
                            <td>${contact.email}</td>
                            <td>${contact.phone}</td>
                            <td>
                                <c:if test="${not empty contact.category}">
                                    <span class="tag">${contact.category}</span>
                                </c:if>
                            </td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/contacts/view/${contact.id}"
                                   class="btn btn-sm btn-info">View</a>
                                <a href="${pageContext.request.contextPath}/contacts/edit/${contact.id}"
                                   class="btn btn-sm btn-warning">Edit</a>
                                <a href="${pageContext.request.contextPath}/contacts/delete/${contact.id}"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('Delete this contact?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/static/js/app.js"></script>
</body>
</html>

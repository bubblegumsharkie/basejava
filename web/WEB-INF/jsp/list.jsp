<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.resumebase.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="org.resumebase.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>List of resumes</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <tr>
                <td>
                    <a href="resume?uuid=${resume.getUuid()}">
                            ${resume.getFullName()}</a>
                </td>
                <td>
                        ${resume.getContact(ContactType.MAIL)}
                </td>
            </tr>
        </c:forEach>
        <%--        <%--%>
        <%--            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {--%>

        <%--                String uuid = resume.getUuid();%>--%>
        <%--        <tr>--%>
        <%--            <td>--%>
        <%--                <a href="resume?uuid=<%=uuid%>">--%>
        <%--                <%=resume.getFullName()%></a>--%>
        <%--            </td>--%>
        <%--            <td>--%>
        <%--                <%=resume.getContact(ContactType.MAIL)%>--%>
        <%--            </td>--%>
        <%--        </tr>--%>
        <%--        <%--%>
        <%--            }--%>
        <%--        %>--%>
    </table>
</section>
</body>
</html>

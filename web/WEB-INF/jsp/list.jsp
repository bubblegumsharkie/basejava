<%@ page import="org.resumebase.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="org.resumebase.model.ContactType" %><%--
  Created by IntelliJ IDEA.
  User: aljoscha
  Date: 25.01.2023
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
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
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {

                String uuid = resume.getUuid();%>
        <tr>
            <td>
                <a href="resume?uuid=<%=uuid%>">
                <%=resume.getFullName()%></a>
            </td>
            <td>
                <%=resume.getContact(ContactType.MAIL)%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
</body>
</html>

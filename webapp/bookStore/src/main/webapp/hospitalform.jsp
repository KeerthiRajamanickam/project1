<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hospital Application</title>
</head>
<body style="color: black;">
    <center>
        <h1>Hospital Management</h1>
        <h2>
            <a href="/new">Add New Hospital</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/list">List All Hospital</a>
        </h2>
    </center>
    <div align="center">
        <c:if test="${hospital != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${hospital == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${hospital != null}">
                        Edit Hospital
                    </c:if>
                    <c:if test="${hospital == null}">
                        Add New Hospital
                    </c:if>
                </h2>
            </caption>
            <c:if test="${hospital != null}">
                <input type="hidden" name="id" value="<c:out value='${hospital.hospital_id}' />" />
            </c:if>      
            <tr>
                <th>Hospital Name: </th>
                <td>
                    <input type="text" name="hospital_name" size="45"
                            value="<c:out value='${hospital.hospital_name}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>location: </th>
                <td>
                    <input type="text" name="location" size="45"
                            value="<c:out value='${hospital.location}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Cont No: </th>
                <td>
                    <input type="text" name="contno" size="5"
                            value="<c:out value='${hospital.contno}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>website: </th>
                <td>
                    <input type="text" name="website" size="5"
                            value="<c:out value='${hospital.website}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>
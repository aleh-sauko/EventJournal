<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<p></p>

<h3><spring:message code="administration" />
: <security:authentication property="principal.username" /></h3>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading"><spring:message code="users_table" /></div>
  <div class="panel-body">
    <h3><spring:message code="table_desc" /></h3>
  </div>
  <c:if test="${!empty userList}">
  <!-- Table -->
  <% int number = 0; %>
  <table class="table">
        <tr>
            <th>#</th>
            <th><spring:message code="firstname" /></th>
            <th><spring:message code="email" /></th>            
            <th><spring:message code="role" /></th>
            <%-- <th><spring:message code="password" /></th> --%>
            <th><spring:message code="enabled" /></th>
            <th colspan="3" align="center"><spring:message code="operation" /></th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td><%= ++number %></td>
                <td>${user.firstname} - ${user.lastname}</td>
                <td>${user.email}</td>      
                <td>${user.role}</td>
                <%-- <td style="max-width:400px">${user.password}</td> --%>
                <td>${user.enabled}</td>
                <td><a href="delete/${user.userId}"><spring:message code="delete" /></a></td>
                <td><a href="addAdmin/${user.userId}"><spring:message code="addAdmin" /></a></td>
                <td><a href="setNewPass/${user.userId}"><spring:message code="setNewPassword" /></a></td>
            </tr>
        </c:forEach>
  </table>
  </c:if>
</div>


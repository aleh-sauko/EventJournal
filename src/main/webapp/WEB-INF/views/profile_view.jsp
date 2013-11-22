<%@ include file="/WEB-INF/views/utils/include.jsp" %>
  



<div class="panel panel-default" >
  <!-- Default panel contents -->
  <div class="panel-heading">
    <h2>
    <spring:message code="profile" />
    <spring:message text=": ${user.getFirstname()}"  />
    </h2>
  </div>
    <table>
        <tr>            
            <td rowspan="4">
                <div style="width:250px;padding:20px">
                    <img src="${home}/files/get/${user.avatarId}" alt="avatar" class="img-rounded img-responsive">
                </div>
            </td>
            <td>
                <label>
                    <spring:message code="firstname"/>
                </label>
                <td><spring:message text="${user.getFirstname()}" /></td>
            </td>
        </tr>
        <tr>
            <td>
                <label>
                    <spring:message code="lastname" />
                </label>
                <td><spring:message text="${user.getLastname()}" /></td>
            </td>
        </tr>
        <tr>
            <td><label>
                <spring:message code="email" />
                </label>
                <td><spring:message text="${user.getEmail()}" /></td>
           </td>
        </tr>
        <tr>
            <td>
                <label><spring:message code="rate" />: <c:out value="${user.getRate()}" /></label>  
            </td>
        </tr>
        <tr>
            <td><label>
                <spring:message code="description" />
            </label></td>
            <td><spring:message text="${user.getDescription()}" /></td>
        </tr>   
        <tr>
            <td>
                <label><spring:message code="role" /></label>
            </td>
            <td><spring:message text="${user.getRole()}" /></td>
        </tr>
    </table>
</div>

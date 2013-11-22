<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() { 
    $("#email").keyup(function() {document.all.savebtn.disabled=bad();});
    $("#firstname").keyup(function() {document.all.savebtn.disabled=bad();});
    $("#language").keyup(function() {document.all.savebtn.disabled=bad();});
});

function bad(){
    
    var email1 = $("#email").val();
    var firstname1 = $("#firstname").val();
    var lang = $("#language").val();
    var error = false;
    
    if (firstname1 === "") {
        firstname.style.borderColor = 'red';
        error = true;
    } else {
        firstname.style.borderColor = 'green';
    }
    
    if(email1 === "" || !isValidEmailAddress(email1))
    {
        email.style.borderColor = 'red'; 
        error = true;
    } else {
        email.style.borderColor = 'green';
    }
    
    if (lang != "ru" && lang != "en") {
        language.style.borderColor = 'red';
        error = true;
    } else {
        language.style.borderColor = 'green';
    }
    
    return error;
}

function isValidEmailAddress(email) {
    var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
    return pattern.test(email);
}  
    
</script>  


<form:form method="post" action="profile_change" commandName="user">
<div class="panel panel-default" >
  <!-- Default panel contents -->
  <div class="panel-heading">
    <h2>
	<spring:message code="profile" />
	<spring:message text=": ${user.getFirstname()}"  />
	</h2>
  </div>
  <div class="panel-body">
    <a href="<c:url value="password/${user.userId}" />">
	    <spring:message code="password_change"/>
	</a>
  </div>
    <table>
        <tr>
            <form:hidden path="userId"/>
            <form:hidden path="avatarId"/>
            <form:hidden path="enabled"/>
            <form:hidden path="password"/>
            <form:hidden path="role"/>
            <form:hidden path="rate"/>
            
            <td rowspan="4">
                <div style="width:250px;padding:20px">
	                <img src="files/get/${user.avatarId}" alt="avatar" class="img-rounded img-responsive">
	                <input id="fileupload" type="file" name="files[]" data-url="files/upload/userId${user.userId}">
	             
	                <div id="dropzone">Drop avatar here</div>
	             
	                <div id="progress">
	                    <div style="width: 0%;"></div>
	                </div>
	            </div>
            </td>
            <td>
                <form:label  path="firstname">
                    <spring:message code="firstname"/>
                </form:label>
                <form:input id="firstname" path="firstname" class="form-control" />
            </td>
        </tr>
        <tr>
            <td>
	            <form:label path="lastname">
	                <spring:message code="lastname" />
	            </form:label>
	            <form:input path="lastname" class="form-control" />
	        </td>
        </tr>
        <tr>
            <td><form:label path="email">
                <spring:message code="email" />
                </form:label>
                <form:input id="email" path="email" class="form-control" />
           </td>
        </tr>
        <tr>
            <td>
                <form:label path="rate"><spring:message code="rate" />: <c:out value="${user.getRate()}" /></form:label>  
            </td>
        </tr>
        <tr>
            <td><form:label path="description">
                <spring:message code="description" />
            </form:label></td>
            <td><form:textarea path="description" class="form-control" rows="5" /></td>
        </tr>   
        <tr>
            <td>
                <form:label path="role"><spring:message code="role" /></form:label>
            </td>
            <td><spring:message text="${user.getRole()}" /></td>
        </tr>
        <tr>
           <td><spring:message code="categories"/></td>
           <td><form:select path="categories" multiple="true" class="multiselect">
               <form:options items="${categories}" />
           </form:select>
           </td>
       </tr>
        <tr>
            <td><form:label path="language">
                <spring:message code="language" />
            </form:label></td>
            <td><form:select id="language" path="language" class="form-control" >
                <form:option value="ru" />
                <form:option value="en" />
            </form:select>
            </td>
        </tr>
        <tr> 
            <td colspan="2">
            <button id="savebtn" class="btn btn-lg btn-primary btn-block" type="submit" > 
            <spring:message code="save"/></button>
            </td>
        </tr>
    </table>
</div>
</form:form>

<script>
$('.multiselect').multiselect();
</script>


<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() { 
    $("#email").keyup(function() {document.all.savebtn.disabled=bad();});
    $("#firstname").keyup(function() {document.all.savebtn.disabled=bad();});
    $("#password").keyup(function() {document.all.savebtn.disabled=bad();});
});

function bad(){
    
    var email1 = $("#email").val();
    var firstname1 = $("#firstname").val();
    var pass = $("#password").val();
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
    
    if (pass === "") {
        password.style.borderColor = 'red';
        error = true;
    } else {
        password.style.borderColor = 'green';
    }
    
    return error;
}

function isValidEmailAddress(email) {
    var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
    return pattern.test(email);
}  
    
</script>    


<div class="container">
  <form:form class="form-signin" method="post" action="user_create" commandName="user">
    <h2 class="form-signin-heading"><spring:message code="registration" /></h2>
    <form:input id="firstname" path="firstname" type="text" class="form-control" placeholder="First Name" />
    <form:input path="lastname" type="text" class="form-control" placeholder="Second Name" />
    <form:input id="email" path="email" type="text" class="form-control" placeholder="Email address" />
    <form:input id="password" path="password" type="password" class="form-control" placeholder="Password" />
    
    <button id="savebtn" class="btn btn-lg btn-primary btn-block" type="submit" disabled>
    <spring:message code="register"/>
    </button>
  </form:form>
</div>
<!-- /container -->
  


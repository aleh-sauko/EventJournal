<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<script type="text/javascript">
$(document).ready(function() { 
	//$("#pass").keyup(function() {document.all.savebtn.disabled=check();});
	$("#pass1").keyup(function() {document.all.savebtn.disabled=check();});
	$("#pass2").keyup(function() {document.all.savebtn.disabled=check();});
});

function check(){
    
    //var pass_old = $("#pass").val();
    var pass_new = $("#pass1").val();
    var pass_new1 = $("#pass2").val();
    //var user_pass = ${user.password};
    var error = false; 
    /* 
    if (pass_old != user_pass) {
        pass.style.borderColor = 'red';
        error = true;
    } else {
        pass.style.borderColor = 'green';
    }
     */
    if(pass_new === "" || pass_new1 === "" || pass_new != pass_new1)
    {
        pass1.style.borderColor = 'red';
        pass2.style.borderColor = 'red';
        error = true;
    } else {
    	pass1.style.borderColor = 'green';
        pass2.style.borderColor = 'green';
    }
    
    return error;
}
</script>  


<form:form class="form-signin" method="post" action="${user.userId}/"
    onsubmit="location.href = this.action + this.pass1.value; return false;">

    <h2><spring:message code="password_change" /></h2>
    <input class="form-control" type="text" id="pass" name="pass" placeholder="<spring:message code="password_old" />"/>
    <input class="form-control" type="password" id="pass1" name="pass1" placeholder="<spring:message code="password_new" />"/>
    <input class="form-control" type="password" id="pass2" name="pass2" placeholder="<spring:message code="repeat" />"/>
    <button id="savebtn" class="btn btn-lg btn-primary btn-block" type="submit" > 
            <spring:message code="password_change"/></button>
    
</form:form>
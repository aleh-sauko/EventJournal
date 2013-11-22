<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<style type="text/css">
  body {
    padding-top: 150px;
    padding-bottom: 40px;
    background-color: #eee;
  }
  .form-signin {
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
  }
</style>


<c:if test="${not empty param.error}">
    <div class="container">
    <br/>
	<h2 class="form-signin-heading"><font color="red"> <spring:message code="login_error" />
	   : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
	</h2>
	</div> <!-- /container -->
</c:if>

<div class="container">
  <form class="form-signin" action="<c:url value="/j_spring_security_check" />" method="POST">
    <h1 class="form-signin-heading"><spring:message code="login" /></h1>
    <input type="text" class="form-control" name="j_username" placeholder="Firstname">
    <input type="password" class="form-control" name="j_password" placeholder="Password">
    <label class="checkbox">
      <input type="checkbox" value="_spring_security_remember_me"/> 
      <spring:message code="remember_me" />
    </label>
    <button class="btn btn-lg btn-primary btn-block" type="submit" value="Login" > <spring:message code="login" /> </button>
    <button class="btn btn-lg btn-primary btn-block" type="reset" value="Reset" > <spring:message code="reset" /> </button>
  </form>

</div> <!-- /container -->

<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<style type="text/css">
  body {
    padding-top: 70px;
  }
</style>

<script type="text/javascript">
$(document).ready( function() {
	$('.dropdown-toggle').dropdown();
	});
</script>

<div class="navbar navbar-inverse navbar-fixed-top">
 <div class="container">
   <div class="navbar-header">
     <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
       <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
     </button>
     <a class="navbar-brand" href="<c:url value="/index" />">
        <spring:message code="site" />
     </a>
   </div>
   <div class="navbar-collapse collapse">
     <form class="navbar-form navbar-left">
     <ul class="nav navbar-nav">
         <li>
           <a href="<c:url value="/index" />"><spring:message code="home" /></a>
         </li>
	     <li class="dropdown active" >
	        <a href="<c:url value="/events_all" />" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="events" />
	            <b class="caret"></b>
	        </a>
	        <ul class="dropdown-menu" >
	          <li><a href="<c:url value="/events_all" />"><spring:message code="events_all" /></a></li>
	          <security:authorize access="isAuthenticated()"> 
	               <li><a href="<c:url value="/events_my" />"><spring:message code="events_my" /></a></li>
	               <li><a href="<c:url value="/events_subscriptions" />"><spring:message code="subscriptions" /></a></li>
	          </security:authorize>
	          <li><a href="<c:url value="/categories" />"><spring:message code="categories" /></a></li>
	          <li><a href="<c:url value="/events_income" />"><spring:message code="events_income" /></a></li>
	          <li><a href="<c:url value="/events_past" />"><spring:message code="events_past" /></a></li>
	        </ul>
	     </li>
	     <li>
           <a href="<c:url value="/search" />"><spring:message code="search" /></a>
         </li>     
     </ul>
     </form>
     <form class="navbar-form navbar-right">
        <ul class="nav navbar-nav">
        <security:authorize access="!isAuthenticated()">    
		       <li class="active">
		         <a href="<c:url value="/login" />"><spring:message code="login" /></a>
		       </li>
		       <li>
		         <a href="<c:url value="/registration" />"><spring:message code="registration" /></a>
		       </li>
	    </security:authorize>
	    <security:authorize access="isAuthenticated()"> 
		       <li class="active">
                 <a href="<c:url value="/profile" />">
                 <span class="glyphicon glyphicon-user"></span>
                 <spring:message code="profile" />
                 : <security:authentication property="principal.username" />
                 </a>
               </li>
               <security:authorize access="hasRole('ROLE_ADMIN')">
               <li>
                 <a href="<c:url value="/admin" />"><spring:message code="administration" /></a>
               </li>
               </security:authorize>
               <li>
                 <a href="<c:url value="/logout" />">
                 <span class="glyphicon glyphicon-off"></span>
                 <spring:message code="logout" /></a>
               </li>
         </security:authorize>
               <li class="divider"></li>
         <c:if test="${locale == 'ru'}">
               <li>
                 <a id="href_en" href="<c:url value="?lang=en" />">English</a> 
               </li>
         </c:if>
         <c:if test="${locale == 'en'}">
               <li>
                 <a id="href_ru" href="<c:url value="?lang=ru" />">Russian</a>
               </li>
         </c:if>    
	   </ul>
     </form>
   </div>
   <!--/.navbar-collapse -->
  </div>
</div>
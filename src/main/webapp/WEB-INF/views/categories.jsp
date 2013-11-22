<%@ include file="/WEB-INF/views/utils/include.jsp" %>


<h1><spring:message code="categories"/></h1>


<div class="container">
<c:forEach items="${categories}" var="category">
  <!-- Example row of columns -->
  <div class="well">
      <h2><spring:message code="${category}"/></h2>
      <p><a class="btn btn-default" href="${home}/categories/${category}"><spring:message code="view" /> &raquo;</a></p>
  </div>
  <br/>
 </c:forEach> 
</div>
 

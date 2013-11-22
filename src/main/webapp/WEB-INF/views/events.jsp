<%@ include file="/WEB-INF/views/utils/include.jsp" %>


<h1><spring:message code="${title}"/></h1>

<c:if test="${title == 'search'}">
<div>
<form:form action="${home}/search/" method="post"
    onsubmit="location.href = this.action + this.content.value; return false;" >
    <input type="text" class="form-control" id="content" style="height:30px;">
    <button name="submitbtn" class="btn btn-primary" type="submit" >
      <spring:message code="search"/>
    </button>
</form:form>
</div>
</c:if>

<c:if test="${createEvent}">
<div class="jumbotron">
  <div class="container">
    <h1><spring:message code="event_create" /></h1>
    <p><spring:message code="event_create_desc" /></p>
    <p><a class="btn btn-primary btn-lg" href="event_create"><spring:message code="create" /> &raquo;</a></p>
  </div>
</div>
</c:if>

<c:if test="${empty eventsList}">
    <h3><spring:message code="not_found" /></h3>
</c:if>

<div class="container">
<c:forEach items="${eventsList}" var="event">
  <!-- Example row of columns -->
  <div class="well">
      <h2>${event.title}</h2>
      
	  <table class="table">
	       <tr>
	           <td>
	               <div class="conteiner">
			        <spring:message code="categories" />: 
			        <c:forEach items="${event.categories}" var="category">
			             <a href="${home}/categories/${category}">
			            <spring:message code="${category}"/>,  
			            </a>
			        </c:forEach>
			       </div>
	           </td>
	           <td>
	               <label
			        class="input-medium tm-input tm-input-info tagsManager${event.eventId}" id="tagsManager${event.eventId}">
			        <script>
			            $(function () {
			            var tagApi = jQuery(".tagsManager${event.eventId}").tagsManager({
			                prefilled: "${event.tags}",
			                CapitalizeFirstLetter: true,
			                AjaxPush: null,
			                AjaxPushAllTags: null,
			                AjaxPushParameters: null,   
			                delimiters: [44, 9, 13],
			                backspace: [8],
			                hiddenTagListName : 'hidden_tags',
			                hiddenTagListId: null,
			                tagsContainer: null,
			                tagCloseIcon: '',
			                tagClass: '',
			                validator: null,
			                onlyTagList: false
			             });
			            });        
			        </script>
			      </label>
	           </td>
	       </tr>
	       <tr>
	           <td>
	               <div style="width:250px;padding:20px">
			          <img src="${home}/files/get/${event.avatarId}" alt="avatar" class="img-rounded img-responsive">
			      </div>
	           </td>
	           <td>
	               <spring:message code="description"/>
                   <div id="mm${event.eventId}" class="well" align="left">
                    </div>
                    <script type="text/javascript">
                    function hereDoc(f) {
                          return f.toString().
                              replace(/^[^\/]+\/\*!?/, '').
                              replace(/\*\/[^\/]+$/, '');
                        }
                    $(function(){mm${event.eventId}.innerHTML = markdown.toHTML(hereDoc(function () {/*!    
${event.description}*/}));});
                    </script>
               </td>
	       </tr>
	  </table>
	  
      
      <p><a class="btn btn-default" href="${home}/event/${event.eventId}"><spring:message code="view" /> &raquo;</a></p>
  </div>
  <br/>
 </c:forEach> 
 </div>
 

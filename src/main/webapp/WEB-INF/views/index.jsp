<%@ include file="/WEB-INF/views/utils/include.jsp" %>


<script src="${home}/resources/tag-cloud/jquery.tagcloud.js"></script>
<script type="text/javascript">
$.fn.tagcloud.defaults = {
  size: {start: 16, end: 26, unit: 'pt'},
  color: {start: '#cde', end: '#f52'}
};

$(function () {
  $('#whatever a').tagcloud();
});
</script>


<div class="well">
<table class="table">  
  <tr>
    <td>
	    <div class="panel-heading">
		  <h1><spring:message code="site" /></h1>
		</div>
    </td>
    <td rowspan="2">
        <div class="panel panel-default">
        <div class="panel-heading"><spring:message code="rate" /></div>
        <table class="table">
            <% int number = 1; %>
            <c:forEach items="${userList}" var="user">
		    <tr>
		      <td><%=number++ %>. </td>
		      <td>${user.firstname}: </td>
		      <td>${user.rate}</td>
		    </tr>
		    </c:forEach>
        </table>
        </div>
    </td>
  </tr>
  <tr>
	  <td>
	    <div class="panel-body">
			<h3><spring:message code="site_desc" /></h3>
			<div id="whatever" align="center">
			  <c:forEach items="${tags}" var="tag">
			     <a href="${home}/search/tag/${tag}" rel="10">${tag}</a>
			     <a href="${home}/search/tag/${tag}" rel="3">${tag}</a>
			     <a href="${home}/search/tag/${tag}" rel="7">${tag}</a>
			  </c:forEach>
			</div>
		</div>
	  </td>
  </tr>
</table>
</div>




<div class="container">
<c:forEach items="${eventList}" var="event">
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
                      <img src="files/get/${event.avatarId}" alt="avatar" class="img-rounded img-responsive">
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
      <p><a class="btn btn-default" href="event/${event.eventId}"><spring:message code="view" /> &raquo;</a></p>
  </div>
  <br/>
 </c:forEach> 
 </div>


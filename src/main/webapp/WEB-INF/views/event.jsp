<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<style type="text/css">
div#map_container{
    width:250px;
    height:200px;
}
</style>
<script type="text/javascript" 
   src="http://maps.googleapis.com/maps/api/js?sensor=false&language=${locale}"></script>
 
<script type="text/javascript">
  function loadMap() {
    var latlng = new google.maps.LatLng(${event.coordinates});
    var myOptions = {
      zoom: 4,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
 
    var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      title:"${event.title}"
    }); 
 
  }
</script>

<script type="text/javascript" src="${home}/resources/simple-player/jquery.simpleplayer.js"></script>
<link rel="stylesheet" href="${home}/resources/simple-player/simpleplayer.css" type="text/css">


<script type="text/javascript">
    $(document).ready(function() {
        var settings = {
            progressbarWidth: '200px',
            progressbarHeight: '5px',
            progressbarColor: '#22ccff',
            progressbarBGColor: '#eeeeee',
            defaultVolume: 0.8
        };
        $(".player").player(settings);
    });
</script>


<body onload="loadMap()">
<!-- /container -->
<div class="panel panel-default"> 
  <div class="panel-heading">
    <spring:message code="title"/>:
    <h1><c:out value="${event.title}" /></h1>
  </div>
  <div class="panel-body">
    <div class="conteiner">
        <spring:message code="categories" />: 
        <c:forEach items="${event.categories}" var="category">
            <a href="${home}/categories/${category}">
            ${category},
            </a>  
        </c:forEach>
    </div>
    <spring:message code="tags"/>: 
    <label class="input-medium tm-input tm-input-info tagsManager">
    <script>
        $(function () {
        jQuery(".tagsManager").tagsManager({
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
  </div>
  <table class="table" >
     <tr>
        <td style="width:200px">
          <div align="center" >
            <table class="table">
                <tr>
                    <td>
	                   <div style="width:200px;">
	                      <img src="${home}/files/get/${event.avatarId}" alt="avatar" class="img-rounded img-responsive">
	                  </div>
	                </td>
                </tr>
                <tr>
                    <td><spring:message code="status"/>: </td>
                    <td>
                        <c:if test="${event.status}">
                            <spring:message code="income"/>
                        </c:if>
                        <c:if test="${!event.status}">
                            <spring:message code="past"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="date"/>: </td>
                    <td><c:out value="${event.date}" /></td>
                </tr>
                <tr>
                    <td><spring:message code="place"/>: </td>
                    <td><c:out value="${event.place}" /></td>
                </tr>
                <tr>
                    <td colspan = "2">
                        <div id="map_container"></div>
                    </td>
                </tr>
                <security:authorize access="isAuthenticated()"> 
                <c:if test="${event.status}">
	               <c:if test="${!inEvent}">
		                <tr>
		                    <td colspan="2">
		                        <button name="submitbtn" class="btn btn-primary btn-block" 
		                            onclick="location.href='${home}/event_join/${event.eventId}'">
		                            <spring:message code="join"/>
		                        </button>
		                    </td>
		                </tr>
	                </c:if>
	                <c:if test="${inEvent}">
		              <tr>
		                   <td colspan="2">
		                    <button name="submitbtn" class="btn btn-primary btn-block" 
		                        onclick="location.href='${home}/event_edit/${event.eventId}'">
				                <spring:message code="edit"/>
				            </button>
			            </td>
			          </tr>
			        
		               <tr>
		                   <td colspan="2">
		                       <button name="submitbtn" class="btn btn-primary btn-block" 
		                           onclick="location.href='${home}/event_leave/${event.eventId}'">
		                           <spring:message code="leave"/>
		                       </button>
		                   </td>
		               </tr>
		               <tr>
		                   <td colspan="2">
		                       <button name="submitbtn" class="btn btn-primary btn-block" 
		                           onclick="location.href='${home}/event_close/${event.eventId}'">
		                           <spring:message code="close"/>
		                       </button>
		                   </td>
		               </tr>
	                </c:if>
                </c:if>
                </security:authorize>
            </table>
          </div>
        </td>
        <td>
            <spring:message code="description"/>
		    <div id="mm" class="well" style="width:450px;">
		    </div>
		    <script type="text/javascript">
		    function hereDoc(f) {
		    	  return f.toString().
		    	      replace(/^[^\/]+\/\*!?/, '').
		    	      replace(/\*\/[^\/]+$/, '');
		    	}
		    $(function(){mm.innerHTML = markdown.toHTML(hereDoc(function () {/*!    
${event.description}*/}));});
		    </script>    
        </td>
        <td>
			<div class="well" style="width:350px;padding:20px">
			    <h3><spring:message code="playlist"/></h3>
			    
			    <c:if test="${inEvent}">
			    <input id="fileupload" type="file" name="files[]" data-url="${home}/files/upload_files/${event.eventId}/audio" multiple>
			    <div id="dropzone">Drop files here</div>
			    <div id="progress">
			        <div style="width: 0%;"></div>
			    </div>
			    </c:if>
			    
			    <table id="uploaded-files">
			        <c:forEach items="${event.audio}" var="trackId">
			            <div style="width:350px;padding:20px">
			              <p>${trackId}</p>
			              <form class="inline"> 
				              <audio class="player" src="${home}/files/get/${event.eventId}/audio/${trackId}">   
							  </audio>
							  <c:if test="${inEvent}">
							  <button type="button" class="btn btn-default btn-xs"
							          onclick="location.href='${home}/files/remove/${event.eventId}/audio/${trackId}'" >
	                              <span class="glyphicon glyphicon-trash"></span>
	                          </button>
	                          </c:if>
						  </form>
			            </div>
			        </c:forEach>
			    </table>
			</div>
        </td>
      </tr>
      <tr>
        <td>
            <div class="well">
	           <h2><spring:message code="participants"/></h2>
	           <c:forEach items="${event.users}" var="user">
	               <a href="${home}/profile/${user.firstname}">
	              <label>${user.firstname} </label>
	              </a>
	           </c:forEach>
            </div>
            <c:if test="${inEvent}">
            <div class="well">
               <h2><spring:message code="photos"/></h2>
               <button name="submitbtn" class="btn btn-primary btn-block" 
                   onclick="location.href='${home}/event/${event.eventId}/photos'">
                   <spring:message code="view"/>
               </button>
            </div>
            </c:if>
        </td>
        <td colspan="2">
          <ul>
            <security:authorize access="isAuthenticated()"> 
            <li>
              <form:form action="${home}/event/${event.eventId}/" method="post"
                  onsubmit="location.href = this.action + this.content.value; return false;" >
	              <h4><spring:message code="comment"/></h4>
	              <textarea class="form-control" rows="3" id="content"></textarea>
	              <button name="submitbtn" class="btn btn-primary" type="submit" >
	                <spring:message code="post"/>
	              </button>
              </form:form>
            </li>
            </security:authorize>
            <c:forEach items="${event.comments}" var="comment">
	            <li>
	              <table class="table">
	                 <tr>
	                   <td style="width:150px;padding:20px">
	                       <a href="${home}/profile/${comment.author}">
	                       <img src="${home}/files/get_user_avatar/${comment.author}" alt="avatar" class="img-rounded img-responsive">
	                       ${comment.author} : </a>
	                   </td>
	                   <td>
	                       <div class="well"><p>${comment.content}</p></div>
	                       <p><spring:message code="posted"/>: ${comment.date}</p>
	                   </td>
	                 </tr>
	              </table>
	            </li>
            </c:forEach>
          </ul>
        </td>
     </tr>
  </table>

</div>
</body>



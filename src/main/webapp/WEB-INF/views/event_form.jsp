<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<style type="text/css">
div#map_container{
    width:300px;
    height:250px;
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
</head>


<body onload="loadMap()">
<!-- /container -->
<div class="container">
<form:form action="${home}/event_create_press" commandName="event" method="post">
<div class="panel panel-default">  
  <div class="panel-heading">
    <spring:message code="title"/>
    <form:input path="title" type="text" class="form-control" placeholder="Title" />
  </div>
  <div class="panel-body">
    <p><spring:message code="tags"/></p>
    <input class="form-control input-medium tm-input tm-input-info tagsManager" id="tagsManager"
        placeholder="Tags" type="text" autocomplete="off" style="width:9em;" data-original-title="" />
  </div>
  <table class="table">
     <tr>
        <td>
          <div align="center">
            <form:hidden path="status" />
            <form:hidden path="eventId" />
            <form:hidden path="avatarId" />
            <form:hidden path="audio" />
            <form:hidden path="photos" />
            <input type="hidden" name="tags" value="" />
            <table>
                <tr>
                    <td>
                       <div style="width:250px;padding:20px">
                          <img src="${home}/files/get/${event.avatarId}" alt="avatar" class="img-rounded img-responsive">
                          <input id="fileupload" type="file" name="files[]" data-url="${home}/files/upload/eventId${event.eventId}">
                          <div id="dropzone">Drop avatar here</div>
                 
		                  <div id="progress">
		                      <div style="width: 0%;"></div>
		                  </div>
                      </div>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="categories"/></td>
                    <td><form:select path="categories" multiple="true" class="multiselect">
                        <form:options items="${categories}" />
                    </form:select>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="date" /></td> 
                    <td><form:input path="date" type="text" class="form-control" placeholder="Date" /></td>
                </tr>
                <tr>
                    <td><spring:message code="place"/></td>
                    <td><form:input path="place" type="text" class="form-control" placeholder="Place" /></td>
                </tr>
                <tr>
                    <td colspan = "2">
                        <div id="map_container"></div>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="coordinates"/></td>
                    <td><form:input path="coordinates" type="text" class="form-control" placeholder="Coordinates for Google Maps" />
                    <a href="https://maps.google.ru/maps"><spring:message code="search"/></a>
                    </td>
                </tr>
            </table>
            <button name="submitbtn" class="btn btn-primary btn-block" type="submit">
                <c:if test="${event.eventId == 0}">
                    <spring:message code="create"/>
                </c:if> 
                <c:if test="${event.eventId != 0}">
                    <spring:message code="save"/>
                </c:if>
            </button>
            <br/>
            <c:if test="${event.eventId == 0}">
               <a href="${home}/events_my" >
		           <button class="btn btn-primary btn-block" type="button" >
		               <spring:message code="back"/>
		           </button>
		       </a>
            </c:if>
            <c:if test="${event.eventId != 0}">
               <a href="${home}/event/${event.eventId}" >
                   <button class="btn btn-primary btn-block" type="button" >
                       <spring:message code="back"/>
                   </button>
               </a>
            </c:if>
          </div>
        </td>
        <td>
          <spring:message code="description"/>
          <form:textarea path="description" id="markdown" data-provide="markdown" data-width="600"
            placeholder="Description" class="markdown" rows="10" />
        </td>
      </tr>
  </table>
</div>
</form:form>
</div>
</body>

<script>
    $(function () {
    var tagApi = jQuery(".tagsManager").tagsManager({
    	prefilled: '${event.tags}',
        CapitalizeFirstLetter: true,
        AjaxPush: null,
        AjaxPushAllTags: null,
        AjaxPushParameters: null,
        delimiters: [44, 9, 13],
        backspace: [8],
        blinkBGColor_1: '#FFFF9C',
        blinkBGColor_2: '#CDE69C',
        hiddenTagListName : 'tags',
        hiddenTagListId: 'tags',
        tagsContainer: null,
        tagCloseIcon: '×',
        tagClass: '',
        validator: null,
        onlyTagList: false
     });
    
    jQuery("#tagsManager").typeahead({
        //name: 'name',
        limit: 15,
        prefetch: '${home}/tags',
      }).on('typeahead:selected', function (e, d) {

        console.log("new tag from typeahead.js: " + d.value );
        console.log("all existing tags: " + tagApi.tagsManager("tags").tags);

        tagApi.tagsManager("pushTag", d.value);

        console.log("new list of tags: " + tagApi.tagsManager("tags").tags);

      });
    
    $('.multiselect').multiselect();

});
</script>

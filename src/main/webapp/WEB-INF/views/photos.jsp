<%@ include file="/WEB-INF/views/utils/include.jsp" %>

<h2><spring:message code="photos"/>: ${event.title}</h2>

<button type="button" class="btn btn-default btn-xs"
        onclick="location.href='${home}/event/${event.eventId}'" >
    <spring:message code="back"/>
</button>


<div style="width:500px;padding:20px">
 
    <input id="fileupload" type="file" name="files[]" data-url="${home}/files/upload_files/${event.eventId}/photos" multiple>
 
    <div id="dropzone">Drop files here</div>
 
    <div id="progress">
        <div style="width: 0%;"></div>
    </div>
 
    <table id="uploaded-files">
        <c:forEach items="${event.photos}" var="photoId">
            <div style="width:250px;padding:20px">
              <img src="${home}/files/get/${event.eventId}/photos/${photoId}" alt="photo" class="img-rounded img-responsive">
              <button type="button" class="btn btn-default btn-xs"
                      onclick="location.href='${home}/files/remove/${event.eventId}/photos/${photoId}'" >
                  <span class="glyphicon glyphicon-trash"></span>
              </button>
            </div>
        </c:forEach>
    </table>

</div>
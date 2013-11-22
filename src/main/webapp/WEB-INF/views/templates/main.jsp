<%@ include file="/WEB-INF/views/utils/include.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
    <title><tiles:getAsString name="title" /></title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="${home}/resources/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${home}/resources/bootstrap/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css" href="${home}/resources/css/style.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${home}/resources/tagmanager/tagmanager.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${home}/resources/bootstrap-markdown/css/bootstrap-markdown.min.css">
    
    <script type="text/javascript" src="${home}/resources/jquery-1.10.2.js"></script>
    <script src="${home}/resources/jq-file-upload/jquery.ui.widget.js"></script>
    <script src="${home}/resources/jq-file-upload/jquery.iframe-transport.js"></script>
    <script src="${home}/resources/jq-file-upload/jquery.fileupload.js"></script>
    <script src="${home}/resources/jq-file-upload/myuploadfunction.js"></script>
    <script type="text/javascript" src="${home}/resources/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${home}/resources/bootstrap/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="${home}/resources/bootstrap-markdown/js/markdown.js"></script>
    <script type="text/javascript" src="${home}/resources/bootstrap-markdown/js/to-markdown.js"></script>
    <script type="text/javascript" src="${home}/resources/tagmanager/typeahead.js"></script>
    <script type="text/javascript" src="${home}/resources/tagmanager/tagmanager.js"></script>
    <script type="text/javascript" src="${home}/resources/bootstrap-markdown/js/bootstrap-markdown.js"></script>
</head>
<body>
  <div class="container">
      <tiles:insertAttribute name="header" />
  </div>
  <div class="container">
      <tiles:insertAttribute name="menu" />
  </div>
  <div class="container">
      <tiles:insertAttribute name="body" />
  </div>
  <div class="container">
      <tiles:insertAttribute name="footer" />
  </div>
</body>
</html>
       
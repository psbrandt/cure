<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>



<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>CuRE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico" />

    <!-- Le styles -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">CuRE</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="${pageContext.request.contextPath}/">Home</a></li>
              <li class="active"><a href="${pageContext.request.contextPath}/classification">Exporter</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      	<h1>Support Vector Machine Classification Results</h1> <br/>
      	<!-- 
      	<p>
      		<h3>Summary</h3>
      		<pre>${evalSummary}</pre>      		
      	</p>
      	
      	<p>
      		<h3>Detail</h3>
      		<pre>${evalDetail}</pre>      		
      	</p>
      	<p>
      		<h3>Confusion Matrix</h3>
      		<pre>${evalConfusionMatrix}</pre>
      	</p>
      	-->
      	<p>
      		<h3>Cross Validation Summary</h3>
      		<pre>${CVSummary}</pre>      		
      	</p>
      	
      	<p>
      		<h3>Cross Validation Detail</h3>
      		<pre>${CVDetail}</pre>      		
      	</p>
      	<p>
      		<h3>Cross Validation Confusion Matrix</h3>
      		<pre>${CVConfusionMatrix}</pre>
      	</p>
      	
	 
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

  </body>
</html>

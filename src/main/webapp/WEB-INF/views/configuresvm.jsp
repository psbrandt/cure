<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

      <h1>Dataset Configuration</h1> <br/>

      <p>
		 <form:form modelAttribute="config" class="form-horizontal" action="${pageContext.request.contextPath}/classification" method="post">
		  <fieldset>
		    
		    <div class="alert alert-info">
  				Select the feature vector attributes.
			</div>	    
			<!--  
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Demographic</span><br/><br/>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[0] name="attrAge" checked> Age
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[1] name="attrGender" checked> Gender
			      </label>		
			      <label class="checkbox">
			        <input type="checkbox" id=attr[2] name="attrEthnicity" checked> Ethnicity
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[3] name="attrProvince" checked> Province
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[4] name="attrCountry" checked> Country of Origin
			      </label>	
			       <label class="checkbox">
			        <input type="checkbox" id=attr[5] name="attrOrigin" checked> Geographic Origin
			      </label>	  
			    </div>
			 </div>
			  
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Clinical</span><br/><br/>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[6] name="attrWeight" checked> Weight
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[6] name="attrBLCD4" checked> Baseline CD4 Count
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[7] name="attrPRTCD4" checked> Pre-Resistance Testing CD4 Count
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[8] name="attrPRTVL" checked> Pre-Resistance Testing Viral Load
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[9] name="attrPRTImmunoFailure" checked> Pre-Resistance Testing Immunological Failure
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[10] name="attrTFR" checked> Time on Failing Regimen
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[11] name="attrPRTCD4" checked> Pre-Resistance Testing Viral Load
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[12] name="attrTransGroup" checked> Transmission Group
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[13] name="attrHTLV1Status" checked> HTLV-1 Status
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[14] name="attrHBVStatus" checked> HBV Status
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[15] name="attrTBPrior" checked> Tuberculosis Therapy (Prior)
			      </label> 
			      <label class="checkbox">
			        <input type="checkbox" id=attr[16] name="attrTBDuring" checked> Tuberculosis Therapy (During)
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[17] name="attrTBPost" checked> Tuberculosis Therapy (Post)
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[18] name="attrBloodHB" checked> Recent Blood HB
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[19] name="attrBloodCC" checked> Recent Blood Creatinine Clearance
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[20] name="attrBloodALT" checked> Recent Blood ALT
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[21] name="attrOtherDrug1" checked> Other Drug 1
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[22] name="attrOtherDrug2" checked> Other Drug 2
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[23] name="attrOtherDrug3" checked> Other Drug 3
			      </label>
			    </div>
			 </div>
			 
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Adherence</span><br/><br/>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[24] name="attrEstimatedAdherence" checked> Patient Estimated Adherence
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[25] name="attrRemember" checked> Remember
			      </label>	
			      <label class="checkbox">
			        <input type="checkbox" id=attr[26] name="attrStop" checked> Stop
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[27] name="attrMissed" checked> Missed
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[28] name="attrWorstStop" checked> Worst Stop
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[29] name="attrNames" checked> Names
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[30] name="attrSideEffects" checked> Side Effects
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[31] name="attrDisclosure" checked> Disclosure		
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[32] name="attrBuddy" checked> Buddy		
			      </label>	
			      <label class="checkbox">
			        <input type="checkbox" id=attr[33] name="attrBuddy" checked> Councelling		
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[34] name="attrTreamentBreak" checked> Treatment Break		
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[35] name="attrTreamentBreakLength" checked> Treatment Break Length		
			      </label>	  
			    </div>
			 </div>
			 
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Other</span><br/><br/>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[36] name="attrAlcohol" checked> Alcohol Consumption
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[37] name="attrTraditionalMedicine" checked> Traditional Medicine
			      </label>	
			      <label class="checkbox">
			        <input type="checkbox" id=attr[38] name="attrIdentifiedReason" checked> Identified Virological Failure Reason
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[39] name="attrOtherCM" checked> Other Co-morbidities
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[40] name="attrOtherCMTBSoon" checked> TB Treatment Starting Soon
			      </label>	  
			      <label class="checkbox">
			        <input type="checkbox" id=attr[41] name="attrOtherCMDiaVom" checked> Diarrhea or Vomiting
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[42] name="attrPartnerOnTreatment" checked> Partner On Treatment
			      </label>
			      <label class="checkbox">
			        <input type="checkbox" id=attr[43] name="attrPartnerOnTreatment" checked> Exposure to Single Dose NVP
			      </label>
			    </div>
			 </div>
			-->
			
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Demographic</span><br/><br/>
					<c:forEach items="${demographicAttributeList}" var="var" varStatus="loopStatus">
						<form:checkbox path="selectedDemographicAttributes" value="${var}"/> ${var}<br/>
					</c:forEach>	
				</div>
			</div>
			
			<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Clinical</span><br/><br/>
					<c:forEach items="${clinicalAttributeList}" var="var" varStatus="loopStatus">
						<form:checkbox path="selectedClinicalAttributes" value="${var}"/> ${var}<br/>
					</c:forEach>
				</div>
			</div>
			
						<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Adherence</span><br/><br/>
					<c:forEach items="${adherenceAttributeList}" var="var" varStatus="loopStatus">
						<form:checkbox path="selectedAdherenceAttributes" value="${var}"/> ${var}<br/>
					</c:forEach>
				</div>
			</div>
			
						<div class="control-group">				
			    <div class="controls">
			    <span class="label label-success">Other</span><br/><br/>
					<c:forEach items="${otherAttributeList}" var="var" varStatus="loopStatus">
						<form:checkbox path="selectedOtherAttributes" value="${var}"/> ${var}<br/>
					</c:forEach>
				</div>
			</div>
				
		    <!-- Submit -->
		    
		    <div class="form-actions">
		    	<div class="btn-group">
			    	<!-- <button type="submit" class="btn btn-primary" name="submitButton" value="Classify">Classify</button> -->
			    	<button type="submit" class="btn btn-primary" name="submitButton" value="ARFF">Generate ARFF File</button>
			    	<button type="submit" class="btn btn-primary" name="submitButton" value="Excel">Generate Excel File</button>
		    	</div>
		    </div>
		  </fieldset>
		 </form:form>
		</p>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>


  </body>
  

</html>

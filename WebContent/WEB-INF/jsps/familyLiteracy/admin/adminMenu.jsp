<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="flAdminNav.do?item=loadLitAdminHome&m=40">Family Literacy Admin</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <%--<li class="active"><a href="flAdminNav.do?item=checklist">Checklist <span class="sr-only">(current)</span></a></li> --%>
        <li class="dropdown">
          <a href="flAdminNav.do?item=checklist" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Checklist <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="flAdminNav.do?item=checklist">Checklist</a></li>
            <li><a href="familyLiteracyYr1FinalNarrativeAdmin.action">Final Narrative</a></li>
            <li><a href="familyLiteracyYr1ContractedServicesAdmin.action">Budget</a></li>
            <li><a href="familyLiteracyYr1StatisticsAdmin.action">Statistics</a></li>
            <li><a href="familyLiteracyYr1FinalSignoffAdmin.action">Final Signoff</a></li>
            <%--<li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">One more separated link</a></li>  --%>
          </ul>
        </li>
      </ul>
      
      
      <ul class="nav navbar-nav navbar-right">        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin Actions <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="adminSearch.do?item=loadsearch&m=lit">Application Search</a></li>
            <li><a href="LitNewMail.do">Emails</a></li>
            <li><a href="flAdminAllocNav.do?i=allocation">PLS Appropriation</a></li>
            <li><a href="adminAppDates.do?item=viewdates&m=lit">Application Dates</a></li>
            <li><a href="flAdminNav.do?item=reports">Reports</a>
            <li role="separator" class="divider"></li>
            <li><a href="LitAdminHelp.do">Help</a></li>
          </ul>
        </li>
        <li><a href="welcomePage.jsp">Home</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

</body>
</html>
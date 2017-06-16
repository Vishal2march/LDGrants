<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="container">

<div class="panel panel-default">
	<div class="panel-heading"><h2>Family Literacy Admin Project Budget</h2></div>
	<div class="panel-body">

		<ul class="nav nav-pills nav-justified">
		<li class="menu1"><a data-toggle="pill" href="<s:url action='literacyYr1ContractedServicesAdmin'/>">Purchased Services</a></li>
		<li class="menu2"><a data-toggle="pill" href="<s:url action='literacyYr1SuppliesAdmin'/>">Supplies/Materials</a></li>
		<li class="menu3"><a data-toggle="pill" href="<s:url action='literacyYr1EquipmentAdmin'/>">Equipment</a></li>
		<li class="menu4"><a data-toggle="pill" href="<s:url action='literacyYr1TravelAdmin'/>">Travel</a></li>
		</ul>
	</div>
</div>

</div>


</body>
</html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>LGRMIF Statistic Report</title>
  </head>
  <body>
  
  <h4>LGRMIF Statistical Report</h4>
  
  <display:table name="sessionScope.allStats" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="" export="true">
       
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
    
    <display:column property="instName" title="LG Name" sortable="true" headerClass="sortable" />
    
    <display:column property="inventory" title="Cubic Feet Inventoried" />
    <display:column property="destroy" title="Cubic Feet Destroyed" />
    <display:column property="scan" title="Cubic Feet Digitized/Scanned" />
    <display:column property="microfilm" title="Cubic Feet Microfilmed" />
    <display:column property="destroymicrofilm" title="Cubic Feet Destroyed after Microfilming" />
    <display:column property="destroyscan" title="Cubic Feet Destroyed after Digitizing/Scanning" />
    <display:column property="recordsarranged" title="Cubic Feet of Historical Records Arranged" />
    <display:column property="recordsdescribed" title="Cubic Feet of Historical Records Described" />
    <display:column property="inactiverecords" title="Cubic Feet of Inactive Records Moved to Records Storage" />
    <display:column property="recordsconserved" title="Cubic Feet or Number of Volumes of Records Conserved" />
    <display:column property="hours" title="Hours Saved Per Week as a Result of Process Changes" />
    <display:column property="image" title="Number of Images Created" />
    <display:column property="online" title="Number of Records Series Made Available Online" />
    <display:column property="seriesdescrip" title="Number of Series Descriptions Produced" />
    
    <display:column property="other" title="Other" />
    <display:column property="otherExplained" title="Describe below" />
    
  </display:table>
  
  
  </body>
</html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Budget Category Report</title>
  </head>
  <body>
  
  <h5>LGRMIF Budget Totals By Category</h5>
  
  <display:table name="sessionScope.allGrants" decorator="org.displaytag.decorator.TotalTableDecorator" id="row" requestURI="" export="true" >
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projnum}" pattern="####" minIntegerDigits="4"/>
    </display:column> 
       
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="panel" sortable="true" headerClass="sortable" />
    
    <display:column title="Code">
        15<br/>
        16<br/>
        40<br/>
        45<br/>
        46<br/>
        80<br/>
        49<br/>
        30<br/>
        20<br/>
        Totals
    </display:column>
    <display:column title="Category">
        Professional Salaries<br/>
        Support Staff Salaries<br/>
        Purchased Services<br/>
        Supplies and Materials<br/>
        Travel Expenses<br/>
        Employee Benefits<br/>
        BOCES Services<br/>
        Minor Remodeling<br/>
        Equipment<br/>
        Totals
    </display:column>
    
    <display:column title="Amount Requested" >
        <fmt:formatNumber value="${row.proffAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.suppAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.purchAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.supplyAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.travAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.benAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.bocesAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.othAmtReq}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.equipAmtReq}" type="currency" minFractionDigits="0"/><br/>   
        <fmt:formatNumber value="${row.totAmtReq}" type="currency" minFractionDigits="0"/><br/>   
    </display:column>
    
    <display:column title="Amount Awarded" >
        <fmt:formatNumber value="${row.proffAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.suppAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.purchAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.supplyAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.travAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.benAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.bocesAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.othAmtAppr}" type="currency" minFractionDigits="0"/><br/>
        <fmt:formatNumber value="${row.equipAmtAppr}" type="currency" minFractionDigits="0"/><br/>   
        <fmt:formatNumber value="${row.totAmtAppr}" type="currency" minFractionDigits="0"/><br/>   
    </display:column>
  </display:table>  
  
  
  
  
  </body>
</html>
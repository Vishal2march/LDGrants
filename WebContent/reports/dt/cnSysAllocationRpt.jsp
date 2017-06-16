<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <th>Public Library Construction Grant Program: System Allocation and Amount Spent</th>
    </tr>
  </table>
  <br/><br/>
    
  <display:table name="sessionScope.allAllocations" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
   
    <display:column property="systemName" sortable="true" headerClass="sortable"/>
    <display:column property="year"/>
    <display:column property="initialAlloc" sortable="true" headerClass="sortable" format="{0,number,$0,000}" total="true" title="Initial Allocation"/>
    <display:column property="additionalAlloc" sortable="true" headerClass="sortable" format="{0,number,$0,000}" total="true" title="Additional Allocation"/>
    <display:column property="tallyAmountRecommend" sortable="true" headerClass="sortable" format="{0,number,$0,000}" total="true" title="AmountSpent"/>
    
  </display:table>    
  
     
  </body>
</html>
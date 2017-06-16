<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>Public Library Construction Application - Additional Sources of Funding</title>
  </head>
  <body>
  <br/><br/>
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Public Library Construction Program
          <br/>Additional Sources of Funding
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <table width="95%" class="boxtype" summary="for layout only">
      <tr>
        <th width="33%">Fund Source</th>
        <th width="33%">Description</th>
        <th width="33%">Amount</th>
      </tr> 
      
   <c:forEach var="fundItem" items="${allFunds}">
      <tr>      
        <td width="33%"><c:out value="${fundItem.fundSource}" /></td>      
        <td width="33%"><c:out value="${fundItem.description}" /></td>         
        <td width="33%"><fmt:formatNumber value="${fundItem.amountReceived}" type="currency"/></td>        
      </tr>          
   </c:forEach>  
      
  </table>
  
  </body>
</html>
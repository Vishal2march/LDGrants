<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Local Government Records Management Improvement Fund (LGRMIF) 
          <br/>Project Narratives
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only"> 
    <tr>
      <th bgcolor="Silver">1a. Defining the problem</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr69" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">1b. Defining records involved and previous grants</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr70" property="narrative" filter="false"/></td>
    </tr>
    
    
    <c:if test="${thisGrant.fycode < 15}">
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">1c. Explain why funding is essential</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr71" property="narrative" filter="false"/></td>
    </tr>
    </c:if>
    
    
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">2a. Methodology</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr72" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    
    <c:choose>
    <c:when test="${thisGrant.fycode < 15}">
      <tr>
        <th bgcolor="Silver">2b. Contribution to development of records management program</th>
      </tr>
    </c:when>
    <c:otherwise>
       <tr>
        <th bgcolor="Silver">2b. Anticipated benefits</th>
      </tr>
    </c:otherwise>
    </c:choose>
    <tr>  
      <td align="left"><bean:write name="projNarr73" property="narrative" filter="false"/></td>
    </tr>
    
    
    <c:if test="${thisGrant.fycode < 15}">
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">2c. Contribution of Service to the public</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr88" property="narrative" filter="false"/></td>
    </tr>
    </c:if>
    
    
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">3a. Project outline</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr74" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">3b. Grant requirements</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr75" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fycode < 16}">
        <tr>
          <th bgcolor="Silver">3c. Responsible parties and qualifications</th>
        </tr>
        <tr>  
          <td align="left"><bean:write name="projNarr76" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
    
    <tr>
      <th bgcolor="Silver">4a. Previous records management and current project support </th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr77" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">4b. Future program support</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr78" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Professional Salaries</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr83" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Support Staff Salaries</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr81" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Equipment</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr82" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Minor Remodeling</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr79" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Purchased Services</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr84" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Purchased Services - BOCES</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr80" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Supplies and Materials</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr85" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Travel</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr86" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Employee Benefits</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr87" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Final Project Narrative</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
  </table>
  </font>  
  </body>
</html>
</pd4ml:transform>
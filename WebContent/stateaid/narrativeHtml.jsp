<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>narrativeHtml</title>
  </head>
  <body>
  
  <h4 align="center">State Aid Narrative</h4>
  
  
  <table align="center" width="55%" summary="for layout only">
  <tr>
    <td width="30%"><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
  </tr>  
  <tr>
    <td><b>Institution ID</b></td>
    <td><c:out value="${thisGrant.instID}" /></td>
  </tr>
  <tr>
    <td><b>Institution</b></td>
    <td><c:out value="${thisGrant.instName}" /></td>
  </tr>
  </table>
  
  
  <br/><br/><br/>
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <th bgcolor="Silver">Description of Activities: Describe in detail the activities you plan to accomplish this fiscal year 
    using NEW YORK STATE AID.  Indicate the specific types of activities to be performed, and how they will be accomplished.
      </th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr3" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>      
    <tr>
      <th bgcolor="Silver">Budget Narrative:  Provide a brief narrative, no more than five hundred (500) words, explaining how 
           expenditures in the proposed budget application attain the institution's service goals for the 
          funding year. </th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr93" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>
    
    <tr>
      <th bgcolor="Silver">Final Report Narrative</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false" /></td>
    </tr>
     <tr>
      <td height="30"></td>
    </tr>
  </table>
  
  
  </body>
</html>
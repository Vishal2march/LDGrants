<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>municipalConsent</title>
  </head>
  <body>
  
  
  <h4>Municipal Consent for Site/Building Acquisition Projects (optional)</h4>
    
 <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <th>Municipal Consent for Site/Building Acquisition Projects</th>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>Please provide documentation from the municipality that the proposed use of the site/building that is being acquired is allowable according to local land use.</td></tr>
 <tr>
    <td height="20"></td>
 </tr>
 
 
 
 
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>These documents should be electronically attached to the grant application.</td>
    </tr>
 </c:when>
 <c:otherwise>
    <tr>
        <td>These documents should be electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
        <br/><br/></td>
    </tr>
 </c:otherwise>
 </c:choose>
 
 </table>
  
  
  
  
  </body>
</html>
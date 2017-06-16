<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
   <h4> Building Photographs </h4>
  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <td>Detailed photographs of the proposed construction project site are required to 
    accompany each application.  Pictures must be submitted as part of 
    the online application process. </td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td> Photographs showing completion of project activities are required to be submitted before final 
    fiscal forms can be filed.</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>Photos should be electronically attached to the grant application.</td>
    </tr>
 </c:when>
 <c:otherwise>
      <tr>
        <td>Photos should be electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
        <br/><br/>
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%></td>
      </tr>
  </c:otherwise>
  </c:choose>
  
  </table>
  <br><br>
  
 
  </body>
</html>
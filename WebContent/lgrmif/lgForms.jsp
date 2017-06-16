<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>lgForms</title>
  </head>
  <body>
  
  <h5>LGRMIF Forms</h5>
  
  <table width="90%" summary="for layout only">
  
  <c:choose>
  <c:when test="${lgfrm=='a'}">
    <tr>
      <td>Appendix A and A-1 G  <a href="docs/lgrmif/appendixA.doc" target="_blank">
      Microsoft Word</a></td>
    </tr>  
    <tr>
      <td>Appendix A and A-1 G  <a href="docs/lgrmif/appendixA.htm" target="_blank">
      HTML</a></td>
    </tr>  
   </c:when>
   
   
   <c:when test="${lgfrm=='vq'}">
    <tr>
      <td>Vendor Quote Form <a href="docs/lgrmif/lg_vq.htm" target="_blank">HTML</a></td>
    </tr>  
   </c:when>
   
   
   <c:when test="${lgfrm=='im'}">
    <tr>
      <td>Imaging and Microfilming Project Information Form <a href="docs/lgrmif/lg_im.htm" target="_blank">HTML</a></td>
    </tr>  
   </c:when>
    
    
   <c:when test="${lgfrm=='f'}">
    <tr>
      <td>Final Report for Educational Uses Projects <a href="docs/lgrmif/finalReportEducation.pdf" target="_blank">PDF</a></td>
    </tr>  
    <tr>
      <td>Final Report for Educational Uses Projects <a href="docs/lgrmif/finalReportEducation.htm" target="_blank">HTML</a> </td>
    </tr>  
   </c:when>
    
  </c:choose>
  </table>  
  
  <br/><br/><br/>
  </body>
</html>
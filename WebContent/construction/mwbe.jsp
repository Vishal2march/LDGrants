<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>mwbe</title>
  </head>
  <body>
  
  <h4>M/WBE (Minority and Women-Owned Business Enterprises) Requirement (required)</h4>
  
  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <td>All applicants must comply with M/WBE, for further information and forms see 
    <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a></td>
  </tr>
   <tr>
      <td height="20"></td>
   </tr>
 
 
   <c:choose>
   <c:when test="${param.p!=null && param.p=='rev'}">
      <tr>
          <td>The applicable forms should be printed, completed, signed and electronically attached 
          to your grant application as a pdf document.</td>
      </tr>
   </c:when>
   <c:otherwise>
      <tr>
          <td>The applicable forms should be printed, completed, signed and electronically 
          <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application as a pdf document.
          </td>
      </tr>
   </c:otherwise>
   </c:choose>
   
   <tr>
      <td height="20"></td>
   </tr>
   <tr>
    <td>The original forms with original signatures must be kept on file, and can be requested at any point
    in the future, should the need arise.</td>
  </tr>
   
   
   </table>
 
  
  </body>
</html>
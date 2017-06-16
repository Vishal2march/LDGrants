<%--
 * 5/31/12 this jsp no longer used. per MLT. now using completionDataEntry.jsp
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>Project Completion Form</h4>
  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <td>The Project Completion Form is required to be submitted in order
    for the library to receive the final 10% of their award amount.</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td>Completion Form in <a href="ApcntFunctionsServlet?i=completion" target="_blank">
            HTML</a> format<br/>
        Completion Form in <a href="ApcntFunctionsServlet?i=completionPdf" target="_blank">
            PDF</a> format</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td>
  
    <c:choose>
    <c:when test="${param.p!=null && param.p=='rev'}">
        The form can be printed, signed, scanned and electronically attached to the grant application.
    </c:when>
    <c:otherwise>
        The form can be printed, signed, scanned and electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application. 
        <br/><br/>
        A digital signature is not required. 
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%><br/><br/>
    </c:otherwise>
    </c:choose>
    
    </td>
  </tr>
  <tr>
    <td>The original form with original signatures must be kept on file, and can be 
    requested at any point in the future, should the need arise. </td>
  </tr>  
  </table>
  <br><br>
  
  
  </body>
</html>
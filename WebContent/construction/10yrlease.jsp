<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
  <h4>Certification of 10 year minimum lease agreement and project approval from building owner</h4>
  
  
 <table align="center" width="90%" class="boxtype" summary="for layout only">
 <tr>
    <td>
      If the library building or site is under a lease arrangement or otherwise legally available, the applicant
      must include a letter from the owner of such building or site certifying that the lease agreement or other legal
      arrangement will be in effect for a minimum 10 years from the date of anticipated project completion, that there
      is full awareness of and agreement with the construction project implications, that the owner has the legal authority
      to approve the improvement of the space, and that the building is open to the public.</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td>This requirement does not apply to library buildings that are owned by a 
        school district or BOCES.  Such letter must be submitted as a PDF attachment.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>These documents can be electronically attached to the grant application.</td>
    </tr>
 </c:when>
 <c:otherwise>
     <tr>
        <td>These documents should be electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
        <br/><br/>
        A digital signature is not required.
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%><br/><br/>        
        </td>
     </tr>
 </c:otherwise>
 </c:choose>
 <tr>
    <td>The original documents with original signatures must be kept on file, and can be requested at any point in the
    future, should the need arise.</td>
 </tr>
 </table>
  
  
  </body>
</html>
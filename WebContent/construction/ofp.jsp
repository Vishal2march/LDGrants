<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>

 <h4>SED Office of Facilities Planning Certificate of Project Approval</h4>
 
 <table align="center" width="90%" class="boxtype" summary="for layout only">
 <tr>
    <td>
      The State Education Department is charged by the Secretary of State [19NYCRR441.2(d)] with the “administration and 
      enforcement of the NYS Uniform Fire Prevention and Building Code with respect to buildings, premises and equipment
      in the custody of, or activities related thereto, undertaken by School Districts and Boards of Cooperative Educational
      Services.”</td>
 </tr>
 <tr>
   <td height="20"></td>
 </tr>
 <tr>
   <td>
      If the library building is owned by a School District or BOCES and the total Public Library Construction Grant 
      Program project cost will be $10,000 or more, the applicant must submit plans and specifications to the State Education
      Department’s Office of Facilities Planning for review and approval. The OFP Certificate of Project Approval, including
      the OFP issued Building Permit, must be included as an attachment to the project application form.</td>
 </tr>
 <tr>
   <td height="20"></td>
 </tr>
 
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>These documents can be scanned and electronically attached to the grant application.</td>
    </tr>
 </c:when>
 <c:otherwise>
     <tr>
        <td>These documents should be scanned and electronically
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
        <br/><br/>
        A digital signature is not required.
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%></td>
     </tr>
 </c:otherwise>
 </c:choose>
 <tr>
    <td height="20"></td>
  </tr>
 <tr>
    <td>The original documents with original signatures must be kept on file, and can be requested at any point in the
    future, should the need arise.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>Questions regarding the OFP approval process should be directed to OFP at (518) 474-3906.   Calls will be directed
        to the appropriate Project Manager for the school district in which the library building is located.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>NOTE: The designator “school district public library” does not necessarily indicate ownership of a library building
        by the school district.  It refers only to the boundaries of population served by the library.  OFP approval
        is required only if the building is owned by a school district or BOCES, regardless of the library’s service area.</td>
 </tr>
 </table>  
  
  
  </body>
</html>
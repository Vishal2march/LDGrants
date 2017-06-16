<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SHPO for Construction</title>
  </head>
  <body>
  
 <h4>State Historic Preservation Office (SHPO) Approval Documentation</h4>
 
 
 <table align="center" width="90%" class="boxtype" summary="for layout only">
 <tr>
    <td>
 New York’s State Historic Preservation Office (SHPO), is part of the New York State Office of Parks, 
 Recreation & Historic Preservation.  The mission of SHPO is to help communities identify, evaluate, 
 preserve and revitalize their historic, archaeological and cultural resources.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 
 
 <tr>
  <td>
   If your library building is 50 years old or older and/or the project involves ground 
   disturbance and/or demolition, please read the following documents to determine
   if your project requires a SHPO approval:<br/><br/>

    <ul>    
    <li>
      <a href="http://www.nysl.nysed.gov/libdev/construc/14m/shpo_sed_resolution.pdf" target="_blank">Formal Agreement
      with the State Education Department including Attachment A</a>  [Examples of Activities Exempt from OPRHP Review]
      (PDF format) [Letter of Resolution between the New York State
      Office of Parks, Recreation and Historic Preservation and the New York State Education Department; 
      For the purpose of expediting the review of projects in accordance with Article 14; Section 14.09 of the 
      New York State Parks, Recreation and Historic Preservation Law]
    </li>
    </ul>    
  </td>
 </tr>
 <tr>
  <td height="20"></td>
 </tr>
 <tr>
  <td>
    If appropriate, an approval letter from SHPO must be attached your construction grant application as a signed .PDF.
  </td>
 </tr>
 <tr>
  <td height="20"></td>
 </tr>

	<tr>
     <td>More information regarding SHPO is available on the Division of Library
    Development's website.</td>
    </tr>
    <tr>
	  <td height="20"></td>
	</tr>
 
 <tr>
 	<td>
 	<c:choose>
    <c:when test="${thisGrant.fycode <17}">
    
 	<b>$14 Million State Aid for Library Construction:</b> 
     <a href="http://www.nysl.nysed.gov/libdev/construc/14m/shpo.htm" target="_blank">SHPO</a><br/> 
         
     </c:when>
     <c:otherwise>
     
     <b>$19 Million State Aid for Library Construction:</b>  
     <a href="http://www.nysl.nysed.gov/libdev/construc/19m/shpo.htm" target="_blank">SHPO</a><br/> 
     <br/><br/>
     
     </c:otherwise>
     </c:choose>
     </td>
 </tr>       
 <tr>
  <td height="20"></td>
 </tr>
 
 <tr>
  <td>In the event a building is determined by SHPO to be of historical significance,
  the SHPO approval letter must specify the precise changes to the building that are
  being approved.  Any project components not specifically approved in writing will not
  be considered for funding.  In the event a building is determined by SHPO to lack historical 
  significance, a letter to that effect from SHPO must accompany the application.  Such letter 
  may be used in subsequent years to comply with construction application requirements.  All SHPO 
  letters must be submitted as PDF attachments.    </td>
 </tr>
 <tr>
  <td height="20"></td>
 </tr>
 
 <tr>
  <td><b>Questions regarding the SHPO approval process should be directed to SHPO at 518. 237.8643.</b>
      <br/><br/>
      <a href="http://www.nysparks.state.ny.us/shpo/" target=_blank>Information about New York State’s 
      historic preservation initiatives</a>
  </td>
</tr>
 <tr>
    <td height="20"></td>
 </tr>
 
  
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>If appropriate, an approval letter from SHPO must be attached your construction grant application as a signed .PDF.</td>
    </tr>
 </c:when>
 <c:otherwise>
      <tr>
        <td>The SHPO approval letter should be scanned and electronically 
             <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
             <br/><br/>
        A digital signature is not required.
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%></td>
      </tr>
  </c:otherwise>
  </c:choose>
  
</table>
 
 
 
  </body>
</html>

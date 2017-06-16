<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
    <h4>Certification of Available Funds to Finance Project</h4>
    
 <table align="center" width="90%" class="boxtype" summary="for layout only">
 <tr>
    <td>
    The law allows that State funding through the Public Library Construction Grant 
    Program can be provided for up to 75% of total construction project costs.  
    It also stipulates that the availability of funds to pay for the cost of the 
    project, minus the amount awarded through the Construction Grant Program must 
    be verified as part of the application. Such verification must be in the form 
    of bank or bond certification, an official document(s) signed by a financial 
    authority connected with the applying institution, and/or other such evidentiary 
    documents as necessary.  Such available funds can include public funds (federal, 
    state or local), private funds, or a combination thereof.  All funding certification 
    documents must be submitted as PDF attachments.</td>
 </tr>
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
  <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>NOTE: Many projects are not funded at the maximum 75% due to the unavailability 
    of sufficient funds available through the construction program.  Applicants 
    funded less than 75% of project cost must show certification of available funds 
    to pay for the cost of the project, minus the award amount before a final award 
    can be made.  Such certification documents must be submitted as PDF attachments.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
  <tr>
    <th>Projects Funded, in Whole or in Part, Through the Issuance of Tax-Exempt Bonds, 
    Bond Anticipation Notes, Revenue Anticipation Notes, or Other Similar Form of 
    Obligation</th>
 </tr>
  <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>If the project for which a library has submitted an application is or will be funded in whole or in part 
    through the issuance of tax-exempt bonds, bond anticipation notes, revenue anticipation notes, or some similar 
    form of obligation, the application must include the applicable authorizing resolutions adopted by the library 
    or issuing party authorizing the bond issuance, a detailed breakdown of the expected or actual sources and uses 
    of bond proceeds, equity or other funding sources for the project, a copy of the final official statement relating 
    to the applicable issuance if available and a current cost estimate of the entire project.   All such documents 
    must be submitted as PDF attachments.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 </table>  
   
  
  </body>
</html>
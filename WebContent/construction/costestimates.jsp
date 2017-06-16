<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Cost Estimate</title>
  </head>
  <body>
  
  
  <h4>Project Cost Estimate</h4>
    
 <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <th>Project Cost Estimate</th>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>A project cost estimate that is a reliable assessment of true project cost must accompany the application. Such estimate must be in the form of a quote document from a contractor(s) to assure that the full cost of the project can be reliably assessed. If the construction project is multifaceted and more than one contractor will be involved, e.g., an electrician, plumber and carpenter, a quote from each contractor is required. All quote documents should detail individual costs (as appropriate) and be submitted as PDF attachments. </td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>Note:  <b>QUOTE DOCUMENTS ARE REQUIRED ONLY FOR ELIIGIBLE PROJECT COSTS FOR WHICH FUNDING IS BEING REQUESTED.</b></td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>If the project for which funding is being requested is part of a larger comprehensive project then please provide details on the larger project’s activities in the “Project Narrative” section of your application. 
    <br/><br/><br/>
    It is not required that a library has entered into final agreements with a contractor(s) at the time of application. However, allowing for reasonable increases in the cost of construction from the time the application is submitted, steps should be taken to assure that the cost estimates reflect anticipated true cost to ensure that sufficient funds are available to pay for the cost of the project, minus the award amount. Note: Contingency costs that allow for price increases cannot be entered as separate entries on the budget as it will be assumed that the contractor quote covers such contingencies. 
    <br/><br/><br/>
    If the Cost of the Project for Which Funding is Being Requested listed as (b) on the application form is part of a larger comprehensive project, then list the cost of the larger comprehensive project in (a) Total Project Cost on the application form.  If the Cost of the Project for Which Funding is Being Requested listed as (b) on the application form is not part of a larger project then (a) Total Project Cost and (b) Cost of Project for Which Funding is Being Requested will be equal.
    </td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 
 <tr>
    <td><b>Note: For the purposes of the 2014-17 application, the total project cost for which funding is requested, designated as “(b)” on the application form, cannot include any costs incurred prior to January 1, 2014.</b>
    </td>
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
 
 </table>
  
  
  </body>
</html>
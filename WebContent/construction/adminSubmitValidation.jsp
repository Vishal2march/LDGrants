<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction</title>
  </head>
  <body>
  
    
  <h4>Admin Submission Validation</h4>
  <p>This page allows Admin to view any validation/error messages the applicant might
  receive after clicking the application 'submit' button.</p><br/><hr/>
    
        <p style="margin:5.0pt;">
        <b>Submit Application to PLS</b><br/><br/>
        If you submit the application to your PLS, you will no longer be able to edit it.  If your application 
        is complete and accurate, click the Submit button.<br/>  
          
          The following items need to be mailed to your Public Library System.  
          Forms must have original signatures in <b>blue ink</b>.
          <ul>
            <li>3 original FS-10 Forms</li>
            <li>1 original Payee Information Form</li>
          </ul>     
      </p>
      
          
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following narratives were not completed. If the narrative
        does not apply to your project - please put 'Not applicable' in the corresponding narrative
        box on the Project Narratives page.</p>
        <ul>
        <c:forEach var="row" items="${missingNarr}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul>
      </c:if>
      
      
      <c:if test="${missingBuilding!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The building information
        on the Application Form is incomplete.  All fields on the Application Form must be completed,
        including Building name/address, date constructed, building ownership, project manager, etc.
        </p>
      </c:if>   
      
      <c:if test="${missingProjCategory!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The project category information
        on the Application Form is incomplete.  A project must have at least 1 project category (ie. New Construction, 
        Building Expansion, Site Acquisition, Renovation, Energy Conservation, Accessibility, etc.)
        </p>
      </c:if>         
      
      <c:if test="${missingFunds!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The Additional 
        Sources of Funding section is incomplete. All funding sources that contribute to 
        this construction project must be listed on the Additional Funding section.</p>
      </c:if>   
      
      
      <c:if test="${missingAttach!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The grant application does not contain
        any attachments.  Required attachments to the application include Certificate of
        Available Funds, pre-project photos,
        SEAF, Smart Growth, Assurances, supporting data for purchased services, bids, etc.</p>
      </c:if>     
      
      
      <c:if test="${requiredAttach!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following 
        attachments are required.  Check that all documents attached to the application 
        have the correct 'File Description' drop down item selected.<br/>
        <ul>
        <c:forEach var="row" items="${requiredAttach}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul>
        </p>
      </c:if>     
      
      
      <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total Cost of the project 
        for this grant application is $0.  The Cost of the project should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
       <c:if test="${wrongFund!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The Amount of 
        Public Library Construction Program Funds requested for this project (from part c of the Application Form) 
        cannot be more than 75% of the Cost of Project for Which Funding is Being Requested 
        (from part b of the Application Form).</p>
      </c:if>     
      
  
      <c:choose>
      <c:when test="${missingBudg!=null || missingNarr!=null || wrongFund!=null ||
            missingBuilding!=null || missingFunds!=null || requiredAttach!=null}">
            <b><font color="red">**Warning </font>The application cannot be submitted 
            to your PLS
            until all required portions of the application are completed.</b>    
      </c:when>
      <c:otherwise>
              <br/><br/>
              Do you want to submit the application to your PLS?<br/>
              <input type="button" value="Submit" disabled="disabled" /><br/>
              (Only applicants can submit the application.)
        </c:otherwise>
        </c:choose>
  
    
  </body>
</html>
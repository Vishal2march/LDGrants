<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Confirm Application Submission</h4> 
    
    <c:if test="${param.t=='initial'}">    
      
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
          <hr/>
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
      
      
   <%--   <c:if test="${missingMwbe!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> All applicants requesting $25,000 or more 
        are required to comply with NYSED’s Minority and Women-Owned Business Enterprises (M/WBE) policy. One of the 
        M/WBE compliance options must be selected on the Checklist page.</p>
      </c:if>   --%>
      
      
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
          <form method="POST" action="cnSubmitApp.do?i=submitinitial">    
              <br/><br/>
              Do you want to submit the application to your PLS?<br/>
              <c:choose >
              <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgconstruction!='submit' || appStatus.dateAcceptable=='false'||appStatus.lockapp=='true'}">
                  <input type="button" value="Submit" disabled="disabled" />
              </c:when>
              <c:otherwise >         
                  <input type="SUBMIT" value="Submit"/>
                  <input type="HIDDEN" name="t" value="initial" />  
                  <input type="HIDDEN" name="prog" value="86" />  
                  <input type="HIDDEN" name="p" value="cn" />
                  <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />  
              </c:otherwise>
              </c:choose>
          </form>
        </c:otherwise>
        </c:choose>
    </c:if>
    
    
    
    
     <c:if test="${param.t=='final'}"> 
     
     <p>
          <b>Submit Final Report</b><br/><br/>        
          If your application is complete and accurate, click the Submit button. 
          The Division of Library Development will contact you once your Final 
          Budget Expenses have been approved.  
          <br/><br/>After you receive approval, 3 original FS-10-F Forms should
          be mailed to the Division of 
          Library Development. All forms must have 
          original signatures in <b>blue ink</b>.
          <br/><br/>
          
       <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The project budget expenses should be entered on the
        Final Budget Expenses pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      
      <c:if test="${requiredFinalAttach!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following 
        attachments are required.  Check that all documents attached to the application 
        have the correct 'File Description' drop down item selected.<br/>
        <ul>
        <c:forEach var="row" items="${requiredFinalAttach}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul>
        </p>
      </c:if>     
      
      <c:choose>
      <c:when test="${missingBudg!=null || requiredFinalAttach!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the final application are completed.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit final--%>
            <form method="POST" action="cnSubmitApp.do?i=submitfinal">
              Do you want to submit the final report?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="86" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
            </form>
      </c:otherwise>
      </c:choose>
    </p>
     
    </c:if>
    
    
    
    
    
    <c:if test="${param.t=='mwbe'}"> 
     
     <p>
      <b>Submit M/WBE Materials</b><br/><br/>        
                   
      <c:if test="${missingMwbe!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> All applicants requesting $25,000 or more 
        are required to comply with NYSED’s Minority and Women-Owned Business Enterprises (M/WBE) policy. One of the 
        M/WBE compliance options must be selected on the Checklist page.</p>
      </c:if> 
     
      
      <c:choose>
      <c:when test="${missingMwbe!=null}">
            <%--cannot submit mwbe; items missing--%>
            <b><font color="red">**Warning </font>Your M/WBE Materials cannot be submitted 
            until all required portions are completed.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit mwbe--%>
            <form method="POST" action="cnSubmitApp.do?i=submitmwbe">
              Do you want to submit the M/WBE materials?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="86" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
            </form>
      </c:otherwise>
      </c:choose>
    </p>
     
    </c:if>
          
  </body>
</html>

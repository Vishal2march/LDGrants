<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Help Submitting application</a>
  <br/>
  
  <h4>Confirm Application Submission</h4> 
  
  <c:choose >
  <c:when test="${lduser.prglg=='submit'}">
  
    <c:if test="${param.t=='initial'}">          
        <p style="margin:5.0pt;">
        <b>Submit Application</b><br/><br/>If you submit the application you will no longer be 
        able to edit it.  If your application is complete and accurate, click the Submit button.<br/>  
          
          The following items need to be mailed to the State Archives Grants Administration Unit.  
          Forms must have original signatures in <b>blue ink</b>
          <ul>
            <li>3 copies of the FS-10 form</li>
            <li>1 copy of the Institutional Authorization Form</li>
            <li>1 copy of the Payee Information Form</li>
            <li>1 copy of the Standard Data Capture Form</li>
          </ul>                    
          <br/><br/>
          
          <c:if test="${missingNarr!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following narratives were not completed. If the narrative
            does not apply to your project - please put 'Not applicable' in the corresponding narrative
            box on the Project Narrative page.</p>
            <ul>
            <c:forEach var="row" items="${missingNarr}">
              <li><c:out value="${row}" /></li>
            </c:forEach>
            </ul><br/>
          </c:if>     
          
          <c:if test="${missingAttach!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The grant application does not contain
            any attachments.  The Institutional Authorization Form must be attached to the application, 
            as well as the Cooperative Agreement Form if applicable.</p><br/>
          </c:if>     
          
          <c:if test="${missingBudg!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
            for this grant application is $0.  The amount requested should be entered on the Project Budget pages,
            under the appropriate budget category.</p><br/>
          </c:if>     
          
          <c:if test="${incorrectBudg!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
            for this grant application is <fmt:formatNumber value="${amtReq}" type="currency" minFractionDigits="0"/>  The amount requested is over the amount allowed for this
             type of LGRMIF application.<br/>
             Individual applications may request up to $75,00.<br/>
             Shared Services applications may request up to $150,000.<br/>
             NYC-Administrative applications may request up to $200,000.<br/>
             Demonstration - Planning applications may request up to $100,000.<br/>
             Demonstration - Implementation applications may request up to $500,000.</p><br/>
          </c:if>     
          
          <c:if test="${missingCover!=null}">
            <p style="margin:5.0pt;"><b><font color="red">**</font>Warning:</b>Required fields on
            the Application Sheet have not been completed.  All required fields on the Application 
            Sheet must be completed in order to submit the application.</p><br/>
          </c:if>
          
           <c:if test="${missingGisCounty!=null}">
            <p style="margin:5.0pt;"><b><font color="red">**</font>Warning:</b>All projects 
            to implement Geographic Information Systems (GIS) must include the respective 
            county as an applicant (lead or partner) in the request.
            </p><br/>
          </c:if>
          
          <br/>Do you want to submit the application?<br/>
          
          <c:choose>
          <c:when test="${missingCover!=null || missingBudg!=null || missingNarr!=null || incorrectBudg!=null || missingGisCounty!=null}">
           
            <b><font color="red">**Warning </font>Your application cannot be submitted 
            until all required portions of the application are completed.  The Application 
            Sheet, Project Narratives and Budget must be completed before the application
            can be submitted.</b>          
          </c:when>
          <c:otherwise>
               <%--allow submit initial--%>
              <form method="POST" action="lgSubmitApp.do?i=submitinitial">         
                  <input type="SUBMIT" value="Submit"/>
                  <input type="HIDDEN" name="prog" value="80" />
                  <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
               </form>
           
          </c:otherwise>
          </c:choose>        
    </c:if>
    
    
    <c:if test="${param.t=='final'}">
      
        <p style="margin:5.0pt;">
        <b>Submit Final Report</b><br/><br/>        
        If you submit the final report you will no longer be able to edit it.  If your application 
        is complete and accurate, click the Submit button.  Remember to mail 3 copies of the FS-10-F 
        form to the State Archives Grants Administration Unit.  Forms must have original signatures 
        in <b>blue ink</b>
        <br/><br/>
                 
       <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The expenses submitted should be entered on the Final Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>   
      
      <c:if test="${overBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application exceeds the total amount approved.  The expenses submitted must be less than,
        or equal to the total amount approved for this grant application.</p>
      </c:if>   
                    
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The Final Project 
        Narrative must be completed.</p>        
      </c:if>     
      
      <c:if test="${missingStat!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The Final Statistics
        Report must be completed.</p>        
      </c:if>     
                 
                  
      <br/><br/>Do you want to submit the final report?<br/>
      
      <c:choose>
      <c:when test="${missingBudg!=null || missingNarr!=null || overBudget!=null || missingStat!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the application are completed.  The Final Project Budget,
            Final Project Narrative, and Final Statistics Report must be completed before the application
            can be submitted.</b>    
      </c:when>
      <c:otherwise>
          <%--allow submit final--%>
          <form method="POST" action="lgSubmitApp.do?i=submitfinal">
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="80" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />          
          </form>
      </c:otherwise>
      </c:choose>
      
      </p>  
    </c:if>
    
         
    <c:if test="${param.t=='amend'}">
    <form method="POST" action="lgSubmitApp.do?i=submitamend">
      <p>
          <b>Submit FS-10-A Budget Amendment Summary</b><br/>
          If you submit the FS-10-A Budget Amendment Summary you will 
          no longer be able to edit them. <br/>
          If required, remember to mail 3 copies of the FS-10-A Form (signed in blue ink) to the State Archives
          Grants Administration Unit.  (See the Checklist for Formal Amendment Requirements)<br/><br/>
          Do you want to submit the FS-10-A Budget Amendment Summary?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="prog" value="80" />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
    </form>    
    </c:if>
    

    </c:when>
    <c:otherwise >
    
    <p>
      <b>Note:</b><br/>
      You do not have access to Submit the application.<br/>Only users
      with sufficient account priviledges may Submit the
      application. If you believe you should be able to Submit the application,
      contact State Archives staff to update your account.
    </p>    
    
    </c:otherwise>
    </c:choose>
      
  </body>
</html>

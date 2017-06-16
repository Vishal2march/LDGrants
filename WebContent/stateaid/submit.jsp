<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>submit</title>
  </head>
  <body>
  
  
  <h4>Confirm Application Submission</h4>
      
  
  <c:choose >
  <c:when test="${lduser.prgNycStateaid=='submit'}">
  
    <c:if test="${param.todo=='initial'}">    
        <p>
          <b>Submit Application</b><br/><br/>
          If you submit the application you will no longer be able to edit it.  If your application is
          complete and accurate, click the Submit button.  Remember to mail 3 original FS-10 Forms
          to the Division of Library Development.  All forms must 
          have original signatures in <b>blue ink</b>.
          <br/><br/>
          
          <c:if test="${missingNarr!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following narratives were not completed. If the narrative
            does not apply to your project - please put 'Not applicable' in the corresponding narrative
            box on the Project Narrative page.</p>
            <ul>
            <c:forEach var="row" items="${missingNarr}">
              <li><c:out value="${row}" /></li>
            </c:forEach>
            </ul>
          </c:if>
                    
          <c:if test="${missingAssurance!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The Assurance Form has not been completed.</p>
          </c:if>    
          
                    
          <c:if test="${missingBudg!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
            for this grant application is $0.  The amount requested should be entered on the Project Budget pages,
            under the appropriate budget category.</p>
          </c:if>    
          
          
          <c:if test="${overBudget!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
            for this grant application is greater than the appropriation.  Please check the amounts entered on the Project 
            Budget pages, for "amount requested" and "Appropriation".</p>
          </c:if>    
                    
          
          <c:choose>
          <c:when test="${missingBudg!=null || missingNarr!=null  ||  overBudget!=null   ||  missingAssurance!=null}">
           
            <b><font color="red">**Warning </font>Your application cannot be submitted 
            until all edit checks/warnings listed above have been resolved.</b>          
          </c:when>
          <c:otherwise>
               <%--allow submit initial--%>
              <form method="POST" action="staidSubmitApp.do?i=submitinitial">
              Do you want to submit the application?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="20" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
              </form>
          </c:otherwise>
          </c:choose>
          
          
          <br/><br/><br/>
          Send your completed FS-10s (3 copies) to:<br/>
          DIVISION OF LIBRARY DEVELOPMENT<br/>
          NEW YORK STATE LIBRARY<br/>
          10B41 CULTURAL EDUCATION CENTER<br/>
          222 MADISON AVENUE<br/>
          ALBANY, NY  12230<br/>

       </p>           
    </c:if>
    
    
    <c:if test="${param.todo=='final'}">
        <p>
          <b>Submit Final Report</b><br/><br/>        
          If you submit the final report you will no longer be able to edit it.  If your 
          application is complete and accurate, click the Submit button.  Remember to mail 3 
          original FS-10-F forms to the Division of Library Development. All forms must have 
          original signatures in <b>blue ink</b>
          <br/><br/>
          
       <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The expenses submitted should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      <c:if test="${overBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application exceeds the total amount approved.  The expenses submitted must be less than,
        or equal to the total amount approved for this grant application.</p>
      </c:if>   
                    
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The final report 
        narrative must be completed.</p>        
      </c:if>     
                 
    <br/><br/>
          
     <c:choose>
      <c:when test="${missingBudg!=null || missingNarr!=null || overBudget!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the application are completed.  The Final Project Budget,
            and Final Report Narrative must be completed before the application
            can be submitted.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit final--%>
            <form method="POST" action="staidSubmitApp.do?i=submitfinal">
              Do you want to submit the final report?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="20" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
            </form>
      </c:otherwise>
      </c:choose>
    </p>
    </c:if>
    
    
       
    
  </c:when>
  <c:otherwise >
  
    <p>
      <b>Note:</b><br/>
      You do not have access to Submit the application.<br/>Only users
      with sufficient account priviledges may Submit the
      application. If you believe you should be able to Submit the application,
      contact Division of Library Development staff to update your account.
    </p>
    
  </c:otherwise>
  </c:choose>  
  
  
  </body>
</html>
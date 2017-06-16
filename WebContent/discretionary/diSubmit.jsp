<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
   <h4>Confirm Application Submission</h4>
      
  
  <c:choose >
  <c:when test="${lduser.prgdi=='submit'}">
  
    <c:if test="${param.todo=='initial'}">    
        <p>
          <b>Submit Application</b><br/><br/>
          If you submit the application you will no longer be able to edit it.  If your application is
          complete and accurate, click the Submit button.  Remember to mail 3 original FS-10 Forms, 1 Payee Information 
          Form, and M/WBE Documents (if required) to the Division of Library Development.  All forms must 
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
                    
          <c:if test="${missingAttach!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The grant application does not contain
            any attachments.  The Institutional Authorization Form must be attached to the application, 
            as well as the Cooperative Agreement Form and Microform Guidelines Form if applicable.</p>
          </c:if>     
          
          <c:if test="${missingBudg!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
            for this grant application is $0.  The amount requested should be entered on the Project Budget pages,
            under the appropriate budget category.</p>
          </c:if>     
          
          <c:if test="${warningMwbe!=null}">
            <p style="margin:5.0pt;"><b><font color="Red">**</font>Reminder:</b> 
            All applicants applying for grants in excess of $25,000 are required to comply with NYSED’s Minority and 
            Women-Owned Business Enterprises (M/WBE) policy. All M/WBE documents should be mailed to the Division of 
            Library Development.</p>
          </c:if>
          
          <c:choose>
          <c:when test="${missingBudg!=null || missingNarr!=null}">
           
            <b><font color="red">**Warning </font>Your application cannot be submitted 
            until all required portions of the application are completed.  The Cover 
            Sheet, Project Narratives and Budget must be completed before the application
            can be submitted.</b>          
          </c:when>
          <c:otherwise>
               <%--allow submit initial--%>
              <form method="POST" action="diSubmitApp.do?i=submitinitial">
              Do you want to submit the application?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="5" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
              </form>
          </c:otherwise>
          </c:choose>
          
          
          <br/><br/><br/>
          Send your completed FS-10s (3 copies) and M/WBE documentation to:<br/>
          CONSERVATION/PRESERVATION PROGRAM<br/>
          ATTN:  KERRY LYNCH<BR/>
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
            <form method="POST" action="diSubmitApp.do?i=submitfinal">
              Do you want to submit the final report?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="5" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
            </form>
      </c:otherwise>
      </c:choose>
    </p>
    </c:if>
    
    
    <c:if test="${param.todo=='amend'}">
    <form method="POST" action="diSubmitApp.do?i=submitamend">
      <p>
          <b>Submit Budget Amendment Records</b><br/>
          If you submit the Budget Amendment Records you will 
          no longer be able to edit them. <br/>
          Remember to mail 3 original FS-10-A Forms (signed in blue ink) to the Division of Library Development.<br/><br/>
          Do you want to submit the FS-10-A Budget Amendment Records?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="prog" value="5" />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
    </form>    
    </c:if>
    
    <p>
     Please use the following link to print or save your completed application in HTML form.
     <br/>
     <a href="PrintAppServlet?i=app&a=false" target="_blank">C/P Discretionary Application - HTML</a>    
    </p>
  
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

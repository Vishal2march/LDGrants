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
        <b>Submit Application</b><br/><br/>
        If you submit the application you will no longer be able to edit it.  If your application 
        is complete and accurate, click the Submit button.<br/>  
          
      The following items need to be mailed to the Division of Library Development.  
      Forms must have original signatures in <b>blue ink</b>
      <ul>
        <li>3 copies of the FS-10 form for each Year of the project</li>
        <li>3 copies of the Board Certification Form</li>
        <li>3 copies of the Cover Page Authorization Form</li>
      </ul>              
      <br/><br/></p>
          
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
      
     <%-- 10/22/12 no longer need attachments per LA.
     <c:if test="${missingAttach!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The grant application does not contain
        any attachments.  Possible attachments to the application include letters of support, resumes, supporting data for purchased services, bids, 
        if applicable.</p>
      </c:if>     --%>
      
      <%--10/23/12 participants required for adult lit only--%>
      <c:if test="${missingParticipant!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> Adult Literacy 
        applications are required to have participants.  To add participants: click the 
        Participating Institutions link on the Checklist page.</p>
      </c:if>
      
      <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total amount requested 
        for this grant application is $0.  The amount requested should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      <c:if test="${incompleteBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The amount requested for
        each year of the Literacy grant must be greater than $0, and less than 
        the Library System's allocation.</p>
      </c:if>
          
      <c:choose>
      <c:when test="${missingParticipant!=null || missingNarr!=null || missingBudg!=null || incompleteBudget!=null}">
            <b><font color="red">**Warning </font>Your application cannot be submitted 
            until all required portions of the application are completed.</b>    
      </c:when>
      <c:otherwise>
          <form method="POST" action="litSubmit.do?i=submitliteracy">    
              <br/><br/>
              Optional short comment (Ex. "FS-10 form will be mailed next week")<br/>
              <textarea name="acomment" rows="6" cols="100"></textarea>
              
              <br/><br/>
              Do you want to submit the application?<br/>
              <input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="t" value="initial" />  
              <input type="HIDDEN" name="p" value='<c:out value="${prog}" />' />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />  
          </form>
        </c:otherwise>
        </c:choose>
    </c:if>
    
                         <%--  SUBMIT FINAL YEAR 1--%> 
     
    <c:if test="${param.t=='final'}">
        <p style="margin:5.0pt;">
          <b>Submit Final Report</b><br/><br/>        
          If you submit the final report you will no longer be able to edit it.  If your 
          application is complete and accurate, click the Submit button.  Remember to mail 3 
          sets of the FS-10-F form to the Division of Library Development. Forms must have 
          original signatures in <b>blue ink</b><br/><br/>
                    
      <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The expenses submitted should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      <c:if test="${overBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application exceeds the total amount approved.  The expenses submitted must be less than,
        or equal to the total amount approved for this grant application.</p><br/>
      </c:if>         
      
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following final report narratives were not completed. If the narrative
        does not apply to your project - please put 'Not applicable' in the corresponding final report narrative
        box on the Final Report Narrative page.</p>
        <ul>
        <c:forEach var="row" items="${missingNarr}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul><br/><br/>
      </c:if>     
      
      <c:choose>
      <c:when test="${missingBudg!=null || overBudget!=null || missingNarr!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the application are completed.  The Final Project Budget,
            and Final Report Narrative must be completed before the application
            can be submitted.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit final--%>                          
          <form method="POST" action="litSubmit.do?i=submitliteracy">
          Optional short comment (Ex. "FS-10-F form will be mailed next week")<br/>
          <textarea name="acomment" rows="6" cols="100"></textarea>
          
          <br/><br/>
          Do you want to submit the final report?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="t" value="final" /> 
          <input type="HIDDEN" name="p" value='<c:out value="${prog}" />' />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </form>
      </c:otherwise>
      </c:choose>
    </p>      
    </c:if>
    
                          <%-- SUBMIT FINAL YEAR 2--%> 
    
    <c:if test="${param.t=='final2'}">
        <p style="margin:5.0pt;">
          <b>Submit Final Report for Year 2</b><br/><br/>        
          If you submit the final report you will no longer be able to edit it.  If your 
          application is complete and accurate, click the Submit button.  Remember to mail 3 
          sets of the FS-10-F form to the Division of Library Development. Forms must have 
          original signatures in <b>blue ink</b><br/><br/>
                    
      <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The expenses submitted should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      <c:if test="${overBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application exceeds the total amount approved.  The expenses submitted must be less than,
        or equal to the total amount approved for this grant application.<br/></p>
      </c:if>   
            
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following final report narratives were not completed. If the narrative
        does not apply to your project - please put 'Not applicable' in the corresponding final report narrative
        box on the Final Report Narrative page.</p>
        <ul>
        <c:forEach var="row" items="${missingNarr}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul><br/><br/>
      </c:if>
            
      
      <c:choose>
      <c:when test="${missingBudg!=null || overBudget!=null || missingNarr!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the application are completed.  The Final Project Budget,
            and Final Report Narrative must be completed before the application
            can be submitted.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit final--%>                          
          <form method="POST" action="litSubmit.do?i=submitliteracy">
          Optional short comment (Ex. "FS-10-F form will be mailed next week")<br/>
          <textarea name="acomment" rows="6" cols="100"></textarea>
          
          <br/><br/>
          Do you want to submit the final report?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="t" value="finalyr2" /> 
          <input type="HIDDEN" name="p" value='<c:out value="${prog}" />' />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </form>
      </c:otherwise>
      </c:choose>
    </p>      
    </c:if>
    
                        <%-- SUBMIT FINAL YEAR 3--%> 
    
    <c:if test="${param.t=='final3'}">
          <p style="margin:5.0pt;">
          <b>Submit Final Report for Year 3</b><br/><br/>        
          If you submit the final report you will no longer be able to edit it.  If your 
          application is complete and accurate, click the Submit button.  Remember to mail 3 
          sets of the FS-10-F form to the Division of Library Development. Forms must have 
          original signatures in <b>blue ink</b><br/><br/>
                    
      <c:if test="${missingBudg!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application is $0.  The expenses submitted should be entered on the Project Budget pages,
        under the appropriate budget category.</p>
      </c:if>     
      
      <c:if test="${overBudget!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The total expenses submitted 
        for this grant application exceeds the total amount approved.  The expenses submitted must be less than,
        or equal to the total amount approved for this grant application.<br/></p>
      </c:if>   
            
      <c:if test="${missingNarr!=null}">
        <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following final report narratives were not completed. If the narrative
        does not apply to your project - please put 'Not applicable' in the corresponding final report narrative
        box on the Final Report Narrative page.</p>
        <ul>
        <c:forEach var="row" items="${missingNarr}">
          <li><c:out value="${row}" /></li>
        </c:forEach>
        </ul><br/><br/>
      </c:if>
                  
      <c:choose>
      <c:when test="${missingBudg!=null || overBudget!=null || missingNarr!=null}">
            <%--cannot submit final; items missing--%>
            <b><font color="red">**Warning </font>Your final report cannot be submitted 
            until all required portions of the application are completed.  The Final Project Budget,
            and Final Report Narrative must be completed before the application
            can be submitted.</b>    
      </c:when>
      <c:otherwise>
            <%--allow submit final--%>                          
          <form method="POST" action="litSubmit.do?i=submitliteracy">
          Optional short comment (Ex. "FS-10-F form will be mailed next week")<br/>
          <textarea name="acomment" rows="6" cols="100"></textarea>
          
          <br/><br/>
          Do you want to submit the final report?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="t" value="finalyr3" /> 
          <input type="HIDDEN" name="p" value='<c:out value="${prog}" />' />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </form>
      </c:otherwise>
      </c:choose>
    </p>      
    </c:if>
    
                        <%-- SUBMIT INTERIM--%> 
    
    <c:if test="${param.t=='interim'}" >
      <form method="POST" action="litSubmit.do?i=submitliteracy">
        <p style="margin:5.0pt;"><b>Submit Interim Report</b><br/><br/>
           If you submit the interim report you will no longer be able to edit it.<br/>
           Do you want to submit the interim report?<br/>
           
           <br/><br/>
          Optional short comment (Ex. "Authorization form will be mailed next week")<br/>
          <textarea name="acomment" rows="6" cols="100"></textarea><br/>
          
           <input type="SUBMIT" value="Submit"/>
           <input type="HIDDEN" name="t" value="interim" />
           <input type="HIDDEN" name="p" value='<c:out value="${param.prog}" />' />
           <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
        </p>
      </form>    
    </c:if>
    
                         <%-- SUBMIT AMENDMENT--%> 
    
    <c:if test="${param.t=='amend'}">
    <form method="POST" action="litSubmit.do?i=submitliteracy">
      <p>
          <b>Submit Budget Amendment Records</b><br/>
          If you submit the Budget Amendment Records you will 
          no longer be able to edit them. <br/>
          Remember to mail 3 copies of the FS-10-A Form (signed in blue ink) to the Division of Library Development.<br/><br/>
          Do you want to submit the FS-10-A Budget Amendment Records?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="t" value="amend" />
          <input type="HIDDEN" name="p" value='<c:out value="${param.prog}" />' />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
    </form>    
    </c:if>
  
    
  </body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminSubmitValidate</title>
  </head>
  <body>
  
  <h4>Admin Submission Validation</h4>
  <p>This page allows Admin to view any validation/error messages the applicant might
  receive after clicking the application 'submit' button.</p><br/><hr/>
  
  
  
  
  
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
          <form method="POST" action="liAdminSubmit.do?i=submitliteracy">    
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
  
  </body>
</html>
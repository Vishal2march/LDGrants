<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
 
 
  <table width="90%" summary="for layout only">
    <tr>
      <th colspan="2">Reviewer Rating Form Submission</th>
    </tr>   
    <tr>
      <td><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" /></td>
    </tr>
    <tr>
      <td><b>Sponsoring Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Title</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2">Once the Rating Form and comments are submitted, you will
          NOT be able to update any scores or comments for this grant proposal. Click the Submit button to
          submit this Rating Form or Cancel to return to the Reviewer Assignments.</td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    
    <c:choose >
    <c:when test="${assignBean.ratingcomp=='true'}">
    
    <tr>
      <th colspan="2">*This grant proposal Rating Form and Comment has already been submitted.*</th>
    </tr>      
      
    </c:when>
    <c:when test="${assignBean.conflictinterest=='true'}">
    
    <tr>
      <th colspan="2">*This grant proposal has been identified
      as a conflict of interest application.  A Rating Form cannot be submitted.*</th>
    </tr>  
    
    </c:when>
    <c:when test="${revDue.dateAcceptable=='false'}">
    
        <tr>
          <th colspan="2">*The due date for Rating Form and Comments has expired.*</th>
        </tr>  
    
    </c:when>
    
    <c:when test="${assignBean.lgMinimumScore=='true'}">
    
        <tr>
          <th colspan="2">*The minimum score for funding is 60.  If the Total At-Home Score 
          is less than 60, you must select 'N' (No Fund) for Recommendation.  The Rating Form cannot
            be submitted unless the Score/Recommendation issue is resolved.*</th>
        </tr>  
    
    </c:when>
    <c:otherwise >
    
    <form method="POST" action="submitRatingForm.do?i=dosubmit">
      <tr>
        <td colspan="2">Do you want to submit the Rating Form and Comments for this Grant Proposal?</td>
      </tr>
      <tr>
        <input type="HIDDEN" name="assignid" value='<c:out value="${assignBean.assignid}" />' />
        <input type="HIDDEN" name="module" value='<c:out value="${assignBean.module}" />' />
        <td colspan="2"><input type="SUBMIT" value="Submit" /> &nbsp;
        <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" /></td>
      </tr>
    </form>
    
    </c:otherwise>
    </c:choose>  

  </table>
  
  </body>
</html>
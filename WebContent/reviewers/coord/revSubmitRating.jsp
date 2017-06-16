<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revSubmitRating.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       8/6/07     Created
 *
 * Description
 * This page allows the reviewer to submit the selected grant id rating
 * and comments they entered. They can only submit 1x, and cannot edit thier
 * scores/comments afterwards.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <c:choose >
  <c:when test="${param.p=='lg'}">
    <c:set var="fcpre" value="05"/>
  </c:when>
  <c:otherwise>
    <c:set var="fcpre" value="03"/>
  </c:otherwise>
  </c:choose>
  <br/><br/>
  <table align="center" width="90%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2">Reviewer Rating Form Submission</th>
    </tr>   
    <tr>
      <td><b>Project Number</b></td>
      <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
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

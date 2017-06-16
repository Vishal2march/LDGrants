<%--
 * @author  shusak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  addDocument.jsp
 * Creation/Modification History  :
 *     SHusak   Created
 *
 * Description
 * The decision summary page available to the applicant. It displays the final decision
 * notes, only after the 'showScoresComments' flag is set by an admin.
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Decision Summary</title>
  </head>
  <body>
  
   
  <b>Local Government Records Management Improvement Fund (LGRMIF)<br/>  
  Summary of Reviewers’ Recommendations</b><br/><br/>
  
  Institution: <c:out value="${thisGrant.instName}"/><br/><br/>
  Project Number: 05<fmt:formatNumber value="${thisGrant.fccode}" />
                -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /><br/><br/>
  
  <c:choose >
  <c:when test="${appStatus.showscorecomm=='true'}" >
    
  Decision: <br/>
  <c:out value="${panelBean.decisionnotes}"/>
  
  
  </c:when>
  <c:otherwise>
  
  <p align="center">
    <br/>
    <font color="Red">The decision notes are not available at this time</font></p>
    
  </c:otherwise>
  </c:choose>
  
  
  </body>
</html>
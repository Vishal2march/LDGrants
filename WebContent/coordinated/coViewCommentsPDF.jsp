<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coViewCommentsPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       11/2/07     Created
 *
 * Description
 * This page allows the apcnt to view any comments that were made by reviewers during
 * the approval process. Does not show reviewer name or info b/c reviews are anonymous.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="700" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Comments and Scores</title>
  </head>
  <body>
  
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation Preservation Program - Coordinated Grants
          <br/>Reviewer Comments and Scores
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Contract Number</td>
      <td><c:out value="${thisGrant.contractNum}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <c:choose >
  <c:when test="${grStatus.showscorecomm=='true' }" >
    
  <font size="1">
  <table width="80%" align="center" border="1" summary="for layout only">
    <tr>
      <th colspan="2">Average Scores by Rating Category</th>
    </tr>    
    <tr>  
      <td>Research Value of Materials to be Preserved</td>
      <td><fmt:formatNumber var="rsum" value="${totScores.appropriate + totScores.significance}" />
          <fmt:formatNumber value="${rsum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Cooperative Planning and Operation</td>
      <td><fmt:formatNumber var="csum" value="${totScores.involvement + totScores.coordination}" />
          <fmt:formatNumber value="${csum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Bibliographic Accessibility of Collections</td>
      <td><fmt:formatNumber var="bsum" value="${totScores.bibliographic + totScores.onlinedb}" />
          <fmt:formatNumber value="${bsum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Plan of Work</td>
      <td><fmt:formatNumber var="psum" value="${totScores.timetable + totScores.soundness + totScores.equipment + totScores.personnel + totScores.storage}" />
          <fmt:formatNumber value="${psum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Budget</td>
      <td><fmt:formatNumber var="bdsum" value="${totScores.consistentdesc + totScores.consistentexp + totScores.costeffective}" />
          <fmt:formatNumber value="${bdsum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Overall Rating</td>
      <td><fmt:formatNumber var="osum" value="${totScores.overallscore}" />
          <fmt:formatNumber value="${osum/totScores.ratingtype}" /></td>
    </tr>
    <tr>
      <td>Average Rating of Proposal</td>
      <td><b><fmt:formatNumber var="ssum" value="${totScores.sumscore}" />
          <fmt:formatNumber value="${ssum/totScores.ratingtype}" /></b></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <font size="1">
  <table width="80%" align="center" border="1" summary="for layout only">
    <tr>
      <th>Comments made by Reviewers during approval process</th>
    </tr>    
    <c:set value="1" var="countrow" />
    <c:forEach var="row" items="${allComments}">
    <tr>  
      <td><b>Reviewer <c:out value="${countrow}" /> Comment</b></td>
    </tr>
    <tr>
      <td><c:out value="${row.comment}" /></td>
    </tr>
    <c:set var="countrow" value="${countrow +1}" />
    </c:forEach>  
  </table>
  </font>
  
  </c:when>
  <c:otherwise >
  
  <font size="1">
  <p align="center">
    Comments and Scores will be available after the C/P Program Officer has reviewed the application.
    <br/><br/>
    <font color="Red">Comments and Scores are unavailable at this time</font>
  </p>
  </font>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

</pd4ml:transform>

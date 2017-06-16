<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coViewComments.jsp
 * Creation/Modification History  :
 *
 *     SHusak       8/22/07     Created
 *
 * Description
 * This page allows the apcnt to view any comments that were made by reviewers during
 * the approval process. Does not show reviewer name or info b/c reviews are anonymous.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Reviewer Comments and Scores</h4>
  
  
  <c:choose >
  <c:when test="${appStatus.showscorecomm=='true'}" >
  
  <a href="ApcntFunctionsServlet?i=commentsPdf" target="_blank">Reviewer Comments and Scores - PDF</a><br/>
  <br/><br/>
  
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
      <td>Education Pre-planning</td>
      <td><fmt:formatNumber var="edsum" value="${totScores.groupneed + totScores.goal + totScores.publicity + totScores.sharing}" />
          <fmt:formatNumber value="${edsum/totScores.ratingtype}" /></td>
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
  <br/><br/>
  
  
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
    <tr>
      <td height="20"></td>
    </tr>
    <c:set var="countrow" value="${countrow +1}" />
    </c:forEach>  
  </table>
  
  
  </c:when>
  <c:otherwise >
  
  <p align="center">
    Comments and Scores will be available after the C/P Program Officer has reviewed the application.
    <br/><br/>
    <font color="Red">Comments and Scores are unavailable at this time</font>
  </p>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="400" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation Preservation Program - Discretionary Grants
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
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <c:choose >
  <c:when test="${grStatus.showscorecomm=='true'}" >
  
    <font size="1">
    <table width="95%" align="center" border="1" summary="for layout only">
      <tr>
        <th colspan="2">Average Scores by Rating Category</th>
      </tr>    
      <tr>  
        <td>Institutional Commitment to Conservation/Preservation Work</td>
        <td><fmt:formatNumber value="${totScores.instcpStr}" /></td>
      </tr>
      <tr>
        <td>Accessibility of Collections to the Public</td>
        <td><fmt:formatNumber value="${totScores.availabilityStr}" /></td>
      </tr>
      <tr>
        <td>Research Value of Materials to be Preserved</td>
        <td><fmt:formatNumber value="${totScores.significanceStr}" /></td>
      </tr>
      <tr>
        <td>Plan of Work</td>
        <td><fmt:formatNumber value="${totScores.timetableStr}" /></td>
      </tr>
      <tr>
      <td>Education Pre-planning</td>
      <td><fmt:formatNumber value="${totScores.educationStr}" /></td>
    </tr>
      <tr>
        <td>Institutional Contribution to the Project</td>
        <td><fmt:formatNumber value="${totScores.financialsupportStr}" /></td>
      </tr>
      <tr>
        <td>Budget</td>
        <td><fmt:formatNumber value="${totScores.consistentexpStr}" /></td>
      </tr>
      <tr>
        <td>Overall Rating</td>
        <td><fmt:formatNumber value="${totScores.overallscoreStr}" /></td>
      </tr>
      <tr>
        <td>Average Rating of Proposal</td>
        <td><b><fmt:formatNumber value="${totScores.scoreStr}" /></b></td>
      </tr>
    </table>
    </font>
    <br/><br/>
  
  
  
    <font size="1">
    <table width="95%" align="center" border="1" summary="for layout only">
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
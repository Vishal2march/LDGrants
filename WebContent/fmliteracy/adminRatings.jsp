<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Reviewer Scores/Comments</title>
  </head>
  <body>
    
  <br/>
  <table align="center" width="80%" class="borderbox" summary="for layout only">
    <tr>
      <th colspan="3">Reviewer Ratings and Comments for Grant Proposal</th>
    </tr>
    <tr>             
      <th>Project Num</th>
      <th>Institution</th>
      <th>Title</th>  
    </tr>
    <tr>      
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      <td ><c:out value="${thisGrant.instName}" /></td> 
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>    
  </table>
  <br/><br/>
    
    
  <table align="center" width="80%" class="borderbox" summary="for layout only">
    <tr>
      <th colspan="4">The following reviewers were assigned to this grant</th>
    </tr> 
    <tr>
      <th>Name</th><th>Rating Complete</th>
      <th>Score</th><th>Rating</th>
    </tr>
    <c:set var="sum" value="0" />
    <c:set var="srate" value="0" />
    <c:set var="count" value="0" />
    <c:forEach var="assignBean" items="${allAssigned}" >
      <tr>
        <td><c:out value="${assignBean.fname}" /> <c:out value="${assignBean.lname}" /></td>
        <td><c:out value="${assignBean.reviewerAssigns[0].ratingcomp}" /></td>
        <td><c:out value="${assignBean.reviewerAssigns[0].score}" /></td>
        <td><c:out value="${assignBean.reviewerAssigns[0].overallScore}" /></td>
      </tr>
      <c:set var="sum" value="${sum + assignBean.reviewerAssigns[0].score}" />
      <c:set var="srate" value="${srate + assignBean.reviewerAssigns[0].overallScore}" />
      <c:if test="${assignBean.reviewerAssigns[0].score!=null && assignBean.reviewerAssigns[0].score >0}">
        <c:set var="count" value="${count + 1}" />
      </c:if>
    </c:forEach>
    <tr>
      <td colspan="2"><b>Average Score/Rating</b></td>
      <td><b><fmt:formatNumber maxFractionDigits="2" value="${sum/count}" /></b></td>
      <td><b><fmt:formatNumber maxFractionDigits="2" value="${srate/count}" /></b></td>
    </tr>
  </table> 
  <br/><br/>
  
  
  <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="3">Reviewer Comments</th>
    </tr>
    <c:forEach var="row" items="${allComments}" >       
      <tr> 
        <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
        <td><c:out value="${row.reviewerAssigns[0].comment}" /></td>
      </tr>
      <tr>
        <td colspan="3"><hr/></td>
      </tr>
    </c:forEach>      
  </table>
  <br/><br/>
  
  </body>
</html>

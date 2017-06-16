<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revScoresRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * Description
 * This admin jsp report list each reviewer's average score and comment for the
 * selected grant num. Defaults to show reviewerID, but can also display reviewer name, if
 * admin has selected the name option. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report - Reviewer Comments/Scores</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th align="center">Reviewer Comments/Scores Report</th>
    </tr>
  </table>
  <br/>
  
  <c:if test="${grantBean.grantid!=0}" >
  
  
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th>
    </tr>
    <tr>
      <td>030<c:out value="${grantBean.fccode}" />-
        <fmt:formatNumber value="${grantBean.fycode}" pattern="##" minIntegerDigits="2" />-
        <fmt:formatNumber value="${grantBean.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
      <td><c:out value="${grantBean.title}" /></td>
      <td><c:out value="${grantBean.instName}" /></td>
    </tr>
  </table>
  <br/>
  
  <c:choose >
  <c:when test="${showRev=='Y'}" >
  
      <table width="95%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td><b>Rev ID</b></td><td><b>Name</b></td>
          <td><b>Total Score</b></td><td><b>Rating Complete</b></td>
        </tr>
        
         <c:set var="sum" value="0" />
         <c:set var="count" value="0" />
        <c:forEach var="row" items="${allScores}" >
        <tr>  
          <td><c:out value="${row.revid}" /></td>
          <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].score}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].ratingcomp}" /></td>
        </tr>
        
        <c:set var="sum" value="${sum + row.reviewerAssigns[0].score}" />
        <c:if test="${row.reviewerAssigns[0].score!=null && row.reviewerAssigns[0].score >0}">
          <c:set var="count" value="${count + 1}" />
        </c:if>
        </c:forEach>  
        
        <tr>
          <td colspan="2"><b>Average Score</b></td>
          <td><b><fmt:formatNumber maxFractionDigits="2" value="${sum/count}" /></b></td>
        </tr>
      </table>
      <br/>
      
      <table width="95%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td><b>Rev ID</b></td><td><b>Name</b></td>
          <td><b>Comment</b></td>
        </tr>
        
        <c:forEach var="row" items="${allComments}" >
        <tr>  
          <td><c:out value="${row.revid}" /></td>
          <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].comment}" /></td>
        </tr>
        </c:forEach>    
      </table>
  
  </c:when>
  <c:otherwise >
  
  
      <table width="95%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td><b>Rev ID</b></td>
          <td><b>Total Score</b></td><td><b>Rating Complete</b></td>
        </tr>
        
         <c:set var="sum" value="0" />
         <c:set var="count" value="0" />
        <c:forEach var="row" items="${allScores}" >
        <tr>  
          <td><c:out value="${row.revid}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].score}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].ratingcomp}" /></td>
        </tr>
        
        <c:set var="sum" value="${sum + row.reviewerAssigns[0].score}" />
        <c:if test="${row.reviewerAssigns[0].score!=null && row.reviewerAssigns[0].score >0}">
          <c:set var="count" value="${count + 1}" />
        </c:if>
        </c:forEach>   
        
        <tr>
          <td><b>Average Score</b></td>
          <td><b><fmt:formatNumber maxFractionDigits="1" value="${sum/count}" /></b></td>
        </tr>
      </table>
      <br/>
      
      <table width="95%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td><b>Rev ID</b></td><td><b>Comment</b></td>
        </tr>
        
        <c:forEach var="row" items="${allComments}" >
        <tr>  
          <td><c:out value="${row.revid}" /></td>
          <td><c:out value="${row.reviewerAssigns[0].comment}" /></td>
        </tr>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        </c:forEach>    
      </table>
  
  </c:otherwise>
  </c:choose>
  
  
  </c:if>
  
  <c:if test="${grantBean.grantid==0}">
    <h4>The project number could not be found</h4>
  </c:if>
  
  </body>
</html>

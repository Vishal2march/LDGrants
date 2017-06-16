<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revParticipationView.jsp
 * Creation/Modification History  :
 *
 *     SH       7/30/07     Created
 *
 * Description
 * This page displays all grant_assign_max records for given reviewer
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>  
  <br/><br/>
  
   <table align="center" width="90%" summary="for layout only">
    <tr>
      <th colspan="5">Grant Review Participation</th>
    </tr>
    <tr>  
      <td colspan="5">The following list displays the grant programs you have agreed
        to review for a given year.</td>
    </tr>    
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td><b>Action</b></td>
      <td><b>Grant Program</b></td>
      <td><b>Fiscal Year</b></td>
      <td><b>Available to Review</b></td>
      <td><b>Comments</b></td>
    </tr>
    
    <c:forEach var="row" items="${allAccepted}" >
    
      <c:choose >
        <c:when test="${param.p=='lg'}">
          <c:url var="upURL" value="lgReviewer.do">
            <c:param name="item" value="record" />
            <c:param name="id" value="${row.id}" />
            <c:param name="p" value="lg" />
          </c:url>
        </c:when>
        
        <c:when test="${param.p=='co'}">   
          <c:url var="upURL" value="coReviewerForms.do">
            <c:param name="item" value="record" />
            <c:param name="id" value="${row.id}" />
            <c:param name="p" value="co" />
          </c:url>
        </c:when>
        
        <c:when test="${param.p=='di'}">
          <c:url var="upURL" value="diReviewerForms.do">
            <c:param name="item" value="record" />
            <c:param name="id" value="${row.id}" />
            <c:param name="p" value="di" />
          </c:url>
        </c:when>        
      </c:choose>
    
      <tr>
        <td><a href='<c:out value="${upURL}" />'>Update</a></td>
        <td><c:out value="${row.grantprogram}" /></td>
        <td><c:out value="${row.fiscalyear}" /></td>
        <c:choose >
          <c:when test="${param.p=='lg'}">
            <td><c:out value="${row.response}" /></td>
          </c:when>
          <c:otherwise >
            <td><c:out value="${row.numaccepted}" /></td>
          </c:otherwise>
        </c:choose>
        <td><c:out value="${row.descrip}" /></td>
      </tr>
    </c:forEach>
    
    <tr>  
      <td height="20" />
    </tr>
    <tr>
      <td colspan="5">      
        <c:choose >
          <c:when test="${param.p=='co'}">   
            <a href="coReviewerForms.do?item=addrecord&p=co" >Add new Participation record</a>
          </c:when>
          <c:when test="${param.p=='di'}">
            <a href="diReviewerForms.do?item=addrecord&p=di" >Add new Participation record</a>
          </c:when>
          <c:when test="${param.p=='lg'}">
            <a href="lgReviewer.do?item=addrecord&p=lg" >Add new Participation record</a>
          </c:when>
        </c:choose>
      </td>
    </tr>
   </table>   
  
  </body>
</html>

<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminViewRatings.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       8/7/07 Created       2/14/08 Modified for Co and DI use
 *
 * Description
 * This page allows the admin to view the reviewers assigned to this grant with thier scores
 * and comments if they have submitted scores.  Links to internal reports on reviewer scores
 * and score order report for fy.  Used for BOTH CO AND DI ADMIN VIEW.  
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
  <br/><br/>
  <table align="center" width="80%" summary="for layout only">
    <tr>
      <th colspan="3">Reviewer Ratings and Comments for this Grant Proposal</th>
    </tr>
    <tr>             
      <td><b>Project Num</b></td>
      <td><b>Institution</b></td>
      <td ><b>Title</b></td>  
    </tr>
    <tr>      
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" />
      </td>
      <td ><c:out value="${thisGrant.instName}" /></td> 
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>    
    <tr>
      <td height="20" />
    </tr>
    <tr>  
      <td colspan="3">The following reports will open in a new window.</td>
    </tr>
           
    <c:if test="${module=='co'}" >
    
        <%--<c:url var="scores" value="ReportServlet">
          <c:param name="todo" value="revscores" />
          <c:param name="projnum" value="${thisGrant.projseqnum}" />
        </c:url>--%>
        <c:url var="scores" value="cpAdminReport.do">
          <c:param name="item" value="revscores" />
          <c:param name="projnum" value="${thisGrant.projseqnum}" />
        </c:url>
        <tr>
          <td colspan="2">Reviewer Ratings/Comments for this grant</td>
          <td><a href='<c:out value="${scores}" />' target="_blank">HTML Report</a></td>
        </tr>
        <%-- report shows score order for all coord grants received this fy--%>
        <%--<c:url var="allscores" value="ReportServlet">
          <c:param name="todo" value="coscores" />
          <c:param name="corevfycode" value="${thisGrant.fycode}" />
        </c:url>--%>
        <c:url var="allscores" value="cpAdminReport.do">
          <c:param name="item" value="scoresrpt" />
          <c:param name="fy" value="${thisGrant.fycode}" />
          <c:param name="fc" value="${thisGrant.fccode}" />
        </c:url>
        <tr>
          <td colspan="2">Coordinated Grant Proposals for fiscal year in score order</td>
          <td><a href='<c:out value="${allscores}" />' target="_blank">HTML Report</a></td>
        </tr>
    </c:if>
    
    <c:if test="${module=='di'}" >
    
        <c:url var="scores" value="cpAdminReport.do">
          <c:param name="item" value="revscores" />
          <c:param name="projnum" value="${thisGrant.projseqnum}" />
        </c:url>
        <tr>
          <td colspan="2">Reviewer Ratings/Comments for this grant</td>
          <td><a href='<c:out value="${scores}" />' target="_blank">HTML Report</a></td>
        </tr>
        <%-- report shows score order for all disc grants received this fy--%>
        <c:url var="discores" value="cpAdminReport.do">
          <c:param name="item" value="scoresrpt" />
          <c:param name="fy" value="${thisGrant.fycode}" />
          <c:param name="fc" value="${thisGrant.fccode}" />
        </c:url>
        <tr>
          <td colspan="2">Discretionary Grant Proposals for fiscal year in score order</td>
          <td><a href='<c:out value="${discores}" />' target="_blank">HTML Report</a></td>
        </tr>
    </c:if>
    
  </table>
  <br/><br/>
    
    
  <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="5">The following reviewers were assigned to this grant</th>
    </tr> 
    <tr>
      <td><b>Name</b></td><td><b>Interest</b></td>
      <td><b>Rating Complete</b></td><td><b>Total Score</b></td>
      <td><b>Rating Form</b></td><td><b>Unsubmit/Unlock Rating</b></td>
    </tr>
    <c:set var="sum" value="0" />
    <c:set var="count" value="0" />
    <c:forEach var="revBean" items="${allAssigned}" >
    
      <c:url var="formURL" value="ApcntFunctionsServlet">
         <c:param name="i" value="ratingadminpdf"/>
         <c:param name="assignid" value="${revBean.reviewerAssigns[0].assignid}"/>
      </c:url>
      
      <c:if test="${module=='di'}" >
          <c:url var="unlockURL" value="diAdminRevNav.do" >
            <c:param name="item" value="unlock" />
            <c:param name="m" value="di" />
            <c:param name="id" value="${revBean.reviewerAssigns[0].assignid}" />
          </c:url>
        </c:if>
        
        <c:if test="${module=='co'}" >
          <c:url var="unlockURL" value="coAdminRevNav.do" >
            <c:param name="item" value="unlock" />
            <c:param name="m" value="co" />
            <c:param name="id" value="${revBean.reviewerAssigns[0].assignid}" />
          </c:url>          
        </c:if>     
      
      <tr>
        <td><c:out value="${revBean.fname}" /> <c:out value="${revBean.lname}" /></td>
        <td><c:out value="${revBean.interest}" /></td>       
        <td><c:out value="${revBean.reviewerAssigns[0].ratingcomp}" /></td>
        <td><c:out value="${revBean.reviewerAssigns[0].score}" /></td>                
        <td><a href='<c:out value="${formURL}" />' target="_blank">Form</a></td>
        <td><a href='<c:out value="${unlockURL}" />'>Unsubmit</a></td>
      </tr>
      
      <c:set var="sum" value="${sum + revBean.reviewerAssigns[0].score}" />
      <c:if test="${revBean.reviewerAssigns[0].score!=null && revBean.reviewerAssigns[0].score >0}">
        <c:set var="count" value="${count + 1}" />
      </c:if>
    </c:forEach>
    <tr>
      <td colspan="3"><b>Average Score</b></td>
      <td><b><fmt:formatNumber maxFractionDigits="2" value="${sum/count}" /></b></td>
    </tr>
  </table> 
  <br/><br/>
  
  
  <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="3">Reviewer Comments</th>
    </tr>
    <c:forEach var="row" items="${allComments}" >
     
        <c:if test="${module=='di'}" >
          <c:url var="comURL" value="diAdminRevNav.do" >
            <c:param name="item" value="comment" />
            <c:param name="m" value="di" />
            <c:param name="id" value="${row.reviewerAssigns[0].commentId}" />
          </c:url>
        </c:if>
        
        <c:if test="${module=='co'}" >
          <c:url var="comURL" value="coAdminRevNav.do" >
            <c:param name="item" value="comment" />
            <c:param name="m" value="co" />
            <c:param name="id" value="${row.reviewerAssigns[0].commentId}" />
          </c:url>          
        </c:if>     
        
      <tr> 
        <td><a href='<c:out value="${comURL}" />' >Update</a></td>
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

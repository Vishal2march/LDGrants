<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  applicationStatus.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the applicant to view the history of submissions
 * and approvals for this grant, and any admin comments regarding the grant.
 * now used for SA, CO, DI, LG apcnts
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Application Status</title>
  </head>
  <body>
  
  <h4>Application Status</h4>
  
    <table width="80%" align="center" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th>Date Submitted</th>
        <th>Submitted By</th>
        <th>Version</th>
      </tr>

      <c:forEach var="submitBean" items="${allSubmissions}" >
      <tr>
        <td><fmt:formatDate value="${submitBean.dateSubmitted}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${submitBean.userSubmitted}"/></td>
        <td><c:out value="${submitBean.versionSubmitted}"/></td>
      </tr>    
      </c:forEach>
    </table>     
    <br/><br/>
     
    <c:if test="${param.p!='lg'}">
    
    <c:if test="${appStatus.showscorecomm=='true'}">
    <table width="80%" align="center" border="1" class="graygrid" summary="for layout only">         
      <tr>
        <th>Date Reviewed</th>
        <th>Admin</th>
        <th>Version</th>
        <th>Result</th>
        <th>Amount Approved</th>
      </tr>
     
      <c:forEach var="appBean" items="${allApprovals}">
      <tr>
        <td><fmt:formatDate value="${appBean.dateapproved}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${appBean.adminuser}" /></td>
        <td><c:out value="${appBean.version}" /></td>
        <td><c:out value="${appBean.approvalType}" /></td>
        <td><fmt:formatNumber value="${appBean.amountappr}" type="currency" /></td>
      </tr>
      </c:forEach>  
    </table>   
    </c:if>
    
    <br/><br/>
    
    
    <logic:notEmpty name="allComments" >
    <table align="center" width="80%" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="3">Admin Comments During Application Review/Approval</th>
      </tr>
      <tr>
        <th>Comment Date</th>
        <th>Admin</th>
        <th>Comment</th>
      </tr>
      <c:forEach var="commBean" items="${allComments}" >
      <tr >            
        <td><fmt:formatDate value="${commBean.commentdate}" pattern="MM/dd/yyyy" /></td>
        <td><c:out value="${commBean.createdby}" /></td>
        <td><c:out value="${commBean.comment}" /></td>
      </tr>  
      </c:forEach>
    </table>  
    <br/><br/>
    </logic:notEmpty>    
    
    
   <logic:notEmpty name="apcntComments">
    <table align="center" width="80%" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="3">Applicant Comments During Application Submission</th>
      </tr>
      <tr>
        <th>Comment Date</th>
        <th>User name</th>
        <th>Comment</th>
      </tr>
      <c:forEach var="commBean" items="${apcntComments}" >
      <tr >            
        <td><fmt:formatDate value="${commBean.commentdate}" pattern="MM/dd/yyyy" /></td>
        <td><c:out value="${commBean.createdby}" /></td>
        <td><c:out value="${commBean.comment}" /></td>
      </tr>  
      </c:forEach>
    </table>  
    </logic:notEmpty>
 
  </c:if>
 
  </body>
</html>

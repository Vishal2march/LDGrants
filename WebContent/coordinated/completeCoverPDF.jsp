<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  completeCoverPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       9/12/07     Created
 *
 * Description
 * This page gives a HTML version of the coversheet
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="750" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>NYS Library Coordinated Grant Application</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th>Conservation Preservation Program Grant Application
          <br/>The University of the State of New York 
          <br/>The State Education Department 
          <br/>Division of Library Development 
          <br/>Coordinated Grants
      </th>
    </tr>
  </table>
  </font>
  
  <font size="1">
  <table align="center" border="1" width="95%" summary="for layout only">
  <tr >
    <th colspan="2">Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
  
  <tr>
    <td><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" /></td>
  </tr>  
  <tr>
    <td><b>Contract Number</b></td>
    <td><c:out value="${thisGrant.contractNum}" /></td>
  </tr>
  <tr>
    <td><b>Project Title</b></td>
    <td><c:out value="${thisGrant.title}" /></td>
  </tr>
  <tr>
    <td><b>Institution ID</b></td>
    <td><c:out value="${thisGrant.instID}" /></td>
  </tr>
  <tr>
    <td><b>Institution</b></td>
    <td><c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
    <td><b>Address</b></td>
    <td>
      <c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" />
    </td>
  </tr>
  <tr>
    <td><b>City, State</b></td>
    <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" /> 
        <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
  </tr>
  <tr>
    <td><b>Library Director</b></td>
    <td><c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.lname}" /></td>
  </tr>
   <tr>
    <td><b>Preservation Administrative Officer</b></td>
    <td>
     <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.lname}" />
    </td>
  </tr>
  <tr>
    <td><b>Project Manager</b></td>
    <td>
      <c:out value="${projManagerBean.fname}" /> <c:out value="${projManagerBean.mname}" /> <c:out value="${projManagerBean.lname}" />
    </td>
  </tr>
  <tr>
    <td><b>Project Manager Phone</b></td>
    <td><c:out value="${projManagerBean.phone}" /></td>
  </tr>
  <tr>
    <td><b>Project Manager Email</b></td>
    <td><c:out value="${projManagerBean.email}" /></td>
  </tr>
  <tr>
    <th colspan="2">Participating Institutions</th>
  </tr>    
    
  <c:forEach var="partInstBean" items="${allPartInst}">
    <tr>
      <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
    </tr>
  </c:forEach>

  <tr>
    <th colspan="2">Total Budget Amount Requested</th>
  </tr>
  <c:forEach var="row" items="${allsums}">
    <tr>
      <td>Total requested for fy <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
      <td><fmt:formatNumber value="${row.totAmtReq}" type="currency"  /></td>
    </tr>
  </c:forEach>
    
  </table>  
  </font>
  <br/><br/>  
  
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only"> 
    <tr bgcolor="Silver">
      <th colspan="5">Authorizations</th>
    </tr>
    
    <c:if test="${not empty grantAuth}">
    <tr>
      <td><b>Version</b></td>
      <td><b>Date Authorized</b></td>
      <td><b>Title</b></td>
      <td><b>Name</b></td>
      <td><b>User Authorized</b></td>      
    </tr>
      
    <c:forEach var="authBean" items="${grantAuth}" >
      <tr>
        <td><c:out value="${authBean.version}" /></td>
        <td ><fmt:formatDate value="${authBean.authdate}" pattern="MM/dd/yyyy" /></td>
        <td><c:out value="${authBean.title}" /></td>
        <td><c:out value="${authBean.name}" /></td>
        <td><c:out value="${authBean.user}" /></td>
      </tr>    
    </c:forEach>
    </c:if>
    
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5" >Application Submissions</th>
    </tr>
    
    <c:if test="${not empty allSubmissions}">
        <tr>
          <td width="25%"><b>Date Submitted</b></td>
          <td width="25%"><b>Submitted By</b></td>
          <td width="25%"><b>Version</b></td>
        </tr>    
        <c:forEach var="submitBean" items="${allSubmissions}" >
        <tr>
          <td><fmt:formatDate value="${submitBean.dateSubmitted}" pattern="MM/dd/yyyy"/></td>
          <td><c:out value="${submitBean.userSubmitted}"/></td>
          <td><c:out value="${submitBean.versionSubmitted}"/></td>
        </tr>
        </c:forEach>
    </c:if>
    
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5" >Admin Application Reviews</th>
    </tr>
    
    <c:if test="${not empty allApprovals}">
        <tr>
          <td><b>Date Reviewed</b></td>
          <td><b>Admin</b></td>
          <td><b>Version</b></td>
          <td><b>Result</b></td>
        </tr>   
        <c:forEach var="appBean" items="${allApprovals}">
        <tr>
          <td><fmt:formatDate value="${appBean.dateapproved}" pattern="MM/dd/yyyy"/></td>
          <td><c:out value="${appBean.adminuser}"/></td>
          <td><c:out value="${appBean.version}"/></td>
          <td><c:out value="${appBean.approvalType}" /></td>
        </tr>
        </c:forEach>  
    </c:if>
            
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Admin Comments During Application Review/Approval</th>
    </tr>
    
    <c:if test="${not empty allComments}">
        <tr>
          <td><b>Comment Date</b></td>
          <td><b>Admin</b></td>
          <td><b>Comment</b></td>
        </tr>
        <c:forEach var="commBean" items="${allComments}" >
        <tr >            
          <td><fmt:formatDate value="${commBean.commentdate}" pattern="MM/dd/yyyy" /></td>
          <td><c:out value="${commBean.createdby}" /></td>
          <td><c:out value="${commBean.comment}" /></td>
        </tr>  
        <tr>
          <td height="20" />
        </tr>
        </c:forEach>
    </c:if>
  </table>  
  </font>
  <br/><br/>
  
 </body>
</html>

</pd4ml:transform>
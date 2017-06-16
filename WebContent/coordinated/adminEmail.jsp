<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminEmail.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       7/23/07     Modified
 *
 * Description
 * This page allows the admin select an email type, initial, final approval
 * or denial, to send to the PO for SA/CO/DI grants. 
 * Modified 2/21/08 to send OSC intent to award letter. 
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
  
  <c:if test="${param.error=='true'}">
    <p class="error">The approval/denial email cannot be created.  The admin must first mark the checkbox
    indicating the initial or final report has been approved/denied.</p>
  </c:if>
  
  <h4>Approval/Denial Emails for this Grant Proposal</h4>  
  
  <table align="center" width="80%" summary="for layout only">
    <tr>             
      <td><b>Project Num</b></td>
      <td><b>Institution</b></td>
      <td><b>Title</b></td>          
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
  
  <p>Choose the Email type to send to the Preservation Officer/Project Manager of this grant.
     This will send an email only for this grant proposal. </p>      
    

    <form method="POST" action="cpAdminEmail.do?item=template" >
      <input type="RADIO" name="etype" value="osc"/>Pending OSC Approval Email<br/>
      <input type="RADIO" name="etype" value="initial" checked="checked"/>Application Approval Email<br/>      
      <input type="RADIO" name="etype" value="final"/>Final Report Approval Email<br/>
      <input type="RADIO" name="etype" value="deny"/>Application Denied Email <br/>
      <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />'/>
      <input type="HIDDEN" name="m" value='<c:out value="${param.p}" />' />
      <input type="SUBMIT" value="Select" />
    </form>
    
    
  <p align="center">
  <b>If you already started an email but have not sent the email: </b>
  <a href="cpAdminEmail.do?item=unsentmail">View Unsent Emails</a></p>
  <c:choose >
  <c:when test="${param.p=='co'}">
      <c:url var="backURL" value="coAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>
  </c:when>
  <c:when test="${param.p=='sa'}">
      <c:url var="backURL" value="saAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>
  </c:when>
  <c:when test="${param.p=='di'}">
      <c:url var="backURL" value="diAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>
  </c:when>
  </c:choose>
  
  <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p> 
  
  
  </body>
</html>

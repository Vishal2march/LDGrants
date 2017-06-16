<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminHome</title>
  </head>
  <body>
  
  
  <c:set var="permiss" />
  <c:if test="${param.p=='staid'}">
    <c:set var="permiss" value="${lduser.adminstat}" />
  </c:if>
  
  <c:choose >
  <c:when test="${permiss==null}" >  
    <font color="Red">You do not have admin access for this grant program.</font>  
  </c:when>
  <c:otherwise >       
  
        
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">New Application Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="grantBean" items="${allGrants}" >  
    
    <c:url var="appURL" value="staidAdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>         

    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
          -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${grantBean.instName}" /></a></td>           
      <td><c:choose >
          <c:when test="${grantBean.needApproval=='Y'}" >
            Complete
          </c:when>
          <c:otherwise >
            Pending
          </c:otherwise>
          </c:choose>            
      </td>
      <td><fmt:formatDate value="${grantBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
    </c:forEach>
  </table>
    
  <br/><br/><br/>    
  
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">Final Report Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="grantBean" items="${finalGrants}" >  
    
    <c:url var="appURL" value="staidAdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>         

    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
          -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${grantBean.instName}" /></a></td>           
      <td><c:choose >
          <c:when test="${grantBean.needApproval=='Y'}" >
            Complete
          </c:when>
          <c:otherwise >
            Pending
          </c:otherwise>
          </c:choose>            
      </td>
      <td><fmt:formatDate value="${grantBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
    </c:forEach>
  </table>
  <br/><br/>
  
   
    
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="6" bgcolor="Silver">Waiting on Final Submissions 
      (Initial Application has been Approved)</th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${apprGrants}" >
          
   <c:url var="appURL" value="staidAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td>Waiting on Final</td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
  

  <table width="95%" align="center" summary="for layout only">
      <tr>
        <th colspan="6" bgcolor="Silver">Closed Grants</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${closeGrants}" >
            
     <c:url var="appURL" value="staidAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
            -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>          
        <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
        <td><c:out value="${graBean.status}" /></td>
      </tr>
    </c:forEach>            
  </table>
  
  
  
</c:otherwise>
</c:choose>
  
  
  </body>
</html>
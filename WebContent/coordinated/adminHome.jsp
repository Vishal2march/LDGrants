<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminHome.jsp
 * Creation/Modification History  :
 *
 *     SH       7/26/07     Created
 *
 * Description
 * This is the Co admin home page listing all CO grants that have been
 * submitted or are awaiting a final submission. It has links to access
 * each of the grant applications.
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
  <c:choose >
  <c:when test="${lduser.admincoor==null}" >  
    <font color="Red">You do not have access to the administrative portion of Coordinated grants.</font>
  
  </c:when>
  <c:otherwise >
  
  <br/>
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="7">New Application Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Sponsoring Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>       
      <td><b>Total Amt Requested (first year)</b></td>
      <td><b>Total Amt Approved (first year)</b></td>
    </tr>                
     
    <c:set var="totsum" value="0" />
    <c:forEach var="grantBean" items="${allGrants}" >  
    
    <c:url var="appURL" value="coAdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>  

    <tr >
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${grantBean.fccode}" />
          -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${grantBean.instName}" /></a></td>           
      <td><c:out value="${grantBean.title}" /></td>
      <td><c:choose >
          <c:when test="${grantBean.needApproval=='Y'}" >
            Complete
          </c:when><c:otherwise >
            Pending
          </c:otherwise>
          </c:choose>            
      </td>
      <td><fmt:formatDate value="${grantBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
      <td><fmt:formatNumber value="${grantBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${grantBean.totamtappr}" type="currency" minFractionDigits="0"/></td>
    </tr>
    <c:set var="totsum" value="${totsum + grantBean.totamtreq}" />
    </c:forEach>
    
    <tr>
      <td colspan="5"><b>Total requested</b></td>
      <td><b><fmt:formatNumber value="${totsum}" type="currency" minFractionDigits="0" /></b></td>
    </tr>
  </table>
  
  <br/><br/>
  <p>Total amount available for fy <fmt:formatNumber value="${totAvail.fycode}" minFractionDigits="0" /> is 
      <fmt:formatNumber value="${totAvail.remainingFund}" type="currency" minFractionDigits="0"/></p><br/>
      
      
      
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">Final Report Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="grantBean" items="${finalGrants}" >  
    
    <c:url var="appURL" value="coAdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>         

    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
          -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${grantBean.instName}" /></a></td>           
      <td><c:out value="${grantBean.title}" /></td>
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
      <th colspan="6" bgcolor="Silver">FS-10-A Amendment Submitted</th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>       
      <td><b>Project Title</b></td>
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${amendGrants}" >
          
   <c:url var="appURL" value="coAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}"/></td>
      <td>FS-10-A Budget Amendment</td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
   
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="6" bgcolor="Silver">Waiting on Final Submissions (Initial Application has been Approved)</th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Sponsoring Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${apprGrants}" >
          
   <c:url var="appURL" value="coAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td>Waiting on Final</td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
  
  
  <table width="95%" align="center" summary="for layout only">
      <tr>
        <th colspan="6" bgcolor="Silver">Denied Applications</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b>Project Title</b></td> 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${denyGrants}" >
            
     <c:url var="appURL" value="coAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${graBean.fccode}" />
            -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>          
        <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
        <td><c:out value="${graBean.title}" /></td>
        <td><c:out value="${graBean.status}" /></td>
      </tr>
    </c:forEach>            
  </table>
  
  
  <br/><br/>
  <table width="95%" align="center" summary="for layout only">
      <tr>
        <th colspan="6" bgcolor="Silver">Closed Applications</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b>Project Title</b></td> 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${closeGrants}" >
            
     <c:url var="appURL" value="coAdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${graBean.fccode}" />
            -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>          
        <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
        <td><c:out value="${graBean.title}" /></td>
        <td><c:out value="${graBean.status}" /></td>
      </tr>
    </c:forEach>            
  </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

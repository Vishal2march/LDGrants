<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminHome.jsp
 * Creation/Modification History  :
 *
 *     SHusak      Created
 *
 * Description
 * This page is sa/di/lg admin home, contains all new and closed grants. 
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
  
  <c:set var="permiss" />
  <c:if test="${param.p=='sa'}">
    <c:set var="permiss" value="${lduser.adminstat}" />
    <c:set var="fcpre" value="03"/>
    <c:set var="colhead" value="Project Title"/>
  </c:if>
  <c:if test="${param.p=='di'}">
    <c:set var="permiss" value="${lduser.admindisc}" />
    <c:set var="fcpre" value="03"/>
    <c:set var="colhead" value="Project Title"/>
  </c:if>
  <c:if test="${param.p=='lg'}">
    <c:set var="permiss" value="${lduser.lgadmin}" />
    <c:set var="fcpre" value="05"/>
    <c:set var="colhead" value="Category"/>
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
      <td><b><c:out value="${colhead}"/></b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="grantBean" items="${allGrants}" >  
    
    <c:url var="appURL" value="${param.p}AdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>         

    <tr>
      <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
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
    
  <br/><br/><br/>    
  
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">Final Report Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b><c:out value="${colhead}"/></b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="grantBean" items="${finalGrants}" >  
    
    <c:url var="appURL" value="${param.p}AdminNav.do">
      <c:param name="id" value="${grantBean.grantid}" />
      <c:param name="item" value="grant" />
    </c:url>         

    <tr>
      <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
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
      <td><b><c:out value="${colhead}"/></b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${amendGrants}" >
          
   <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td>FS-10-A Amendment</td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
    
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="6" bgcolor="Silver">Waiting on Final Submissions <c:if test="${param.p!='lg'}">
      (Initial Application has been Approved)</c:if></th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b><c:out value="${colhead}"/></b></td> 
      <td><b>Status</b></td>
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${apprGrants}" >
          
   <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td>Waiting on Final</td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
  
  <table width="95%" align="center" summary="for layout only">
      <tr>
        <th colspan="6" bgcolor="Silver">Denied Grants</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b><c:out value="${colhead}"/></b></td> 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${denyGrants}" >
            
     <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
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
        <th colspan="6" bgcolor="Silver">Closed Grants</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b><c:out value="${colhead}"/></b></td> 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${closeGrants}" >
            
     <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
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
        <th colspan="6" bgcolor="Silver">Declined Awards</th>
      </tr>
      <tr> 
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>                 
        <td><b><c:out value="${colhead}"/></b></td> 
        <td><b>Status</b></td>                                  
      </tr>
      
    <c:forEach var="graBean" items="${declineGrants}" >
            
     <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
    
      <tr >
        <td><c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
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

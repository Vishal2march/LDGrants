<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  liAdminHome.jsp
 * Creation/Modification History  :
 *
 *     SHusak       Created
 *
 * Description
 * This page is the fl/al admin home, lists all new grants, closed grants. 
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
  
  <br/>
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">New Application Submissions</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${allGrants}" >  
    
    <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>           

    <tr >
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
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
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${finalGrants}" >  
    
    <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>           

    <tr >
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
    </c:forEach>
  </table>
  <br/><br/>
  
  
    
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="6" bgcolor="Silver">Waiting on Final Submissions</th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${apprGrants}" >
          
   <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
      <td><fmt:formatDate value="${graBean.submissionBean.dateSubmitted}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </c:forEach>            
  </table>        
  <br/><br/>
  
  
  
  
  
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="6" bgcolor="Silver">Year 2 Complete, Ready for Year 3</th>
    </tr>
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>
    
  <c:forEach var="graBean" items="${year3Grants}" >
          
   <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>          
  
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
          -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>          
      <td><a href='<c:out value="${appURL}" />'  class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
      <td><c:out value="${graBean.title}" /></td>
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
            
     <c:url var="appURL" value="${param.p}AdminNav.do">
       <c:param name="id" value="${graBean.grantid}" />
       <c:param name="item" value="grant" />
     </c:url>        
    
      <tr >
        <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
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
            
     <c:url var="appURL" value="${param.p}AdminNav.do">
       <c:param name="id" value="${graBean.grantid}" />
       <c:param name="item" value="grant" />
     </c:url>        
    
      <tr >
        <td>03<fmt:formatNumber minIntegerDigits="2" value="${graBean.fccode}" />
            -<fmt:formatNumber value="${graBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${graBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>          
        <td><a href='<c:out value="${appURL}" />' class="blacklink"><c:out value="${graBean.instName}" /></a></td>           
        <td><c:out value="${graBean.title}" /></td>
        <td><c:out value="${graBean.status}" /></td>
      </tr>
    </c:forEach>            
  </table>
  
  </body>
</html>

<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liAdminHomeNew.jsp
 * Creation/Modification History  :
 * SHusak       Created 1/20/16
 *
 * Description
 * This page is the NEW fl/al admin home, requested by KBALSEN 1/20/16.  This page has FY dropdown search
 * which populates 2 categories: app_submitted, and app_approved.  This will replace existing literacy admin
 * home page.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>liAdminHomeNew</title>
  </head>
  <body>
  
  
  
  <h4>New Admin Home Page</h4>
  
  <p><form action="${param.p}AdminNav.do?item=litAdminHomeSearch" method="POST">
  View applications for Fiscal Year:<br/>
  Fiscal Year 
              <%--<select name="fycode">
                <c:forEach var="row" items="${dropDownList}">
                <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
                </c:forEach>       
              </select> --%>
              <select name="fycode">
                <option value="17">2016-2019</option>
                <option value="14">2013-2016</option>
              </select>
       <input type="hidden" name="mod" value="${param.p}"/> 
       <input type="SUBMIT" value="View"/>
  </form></p>
  
  
  
  
  
  <br/><br/><br/>
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th bgcolor="Silver" colspan="6">Submitted Applications</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${allGrants}" >  
    
   <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>   --%>        
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">First year reporting submitted</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${finalSubmit1}" >  
    
    <%--<c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>  --%>          
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">First year reporting approved</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Approval Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${approved1}" >  
    
    <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>  --%>
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">Second year reporting submitted</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${finalSubmit2}" >  
    
    <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url> --%>           
	<c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">Second year reporting approved</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Approval Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${approved2}" >  
    
    <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>  --%>
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">Third year reporting submitted</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${finalSubmit3}" >  
    
    <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url> --%>    
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">Third year reporting approved</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Approval Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${approved3}" >  
    
    <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url> --%>      
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
      <th bgcolor="Silver" colspan="6">Approved Applications</th>
    </tr>           
    <tr> 
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>                 
      <td><b>Project Title</b></td> 
      <td><b>Submission Date</b></td>                                    
    </tr>                
     
    <c:forEach var="graBean" items="${approvedGrants}" >  
    
   <%-- <c:url var="appURL" value="${param.p}AdminNav.do">
     <c:param name="id" value="${graBean.grantid}" />
     <c:param name="item" value="grant" />
   </c:url>  --%>
   <c:url var="appURL" value="adminChecklist.action">
     <c:param name="id" value="${graBean.grantid}" />
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
  
  
  </body>
</html>
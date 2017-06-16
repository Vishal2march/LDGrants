<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>Search Member Applications</h4>
  
  <p>List member applications created for fiscal year:<br/>
  <form method="POST" action="cnRevMemberSearch.do?i=fysearch">
        <select name="fycode">
          <c:forEach var="row" items="${dropDownList}">
          <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
  </form>
  </p>
 

 
 <p>List all applications created by member:
 <form method="POST" action="cnRevMemberSearch.do?i=instsearch">
       <select name="instid">
          <c:forEach var="row" items="${dropDownMembers}">
          <option value='<c:out value="${row.idLong}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
 </form>
 </p>
  
 <hr/>
  
 <logic:notEmpty name="allApplications">
 <br/>
    <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4">Construction Grant Applications</th>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
    </tr>  
    
    <c:forEach var="assignBean" items="${allApplications}" >
      <c:url var="currURL" value="cnReviewNav.do">
         <c:param name="item" value="checklist"/> 
         <c:param name="id" value="${assignBean.grantId}"/>
      </c:url>
      
      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' >
            03<fmt:formatNumber value="${assignBean.fccode}" />
            -<fmt:formatNumber value="${assignBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${assignBean.projectSeqNum}" minIntegerDigits="4" pattern="####" />
        </a></td>
        <td align="center"><c:out value="${assignBean.projName}" /></td>
        <td align="center"><c:out value="${assignBean.instName}" /></td>
        <td align="center"><c:out value="${assignBean.fiscalYear}" /></td>
      </tr>       
    </c:forEach>
    
  </table>      
  </logic:notEmpty>
  
  </body>
</html>
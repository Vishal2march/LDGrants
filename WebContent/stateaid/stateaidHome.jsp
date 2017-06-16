<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>stateaidHome</title>
  </head>
  <body>
  
  <h3>State Aid Application</h3>
  
  
  <p>
       <c:choose >
       <c:when test="${appDates.canApply=='true'}" >
            <form action="stateaidNav.do">
                Create a new State Aid application:
                <input type="submit" value="Create Application"/>
                <input type="hidden" name="m" value="staid"/>
                <input type="hidden" name="item" value="createapp"/>
            </form>
                        
       </c:when>
       <c:otherwise >
         You may only create a new State Aid application during the new application period,<br/> and
         may complete only 1 new application per fiscal year period.  
      </c:otherwise>
      </c:choose>
      
       <br/><br/>
       <c:if test="${chooseGrant=='true'}" >
         <font color="Red">Choose an application from the links below.</font>
       </c:if>
    </p>
    
    
    
    
    <table width="80%" align="center" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="4">State Aid Applications</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Institution ID</th><th>Institution</th><th>Fiscal Year</th>
    </tr>
    
    <c:forEach var="GrantBean" items="${allGrants}" >
      <c:url var="currURL" value="stateaidForms.do">
        <c:param name="i" value="checklist"/>
        <c:param name="grantid" value="${GrantBean.grantid}"/>
        <c:param name="m" value="staid" />
      </c:url>
      
      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' >
        03<fmt:formatNumber minIntegerDigits="2" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" pattern="####" minIntegerDigits="4"/></a></td>
        <td align="center"><c:out value="${GrantBean.instID}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>
     </c:forEach>
  </table>        
  
  <br/>
  
  </body>
</html>
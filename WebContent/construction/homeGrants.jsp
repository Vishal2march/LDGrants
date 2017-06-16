<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
    <h4 align="center">$19 Million State Aid For Library Construction Program</h4>


    <table width="90%" align="center" summary="for layout only">
    
    <tr>
    <td>For additional information on the Public Library Construction program, see Construction program <a href="http://www.nysl.nysed.gov/libdev/construc/index.html" target="_blank">website</a></td>
    </tr>
    
    <tr>
    <td>Construction Program <a href="constructUpdates.jsp">Updates/Reminders</a></td> 
    </tr>
    
    <tr>
    <td>
        <c:choose >
          <c:when test="${appDates.canApply=='true'}">
            Start a new Construction grant application for FY <fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode-1}" />
            -<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
            <a href="constructionNav.do?item=createapp&m=cn" >Start new application</a>       
          
          </c:when>
          <c:otherwise >
            You can only create a new Construction application during the new application
            period. 
          </c:otherwise>
          </c:choose> 
   </td>
   </tr>
   </table>
   
  
  <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4"><b><font size="4">Construction Grant Applications</font></b></th>
    </tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
      <th>Status</th>
    </tr>  
    <c:forEach var="GrantBean" items="${allGrants}" >
      <c:url var="currURL" value="constructionForms.do">
        <c:param name="i" value="checklist"/> 
        <c:param name="grantid" value="${GrantBean.grantid}"/>
        <c:param name="m" value="cn"/>
      </c:url>
      <tr>
        <td><a href='<c:out value="${currURL}" />' >
        03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td><c:out value="${GrantBean.title}" /></td>
        <td><c:out value="${GrantBean.instName}" /></td>
        <td><c:out value="${GrantBean.fiscalyear}" /></td>
        <td><c:choose>
            <c:when test="${GrantBean.submissionBean.versionSubmitted!=null}">
            Submitted</c:when>
            <c:otherwise>Unsubmitted</c:otherwise>
            </c:choose></td>
      </tr>       
    </c:forEach>
  </table>     

  <br/><br/>
 
  </body>
</html>
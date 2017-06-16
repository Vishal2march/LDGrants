<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>

  <table summary="for layout only">
    <tr>
      <td><a href="welcomePage.jsp">Home</a>|</td>
      <td><a href="lgrmifNav.do?item=homepage&m=lg">LGRMIF Home</a>|</td>
      <td><a href="LgHelp.do">Help</a></td>      
    </tr>
  </table>
  LGRMIF Summary, Guidelines and Instructions: Located on the <a href="LgHelp.do">Help</a> Page
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Creating an Application help</a>
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Accessing an Application help</a>
  
  <br/><br/>

<table width="100%" summary="for layout only">
  <tr>
    <td colspan="2"><b>*<font color="Red">NOTE:</font>
        <c:choose >
        <c:when test="${appDates.canApply=='true'}">
          Create a new LGRMIF application for FY <fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode-1}" />
          -<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
          <a href="lgrmifNav.do?item=createapp&m=lg">Create new application</a>          
        </c:when>
        <c:otherwise >
          You can only create 2 new LGRMIF applications per fiscal year during the new application
          period. 
        </c:otherwise>
        </c:choose> 
    </b></td>
  </tr>
  <tr>
    <td colspan="2" height="15"><hr/></td>
  </tr>
  </table>
  <br/><br/>
      
  <c:if test="${chooseGrant=='true'}" >
    <p align="center" class="error">Please choose a grant application from the links below.</p>
  </c:if>
    
       
   
  <table width="100%" summary="for layout only">
    <tr>
      <th colspan="4">Local Government Records Management Improvement Fund Applications</th>
    </tr>
    <tr>
      <td><b>Project Number</b></td><td><b>Institution</b></td><td><b>Fiscal Year</b></td>
      <td><b>Status</b></td>
    </tr>  
    
    <c:forEach var="GrantBean" items="${allGrants}" >
      <c:url var="currURL" value="lgApplicant.do">
        <c:param name="i" value="checklist"/>
        <c:param name="grantid" value="${GrantBean.grantid}"/>
      </c:url>
      <tr>
        <td><a href='<c:out value="${currURL}" />' >
        05<fmt:formatNumber minIntegerDigits="2"  value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td><c:out value="${GrantBean.instName}" /> -
                           <c:out value="${GrantBean.dorisname}" /></td>
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
  
  
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="4">LGRMIF Applications as a Participating Institution (read only)</th>
    </tr>
    <tr>
      <td><b>Project Number</b></td><td><b>Sponsoring Institution</b></td><td><b>Fiscal Year</b></td>
      <td><b>Status</b></td>
    </tr>  
    
    <c:forEach var="GrantBean" items="${partGrants}" >
      <c:url value="lgParticipantForms.do" var="gotoURL">
          <c:param name="id" value="${GrantBean.grantid}" />
          <c:param name="i" value="coversheet" />
      </c:url>
      <tr>
        <td><a href='<c:out value="${gotoURL}" />'>
        05<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
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
      
      

  </body>
</html>
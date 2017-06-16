<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>  
  <table width="100%" bgcolor="Silver" summary="for layout only">
    <tr>
      <td>
        <a href="welcomePage.jsp">Home</a> | <a href="aliteracyApplication.do?item=homepage&m=a">Adult Literacy Home</a>
      </td>
      <td align="right"><a href="AlHelp.do">Help</a></td>      
    </tr>
  </table>
  <br/>
  
 <table align="center" width="90%" summary="for layout only">
 <tr>
    <TD colspan="2" align="center"><b>New York State Literacy Library Services Programs
        <br/>Adult Literacy Program</b>
    </TD>  
  </tr>
  <tr>
    <td colspan="2">Adult Literacy Announcement, Guidelines, Allocation (opens in new window)
     <a href="http://www.nysl.nysed.gov/libdev/literacy/index.html" target="_blank">Adult Literacy Website</a>
    </td>
  </tr>


  <tr>
    <td colspan="2">
      <c:choose >
      <c:when test="${appDates.canApply=='true'}">
        Create a new Adult Literacy application for FY <fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode-1}" />
        -<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
        <a href="aliteracyApplication.do?item=createapp&m=a" >Create new application</a>       
      
      </c:when>
      <c:otherwise >
        <font color="red">***</font>
        You can only create 1 new Adult Literacy application per 3-year grant cycle during the new application
        period.<br/>
        
        <c:if test="${appDates.PlsInstitution=='false'}">
            <font color="red">***</font>Only Public Library Systems may apply for the Adult Literacy program.
        </c:if>
        
      </c:otherwise>
      </c:choose> 
    </td>
  </tr>
  </table>
  <br/><br/>
      
  <c:if test="${chooseGrant=='true'}" >
    <p align="center" class="error">Please choose an application from the links below.</p>
  </c:if>
    
   
  <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4">Adult Literacy Applications</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Program Cycle</th>
    </tr>  
    <c:forEach var="GrantBean" items="${allGrants}">
      <c:choose>
    	<c:when test="${GrantBean.fycode>16}"><%--SH Note: for 2016-19 apps; new bootstrap layout --%>
	      <c:url var="currURL" value="applicationchecklist.action">
		      <c:param name="fc" value="${GrantBean.fccode}"/>
		      <c:param name="fy" value="${GrantBean.fycode}"/>
		      <c:param name="id" value="${GrantBean.grantid}"/>      
	      </c:url>
	     </c:when>
	     <c:otherwise>	  <%--SH Note: for all old apps; old struts1 format --%>    
	      <c:url var="currURL" value="liInitialForms.do">
	        <c:param name="item" value="checklist"/>
	        <c:param name="id" value="${GrantBean.grantid}"/>
	        <c:param name="p" value="al" />
	      </c:url>
	     </c:otherwise>
	    </c:choose>

      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' onclick='<c:set var="allGrants" value="${allGrants}" scope="session" />' />
        03<fmt:formatNumber value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.title}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center">
            <c:choose>
            <c:when test="${GrantBean.fycode==14}">
              2013-2016
            </c:when>
            <c:when test="${GrantBean.fycode==17}">
              2016-2019
            </c:when>
            <c:otherwise>        
              <c:out value="${GrantBean.fiscalyear}" />
            </c:otherwise>
            </c:choose></td>
      </tr>       
    </c:forEach>

  </table>     
  <br/><br/>
   
      
  <%-- hiding 1/14/16 per KBALSEN
  <table width="90%" align="center" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="4">Adult Literacy Applications as a Participating Library (read only)</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
    </tr>  
    <c:forEach var="GrantBean" items="${partGrants}" >
      <c:url value="liParticipant.do" var="gotoURL">
          <c:param name="id" value="${GrantBean.grantid}" />
          <c:param name="item" value="coversheet" />
      </c:url>
      <tr>
        <td align="center"><a href='<c:out value="${gotoURL}" />'>
        03<fmt:formatNumber value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.title}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>       
    </c:forEach>
  </table>     
  --%>
      
      
  <br/><br/>
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <td>For questions regarding the Adult Literacy program please contact 
       Carol A. Desch: 518-474-7196; carol.desch@nysed.gov</td>
    </tr>  
  </table>
   
  
  
  
  </body>
</html>

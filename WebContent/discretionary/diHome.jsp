<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diHome.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/17/07     Created
 *
 * Description
 * This is the applicant Discretionary home page.  Lists all Di grants as the sponsor, 
 * and all Di grants as a participating institution. Has link to create a new Di app. 
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
  <table summary="for layout only" bgcolor="Silver" width="100%">
    <tr>
      <td>
        <a href="welcomePage.jsp">Home</a> | <a href="discretionaryNav.do?item=homepage&m=di">Discretionary Home</a>
      </td>      
      <td width="40%">&nbsp;&nbsp;&nbsp;</td>
      <td><a href="DiApcntHelp.do">Help</a></td>      
      <td />
      <td><a href="diApcntSearch.do?i=reports">General Reports</a></td>
    </tr>
  </table><br/>
  
 <table align="center" width="90%" summary="for layout only">
 <tr>
    <TD width="20%">
        <IMG height="67" width="80" alt="logo NYS Conservation/Preservation Program" src="images/cplogo2.gif"  align="bottom" border="0"/>
    </TD>
    <TD align="center"><b>The New York State Program for the Conservation and Preservation
        of Library Research Materials<br/>Discretionary Aid Program</b>
    </TD>  
  </tr>
  <tr>
    <td colspan="2">C/P Discretionary Aid 
    <a href="docs/guidelinesDiscretionary.doc" target="_blank">Guidelines and Instructions</a> (Microsoft Word document)
    <%--<a href="docs/guidelinesDiscretionary.htm" target="_blank">HTML</a> --%><br/><br/>
    C/P Discretionary 
    <a href="docs/cpMasterContract.doc" target="_blank">Master Grant Contract terms and conditions</a> (Microsoft Word document)
    <br/><br/>
    <b><i><font color="blue">NEW REQUIREMENT: </font></i></b><a href="http://www.nysl.nysed.gov/libdev/cp/prequal.htm" target="_blank">Prequalification requirement 
                for not-for-profit entities applying for grants</a>
        <br/><br/>
    <b><i><font color="blue">NEW REQUIREMENT: </font></i></b><a href="http://www.nysl.nysed.gov/libdev/cp/mwbe.pdf" target="_blank">M/WBE Participation Goals</a>
         (PDF format)<br/>    
    </td>
  </tr>
  <tr>
    <td colspan="2" height="15" />
  </tr>
  <tr>
    <td colspan="2">
        <c:choose >
          <c:when test="${appDates.canApply=='true'}">
            Create a new Discretionary Aid grant application for FY <fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode-1}" />
            -<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
            <a href="discretionaryNav.do?item=createapp&m=di" >Create new application</a>       
          
          </c:when>
          <c:otherwise >
            You can only create 1 new Discretionary Aid grant application per fiscal year during the new application
            period. 
          </c:otherwise>
          </c:choose> 
    </td>
  </tr>
  </table>
  <br/><br/>
      
  <c:if test="${chooseGrant=='true'}" >
    <p align="center" class="error">Please choose a grant application from the links below.</p>
  </c:if>
    
   
  <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4">Discretionary Grant Applications</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
    </tr>  
    <c:forEach var="GrantBean" items="${allGrants}" >
      <c:url var="currURL" value="diInitialForms.do">
        <c:param name="i" value="checklist"/>
        <c:param name="grantid" value="${GrantBean.grantid}"/>
        <c:param name="m" value="di"/>
      </c:url>
      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' >
        03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.title}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>       
    </c:forEach>
  </table>     
  <br/><br/>
   
      
  <%--removed 2/15/11 -participants don't need access to apcnt grant
  <table width="90%" align="center" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="4">Discretionary Grant Applications as a Participating Library (read only)</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
    </tr>  
    <c:forEach var="GrantBean" items="${partGrants}" >
      <c:url value="diParticipatingForms.do" var="gotoURL">
          <c:param name="id" value="${GrantBean.grantid}" />
          <c:param name="item" value="coversheet" />
      </c:url>
      <tr>
        <td align="center"><a href='<c:out value="${gotoURL}" />'>
        03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.title}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>       
    </c:forEach>
  </table>     --%>
      
  <br/><br/>
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <td>Please contact the Conservation/Preservation Program Administrator, Barbara Lilley, with
      any questions.  518-486-4864  or  barbara.lilley@nysed.gov</td>
    </tr>  
  </table>
  
  
  </body>
</html>

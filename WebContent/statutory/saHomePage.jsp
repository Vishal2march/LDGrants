<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saHomePage.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Statutory Aid home page.  Modified to include links to the current
 * fiscal year application, and links to read only versions of prior year applications.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title></title>
</head>
<body>


<table align="center" width="90%" summary="for layout only">
 <tr>    
    <TD width="30%" align="center">
        <IMG height="89" width="108" alt="logo NYS Conservation/Preservation Program" src="images/cplogo2.gif" align="bottom" border="0"/>
    </TD>
    <TD align="center">
      <H4>The New York State Program for the Conservation and Preservation
        <BR/>of Library Research Materials
        <br/>Statutory Aid
      </H4>
    </TD>       
  </tr>
  <tr>
    <td colspan="2" align="center">
       <c:choose >
       <c:when test="${appDates.canApply=='true'}" >
        No application for the current fiscal year.<br/>
        <a href="statutoryNav.do?item=createapp&m=sa" >Create a new Statutory Aid application</a>
       </c:when>
       <c:otherwise >
         You may only create a new Statutory Aid application during the new application period,<br/> and
         may complete only 1 new application per fiscal year period.  
      </c:otherwise>
      </c:choose>
       <br/><br/>
       <c:if test="${chooseGrant=='true'}" >
         <font color="Red">Choose a grant application from the links below.</font>
       </c:if>
    </td>
  </tr>
</table>  


         
  <table width="80%" align="center" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="4">Statutory Grant Applications</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Institution ID</th><th>Institution</th><th>Fiscal Year</th>
    </tr>
    
    <c:forEach var="GrantBean" items="${allGrants}" >
      <c:url var="currURL" value="saApplicantForms.do">
        <c:param name="item" value="checklist"/>
        <c:param name="grantid" value="${GrantBean.grantid}"/>
        <c:param name="m" value="sa" />
      </c:url>
      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' >
        03<fmt:formatNumber minIntegerDigits="2" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.instID}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>
     </c:forEach>
  </table>        
  
  <br/>
  
 <%--removed 2/1/5/11 -statutory does not have participating grants 
 <table width="80%" align="center" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="4">Grant Applications for prior fiscal years (read only)</th>
    </tr>
    <tr>
      <th>Project Number</th><th>Institution ID</th><th>Institution</th><th>Fiscal Year</th>
    </tr>
    
    <c:forEach var="GrantBean" items="${partGrants}" >
      <tr>
        <c:url value="saPriorForms.do" var="gotoURL">
          <c:param name="id" value="${GrantBean.grantid}" />
          <c:param name="item" value="coversheet" />
        </c:url>
        <td align="center"><a href='<c:out value="${gotoURL}" />'>
        03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td align="center"><c:out value="${GrantBean.instID}" /></td>
        <td align="center"><c:out value="${GrantBean.instName}" /></td>
        <td align="center"><c:out value="${GrantBean.fiscalyear}" /></td>
      </tr>
    </c:forEach>      
  </table>--%>
   
<br/><br/>

</body>
</html>

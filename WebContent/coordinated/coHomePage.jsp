<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coHomePage.jsp
 * Creation/Modification History  :
 *     SHusak       6/1/07     Created
 *
 * Description
 * This is the Coordinated home page. It has links to the institutions Co and participating
 * grants. Also an option that lets you create a new application.
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
    <TD width="20%" align="center">
        <IMG height="89" width="108" alt="logo NYS Conservation/Preservation Program" src="images/cplogo2.gif"  align="bottom" border="0"/>
    </TD>
    <TD align="center">
      <H4>The New York State Program for the Conservation and Preservation
        <BR/>of Library Research Materials
        <br/>Coordinated Aid Program
      </H4>
    </TD>  
  </tr>
</table>

<br/><br/>

    <c:choose >
    <c:when test="${appDates.canApply=='true'}">
      <p align="center">
      Create a new Coordinated Aid grant application for FY <fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode-1}" />
      -<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
      <br/><br/>
      The total amount available for this FY is <fmt:formatNumber value="${totAvail.remainingFund}" type="currency" />
      <br/>
      The total amount requested so far for this FY is <fmt:formatNumber value="${totReq.totAmtReq}" type="currency" />
      <br/>
      <a href="coordinatedNav.do?item=createapp&m=co" >Create new application</a>  
      </p>
    </c:when>
    <c:otherwise >
      <p align="center">
      There are no applications available at this time.  You can only create a new Coordinated Aid
      grant application during the new application period. 
      </p>
    </c:otherwise>
    </c:choose>
   
 
    <c:if test="${chooseGrant=='true'}" >
      <p align="center" class="error">Please choose a grant application from the links below.</p>
    </c:if>
   
              
    <table width="90%" align="center" summary="for layout only" class="boxtype" >
      <tr>
        <th colspan="4">Coordinated Grant Applications</th>
      </tr>
      <tr>
        <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
      </tr>  
      <c:forEach var="GrantBean" items="${allGrants}" >
        <c:url var="currURL" value="coApplicantForms.do">
          <c:param name="item" value="checklist"/>
          <c:param name="grantid" value="${GrantBean.grantid}"/>
          <c:param name="m" value="co" />
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
    <br/>
    
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <th colspan="4">Coordinated Grant Applications as a Participating Library (read only)</th>
      </tr>
      <tr>
        <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
      </tr>  
      <c:forEach var="GrantBean" items="${partGrants}" >
        <c:url value="coParticipantForms.do" var="gotoURL">
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
      </table>     
   
   
<br/><br/>
</body>
</html>

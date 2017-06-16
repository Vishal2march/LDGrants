<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.p=='lg'}">
    <c:set var="fcpre" value="05"/>
  </c:if>
  <c:if test="${param.p=='lit'}">
    <c:set var="fcpre" value="03"/>
  </c:if>
  <c:if test="${param.p=='cp'}">
    <c:set var="fcpre" value="03"/>
  </c:if>
    
  <h4>Search for a Grant</h4>
      

  <table width="50%" summary="for layout only">
    <c:if test="${param.p=='cp'}">
      <tr>
        <td colspan="2">View all Grants for an Institution</td>
      </tr>
      <tr>
        <form method="POST" action="adminSearch.do?item=cpinst">
        <td><select name="searchparam">
            <c:forEach var="SedrefInstBean" items="${allInst}">
              <option value='<c:out value="${SedrefInstBean.instID}" />' >
                <c:out value="${SedrefInstBean.name}" />
              </option>
            </c:forEach>
            </select>          
        </td>
        <td><input type="SUBMIT" value="Search" />
            <input type="HIDDEN" name="progparam" value='<c:out value="${param.p}"/>'/></td>
        </form>
      </tr>
      <tr>
        <td height="30" />
      </tr>
    </c:if>
    
    
    <tr>
      <td colspan="2">Search by Institution Name</td>
    </tr>
    <tr>
      <form method="POST" action="adminSearch.do?item=instname">
      <td><input type="TEXT" name="searchparam" /></td>
      <td><input type="SUBMIT" value="Search" />
          <input type="HIDDEN" name="progparam" value='<c:out value="${param.p}"/>'/> </td>
      </form>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
       
    <tr>
      <td colspan="2">View all Grants for a Fiscal Year</td>
    </tr>
    <tr>
      <form method="POST" action="adminSearch.do?item=fiscalyear">
      <td><select name="searchparam">
            <c:forEach var="row" items="${dropDownList}">
              <option value="<c:out value='${row.id}' />">
                  <c:out value="${row.description}" /></option>
            </c:forEach>
          </select>                
      </td>
      <td><input type="SUBMIT" value="Search" />
          <input type="HIDDEN" name="progparam" value='<c:out value="${param.p}"/>'/></td>
      </form>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
    <tr>
      <td colspan="2">Search by Project Number - Last 4 digits only</td>
    </tr>
    <tr>
      <form method="POST" action="adminSearch.do?item=projectnum">
      <td><input type="TEXT" name="searchparam" /></td>
      <td><input type="SUBMIT" value="Search" />
          <input type="HIDDEN" name="progparam" value='<c:out value="${param.p}"/>'/></td>
      </form>
    </tr>
    
    
    <c:if test="${param.p=='lg'}">
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td colspan="2">View all Grants for a Region/Fiscal Year</td>
    </tr>
    <tr>
      <form method="POST" action="adminSearch.do?item=regionfy">
      <td><select name="searchparam">
            <c:forEach var="row" items="${dropDownList}">
              <option value="<c:out value='${row.id}' />">
                  <c:out value="${row.description}" /></option>
            </c:forEach>
          </select>      
          <select name="regionparam">
            <c:forEach var="row" items="${allRegions}">
              <option value="<c:out value='${row.id}' />">
                  <c:out value="${row.description}" /></option>
            </c:forEach>
          </select>      
      </td>
      <td><input type="SUBMIT" value="Search" />
          <input type="HIDDEN" name="progparam" value='<c:out value="${param.p}"/>'/></td>
      </form>
    </tr>    
    </c:if>
    
  </table>  
  <br/><br/>  
  
  
  
  
  <logic:notEmpty name="allGrants">
  
  <p><b>Grant Search Results</b></p>  
  <table width="90%" summary="for layout only">
    <tr>
      <td><b>Project Number</b></td><td><b>Institution</b></td><td><b>Fiscal Year</b></td>
      <td><b>Grant Program</b></td>
    </tr>
    
    <c:forEach var="grantBean" items="${allGrants}">
      <tr>
        <td>
        
        <c:choose >
        <c:when test="${grantBean.fccode==80}">
          <c:url value="lgAdminNav.do" var="grantURL">
            <c:param name="id" value="${grantBean.grantid}" />
            <c:param name="item" value="grant" />
          </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==5}">
            <c:url value="diAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==6}" >
            <c:url value="saAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==20}" >
            <c:url value="staidAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==7}" >
            <c:url value="coAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==40}">
            <c:url value="alAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        <c:when test="${grantBean.fccode==42}">
            <c:url value="flAdminNav.do" var="grantURL">
              <c:param name="id" value="${grantBean.grantid}" />
              <c:param name="item" value="grant" />
            </c:url>
        </c:when>
        </c:choose>
        
        
        <a href='<c:out value="${grantURL}" />' >
        <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${grantBean.fccode}" />
              -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </a>
        </td>
        <td><c:out value="${grantBean.instName}" /></td>
        <td><c:out value="${grantBean.fiscalyear}" /></td>
        <td><c:out value="${grantBean.program}" /></td>
      </tr>
    </c:forEach>  
    
  </table>  
  </logic:notEmpty>
  <br/><br/><br/>
  
  
  </body>
</html>

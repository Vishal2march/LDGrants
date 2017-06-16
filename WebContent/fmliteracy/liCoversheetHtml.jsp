<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Literacy Library Services Cover Page</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <h5 align="center"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if> Literacy Library Services
      <br/>The University of the State of New York 
      <br/>The State Education Department 
      <br/>Division of Library Development 
  </h5> 
      
    <table width="95%" align="center" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Cover Page</th>
      </tr>
      <tr>
        <td>Project Title:</td>
        <td><c:out value="${coversheetBean.projectTitle}" /></td>
      </tr>
      <tr>
        <td>Project Number</td>
        <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber  minIntegerDigits="4" pattern="####" value="${thisGrant.projseqnum}" /></td>
      </tr> 
      
      <c:choose>
      <c:when test="${thisGrant.fycode>13}">
          <tr>
            <td width="30%">Public Library System Applying for Grant:</td>
            <td width="70%"><c:out value="${thisGrant.instName}" /></td>
          </tr>
      </c:when>
      <c:otherwise>
          <tr>
            <td width="30%">Sponsoring Institution:</td>
            <td width="70%"><c:out value="${thisGrant.instName}" /></td>
          </tr>
      </c:otherwise>
      </c:choose>
      
      <tr>
        <td>Mailing Address:</td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
      <tr>
        <td>Address:</td>
        <td><c:out value="${thisGrant.addr2}" /></td>
      </tr>
      <tr>
        <td >City, State, Zip:</td>
        <td ><c:out value="${thisGrant.city}" />
            <c:out value="${thisGrant.state}" />
            <c:out value="${thisGrant.zipcd1}" />  
            <c:out value="${thisGrant.zipcd2}" /></td>
      </tr>
      <tr>
        <td>County:</td>
        <td><c:out value="${thisGrant.county}" /></td>
      </tr>
      <tr>
        <td>Director:</td>
        <td><c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
          <c:out value="${libDirectorBean.lname}" />
        </td>
      </tr>
      <tr>
        <td>Director Contact Information:</td>
        <td><c:out value="${libDirectorBean.email}" />    <c:out value="${libDirectorBean.phone}" /></td>
      </tr>
      <tr>
        <td>State Assembly Districts: <c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                                        <c:out value="${dist}" />
                                     </c:forEach></td>
        <td>State Senate Districts: <c:forEach var="sdist" items="${distBean.senateDistricts}" >
                                      <c:out value="${sdist}" />
                                   </c:forEach></td>
      </tr>
      
      <c:choose>
      <c:when test="${thisGrant.fycode==14}">
          <%--only for projects in 2013-14 per KBALSEN --%>
          <c:forEach var="row" items="${allAlloc}">
            <tr>
             <td>Allocation for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
             <td><fmt:formatNumber value="${row.initialAlloc}" type="currency"  /></td>
            </tr>
          </c:forEach>
      </c:when>
      <c:when test="${thisGrant.fycode>16}">
          <%--for projects in 2016-17 (3 year grants);  added on 1/15/16 per KBALSEN --%>
          <c:forEach var="row" items="${allAlloc}">
            <tr>
             <td>Ed Law Amount for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
             <td><fmt:formatNumber value="${row.initialAlloc}" type="currency"  /></td>
            </tr>
          </c:forEach>
      </c:when>
      </c:choose>
    </table>
    <br/><br/>
     
  
    <table border="1" width="95%" align="center" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager (is someone who has expertise in the area of this program)</th>
      </tr>          
      <tr>
        <td width="30%">Name</td>
        <td width="70%"><c:out value="${coversheetBean.fname}" />  
          <c:out value="${coversheetBean.lname}" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${coversheetBean.title}" /></td>
      </tr>        
      <tr>
        <td>Phone</td>
        <td><c:out value="${coversheetBean.phone}" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Manager email address listed will receive notifications regarding the
        application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${coversheetBean.email}" /></td>
      </tr>      
      <tr>
        <td height="20" colspan="2"/>
      </tr>
      <tr>
        <th colspan="2" bgcolor="Silver">Additional Contact Person (preferably Director or other responsible party)</th>
      </tr>          
      <tr>
        <td width="30%">Name</td>
        <td width="70%"><c:out value="${coversheetBean.rmofname}" />  
          <c:out value="${coversheetBean.rmolname}" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${coversheetBean.rmotitle}" /></td>
      </tr>        
      <tr>
        <td>Phone</td>
        <td><c:out value="${coversheetBean.rmophone}" /> <c:out value="${coversheetBean.rmophoneext}" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${coversheetBean.rmoemail}" /></td>
      </tr>
      <tr>
        <td height="20"/>
     </tr>
     
        
        
      <c:choose>
      <c:when test="${thisGrant.fycode==17}">
          <c:if test="${thisGrant.fccode==40}">
            <%--new per kbalsen 2/2/16; partnering orgs only for adult lit starting 2016-19--%>
            <tr>
              <th colspan="2" bgcolor="Silver">Coordination Partnering Organization</th>
            </tr>  
            <tr>   
              <td colspan="2"><bean:write name="projNarrative" property="narrative" filter="false" /></td>
            </tr>      
          </c:if>
      </c:when>      
      <c:when test="${thisGrant.fycode>13}">
          <tr>
            <th colspan="2" bgcolor="Silver">Coordination Partnering Organization</th>
          </tr>  
          <tr>   
            <td colspan="2"><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
      </c:when>
      <c:otherwise>   
          <tr>
            <th colspan="2" bgcolor="Silver">Participating Organizations</th>
          </tr>  
          <c:forEach var="partInstBean" items="${allPartInst}">
            <tr>
              <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
            </tr>
          </c:forEach>  
      </c:otherwise>
      </c:choose>
      
    </table>  
    <br/>
        
  </body>
</html>

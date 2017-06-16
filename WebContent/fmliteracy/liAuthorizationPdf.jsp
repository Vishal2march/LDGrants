<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>

<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Cover Page Authorization Form<br/>Requires System Director Authorization</th>
    </tr>
    <tr>
      <td colspan="2" align="center"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if> 
      Literacy Library Services<br/>A New York State Library Grant Program</td>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td><b>Public Library System Applying for Grant:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Address:</b></td>
      <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.city}" />
          <c:out value="${thisGrant.state}" /><c:out value="${thisGrant.zipcd1}" />  
          <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>County:</b></td>
      <td><c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>State Assembly Districts:</b> <c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                                        <c:out value="${dist}" /> </c:forEach></td>
      <td><b>State Senate Districts:</b> <c:forEach var="sdist" items="${distBean.senateDistricts}" >
                                      <c:out value="${sdist}" /> </c:forEach></td>
    </tr>  
    <tr>
        <td><b>State Congressional Districts:</b> <c:forEach var="cdist" items="${distBean.congressDistricts}" >
                                            <c:out value="${cdist}" />
                                          </c:forEach></td>
        <td><b>State Judicial District:</b> <c:out value="${distBean.judicialDistricts}" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>Project Title:</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager:</b></td>
      <td><c:out value="${coversheetBean.fname}" /> <c:out value="${coversheetBean.lname}" /></td>
    </tr>          
    <tr>
      <td><b>Project Manager Phone:</b></td>
      <td><c:out value="${coversheetBean.phone}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager Email:</b></td>
      <td><c:out value="${coversheetBean.email}" /></td>
    </tr>
    <tr>
      <td><b>Director:</b> <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
        <c:out value="${libDirectorBean.lname}" /></td>
      <td><b>Director Email:</b> <c:out value="${libDirectorBean.email}" /></td>
    </tr>    
    <tr>
        <td height="20"/>
    </tr>
    <c:forEach var="row" items="${allsums}">
      <tr>
        <td><b>Allocation for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></b></td>
        <td><fmt:formatNumber value="${row.totAmtReq}" type="currency"  /></td>
      </tr>
    </c:forEach>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td colspan="2"><b>Participating Institutions:</b></td>
    </tr>          
    <c:forEach var="partInstBean" items="${allParts}">
      <tr>
        <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
      </tr>
    </c:forEach>  
    
    
    <tr>
      <td height="50" />
    </tr>
    <tr>
      <td width="45%" valign="bottom">Date:<hr/></td>
      <td valign="bottom" height="40">System Director's Signature<br/>(blue ink):<hr/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>System Director's Name:<br/>(typed or printed)</td>
      <td valign="bottom" height="40"><hr/></td>
    </tr>    
  </table>
  </font>
    
  <br/>
  <font size="1">
  <br/><br/>
  <p><b>Instructions</b><br/>
    Three (3) copies of the Cover Page Authorization Form must be printed and signed in blue ink by the Library System Director.
    <br/><br/>
    Mail the signed form to:<br/>
    Division of Library Development, NYS Library<br/>
    Cultural Education Center, Room 10B41<br/>
    222 Madison Ave<br/>
    Albany NY 12230<br/>
    Attn: Carol A. Desch
  </p>  </font>  
  
  </body>
</html>
</pd4ml:transform>
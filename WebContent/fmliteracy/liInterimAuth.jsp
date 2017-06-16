<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Library System Director Interim Report Authorization</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2" align="center"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if>  Literacy Library Services<br/>
      A New York State Library Grant Program</th>
    </tr>
    <tr>
      <td colspan="2" height="10" />
    </tr>
    <tr>
      <td><b>Sponsoring Institution:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Address:</b></td>
      <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.city}" />
          <c:out value="${thisGrant.state}" /><c:out value="${thisGrant.zipcd1}" />  
          <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>Director of Institution:</b></td>
      <td><c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
        <c:out value="${libDirectorBean.lname}" />
      </td>
    </tr>
    <tr>
      <td><b>Project Title:</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager:</b></td>
      <td><c:out value="${projManagerBean.fname}" /> <c:out value="${projManagerBean.lname}" /></td>
    </tr>          
    <tr>
      <td><b>Project Manager Phone:</b></td>
      <td><c:out value="${projManagerBean.phone}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager Email:</b></td>
      <td><c:out value="${projManagerBean.email}" /></td>
    </tr>
    
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Date:<hr/></td>
      <td valign="bottom" height="40">Library System Director's Signature<br/>(blue ink):<hr/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Library System Director's Name:<br/>(please print)</td>
      <td valign="bottom" height="60"><hr/></td>
    </tr>    
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Phone:<hr/></td>
      <td>Email:<hr/></td>
    </tr>
  </table>
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
    The Interim Authorization Form must be printed and signed in blue ink by the Library or System Director.
    <br/><br/>
    Mail the signed form to<br/>
      Division of Library Development<br/>
      NYS Library<br/>
      Cultural Education Center<br/>
      Room 10B41<br/>
      Albany, NY 12230<br/>
      Attn: Carol A. Desch
  </p>
  
  </body>
</html>

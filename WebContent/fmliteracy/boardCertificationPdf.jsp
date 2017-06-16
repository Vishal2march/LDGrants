<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
      <th colspan="2">Certification by Library System Board of Trustees</th>
    </tr>
    <tr>
      <td colspan="2" align="center"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if>  Literacy Library Services<br/>
      A New York State Library Grant Program</td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Sponsoring Institution:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title:</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2">I hereby certify that the Board of Trustees of
      <u><c:out value="${thisGrant.instName}" /></u>
      
      <br/>Located in <u><c:out value="${thisGrant.city}" /></u>
      , New York, voted to approve this Application on 
      _________________________   (Date)</td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Date:<hr/></td>
      <td valign="bottom" height="40">Board President's Signature (blue ink):<hr/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Board President's Name<br/>(please print)</td>
      <td valign="bottom" height="60"><hr/></td>
    </tr>    
  </table>
  </font>
  
  
  <br/>
  <font size="1">
  <p><b>Instructions</b><br/>
    Three (3) copies of the Board Certification Form must be printed and signed in blue ink by the Board President.
    <br/><br/>    
   Mail the signed form to:<br/>
    Division of Library Development, NYS Library<br/>
    Cultural Education Center, Room 10B41<br/>
    222 Madison Ave<br/>
    Albany NY 12230<br/>
    Attn: Carol A. Desch
  </p>
  </font>
  
  </body>
</html>

</pd4ml:transform>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Final Report Authorization Form</h4>
  
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
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  minIntegerDigits="4" pattern="####" value="${thisGrant.projseqnum}" /></td>
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
      <td><b>Certification:</b></td>
      <td>I hereby certify that this is a true and accurate project report and has been 
      approved by the board of trustees.</td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
    <c:choose>
    <c:when test="${thisGrant.fycode<14}"><%--old method prior to fy2013-14--%>
        <tr>
          <td width="30%" valign="bottom">Date:<hr/></td>
          <td valign="bottom" height="40">Library/System Director's Signature<br/>
          (blue ink):<hr/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        <tr>
          <td>Library/System Director's Name:<br/>(please print)</td>
          <td valign="bottom" height="60"><hr/></td>
        </tr>  
    </c:when>
    <c:otherwise><%--starting fy2013-14--%>
        <tr>
          <td width="30%" valign="bottom">Date:<hr/></td>
          <td valign="bottom" height="40">System Director's Signature<br/>
          (blue ink):<hr/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        <tr>
          <td>System Director's Name:<br/>(please print)</td>
          <td valign="bottom" height="60"><hr/></td>
        </tr>  
    </c:otherwise>
    </c:choose>
  </table>  
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
  
  <c:choose>
  <c:when test="${thisGrant.fycode<14}"><%--old method prior to fy2013-14--%>
    The Final Report Authorization Form must be printed and signed by the     
    Library or System Director.
    Then scan the signed form and upload the form to your application as 
    an attachment. 
    <br/><br/>  
    If you do not have access to a scanner, mail the signed form to:<br/>
      Division of Library Development<br/>
      NYS Library<br/>
      Cultural Education Center<br/>
      Room 10B41<br/>
      Albany, NY 12230<br/>
      Attn: Carol A. Desch
   </c:when>
   <c:otherwise><%--starting fy2013-14--%>
        The Final Report Authorization Form must be printed and signed by the     
        System Director.
        Then scan the signed form and upload the form to your application as 
        an attachment. 
        <br/><br/>  
        If you do not have access to a scanner, mail the signed form to:<br/>
        Division of Library Development<br/>
        NYS Library<br/>
        Cultural Education Center<br/>
        Room 10B41<br/>
        Albany, NY 12230<br/>
        Attn: Carol A. Desch 
   </c:otherwise>
   </c:choose>      
  </p>
  
  
  </body>
</html>

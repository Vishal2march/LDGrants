<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diCoopAgreementPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/21/07     Created
 *
 * Description
 * This page is pdf view of Di applicant cooperative agreement form with instructions. 
--%>
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
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Discretionary Grant Cooperative Application Agreement Form</th>
    </tr>
    <tr>
      <td colspan="2" align="center">New York State Program for the Conservation and Preservation
      of Library Research Materials</td>
    </tr>
    <tr>
      <td height="20" />
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
      <td colspan="2">I hereby give assurance to the New York State Education Department that the undersigned 
      supports the enclosed application and will cooperate to the extent described in the attached 
      application.  All materials whose preservation is supported by funds from the State are, 
      or will be, made available for reference, on-site examination, and/or loan. </td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Participating Institution:<hr/></td>
      <td valign="bottom" height="40"><hr/></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Date:<hr/></td>
      <td valign="bottom" height="40">Signed:<hr/></td>
    </tr>
    <tr>
      <th width="30%">Library/Archives Director</th>
      <td valign="bottom" height="60">Type Name:<hr/></td>
    </tr>    
  </table>
  
  
  <br/><br/>
  <p>Instructions<br/>The Cooperative Agreement Form must be printed and signed by each participating institution of your project.
    Then scan the signed form and upload the form to your application as an attachment.  
    Please put "Co Agreement" as the description for your attachment.  
  </p>
  </font>
  
  </body>
</html>

</pd4ml:transform>

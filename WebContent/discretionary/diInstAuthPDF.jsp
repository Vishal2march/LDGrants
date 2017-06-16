<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diInstAuthPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/18/07     Created
 *
 * Description
 * This is pdf view of Di inst auth form with instructions.  
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Discretionary Aid Institutional Authorization</title>
  </head>
  <body>
  
  <font size="1">
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="3">Institutional Authorization <br/>
                      Conservation/Preservation Discretionary Grant Project</th>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="3">I hereby certify that I am the applicant's chief administrative officer and that the information contained in this application is, to the best of my knowledge, complete and accurate.   I further certify, to the best of my knowledge, that any ensuing program and activity will be conducted in accordance with all applicable Federal and State laws and regulations, application guidelines and instructions, Assurances, Certifications, the Master Grant Contract terms and conditions, and that the requested budget amounts are necessary for the implementation of this project.  All materials whose preservation is supported by funds from the State are, or will be, made available for reference, on-site examination and/or loan.  It is understood by the applicant that this application constitutes an offer and, if accepted by the NYS Education Department or renegotiated to acceptance, will form a binding agreement.  It is also understood by the applicant that immediate written notice will be provided to the grant program office if at any time the applicant learns that its certification was erroneous when submitted or has become erroneous by reason of changed circumstances.  </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="3">Project Title: <c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td colspan="3">President of Applicant Institution</td>
    </tr>
    <tr>
      <td colspan="3" valign="bottom" height="40" ><hr/></td>
    </tr>
    <tr>
      <td>signed</td>
      <td>type name</td>
      <td>date</td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="3">Director of Library/Archives</td>
    </tr>
    <tr>
      <td colspan="3" valign="bottom" height="40" ><hr/></td>
    </tr>
    <tr>
      <td>signed</td>
      <td>type name</td>
      <td>date</td>
    </tr>   
  </table>
  
  <br/><br/>
  <p>The Authorization Form must be printed and signed.  Then scan the signed form and upload the form
    to your application as an attachment.  Please put Institutional Authorization as the description
    for your uploaded attachment.</p>
  
  </font>
  
  
  </body>
</html>
</pd4ml:transform>
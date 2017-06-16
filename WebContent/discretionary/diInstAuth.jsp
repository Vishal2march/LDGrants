<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diInstAuth.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/18/07     Created
 *
 * Description
 * This is html view of Di apcnt inst auth form.  It has instructions to print and scan
 * and a link to pdf version of form. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Institutional Authorization</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="3">Conservation/Preservation Discretionary Grant Project</th>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="3">I hereby certify that I am the applicant’s chief administrative officer and that the information contained in this application is, to the best of my knowledge, complete and accurate.   I further certify, to the best of my knowledge, that any ensuing program and activity will be conducted in accordance with all applicable Federal and State laws and regulations, application guidelines and instructions, Assurances, Certifications, the Master Grant Contract terms and conditions, and that the requested budget amounts are necessary for the implementation of this project.  All materials whose preservation is supported by funds from the State are, or will be, made available for reference, on-site examination and/or loan.  It is understood by the applicant that this application constitutes an offer and, if accepted by the NYS Education Department or renegotiated to acceptance, will form a binding agreement.  It is also understood by the applicant that immediate written notice will be provided to the grant program office if at any time the applicant learns that its certification was erroneous when submitted or has become erroneous by reason of changed circumstances.  </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="3"><b>Project Title:</b> <c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td colspan="3"><b>President of Applicant Institution</b></td>
    </tr>
    <tr>
      <td colspan="3" valign="bottom" height="50"><hr/></td>
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
      <td colspan="3"><b>Director of Library/Archives</b></td>
    </tr>
    <tr>
      <td colspan="3" valign="bottom" height="50" ><hr/></td>
    </tr>
    <tr>
      <td>signed</td>
      <td>type name</td>
      <td>date</td>
    </tr>   
  </table>
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
  The Authorization Form must be printed and signed.  Then scan the signed form and upload the form
  to your application as an attachment. <a href="diInitialForms.do?i=attachment&m=di">Attach</a>  the form as a document/attachment to your
  grant application.  Please put Institutional Authorization as the description
  for your attachment.<br/>
  View <a href="ApcntFunctionsServlet?i=diauth" target="_blank">PDF</a> version of Institutional Authorization Form
  </p>
  
  
  </body>
</html>

<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminHelpIndex.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       7/2/07     Created
 *
 * Description
 * This page allows the admin to view both applicant and admin help manuals for SA and 
 * CO grants. It has the Guidelines document for both SA and CO
 * in MS Word format. 
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Admin Help Index</title>
  </head>
  <body> 
  
  <h4>Help Using the Division of Library Development Online Grant System</h4>
    
  <table align="center" width="70%" border="1" class="graygrid" summary="for layout only">    
    <tr>
      <th colspan="2">All Help Manuals will open in a new window.</th>
    </tr>
    <tr>
      <td colspan="2">If the MS Word file is too large to open, then right click on the link and
      choose Save Target As.  This will save the file to your PC, and then click Open to view
      the file. </td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Administrative tasks Help Manual<br/>
        Common to C/P Statutory, Coordinated, Discretionary</td>
      <td><a href="docs/admintasks.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/admintasks.htm" target="_blank">HTML</a>
      </td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>C/P Statutory/Coordinated Aid <b>Applicant</b> User manual</td>
      <td>
        <a href="docs/cpapplicantdoc.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/cpapplicantdoc.htm" target="_blank">HTML</a>
      </td>
    </tr>
    <tr>
      <td>View blank <b>Statutory</b> application</td>
      <td><a href="statutory/saBlankForm.jsp" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Statutory Aid </td>
      <td><a href="docs/guideStatutory.pdf" target="_blank">Guidelines and Instructions</a> (PDF)</td>
    </tr>
    
    <%-- program ended<tr>
      <td>View blank Coordinated application</td>
      <td><a href="coordinated/coBlankForm.jsp" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Coordinated Aid Guidelines and Instructions</td>
      <td><a href="docs/guideCoordinated.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/guideCoordinated.htm" target="_blank">HTML</a></td>
    </tr>  --%>
    
    <tr>
      <td height="40" colspan="2" />
    </tr>
    <tr>
      <td width="80%">Discretionary Aid - Online Grant System <b>Applicant</b> User manual</td>
      <td><a href="docs/diapplicantdoc.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/diapplicantdoc.htm" target="_blank">HTML</a></td>
    </tr>  
    <tr>
      <td>View blank Discretionary application</td>
      <td><a href="discretionary/diBlankForm.jsp" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Discretionary Aid Guidelines and Instructions</td>
      <td><a href="docs/guidelinesDiscretionary.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/guidelinesDiscretionary.htm" target="_blank">HTML</a></td>
    </tr>
 </table> 
  
  <br/><br/><br/><br/>
  </body>
</html>

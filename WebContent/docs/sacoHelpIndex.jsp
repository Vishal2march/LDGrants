<%--
 * @author  Stefanie Husak
 * @version 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  sacoHelpIndex.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       7/1/07     Created
 *
 * Description
 * This page allows the applicant to view the SA/CO/Di applicant user manual in both HTML
 * and pdf formats. Also links to Guidelines MS word doc for SA/Co/Di. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>NYSL Grants Programs - Help Index</title>
  </head>
  <body>
  
 
<br/><br/>
<table align="center" width="90%" border="1" class="graygrid" summary="for layout only">
  <tr>
    <th colspan="2">Help Using the Division of Library Development Online System</th>
  </tr>  
  <tr>
    <td colspan="2">All Help Manuals will open in a new window.  If a MS Word file is too large to open, then right click on the link and
      choose Save Target As.  This will save the file to your PC, and then click Open to view
      the file. </td>
  </tr>
  
  <c:choose >
  <c:when test="${param.p=='di'}" >
  
  <%--display DI help pages --%>
  <tr>
    <td width="50%">C/P Discretionary Aid - Online Grant System Applicant User manual</td>
    <td><a href="docs/diapplicantdoc.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/diapplicantdoc.htm" target="_blank">HTML</a>
    </td>
  </tr>  
  <tr>
    <td width="50%">C/P Discretionary Aid Guidelines and Instructions</td>
    <td><a href="docs/guidelinesDiscretionary.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/guidelinesDiscretionary.htm" target="_blank">HTML</a></td>
  </tr>
  <tr>
    <td width="50%">C/P Discretionary - Master Grant Contract terms and conditions</td>
    <td><a href="docs/cpMasterContract.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/cpMasterContract.htm" target="_blank">HTML</a></td>
  </tr>
  <tr>
    <td>C/P Discretionary Rating Form</td>
    <td><a href="docs/discretionaryReviewForm.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/discretionaryReviewForm.htm" target="_blank">HTML</a></td>
  </tr>  
  
  <tr>
    <td><b><i>NEW: </i></b>Prequalification requirement 
                for not-for-profit entities applying for grants</td>
    <td><a href="http://www.nysl.nysed.gov/libdev/cp/prequal.htm" target="_blank">LD Website</a></td>
  </tr>  
  </c:when>
  
  
  
  
  <c:when test="${param.p=='saco'}">
  <%-- display SA and CO help pages --%>  
  <tr>
    <td width="50%">C/P Statutory/Coordinated Aid - Online Grant System Applicant User manual</td>
    <td><a href="docs/cpapplicantdoc.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/cpapplicantdoc.htm" target="_blank">HTML</a>
    </td>
  </tr>  
  <tr>
    <td width="50%">Statutory Aid </td>
    <td><a href="docs/guideStatutory.pdf" target="_blank">Guidelines and Instructions</a> (PDF)</td>
  </tr>
  
  <%--program ended<tr>
    <td width="50%">Coordinated Aid Guidelines and Instructions</td>
    <td><a href="docs/guideCoordinated.doc" target="_blank">Microsoft Word</a><br/>
        <a href="docs/guideCoordinated.htm" target="_blank">HTML</a></td>
  </tr>  --%>
  </c:when>
  
  
  <c:when test="${param.p=='lit'}">
  <%-- display FL and AL help pages --%>       
      <tr>
        <td width="50%">Announcement & Guidelines on Literacy Website</td>
        <td><a href="http://www.nysl.nysed.gov/libdev/literacy/index.html" target="_blank">Adult Literacy</a><br/>
            <a href="http://www.nysl.nysed.gov/libdev/familylit/index.html" target="_blank">Family Literacy</a>
        </td>
      </tr>  
      <tr>
        <td width="50%">2016-19 Allocation Table on Literacy Website</td>
        <td><a href="http://www.nysl.nysed.gov/libdev/literacy/allocation19.htm" target="_blank">Adult Literacy</a><br/>
            <a href="http://www.nysl.nysed.gov/libdev/familylit/allocation19.htm" target="_blank">Family Literacy</a>
        </td>
      </tr>  
      <tr>
        <td width="50%">2013-16 Allocation Table on Literacy Website</td>
        <td><a href="http://www.nysl.nysed.gov/libdev/literacy/allocation.htm" target="_blank">Adult Literacy</a><br/>
            <a href="http://www.nysl.nysed.gov/libdev/familylit/allocation.htm" target="_blank">Family Literacy</a>
        </td>
      </tr>  
      
      <%--2/18/16 new manual per KB --%>
      <tr>
        <td width="50%">Library Services Program Applicant User Manual</td>
        <td><a href="http://www.nysl.nysed.gov/libdev/grants/user-manual.pdf" target="_blank">Completing an Application </a></td>
      </tr>  
      
      <%--hide 1/15/16 per KB
      <tr>
        <td width="50%">Online Grant System User manual</td>
        <td><a href="docs/litapplicantdoc.doc" target="_blank">MS Word</a><br/>
            <a href="docs/litapplicantdoc.htm" target="_blank">HTML</a>
        </td>
      </tr>  
      <tr>
        <td>Sample Literacy Application Data Entry Items</td>
        <td><a href="fmliteracy/litApplication.jsp" target="_blank">HTML</a></td>
      </tr>    --%>    
  </c:when>
  </c:choose>
  
</table>        
<br/><br/><br/><br/>

  </body>
</html>

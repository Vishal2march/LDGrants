<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  reviewerHelpIndex.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       8/20/07     Created
 *
 * Description
 * This page allows the reviewer to view a user manual for CO reviewers in HTML and PDF 
 * formats. Also has Co grant guidelines. 
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Help using the Division of Library Development Online Grant System</h4>
  <c:choose >
  <c:when test="${param.m=='di'}" >
    
    <table align="center" width="60%" border="1" class="graygrid" summary="for layout only">    
    <tr>
      <th colspan="2">All Help Manuals will open in a new window.</th>
    </tr>
    <tr>
      <td colspan="2">If the PDF file is too large to open, then right click on the link and
      choose Save Target As.  This will save the file to your PC, and then click Open to view
      the file. </td>
    </tr>
    <tr>
      <td>Discretionary Aid <b>Reviewer</b> User Manual for Online Grant System</td>
      <td><a href="docs/reviewerinfo.doc" target="_blank">Microsoft Word</a><br/>
      <a href="docs/reviewerinfo.htm" target="_blank">HTML</a>          
      </td>
    </tr>       
    <tr>
      <td>Guidelines for C/P Discretionary Grant Program</td>
      <td><a href="docs/guidelinesDiscretionary.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/guidelinesDiscretionary.htm" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Discretionary Rating Form</td>
      <td><a href="docs/discretionaryReviewForm.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/discretionaryReviewForm.htm" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Memo to Reviewers</td>
      <td><a href="docs/diReviewerMemo.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/diReviewerMemo.htm" target="_blank">HTML</a></td>
    </tr>      
 </table>   
  
  
  
  </c:when>
  <c:otherwise >
  
  
  <table align="center" width="60%" border="1" class="graygrid" summary="for layout only">    
    <tr>
      <th colspan="2">All Help Manuals will open in a new window.</th>
    </tr>
    <tr>
      <td colspan="2">If the MS Word file is too large to open, then right click on the link and
      choose Save Target As.  This will save the file to your PC, and then click Open to view
      the file. </td>
    </tr>
    <tr>
      <td>Coordinated Aid <b>Reviewer</b> User Manual for Online Grant System</td>
      <td><a href="docs/reviewerinfo.doc" target="_blank">Microsoft Word</a><br/>
      <a href="docs/reviewerinfo.htm" target="_blank">HTML</a>          
      </td>
    </tr>       
    <%--<tr>
      <td>Guidelines for C/P Coordinated Grant Program</td>
      <td><a href="docs/guideCoordinated.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/guideCoordinated.htm" target="_blank">HTML</a></td>
    </tr>  --%>
    <tr>
      <td>Coordinated Rating Form</td>
      <td><a href="docs/coordinatedRatingForm.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/coordinatedRatingForm.htm" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td>Memo to Reviewers</td>
      <td><a href="docs/reviewerMemo.doc" target="_blank">Microsoft Word</a><br/>
          <a href="docs/reviewerMemo.htm" target="_blank">HTML</a></td>
    </tr>      
 </table>   
 
 
 </c:otherwise>
 </c:choose>
 
 
  </body>
</html>

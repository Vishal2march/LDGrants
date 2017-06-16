<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revHome.jsp
 * Creation/Modification History  :
 *
 *     SH       7/30/07     Created
 *
 * Description
 * This is the home page for CO reviewers. Lists brief instructions, and links
 * to CO guidelines and rating sheet.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  <br/><br/>
  
  
  <c:choose >
  <c:when test="${lduser.reviewerfound=='false'}" >
    
    <table align="center" summary="for layout only">
      <tr>
        <td>Your username could not be found as a reviewer for the Conservation and Preservation Grant
         Program.  Please contact Division of Library Development to update your account username as
         a reviewer for the Conservation/Preservation Program.
         </td>
      </tr>    
    </table>
    
  </c:when>
  <c:otherwise >
  
    <table align="center" width="90%" summary="for layout only">
      <tr>
        <th>Welcome to the Conservation and Preservation Grant Program Review</th>
      </tr>
      <tr>
        <td>Please use the link 'Participation' to indicate how many grant proposals
        you are willing to review for this fiscal year. Also, be sure to update your contact
        information. </td>
      </tr>
      <tr>
        <td>The link for 'Reviewer Assignments' will list all grant proposals that you have
        been assigned to review.</td>
      </tr>
      <tr>
        <td height="10" ><hr/></td>
      </tr>
      <tr>
        <td>The links below contain the Guidelines and Rating Form for the Conservation/Preservation  
        Grant Program, available for download.  Please remember that your Rating Form
        and Comments will be submitted electronically using the links on the 'Reviewer Assignments' page.
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      
      <c:if test="${param.p=='co'}">
          <tr>
            <td>
              <table width="60%" border="1" class="graygrid" summary="for layout only">
                <tr>
                  <td colspan="2">The following links open in a new window</td>
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
            </td>
          </tr>
      </c:if>
          
      
      <c:if test="${param.p=='di'}">      
          <tr>
            <td>
              <table width="60%" border="1" class="graygrid" summary="for layout only">
                <tr>
                  <td colspan="2">The following links open in a new window</td>
                </tr>
                <tr>
                  <td>Guidelines for C/P Discretionary Grant Program</td>
                  <td><a href="docs/guidelinesDiscretionary.doc" target="_blank">Microsoft Word</a><br/>
                      <a href="docs/guidelinesDiscretionary.htm" target="_blank">HTML</a></td>
                </tr>
                <tr>
                  <td>Master Grant Contract terms and conditions</td>
                  <td><a href="docs/cpMasterContract.doc" target="_blank">Microsoft Word</a><br/>
                      <a href="docs/cpMasterContract.htm" target="_blank">HTML</a></td>
                </tr>
                <tr>
                  <td>C/P Discretionary Rating Form</td>
                  <td><a href="docs/discretionaryReviewForm.doc" target="_blank">Microsoft Word</a><br/>
                      <a href="docs/discretionaryReviewForm.htm" target="_blank">HTML</a></td>
                </tr>
                <tr>
                  <td>Memo to Reviewers</td>
                  <td><a href="docs/diReviewerMemo.doc" target="_blank">Microsoft Word</a><br/>
                      <a href="docs/diReviewerMemo.htm" target="_blank">HTML</a></td>
                </tr>     
              </table>
            </td>
          </tr>      
      </c:if>
      
    </table>
  
  </c:otherwise>
  </c:choose>
  
  
  </body>
</html>

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
        <td>Your username could not be found as a reviewer for the Literacy Library Services
         Program.  Please contact Division of Library Development to update your account username as
         a reviewer for the Literacy Library Services Program.
         </td>
      </tr>    
    </table>
    
  </c:when>
  <c:otherwise >
  
    <table align="center" width="90%" summary="for layout only">
      <tr>
        <th>Welcome to the Literacy Library Services Program Review</th>
      </tr>
      <tr>
        <td>The link for 'Reviewer Assignments' will list all grant proposals that you have
        been assigned to review.</td>
      </tr>
      <tr>
        <td height="10"><hr/></td>
      </tr>
      <tr>
        <td>The links below contain the Guidelines and Rating Form for the Literacy Library Services Program, 
        available for download.  Please remember that your Adult/Family Literacy Rating Form
        and Comments will be submitted electronically using the links on the 'Reviewer Assignments' page.
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>
          <table width="60%" border="1" summary="for layout only">
            <tr>
              <td colspan="2">The following links open in a new window</td>
            </tr>
            <tr>
              <td>Announcement & Guidelines on Literacy Website</td>
              <td><a href="http://www.nysl.nysed.gov/libdev/literacy/index.html" target="_blank">Adult Literacy</a><br/>
                  <a href="http://www.nysl.nysed.gov/libdev/familylit/index.html" target="_blank">Family Literacy</a>
              </td>
            </tr>  
            
            <tr>
              <td>Literacy Reviewer Rating Form (SAMPLE ONLY)</td>
              <td><a href="docs/literacyReviewForm.doc" target="_blank">MS Word</a><br/>
                  <a href="docs/literacyReviewForm.htm" target="_blank">HTML</a></td>
            </tr>   
          </table>
        </td>
      </tr>
    </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

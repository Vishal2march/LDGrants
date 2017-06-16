<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:choose >
  <c:when test="${lduser.reviewerfound=='false'}" >
    
    <table align="center" summary="for layout only">
      <tr>
        <td>Your username could not be found as a reviewer for the LGRMIF
         Program.  Please contact NYS Archives Grants Administration Unit to update your 
         account as a reviewer for the LGRMIF Program.
         </td>
      </tr>    
    </table>
    
  </c:when>
  <c:otherwise >
  
    <table align="center" width="90%" summary="for layout only">
      <tr>
        <th>Welcome to the LGRMIF Program Review</th>
      </tr>
      <tr>
        <td><%--per DM 7/11/14 remove participation link
        The link titled 'Participation' will indicate whether you have chosen to participate 
        in the review for this fiscal year.  Also, be sure to update your contact 
        information. <br/><br/>--%>
        The link for 'At-Home Evaluation' will list all of the grant proposals that you have 
        been assigned to review.</td>
      </tr>
      <tr>
        <td height="10"><hr/></td>
      </tr>
      <tr>
        <td>The links below contain a User Manual for Reviewers and Evaluation Form(s) for the LGRMIF 
        Grant Program, available for download.  Please remember that your 
        LGRMIF Grant Evaluation Form will be submitted electronically using the links on 
        the 'At-Home Evaluation' page.</td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>
          <table width="100%" border="1" class="graygrid" summary="for layout only">
            <tr>
              <td colspan="2">The following links open in a new window</td>
            </tr>
            <tr>
              <td>LGRMIF Grant Reviewer User Manual</td>
              <td><a href="http://www.archives.nysed.gov/grants/grants_lgrmif.shtml" target="_blank">LGRMIF Website</a></td>
            </tr>
            
            <tr>
              <td>LGRMIF Grant Evaluation Form (At-Home)</td>
              <td><a href="docs/lgrmif/atHomeForm.doc" target="_blank">MS Word</a><br/>
              <a href="docs/lgrmif/atHomeForm.htm" target="_blank">HTML</a></td>
            </tr>
            
            <tr>
              <td>LGRMIF Summary Evaluation Form (Deliberation)</td>
              <td><a href="docs/lgrmif/finalSummaryForm.doc" target="_blank">MS Word</a><br/>
                  <a href="docs/lgrmif/finalSummaryForm.htm" target="_blank">HTML</a></td>
            </tr>
            
            <tr>
                <td>Conflict of Interest Form</td>
                <td><a href="docs/lgrmif/conflictInterest.pdf" target="_blank">PDF</a><br/>
                    <a href="docs/lgrmif/conflictInterest.htm" target="_blank">HTML</a></td>
            </tr>
            
            <tr>
                <td>Eligible/Ineligible Expenditures and Required Forms</td>
                <td><a href="docs/lgrmif/budgetEligibility.doc" target="_blank">MS Word</a><br/>
                    <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">HTML</a></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

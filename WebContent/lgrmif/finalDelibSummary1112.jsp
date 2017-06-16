<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>New Final Deliberation Form</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  
  <c:if test="${PanelReviewBean.totamtappr > 0 && PanelReviewBean.initialappr=='false'}">
        <font color="Red">Warning:</font> The 'Application Approval' field below has
        been marked as Denied.  But the application Award Amounts entered for each of the
        Budget Category Codes is not equal to zero.
    </c:if>
  <br/>
  
  <table align="center" width="95%" summary="for layout only" class="boxtype">
    <tr>
        <th colspan="2">LGRMIF Grant New Final Deliberation Form</th>
    </tr>
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Category</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Rating Guidelines</th>
    </tr>
    <tr>
      <th width="30%">Scores Defined</th><th>Interpretation</th>
    </tr>
    <tr>
      <td>5	= Outstanding</td>
      <td>Applicant addresses the criterion with distinction</td>
    </tr>
    <tr>
      <td>4	= Good</td>
      <td>Applicant has provided a cogent and convincing response to the criterion</td>
    </tr>
    <tr>
      <td>3	= Adequate</td>
      <td>Applicant has addressed the criterion only competently</td>
    </tr>
    <tr>
      <td>2	= Fair</td>
      <td>Applicant may have addressed the criterion but is far from convincing, or the 
      project is inherently weak in this regard</td>
    </tr>
    <tr>
      <td>1	= Poor</td>
      <td>Applicant has offered a few words in response to the criterion, but the words show 
      little to no understanding of the issues</td>
    </tr>
    <tr>
      <td>0	= Unresponsive</td>
      <td>Applicant does not address the criterion directly or indirectly</td>
    </tr>
    <tr>
      <td height="30" colspan="2"><hr/></td>
    </tr>
  </table>
  
  <table width="95%" summary="for layout only">
    <tr>
      <th>Criteria</th><th>At-Home Average Rating</th>
    </tr>
    <tr>
      <th colspan="2">1.  Statement of the Problem (20 Points)</th>
    </tr>
    <tr>
      <td width="85%">a. Describes the specific records management problem the project will address, 
      provides qualitative descriptions and quantitative data about the problem and explains 
      why the project is a high priority  [10 points]</td>
      <td><c:out value="${RatingBean.problemStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identifies specific records that will be involved, and any previous grant-funded 
      projects related to these records and this project.   [5 points]</td>
      <td><c:out value="${RatingBean.recordsStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c.	Explains why funding from this grant program is essential to accomplishing the 
      project (For example, explains why funding is needed if funding was previously awarded 
      for a similar project.)  [5 points]</td>
      <td><c:out value="${RatingBean.otherfundStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>2. Intended Results (15 Points)</th>
    </tr>
    <tr>
      <td>a. Identifies each intended result and describes the anticipated benefits [5 points]</td>
      <td><c:out value="${RatingBean.outcomeStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes in detail how the project will contribute to the development of a 
      records management program [5 points]</td>
      <td><c:out value="${RatingBean.recordprogramStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
     <tr>
        <td>c. Describes in detail how the project will improve local government services to
        the public [5 points]</td>
        <td><c:out value="${RatingBean.improveservStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>3. Plan of Work (30 points) </th>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>a. Provides a detailed outline of the proposed work activities and a timetable 
      showing when each phase of the project will be completed, demonstrates the soundness of 
      the method proposed and demonstrates the project’s goals are attainable by 30 June 2012 
      (15 points)</td>
      <td><c:out value="${RatingBean.timetableStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Addresses each of the requirements of the relevant project category [10 points] </td>
      <td><c:out value="${RatingBean.projcategoryStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explains who will be responsible for performing each project activity, including 
      project management. Indicates the qualifications of  key project staff (including 
      consultants and vendors) in terms of education, training, and experience (5 points)</td>
      <td><c:out value="${RatingBean.staffsupportStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>4. Local Government Support (10 points)</th>
    </tr>
    <tr>
      <td>a. Demonstrates contributions to this project [5 points]</td>
      <td><c:out value="${RatingBean.govtsupportStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes how this project and records management in general will be maintained 
      over the long term [5 points]</td>
      <td><c:out value="${RatingBean.longrangeStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
    <th colspan="2">5. Budget Narrative and Forms (25 points)</th>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [25 points] </td>
      <td><c:out value="${RatingBean.expendituresStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
        <td colspan="2"><hr/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
  </table>
  <br/><br/>
  
    <table width="85%" summary="for layout only">      
        <tr>
            <td><b>Criteria</b></td><td><b>Average Score</b></td><td><b>Final Score</b></td>
        </tr>
        <tr>
          <td>Recommendation (F, M, or N):<br/>
          (LGRMIF program has a minimum score of 60 to be considered for funding)</td>
          <td></td>
          <td><c:out value="${PanelReviewBean.recommendation}"/></td>
        </tr>
        <tr>
          <td>Recommended Amount:</td>
          <td></td>
          <td><fmt:formatNumber value="${PanelReviewBean.recommendamt}" type="currency" minFractionDigits="0"/> </td>
        </tr>
        <tr>
          <td>Total Score (rounded to the nearest integer)</td>
          <td><c:out value="${RatingBean.scoreStr}"/></td>
          <td><c:out value="${PanelReviewBean.finalscore}" /></td>
        </tr>
        <tr>
          <td colspan="2" height="20"/>
        </tr>    
        <tr>
            <td colspan="3"><b>Application Approval</b><br/>Each application must be checked 
            as approved or denied</td>
        </tr>
        <tr>
            <td>Application Approved? <c:choose><c:when test="${PanelReviewBean.initialappr=='true'}">
                                        Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
        </tr>
      </table>
      <br/><br/><br/>
      
        <table width="85%" summary="for layout only">
            <tr>
                <td><b>Justification for change, if any, from the Average of the At-Home Score</b></td>
            </tr>
            <tr>
                <td><c:out value="${PanelReviewBean.justification}"/></td>
            </tr>
            <tr>
                <td height="30"/>
            </tr>
            <tr>
                <td><b>Decision Notes</b></td>
            </tr>
            <tr>
                <td><c:out value="${PanelReviewBean.decisionnotes}"/></td>
            </tr>
        </table> 
  
  </body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Final Deliberation Form (starting 2015-16)</title>
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
        <th colspan="2">LGRMIF Grant Final Deliberation Form (starting 2015-16)</th>
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
      <th>Criteria</th><th>At-Home Average Rating</th><th>Final Rating</th>
    </tr>
    <tr>
      <th colspan="2">1.  Statement of the Problem (15 Points)</th>
    </tr>
    <tr>
      <td width="85%">a. <b>Defining the Problem:</b> Describe the specific records management problem this proposed project will address, provide qualitative descriptions and quantitative data about the problem, and explain why this particular project is a high priority for your records management program. <b>Do not discuss any proposed solutions here, only the 
      problems. Discuss solutions in Intended Results (2a).</b> [5 points]  </td>
      <td><c:out value="${RatingBean.problemStr}" /></td>
      <td><c:out value="${RatingBean.problemDelibStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. <b>Defining Records Involved and Previous Grants:</b> Identify the specific records that will be involved in this project, and include the series titles, retention periods, and volume of each records series. If the proposed project includes imaging or microfilming, provide the required description and condition of each records series in this section. Identify any previous grant-funded projects related to these records series and this project, identifying the names and date ranges of records involved and why this project would not replicate work already completed and, thus, would not constitute an ineligible request for maintenance. If your government has not received any past projects relevant to your current application, indicate so. 
      [10 points]  </td>
      <td><c:out value="${RatingBean.recordsStr}" /></td>
      <td><c:out value="${RatingBean.recordsDelibStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>2. Intended Results (20 Points)</th>
    </tr>
    <tr>
      <td>a.	<b>Methodology:</b> Explain why the methodology you chose to solve your records management problem was the best one. Explain what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology was the best.  
      [10 points] </td>
      <td><c:out value="${RatingBean.outcomeStr}" /></td>
      <td><c:out value="${RatingBean.outcomeDelibStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b.	<b>Anticipated Benefits:</b> Identify each intended result or anticipated benefit of this project and your chosen solution, including specific products, time and cost savings, and service improvements. Describe how the anticipated benefits of this project will contribute to the development of a records management program or enhance an already existing program. Provide both qualitative and quantitative data to support your arguments about the benefits of this project. 
      [10 points]  </td>
      <td><c:out value="${RatingBean.recordprogramStr}" /></td>
      <td><c:out value="${RatingBean.recordprogramDelibStr}" /></td>
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
      <td>a. <b>Project Outline:</b>  Provide a detailed outline of the proposed work activities including a detailed description of each workstep and a timetable that shows when each phase of the project will be completed. Show how you calculated estimated work rates to prove that your local government can attain all the project’s goals by the end of the grant project period. (15 points)
 </td>
      <td><c:out value="${RatingBean.timetableStr}" /></td>
      <td><c:out value="${RatingBean.timetableDelibStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. <b>Grant Requirements:</b> Address each of the general application, project type, and project category requirements. If your application combines elements of two or more grant categories, address the requirements of each. 
      [15 points]  </td>
      <td><c:out value="${RatingBean.projcategoryStr}" /></td>
      <td><c:out value="${RatingBean.projcategoryDelibStr}" /></td>
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
      <td>a. <b>Previous Records Management and Current Project Support:</b> Demonstrate your local government’s contributions to this project, including funds, staffing, equipment, supplies, or the allocation of space. Also, demonstrate your local government’s contributions to its records management program, demonstrating its commitment to records management. Provide specific budget amounts whenever possible. Include only the financial and other support your local government has provided and will provide with its own funds. Note that previous grant projects funded by the LGRMIF do not constitute local support and must not be listed in this section. 
      [5 points]</td>
      <td><c:out value="${RatingBean.govtsupportStr}" /></td>
      <td><c:out value="${RatingBean.govtsupportDelibStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. <b>Future Program Support:</b> Provide concrete information to demonstrate how you will maintain the results of this project long term without additional LGRMIF grant funding. If additional funding will be required in the short term, explain why. 
      [5 points]</td>
      <td><c:out value="${RatingBean.longrangeStr}" /></td>
       <td><c:out value="${RatingBean.longrangeDelibStr}" /></td>
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
      <td>a. Justify the proposed project expenditures in terms of reasonableness of cost, the suitability of 
      the chosen solution, and the necessity of the expenses to ensure the project’s success.  [25 points]  </td>
      <td><c:out value="${RatingBean.expendituresStr}" /></td>
      <td><c:out value="${RatingBean.expendituresDelibStr}" /></td>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>LGRMIF at-home evaluation ratings</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  
  <table align="center" width="95%" summary="for layout only" class="boxtype">
    <tr>
        <th colspan="2">LGRMIF Grant At-Home Evaluation Form - Ratings</th>
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
  </table>
  
  
  <br/><br/>
  <table summary="for layout only">  
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
  
  
  
  <br/><br/>
  <table summary="for layout only">      
    <c:forEach var="ratebean" items="${allRatings}">
    
    <tr>
      <td>Reviewer</td>
      <td><c:out value="${ratebean.name}" /></td>
    </tr>
    <tr>
      <td><b>Recommendation</b></td>
      <td><c:out value="${ratebean.recommendation}"/></td>
    </tr>
    <tr>
      <td><b>Recommended Amount</b></td>
      <td><fmt:formatNumber value="${ratebean.recommendamt}" type="currency" minFractionDigits="0"/></td>
    </tr>
    <tr>
      <td colspan="2"><b>1.  Statement of the Problem (20 Points)</b></td>
    </tr>
    <tr>
      <td width="85%">a. Describes the specific records management problem the project will address, 
      provides qualitative descriptions and quantitative data about the problem and explains 
      why the project is a high priority  [10 points]  (System will multiply score of  0-5 x 2)</td>
      <td><c:out value="${ratebean.problem}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identifies specific records that will be involved, and any previous grant-funded 
      projects related to these records and this project.   [5 points]</td>
      <td><c:out value="${ratebean.records}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c.	Explains why funding from this grant program is essential to accomplishing the 
      project (For example, explains why funding is needed if funding was previously awarded 
      for a similar project.)  [5 points]</td>
      <td><c:out value="${ratebean.otherfund}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2"><b>2. Intended Results (10 Points)</b></td>
    </tr>
    <tr>
      <td>a. Identifies each intended result and describes the anticipated benefits [5 points]</td>
      <td><c:out value="${ratebean.outcome}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes in detail how the project will contribute to the development of a 
      records management program [5 points]</td>
      <td><c:out value="${ratebean.recordprogram}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2"><b>3. Plan of Work (30 points)</b></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>a.  Provide a detailed outline of the proposed work activities including a detailed description of each workstep and a timetable that shows when each phase of the project will be completed. Show how you calculated estimated work rates to prove that your local government can attain all the project’s goals by the end of the grant project period. (15 points)
 (System will multiply score of  0-5 x 3)</td>
      <td><c:out value="${ratebean.timetable}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Addresses each of the requirements of the relevant project category and 
      subcategory [10 points] (System will multiply score of 0-5 x 2)</td>
      <td><c:out value="${ratebean.projcategory}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explains who will be responsible for performing each project activity, including 
      project management. Indicates the qualifications of  key project staff (including 
      consultants and vendors) in terms of education, training, and experience (5 points)</td>
      <td><c:out value="${ratebean.staffsupport}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2"><b>4. Local Government Support (10 points)</b></td>
    </tr>
    <tr>
      <td>a. Demonstrates contributions to this project [5 points]</td>
      <td><c:out value="${ratebean.govtsupport}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes how this project and records management in general will be maintained 
      over the long term [5 points]</td>
      <td><c:out value="${ratebean.longrange}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
    <td colspan="2"><b>5. Budget Narrative and Forms (30 points) (to reach a score for each 
    criteria)</b></td>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [15 points]  (System will multiply score of  0-5 x 3)</td>
      <td><c:out value="${ratebean.budget}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Demonstrates costs are reasonable and necessary to support the project activities 
      and goals [15 points] (System will multiply score of  0-5 x 3)</td>
      <td><c:out value="${ratebean.reasonablecost}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td><b>Total Score</b></td>
      <td><c:out value="${ratebean.score}"/></td>
    </tr>
    <tr>
      <td>1. Cooperative Project  (10 Points)</td>
      <td><c:if test="${CoverBean.cooperative}">10</c:if></td>
    </tr>
    <tr>
      <td>2. 1st Time Inventory & Planning  (5 points)</td>
      <td><c:if test="${CoverBean.inventory}">5</c:if></td>
    </tr>
    <tr>
      <td>3. Electronic records inventory projects   (5 points)</td>
      <td><c:if test="${CoverBean.recordsmgmt}">5</c:if></td>
    </tr>
    <tr>
      <td>4. Email Management projects (5 points)</td>
      <td><c:if test="${CoverBean.emailmgmt}">5</c:if></td>
    </tr>
    <tr>
      <td><b>Total Initial Score</b></td>
      <td><c:out value="${ratebean.score + CoverBean.score}"/></td>
    </tr>
    <tr>
      <td height="30"><hr/></td>
    </tr>   
  
    </c:forEach>
  </table>  
  
  </body>
</html>

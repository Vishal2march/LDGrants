<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>At-Home Rating Summary</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
 <table align="center" width="95%" summary="for layout only" class="boxtype">
    <tr>
        <td colspan="2"><b>New LGRMIF Grant At-Home Evaluation Form - Ratings</b><br/>
        starting in Fiscal Year 2011-2012</td>
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
      <td><b>Reviewer</b></td>
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
    
    
    <c:choose>
    <c:when test="${thisGrant.fycode>15}"><%--  for fy2015-16; new ratings/weights  --%>
    
          <tr>
            <td colspan="2"><b>1.  Statement of the Problem (15 Points)</b></td>
          </tr>
          <tr>
            <td width="85%">a. <b>Defining the Problem:</b> Describe the specific records management problem this proposed project will address, provide qualitative descriptions and quantitative data about the problem, and explain why this particular project is a high priority for your records management program. <b>Do not discuss any proposed solutions here, only the 
            problems. Discuss solutions in Intended Results (2a).</b> [5 points]  </td>
            <td><c:out value="${ratebean.problem}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>b. <b>Defining Records Involved and Previous Grants:</b> Identify the specific records that will be involved in this project, and include the series titles, retention periods, and volume of each records series. If the proposed project includes imaging or microfilming, provide the required description and condition of each records series in this section. Identify any previous grant-funded projects related to these records series and this project, identifying the names and date ranges of records involved and why this project would not replicate work already completed and, thus, would not constitute an ineligible request for maintenance. If your government has not received any past projects relevant to your current application, indicate so. 
            [10 points]  (Assign a score of 0-5, as the system will
            calculate the total value in the Total At-Home Score) </td>
            <td><c:out value="${ratebean.records}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td><b>2. Intended Results (20 Points)</b></td>
          </tr>
          <tr>
            <td>a.	<b>Methodology:</b> Explain why the methodology you chose to solve your records management problem was the best one. Explain what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology was the best.  
            [10 points]   (Assign a score of  0-5, as the system will calculate the 
            total value in the Total Score )</td>
            <td><c:out value="${ratebean.outcome}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>b.	<b>Anticipated Benefits:</b> Identify each intended result or anticipated benefit of this project and your chosen solution, including specific products, time and cost savings, and service improvements. Describe how the anticipated benefits of this project will contribute to the development of a records management program or enhance an already existing program. Provide both qualitative and quantitative data to support your arguments about the benefits of this project. 
            [10 points]   (Assign a score of  0-5, as the system will calculate the 
            total value in the Total Score )</td>
            <td><c:out value="${ratebean.recordprogram}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td><b>3. Plan of Work (30 points)</b> </td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>a. <b>Project Outline:</b> Provide a detailed outline of the proposed work activities including a detailed description of each workstep and a timetable that shows when each phase of the project will be completed. Show how you calculated estimated work rates to prove that your local government can attain all the project’s goals by 30 June 2015. 
            [15 points]  
            (Assign a score of  0-5, as the system will calculate the total value in the Total Score )</td>
            <td><c:out value="${ratebean.timetable}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>b. <b>Grant Requirements:</b> Address each of the general application, project type, and project category requirements. If your application combines elements of two or more grant categories, address the requirements of each. 
            [15 points]   (Assign score of  0-5, as the system will calculate the total value 
            in the final score )</td>
            <td><c:out value="${ratebean.projcategory}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td><b>4. Local Government Contributions (10 points)</b></td>
          </tr>
          <tr>
            <td>a. <b>Previous Records Management and Current Project Support:</b> Demonstrate your local government’s contributions to this project, including funds, staffing, equipment, supplies, or the allocation of space. Also, demonstrate your local government’s contributions to its records management program, demonstrating its commitment to records management. Provide specific budget amounts whenever possible. Include only the financial and other support your local government has provided and will provide with its own funds. Note that previous grant projects funded by the LGRMIF do not constitute local support and must not be listed in this section. 
            [5 points]</td>
            <td><c:out value="${ratebean.govtsupport}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>b. <b>Future Program Support:</b> Provide concrete information to demonstrate how you will maintain the results of this project long term without additional LGRMIF grant funding. If additional funding will be required in the short term, explain why. 
            [5 points]</td>
            <td><c:out value="${ratebean.longrange}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
    
    </c:when>
    <c:otherwise>
    
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
            <td><b>2. Intended Results (15 Points)</b></td>
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
              <td>c. Describes in detail how the project will improve local government services to
              the public [5 points]</td>
              <td><c:out value="${ratebean.improveserv}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
              
          <tr>
            <td><b>3. Plan of Work (30 points)</b> </td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>a. Provides a detailed outline of the proposed work activities and a timetable 
            showing when each phase of the project will be completed, demonstrates the soundness of 
            the method proposed and demonstrates the project’s goals are attainable by 30 June 2013 
            (15 points) (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
            <td><c:out value="${ratebean.timetable}" /></td>
          </tr>
          <tr>
            <td height="10" />
          </tr>
          <tr>
            <td>b. Addresses each of the General Category Requirements <b>and</b> the 
            requirements of the specific project category and type  
            [10 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
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
            <td><b>4. Local Government Support (10 points)</b></td>
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
          
    </c:otherwise>
    </c:choose>
    
    
    
    <tr>
      <td colspan="2"><b>5. Budget Narrative and Forms (25 points)</b></td>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [25 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${ratebean.expenditures}" /></td>
    </tr>    
    <tr>
        <td height="10" />
    </tr>
    <tr>
      <td><b>Total At-Home Score</b></td>
      <td><c:out value="${ratebean.score}"/></td>
    </tr>
    <tr>
      <td><b>Bonus points</b></td>
      <td><c:out value="${ratebean.bonusScore}"/></td>
    </tr>
    <tr>
      <td><b>Total At-Home score + Bonus points</b></td>
      <td><c:out value="${ratebean.score + ratebean.bonusScore}"/></td>
    </tr>
    <tr>
      <td height="30"><hr/></td>
    </tr>   
  
    </c:forEach>
  </table>  
        
  </body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>homeRating1415</title>
    <script language="Javascript">
        function verifyFF(theForm){
            //alert(theForm.recommendation.value);
            if(theForm.recommendation.value=='F')
            {//per fc 3/19/10 if recomm=F; then set recomm amt= totamtreq
                theForm.recommendamtStr.value=theForm.totamtreq.value;
            }
            else if(theForm.recommendation.value=='N')
            {
                theForm.recommendamtStr.value=0;
            }
        }
    </script>
  </head>
  <body>
  
  
  
  <h5>NEW LGRMIF Grant Evaluation Form (starting FY 2014-15)</h5>

  <html:errors />
  <table align="center" width="95%" summary="for layout only" class="boxtype">
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
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
      <td colspan="2">View the Completed Evaluation Form in 
           <a href="ApcntFunctionsServlet?i=rating" target="_blank">PDF</a> format</td>
    </tr>
    <tr>
      <td colspan="2"><a href="lgReviewer.do?item=submit&p=lg">Submit</a> the Evaluation Form<br/>
      Reviews are due by <fmt:formatDate value="${revDue.reviewdate}" pattern="MM/dd/yyyy" /></td>
    </tr>
    <tr>
        <td colspan="2" height="25"/>
    </tr>
    <tr>
        <td><a href="PrintAppServlet?i=cover" target="_blank">Application Sheet - HTML</a></td>
        <td><a href="PrintAppServlet?i=coverpdf" target="_blank" >Application Sheet - PDF</a></td>
      </tr>
      <tr>       
        <td><a href="PrintAppServlet?i=narr" target="_blank">Project Narrative - HTML</a></td>
        <td><a href="PrintAppServlet?i=narrpdf" target="_blank" >Project Narrative - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=budget&a=true" target="_blank">Project Budget - HTML</a></td>
        <td><a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank">Project Budget - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=vq" target="_blank">LG-VQ Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=vqpdf" target="_blank" >LG-VQ Form - PDF</a></td>
      </tr> 
      
      <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
      <c:if test="${thisGrant.fycode<15}">
      <tr>       
        <td><a href="PrintAppServlet?i=im" target="_blank">LG-IM Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=impdf" target="_blank" >LG-IM Form - PDF</a></td>
      </tr>
      </c:if>
      
      <tr>
        <td><a href="FsFormServlet?i=fs10&fy=0" target="_blank">Proposed Budget Summary (FS-10) - HTML</a></td>
        <td><a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">Proposed Budget Summary (FS-10) - PDF</a></td>        
      </tr>   
      <tr>
        <td><a href="lgReviewer.do?item=attachment">Attachments</a></td>
        <td><a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/Ineligible 
        Expenditures and Required Forms</a></td>
      </tr>
    <tr>
      <td colspan="2"><hr/></td>
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
  
  
  
  <c:choose >
 <c:when test="${RatingBean.ratingcomp=='true' || RatingBean.conflictinterest=='true' ||
                    revDue.dateAcceptable=='false'}">        
 <%-- RATING FORM READ ONLY (AFTER SUBMIT or after the due date)   --%>
    
    
    <table align="center" width="95%" summary="for layout only">
    <tr>
      <th colspan="2">Criteria</th>
    </tr>
    <tr>
      <td colspan="2"><b>1.  Statement of the Problem (20 Points)</b></td>
    </tr>
    <tr>
      <td width="85%">a. Describe the specific records management problem this proposed project will address, 
      provide qualitative descriptions and quantitative data about the problem, and explain why the project is 
      a high priority for your records management program. <b>Do not discuss any proposed solutions here, only the 
      problem. Discuss solutions in Intended Results (2a).</b> [10 points]  (Assign a score of 0-5, as the system will
      calculate the total value in the Total At-Home Score )</td>
      <td><c:out value="${RatingBean.problem}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identify the specific records that will be involved, and include the records series titles, retention 
      periods, and volume of each series. Identify any previous grant-funded projects related to these records and 
      this project, identifying the ranges of records involved and why this project would not replicate work already 
      completed or constitute an ineligible request for maintenance. If imaging or microfilming records, provide the 
      required description and condition of the records in this section. [10 points]  (Assign a score of 0-5, as the system will
      calculate the total value in the Total At-Home Score )  </td>
      <td><c:out value="${RatingBean.records}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <c:out value="${RatingBean.problemcomment}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>2. Intended Results (15 Points)</b></td>
    </tr>
    <tr>
      <td>a.	Explain why the methodology you chose to solve your records management problem was the best one. Explain 
      what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology 
      was the best.   [5 points]</td>
      <td><c:out value="${RatingBean.outcome}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b.	Identify each intended result or anticipated benefit of this project and your chosen solution, 
      including specific products, time and cost savings, and service improvements. Describe how the anticipated 
      benefits of this project will contribute to the development of a records management program or enhance an 
      already existing program. Provide both qualitative and quantitative data to support your arguments about 
      the benefits of this project.   [10 points]   (Assign a score of  0-5, as the system will calculate the 
      total value in the Total Score )</td>
      <td><c:out value="${RatingBean.recordprogram}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <c:out value="${RatingBean.resultcomment}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>3. Plan of Work (30 points)</b> </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>a. Provide a detailed outline of the proposed work activities including a detailed description of each 
      workstep and a timetable that shows when each phase of the project will be completed. Show how work rates 
      were calculated or estimated to prove that the project�s goals are attainable by 30 June 2015.  [15 points) ]  
      (Assign a score of  0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.timetable}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Address each of the General Application Requirements and the requirements of the specific project 
      category and type. If your application combines elements of two or more grant categories, address the 
      requirements of each.  [10 points]   (Assign score of  0-5, as the system will calculate the total value 
      in the final score )</td>
      <td><c:out value="${RatingBean.projcategory}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explain who will be responsible for performing each project activity, including project management. 
      Indicate the qualifications of key project staff (including consultants and vendors) and explain how or why 
      they are qualified to work on this project.  [5 points]</td>
      <td><c:out value="${RatingBean.staffsupport}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <c:out value="${RatingBean.workcomment}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>4. Local Government Contributions (10 points)</b></td>
    </tr>
    <tr>
      <td>a. Demonstrate contributions to this project and to your records management program in general, 
      demonstrating your commitment to records management. Types of support may include local funds, staffing, 
      equipment, supplies, or the allocation of space. Provide specific budget amounts whenever possible. Include 
      information only on the support your local government has provided and will provide with its own funds. Note 
      that grant projects funded by the LGMRIF do not constitute local support and must not be listed in this 
      section.  [5 points]</td>
      <td><c:out value="${RatingBean.govtsupport}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Provide concrete information to demonstrate how you will maintain the results of this project long 
      term without additional LGRMIF grant funding. If additional funding will be required in the short term, 
      explain why.  [5 points]</td>
      <td><c:out value="${RatingBean.longrange}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
     <c:out value="${RatingBean.supportcomment}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
    <td colspan="2"><b>5. Budget Narrative and Forms (25 points)</b></td>
    </tr>
    <tr>
      <td>a. Justify the proposed project expenditures in terms of reasonableness of cost, the suitability of the 
      chosen solution, and the necessity of the expenses to ensure the project�s success.  [25 points]  (Assign a 
      score of  0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.expenditures}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <c:out value="${RatingBean.budgetcomment}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>    
    <tr>
      <td>Total At-Home Score</td>
      <td><c:out value="${RatingBean.sumscore}"/></td>
    </tr>
    <tr>
      <td>Recommendation (F, M, or N):</td>
      <td><c:out value="${RatingBean.recommendation}" /></td>
    </tr>
    <tr>
      <td>Total Amount Requested:</td>
      <td><fmt:formatNumber value="${RatingBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><fmt:formatNumber value="${RatingBean.recommendamt}" type="currency" minFractionDigits="0" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>  
      <td colspan="2">Overall Impression:<br/>
                    <c:out value="${RatingBean.comment}" /></td>
    </tr>
    <tr>
      <td colspan="2" height="20"/>
    </tr>        
   </table>     
  
    
    </c:when>
    <c:otherwise >
    
    
  <html:form action="/saveLgRating1415">  
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <th colspan="2">Criteria</th>
    </tr>
    <tr>
      <td><b>1.  Statement of the Problem (20 Points)</b></td>
    </tr>
    <tr>
      <td width="85%">a. Describe the specific records management problem this proposed project will address, 
      provide qualitative descriptions and quantitative data about the problem, and explain why the project is 
      a high priority for your records management program. <b>Do not discuss any proposed solutions here, only the 
      problem. Discuss solutions in Intended Results (2a).</b> [10 points]  (Assign a score of 0-5, as the system will
      calculate the total value in the Total At-Home Score )</td>
      <td><html:text property="problemStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identify the specific records that will be involved, and include the records series titles, retention 
      periods, and volume of each series. Identify any previous grant-funded projects related to these records and 
      this project, identifying the ranges of records involved and why this project would not replicate work already 
      completed or constitute an ineligible request for maintenance. If imaging or microfilming records, provide the 
      required description and condition of the records in this section.   [10 points]    </td>
      <td><html:text property="recordsStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <html:textarea property="problemcomment" rows="6" cols="80"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>2. Intended Results (15 Points)</b></td>
    </tr>
    <tr>
      <td>a.	Explain why the methodology you chose to solve your records management problem was the best one. Explain 
      what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology 
      was the best.   [5 points]</td>
      <td><html:text property="outcomeStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b.	Identify each intended result or anticipated benefit of this project and your chosen solution, 
      including specific products, time and cost savings, and service improvements. Describe how the anticipated 
      benefits of this project will contribute to the development of a records management program or enhance an 
      already existing program. Provide both qualitative and quantitative data to support your arguments about 
      the benefits of this project.   [10 points]   (Assign a score of  0-5, as the system will calculate the 
      total value in the Total Score )</td>
      <td><html:text property="recordprogramStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <html:textarea property="resultcomment" rows="6" cols="80"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>3. Plan of Work (30 points)</b> </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>a. Provide a detailed outline of the proposed work activities including a detailed description of each 
      workstep and a timetable that shows when each phase of the project will be completed. Show how work rates 
      were calculated or estimated to prove that the project�s goals are attainable by 30 June 2015.  [15 points) ]  
      (Assign a score of  0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="timetableStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Address each of the General Application Requirements and the requirements of the specific project 
      category and type. If your application combines elements of two or more grant categories, address the 
      requirements of each.  [10 points]   (Assign score of  0-5, as the system will calculate the total value 
      in the final score )</td>
      <td><html:text property="projcategoryStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explain who will be responsible for performing each project activity, including project management. 
      Indicate the qualifications of key project staff (including consultants and vendors) and explain how or why 
      they are qualified to work on this project.  [5 points]</td>
      <td><html:text property="staffsupportStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <html:textarea property="workcomment" rows="6" cols="80"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><b>4. Local Government Contributions (10 points)</b></td>
    </tr>
    <tr>
      <td>a. Demonstrate contributions to this project and to your records management program in general, 
      demonstrating your commitment to records management. Types of support may include local funds, staffing, 
      equipment, supplies, or the allocation of space. Provide specific budget amounts whenever possible. Include 
      information only on the support your local government has provided and will provide with its own funds. Note 
      that grant projects funded by the LGMRIF do not constitute local support and must not be listed in this 
      section.  [5 points]</td>
      <td><html:text property="govtsupportStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Provide concrete information to demonstrate how you will maintain the results of this project long 
      term without additional LGRMIF grant funding. If additional funding will be required in the short term, 
      explain why.  [5 points]</td>
      <td><html:text property="longrangeStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
     <html:textarea property="supportcomment" rows="6" cols="80"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
    <td colspan="2"><b>5. Budget Narrative and Forms (25 points)</b></td>
    </tr>
    <tr>
      <td>a. Justify the proposed project expenditures in terms of reasonableness of cost, the suitability of the 
      chosen solution, and the necessity of the expenses to ensure the project�s success.  [25 points]  (Assign a 
      score of  0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="expendituresStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>  
      <td colspan="2">Comments:<br/>
      <html:textarea property="budgetcomment" rows="6" cols="80"/></td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td>Total At-Home Score</td>
      <td><c:out value="${RatingBean.sumscore}"/></td>
    </tr>
    <tr>
        <td colspan="2"><c:if test="${RatingBean.sumscore < 60}">
            <font color="Red">**Warning**:</font>If the Total At-Home Score 
            is less than 60, you must select 'N' (No Fund) for Recommendation.</c:if>
        </td>
    </tr>
    <tr>
      <td>Recommendation (F, M, or N):<br/>
      (LGRMIF program has a minimum score of 60 to be considered for funding)</td>
      <td><html:select property="recommendation" onchange="verifyFF(this.form);">
            <html:option value="N"/>
            <html:option value="M"/> 
            <html:option value="F"/> 
          </html:select>
      </td>
    </tr>
    <tr>
       <td>Total Amount Requested:</td>
       <td><fmt:formatNumber value="${RatingBean.totamtreq}" type="currency" minFractionDigits="0"/>
            <html:hidden property="totamtreq"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><html:text property="recommendamtStr" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td colspan="2">Reviewer's Overall Impression: (not required)<br/>
                        <html:textarea property="comment" rows="6" cols="80"/>
        </td>
    <tr>
      <td colspan="2" align="center">
      <html:hidden property="grantassign"/><html:hidden property="module" />
      <html:hidden property="ratingcomp"/> <html:hidden property="conflictinterest"/>
      <html:hidden property="bonuspts"/>
      <html:submit value="Save" /></td>
    </tr>        
    <tr>
      <td colspan="2" height="30"/>
    </tr>    
   </table>     
   </html:form>
    
    
    </c:otherwise>
    </c:choose>
  
  
  
  </body>
</html>
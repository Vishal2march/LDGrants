<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
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
  
  <h5>LGRMIF Grant Evaluation Form</h5>

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
        <td><a href="FsFormServlet?i=fs20" target="_blank">Proposed Budget Summary (FS-20) - HTML</a></td>
        <td><a href="FsFormServlet?i=fs20pdf" target="_blank">Proposed Budget Summary (FS-20) - PDF</a></td>        
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
      <td width="85%">a. Describes the specific records management problem the project will address, 
      provides qualitative descriptions and quantitative data about the problem and explains 
      why the project is a high priority  [10 points]  (Assign a score of 0-5, as the system will
      calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.problem}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identifies specific records that will be involved, and any previous grant-funded 
      projects related to these records and this project.   [5 points]</td>
      <td><c:out value="${RatingBean.records}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c.	Explains why funding from this grant program is essential to accomplishing the 
      project (For example, explains why funding is needed if funding was previously awarded 
      for a similar project.)  [5 points]</td>
      <td><c:out value="${RatingBean.otherfund}" /></td>
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
      <td><b>2. Intended Results (10 Points)</b></td>
    </tr>
    <tr>
      <td>a. Identifies each intended result and describes the anticipated benefits [5 points]</td>
      <td><c:out value="${RatingBean.outcome}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes in detail how the project will contribute to the development of a 
      records management program [5 points]</td>
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
      <td>a.  Provide a detailed outline of the proposed work activities including a detailed description of each workstep and a timetable that shows when each phase of the project will be completed. Show how you calculated estimated work rates to prove that your local government can attain all the project’s goals by the end of the grant project period. (15 points)
 (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.timetable}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Addresses each of the requirements of the relevant project category 
      [10 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.projcategory}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explains who will be responsible for performing each project activity, including 
      project management. Indicates the qualifications of  key project staff (including 
      consultants and vendors) in terms of education, training, and experience (5 points)</td>
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
      <td><b>4. Local Government Support (10 points)</b></td>
    </tr>
    <tr>
      <td>a. Demonstrates contributions to this project [5 points]</td>
      <td><c:out value="${RatingBean.govtsupport}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes how this project and records management in general will be maintained 
      over the long term [5 points]</td>
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
    <td colspan="2"><b>5. Budget Narrative and Forms (30 points) (to reach a score for each criteria)</b></td>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [15 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.budget}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Demonstrates costs are reasonable and necessary to support the project activities 
      and goals [15 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><c:out value="${RatingBean.reasonablecost}" /></td>
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
      <td colspan="2" height="20"/>
    </tr>    
    <tr>
      <td colspan="2"><b>Bonus Scoring</b></td>
    </tr>
    <tr>
      <td>Total Score</td>
      <td><c:out value="${RatingBean.sumscore}"/></td>
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
      <td><b><c:out value="${RatingBean.sumscore + CoverBean.score}"/></b></td>
    </tr>
   </table>     
  
    
    </c:when>
    <c:otherwise >
    
    
  <html:form action="/saveLgRating">  
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <th colspan="2">Criteria</th>
    </tr>
    <tr>
      <td><b>1.  Statement of the Problem (20 Points)</b></td>
    </tr>
    <tr>
      <td>a. Describes the specific records management problem the project will address, 
      provides qualitative descriptions and quantitative data about the problem and explains 
      why the project is a high priority  [10 points]  (Assign a score of 0-5, as the system will 
      calculate the total value in the Total Score )</td>
      <td><html:text property="problemStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identifies specific records that will be involved, and any previous grant-funded 
      projects related to these records and this project.   [5 points]</td>
      <td><html:text property="recordsStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c.	Explains why funding from this grant program is essential to accomplishing the 
      project (For example, explains why funding is needed if funding was previously awarded 
      for a similar project.)  [5 points]</td>
      <td><html:text property="otherfundStr" /></td>
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
      <td><b>2. Intended Results (10 Points)</b></td>
    </tr>
    <tr>
      <td>a. Identifies each intended result and describes the anticipated benefits [5 points]</td>
      <td><html:text property="outcomeStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes in detail how the project will contribute to the development of a 
      records management program [5 points]</td>
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
      <td>a. Provides a detailed outline of the proposed work activities and a timetable 
      showing when each phase of the project will be completed, demonstrates the soundness of 
      the method proposed and demonstrates the project’s goals are attainable by 30 June 2011 
      (15 points) (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="timetableStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Addresses each of the requirements of the relevant project category 
      [10 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="projcategoryStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explains who will be responsible for performing each project activity, including 
      project management. Indicates the qualifications of  key project staff (including 
      consultants and vendors) in terms of education, training, and experience (5 points)</td>
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
      <td><b>4. Local Government Support (10 points)</b></td>
    </tr>
    <tr>
      <td>a. Demonstrates contributions to this project [5 points]</td>
      <td><html:text property="govtsupportStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes how this project and records management in general will be maintained 
      over the long term [5 points]</td>
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
    <td colspan="2"><b>5. Budget Narrative and Forms (30 points) (to reach a score for each criteria)</b></td>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [15 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="budgetStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Demonstrates costs are reasonable and necessary to support the project activities 
      and goals [15 points] (Assign a score of 0-5, as the system will calculate the total value in the Total Score )</td>
      <td><html:text property="reasonablecostStr" /></td>
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
        <td colspan="2"><c:if test="${RatingBean.sumscore + RatingBean.bonuspts < 60}">
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
       <td><fmt:formatNumber value="${RatingBean.totamtreq}" type="currency" minFractionDigits="0"/><html:hidden property="totamtreq"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><html:text property="recommendamtStr" /></td>
    </tr>
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
    <tr>
      <td colspan="2"><b>Bonus Scoring</b></td>
    </tr>
    <tr>
      <td>Total Score</td>
      <td><c:out value="${RatingBean.sumscore}"/></td>
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
      <td><b>Total At-Home Score</b></td>
      <td><b><c:out value="${RatingBean.sumscore + RatingBean.bonuspts}"/></b></td>
    </tr>
   </table>     
   </html:form>
    
    </c:otherwise>
  </c:choose>
  
  
  </body>
</html>

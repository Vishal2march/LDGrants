<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>deliberationForm1415</title>
    <script language="Javascript">            
        function verifyFF(theForm){
            if(theForm.recommendation.value=='F')
            {//per fc 4/28/10 if recomm=F; then set recomm amt= totamtreq
                theForm.recommendamtStr.value=theForm.totamtreq.value;
                
                theForm.initialappr[0].checked='true';
            }
            else if(theForm.recommendation.value=='N'){
                theForm.recommendamtStr.value=0;
                
                theForm.initialappr[1].checked='true';
            }
            else if(theForm.recommendation.value=='M'){
                theForm.initialappr[0].checked='true';
            }
        }
    </script>
  </head>
  <body>
  
  
   <h5>LGRMIF Final Evaluation Form (Deliberation) beginning FY 2014-15</h5>

    <c:if test="${PanelReviewBean.totamtappr > 0 && PanelReviewBean.initialappr=='false'}">
        <font color="Red">Warning: The 'Application Approval' field below has
        been marked as Denied.  But the application Award Amounts entered for each of the
        Budget Category Codes is not equal to zero.</font>
    </c:if>
    <c:if test="${PanelReviewBean.totamtappr==0 && PanelReviewBean.initialappr=='true'}">
        <font color="Red">Warning: The 'Application Approval' field below has
        been marked as Approved.  But the application Award Amounts entered for each of the
        Budget Category Codes is equal to zero.  The Award Amounts for each of the Budget 
        Category Codes must be equal to the Recommended Amount below.</font>
    </c:if>
  <br/><br/>
  
  <html:errors />
  <table align="center" width="95%" summary="for layout only" class="boxtype">
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
        <td><a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >Project Budget - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=vq" target="_blank">LG-VQ Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=vqpdf" target="_blank" >LG-VQ Form - PDF</a></td>
      </tr> 
           
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
  
  
  
    
  <%--modified 4/18/12; new column for final rating data entry--%>
  <html:form action="/saveDelibRating1415">  
  <table width="95%" summary="for layout only">
    <tr>
      <th>Criteria</th><th>At-Home Average Rating</th><th>Final Rating</th>
    </tr>
    <tr>
      <th colspan="2">1.  Statement of the Problem (20 Points)</th>
    </tr>
    <tr>
      <td width="85%">a. Describe the specific records management problem this proposed project will address, 
      provide qualitative descriptions and quantitative data about the problem, and explain why the project is a 
      high priority for your records management program. Do not discuss any proposed solutions here, only the 
      problem. Discuss solutions in Intended Results (2a).  [10 points]</td>
      <td><c:out value="${RatingBean.problemStr}" /></td>
      <td><html:text property="problemDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identify the specific records that will be involved, and include the records series titles, 
      retention periods, and volume of each series. Identify any previous grant-funded projects related to 
      these records and this project, identifying the ranges of records involved and why this project would 
      not replicate work already completed or constitute an ineligible request for maintenance. If imaging 
      or microfilming records, provide the required description and condition of the records in this 
      section.   [10 points]    </td>
      <td><c:out value="${RatingBean.recordsStr}" /></td>
      <td><html:text property="recordsDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">2. Intended Results (15 Points)</th>
    </tr>
    <tr>
      <td>a.	Explain why the methodology you chose to solve your records management problem was the best one. Explain 
      what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology 
      was the best.   [5 points]</td>
      <td><c:out value="${RatingBean.outcomeStr}" /></td>
      <td><html:text property="outcomeDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b.	Identify each intended result or anticipated benefit of this project and your chosen solution, 
      including specific products, time and cost savings, and service improvements. Describe how the anticipated 
      benefits of this project will contribute to the development of a records management program or enhance an 
      already existing program. Provide both qualitative and quantitative data to support your arguments about 
      the benefits of this project.   [10 points] </td>
      <td><c:out value="${RatingBean.recordprogramStr}" /></td>
      <td><html:text property="recordprogramDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">3. Plan of Work (30 points) </th>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>a. Provide a detailed outline of the proposed work activities including a detailed description of each 
      workstep and a timetable that shows when each phase of the project will be completed. Show how work rates 
      were calculated or estimated to prove that the project’s goals are attainable by 30 June 2015.  [15 points) ]  </td>
      <td><c:out value="${RatingBean.timetableStr}" /></td>
      <td><html:text property="timetableDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Address each of the General Application Requirements and the requirements of the specific project 
      category and type. If your application combines elements of two or more grant categories, address the 
      requirements of each.  [10 points]   </td>
      <td><c:out value="${RatingBean.projcategoryStr}" /></td>
      <td><html:text property="projcategoryDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explain who will be responsible for performing each project activity, including project management. 
      Indicate the qualifications of key project staff (including consultants and vendors) and explain how or 
      why they are qualified to work on this project.  [5 points]</td>
      <td><c:out value="${RatingBean.staffsupportStr}" /></td>
      <td><html:text property="staffsupportDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">4. Local Government Support (10 points)</th>
    </tr>
    <tr>
      <td>a. Demonstrate contributions to this project and to your records management program in general, 
      demonstrating your commitment to records management. Types of support may include local funds, staffing, 
      equipment, supplies, or the allocation of space. Provide specific budget amounts whenever possible. Include 
      information only on the support your local government has provided and will provide with its own funds. Note 
      that grant projects funded by the LGMRIF do not constitute local support and must not be listed in this 
      section.  [5 points]</td>
      <td><c:out value="${RatingBean.govtsupportStr}" /></td>
      <td><html:text property="govtsupportDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Provide concrete information to demonstrate how you will maintain the results of this project 
      long term without additional LGRMIF grant funding. If additional funding will be required in the short 
      term, explain why.  [5 points]</td>
      <td><c:out value="${RatingBean.longrangeStr}" /></td>
      <td><html:text property="longrangeDelibStr" /></td>
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
      <td><html:text property="expendituresDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" >
        <html:hidden property="status"/>
        <html:hidden property="problemStr"/>
        <html:hidden property="recordsStr"/>
        <html:hidden property="otherfundStr"/>
        <html:hidden property="outcomeStr"/>
        <html:hidden property="recordprogramStr"/>
        <html:hidden property="improveservStr"/>
        <html:hidden property="timetableStr"/>
        <html:hidden property="projcategoryStr"/>
        <html:hidden property="staffsupportStr"/>
        <html:hidden property="govtsupportStr"/>
        <html:hidden property="longrangeStr"/>
        <html:hidden property="expendituresStr"/>
      </td>
    </tr>
    <c:choose>
    <c:when test="${RatingBean.status=='f'}">
        <tr>
            <td colspan="3" align="center">
                <html:hidden property="graid"/><html:hidden property="panelgrantId"/>
                <html:submit value="Save"/></td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr>
          <td colspan="3" align="center">
            <html:hidden property="graid"/><html:hidden property="panelgrantId"/>
            <input type="button" value="Save" disabled="true"/></td>
        </tr>
    </c:otherwise>
    </c:choose>
    <tr>
      <td height="20" />
    </tr>
  </table>
  </html:form>
  <br/><br/>
  <hr style="border-width:thick; border-color:rgb(0,0,0);"/>
  <br/>
  
  
  
  
  <c:choose>
  <c:when test="${PanelReviewBean.status=='f'}">
    
  <html:form action="/saveDeliberation1415">
  <table width="85%" summary="for layout only">      
    <tr>
        <td><b>Criteria</b></td><td><b>At-Home Average</b></td><td><b>Final Deliberation</b></td>
    </tr>
    <tr>
      <td>Recommendation (F, M, or N):<br/>
      (LGRMIF program has a minimum score of 60 to be considered for funding)<br/></td>
      <td></td>
      <td><html:select property="recommendation" onchange="verifyFF(this.form);">
            <html:option value="N"/>
            <html:option value="M"/> 
            <html:option value="F"/> 
          </html:select></td>
    </tr>
    <tr><td colspan="3" height="20%">&nbsp;</td></tr>
    <tr>
       <td>Total Amount Requested:</td>
       <td><fmt:formatNumber value="${PanelReviewBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
       <td><fmt:formatNumber value="${PanelReviewBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><fmt:formatNumber value="${RatingBean.recommendamt}" type="currency" minFractionDigits="0"/></td>
      <td><html:text property="recommendamtStr"/> </td>
    </tr>
    <tr>
      <td>Total Score (sum of At-Home Average, Final Rating column from above):</td>
      <td><c:out value="${RatingBean.scoreStr}"/></td>
      <td><c:out value="${PanelReviewBean.finalscore}"/>
            <html:hidden property="finalscore"/></td>
    </tr>
    <tr>
        <td height="20%"></td>
    </tr>
    <tr>
      <td colspan="2" height="20"/>
    </tr>    
    <tr>
        <td colspan="3"><b>Application Approval</b><br/>Each application will be 
        systematically checked as approved or denied</td>
    </tr>
    <tr>
        <td><html:radio property="initialappr" value="true"/>Application Approved<br/>
        <html:radio property="initialappr" value="false"/>Application Denied</td>
    </tr>
    <tr>
        <td colspan="3" align="center">
                <html:hidden property="grantid"/><html:hidden property="panelgrantid"/>
                <html:hidden property="status"/><html:hidden property="bonuspts"/>
                <html:hidden property="totamtreq"/>
                <html:submit value="Save"/>
                <a href="#dataEntryAnchor" id="dataEntryAnchor"></a></td>
    </tr>
  </table>
  </html:form>
  <hr style="border-width:thick; border-color:rgb(0,0,0);"/>
  
    <br/><br/><br/>
    <html:form action="saveDelibNarr1415">
    <table width="85%" summary="for layout only">
        <tr>
            <td><b>Justification for change, if any, from the Average of the At-Home Score</b></td>
        </tr>
        <tr>
            <td><html:textarea property="justification" rows="10" cols="60"/></td>
        </tr>
        <tr>
            <td height="30"/>
        </tr>
        <tr>
            <td><b>Decision Notes</b></td>
        </tr>
        <tr>
            <td><html:textarea property="decisionnotes" rows="10" cols="60"/></td>
        </tr>
        <tr>
            <td><html:hidden property="grantid"/><html:hidden property="panelgrantid"/>
                <html:hidden property="status"/><html:hidden property="bonuspts"/>
                <html:submit value="Save"/></td>
        </tr>
        <tr>
            <td height="30"/>
        </tr>
    </table>
    </html:form>
    
  </c:when>
  <c:otherwise>
  
  <!--THIS SECTION IS WHEN THE PANEL STATUS = PARTIAL OR  STATUS = NONE-->
  
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
          <td>Total Amount Requested:</td>
          <td><fmt:formatNumber value="${PanelReviewBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${PanelReviewBean.totamtreq}" type="currency" minFractionDigits="0"/></td>
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
            <td colspan="3"><b>Application Approval</b><br/>Each application will be 
            systematically checked as approved or denied</td>
        </tr>
        <tr>
            <td>Application Approved? <c:choose><c:when test="${PanelReviewBean.initialappr=='true'}">
                                        Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
        </tr>
      </table>
      <br/><br/><br/>
  
  
      <c:choose>
      <c:when test="${PanelReviewBean.status=='p' && lduser.isgovtemp=='true'}">
       
        <a href="#dataEntryAnchor" id="dataEntryAnchor"></a>
        <html:form action="/saveDelibNarr1415">
        <table width="85%" summary="for layout only">
            <tr>
                <td><b>Justification for change, if any, from the Average of the At-Home Score</b></td>
            </tr>
            <tr>
                <td><html:textarea property="justification" rows="10" cols="60"/></td>
            </tr>
            <tr>
                <td height="30"/>
            </tr>
            <tr>
                <td><b>Decision Notes</b></td>
            </tr>
            <tr>
                <td><html:textarea property="decisionnotes" rows="10" cols="60"/></td>
            </tr>
            <tr>
                <td><html:hidden property="grantid"/><html:hidden property="panelgrantid"/>
                 <html:hidden property="status"/>
                 <html:submit value="Save"/></td>
            </tr>
        </table>     
        </html:form>
        
      </c:when>
      <c:otherwise>            
                
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
        
      </c:otherwise>      
      </c:choose>
  
  </c:otherwise>
  </c:choose>
  
  <c:if test="${anchorSection !=null}">
  <script type="text/Javascript">
    document.getElementById('dataEntryAnchor').focus();
  </script>  
  </c:if>
  
  
  </body>
</html>
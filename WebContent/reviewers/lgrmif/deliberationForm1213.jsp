<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>LGRMIF Deliberation Form beginning 2012-13</title>
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
  
  
  <h5>LGRMIF Final Evaluation Form (Deliberation) beginning FY 2012-13</h5>

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
  <html:form action="/saveDelibRating1213">  
  <table width="95%" summary="for layout only">
    <tr>
      <th>Criteria</th><th>At-Home Average Rating</th><th>Final Rating</th>
    </tr>
    <tr>
      <th colspan="2">1.  Statement of the Problem (20 Points)</th>
    </tr>
    <tr>
      <td width="85%">a. Describes the specific records management problem the project will address, 
      provides qualitative descriptions and quantitative data about the problem and explains 
      why the project is a high priority  [10 points]</td>
      <td><c:out value="${RatingBean.problemStr}" /></td>
      <td><html:text property="problemDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Identifies specific records that will be involved, and any previous grant-funded 
      projects related to these records and this project.   [5 points]</td>
      <td><c:out value="${RatingBean.recordsStr}" /></td>
      <td><html:text property="recordsDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c.	Explains why funding from this grant program is essential to accomplishing the 
      project (For example, explains why funding is needed if funding was previously awarded 
      for a similar project.)  [5 points]</td>
      <td><c:out value="${RatingBean.otherfundStr}" /></td>
      <td><html:text property="otherfundDelibStr" /></td>
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
      <td>a. Identifies each intended result and describes the anticipated benefits [5 points]</td>
      <td><c:out value="${RatingBean.outcomeStr}" /></td>
      <td><html:text property="outcomeDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes in detail how the project will contribute to the development of a 
      records management program [5 points]</td>
      <td><c:out value="${RatingBean.recordprogramStr}" /></td>
      <td><html:text property="recordprogramDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
     <tr>
        <td>c. Describes in detail how the project will improve local government services to
        the public [5 points]</td>
        <td><c:out value="${RatingBean.improveservStr}" /></td>
        <td><html:text property="improveservDelibStr" /></td>
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
      <td>a. Provides a detailed outline of the proposed work activities and a timetable 
      showing when each phase of the project will be completed, demonstrates the soundness of 
      the method proposed and demonstrates the project’s goals are attainable by 30 June 2014 
      (15 points)</td>
      <td><c:out value="${RatingBean.timetableStr}" /></td>
      <td><html:text property="timetableDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Addresses each of the General Category Requirements <b>and</b> the requirements 
      of the specific project category and type  [10 points] </td>
      <td><c:out value="${RatingBean.projcategoryStr}" /></td>
      <td><html:text property="projcategoryDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>c. Explains who will be responsible for performing each project activity, including 
      project management. Indicates the qualifications of  key project staff (including 
      consultants and vendors) in terms of education, training, and experience (5 points)</td>
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
      <td>a. Demonstrates contributions to this project [5 points]</td>
      <td><c:out value="${RatingBean.govtsupportStr}" /></td>
      <td><html:text property="govtsupportDelibStr" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Describes how this project and records management in general will be maintained 
      over the long term [5 points]</td>
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
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [25 points] </td>
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
    
  <html:form action="/saveDeliberation1213">
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
    <html:form action="saveDelibNarr1213">
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
        <html:form action="/saveDelibNarr1213">
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
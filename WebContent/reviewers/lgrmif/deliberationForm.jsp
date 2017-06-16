<%--
 * @author  Stefanie Husak
 * @version 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  deliberationForm.jsp
 * Description
 * This is the lgrmif final deliberation form -used during the panel meetings. 
 * Contains the 'avg score' of all at-home reviews for each rating criteria. 
 * Contains txtboxes for panel to enter final score, final amt, and decision notes.
 * 3 access levels for this form(full/partial/none) which are set on admin side
 * (see panel's access level)
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>deliberationForm</title>
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
 <%-- <body onload="document.getElementById('dataEntryAnchor').focus();">--%>
    
  <h5>LGRMIF Final Evaluation Form (Deliberation)</h5>

    <c:if test="${PanelReviewBean.totamtappr > 0 && PanelReviewBean.initialappr=='false'}">
        <font color="Red">Warning:</font> The 'Application Approval' field below has
        been marked as Denied.  But the application Award Amounts entered for each of the
        Budget Category Codes is not equal to zero.
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
      <th>2. Intended Results (10 Points)</th>
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
      the method proposed and demonstrates the project’s goals are attainable by 30 June 2011 
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
    <th colspan="2">5. Budget Narrative and Forms (30 points) (to reach a score for each criteria)</th>
    </tr>
    <tr>
      <td>a. Explains how the proposed expenditures will be used to support the project 
      activities. [15 points] </td>
      <td><c:out value="${RatingBean.budgetStr}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td>b. Demonstrates costs are reasonable and necessary to support the project activities 
      and goals [15 points] </td>
      <td><c:out value="${RatingBean.reasonablecostStr}" /></td>
    </tr>
    <tr>
        <td colspan="2"><hr/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
  </table>
  <br/><br/>
  
  
  <c:choose>
  <c:when test="${PanelReviewBean.status=='f'}">
    
  <html:form action="/saveDeliberation">
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
      <td>Total Score (rounded to the nearest integer)</td>
      <td><c:out value="${RatingBean.scoreStr}"/></td>
      <td><html:text property="finalscore" /></td>
    </tr>
    <tr>
        <td height="20%"></td>
    </tr>
    <tr>
      <td>Bonus Scoring</td>
    </tr>
    <tr>
      <td>1. Cooperative Project  (10 Points)</td>
      <c:if test="${CoverBean.cooperative}"><td>10</td><td>10</td></c:if>
    </tr>
    <tr>
      <td>2. 1st Time Inventory & Planning  (5 points)</td>
      <c:if test="${CoverBean.inventory}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
      <td>3. Electronic records inventory projects   (5 points)</td>
      <c:if test="${CoverBean.recordsmgmt}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
      <td>4. Email Management projects (5 points)</td>
      <c:if test="${CoverBean.emailmgmt}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
        <td>Total Bonus Points</td>
        <td><c:out value="${PanelReviewBean.bonuspts}"/></td>
        <td><c:out value="${PanelReviewBean.bonuspts}"/></td>
    </tr>
    <tr>
        <td></td>
        <td><hr/></td>
        <td><hr/></td>
    </tr>
    <tr>
        <td><b>Total Panel Score</b></td>
        <td><b><c:out value="${RatingBean.scoreStr + CoverBean.score}"/></b></td>
        <td><b><c:out value="${PanelReviewBean.finalscore + PanelReviewBean.bonuspts}" /></b></td>
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
  <hr style="border-width:thick;"/>
  
    <br/><br/><br/>
    <html:form action="saveDelibNarrative">
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
          <td>Bonus Scoring</td>
        </tr>
        <tr>
          <td>1. Cooperative Project  (10 Points)</td>
          <c:if test="${CoverBean.cooperative}"><td>10</td><td>10</td></c:if>
        </tr>
        <tr>
          <td>2. 1st Time Inventory & Planning  (5 points)</td>
          <c:if test="${CoverBean.inventory}"><td>5</td><td>5</td></c:if>
        </tr>
        <tr>
          <td>3. Electronic records inventory projects   (5 points)</td>
          <c:if test="${CoverBean.recordsmgmt}"><td>5</td><td>5</td></c:if>
        </tr>
        <tr>
          <td>4. Email Management projects (5 points)</td>
          <c:if test="${CoverBean.emailmgmt}"><td>5</td><td>5</td></c:if>
        </tr>
        <tr>
            <td>Total Panel Score</td>
            <td><c:out value="${RatingBean.scoreStr + CoverBean.score}"/></td>
            <td><c:out value="${PanelReviewBean.finalscore + CoverBean.score}" /></td>
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
        <html:form action="/saveDelibNarrative">
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
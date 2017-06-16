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
    <title>home</title>
  </head>
  <body>
  
  <br><h4>PLS Reviewer Evaluation Form</h4></br> 
  
  <c:choose>
  <c:when test="${assignBean.systemLockOut=='true'}">

  <table align="center" width="75%" border="1" summary="for layout only" class="boxtype">
    <tr>
        <td><b>Project Number:</b></td>
        <td>03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>
    <tr>
        <td><b>Member Library:</b></td>
        <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td>1. Cost of Project for Which Funding is Being Requested:</td>
        <td><fmt:formatNumber value="${reviewFormBean.amountRequested}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    
    
    <c:choose>
    <c:when test="${reviewFormBean.fycode <13}">
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            50% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (50% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </c:when>
    <c:otherwise>
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            75% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (75% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </c:otherwise>
    </c:choose>
    
    
    <tr>
        <td>4. System Allocation:</td>
        <td><fmt:formatNumber value="${reviewFormBean.initialAlloc}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>5. Total Amount Recommended by System to Members:</td>
        <td><fmt:formatNumber value="${reviewFormBean.tallyAmountRecommend}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <c:if test="${reviewFormBean.tallyAmountRecommend > reviewFormBean.initialAlloc}">
    <tr>
        <td colspan="2"><font color="Red">Warning: The Total Amount Recommended by System to Members
        is more than the System Allocation.</font></td>
    </tr>
    </c:if>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td><b>Project Recommended for Funding:</b></td>
        <td><c:out value="${reviewFormBean.projectrecommend}" /></td>
    </tr>
    <tr>
        <td><b>Recommended Amount:</b></td>
        <td><fmt:formatNumber value="${reviewFormBean.amtrecommended}" type="currency" maxFractionDigits="0" />  </td>
    </tr>
  </table>
  <br/>
  
  <table align="center" width="95%" border="1" summary="for layout only" class="boxtype">
      <tr bgcolor="Silver">
        <th colspan="3">Listed below are the criteria and requirements
                        extracted from Commissioner's Regulations &sect; 90.12</th>
        <th width="5%">Meet Criteria</th>
        <th width="50%">Evaluation Comments</th>
      </tr>
      <tr>
        <td width="10%">90.12 (b)(2)</td>
        <td width="5%">l.</td>
        <td width="25%">How will this project assist the applicant to provide
                        more effective service within the system's standards of
                        organization and service?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionone}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentone}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(1)</td>
        <td width="5%">2.</td>
        <td width="25%">When this project is completed, will adequate operating
                        support and resources be available to sustain an
                        improved level of service?</td>
        <td width="5%"><c:out value="${reviewFormBean.questiontwo}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commenttwo}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">3.</td>
        <td width="25%">Is total cost of the project, minus the grant award
                        amount, available?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionthree}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentthree}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">4.</td>
        <td width="25%">Can this project be under contract to start within 180
                        days of project funding approval?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionfour}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentfour}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">5.</td>
        <td width="25%">Can this project be completed promptly in accordance
                        with the application?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionfive}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentfive}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(3) (c) (5)</td>
        <td width="5%">6.</td>
        <td width="25%">Is the project being conducted in accordance with all
                        applicable laws and are required competitive bidding
                        procedures being followed?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionsix}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentsix}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1)</td>
        <td width="5%">7.</td>
        <td width="25%">Will this project increase and improve building space
                        and capacity?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionseven}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentseven}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1) (d) (3)</td>
        <td width="5%">8.</td>
        <td width="25%">Will the project result in new library programs and
                        increased user accommodations including access by
                        individuals with disabilities?</td>
        <td width="5%"><c:out value="${reviewFormBean.questioneight}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commenteight}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(2)</td>
        <td width="5%">9.</td>
        <td width="25%">Will the project result in such economies as increased
                        energy conservation and greater operational efficiency?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionnine}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentnine}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">10.</td>
        <td width="25%">Will the project extend library service in communities
                        that are geographically isolated?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionten}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentten}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">11.</td>
        <td width="25%">Will the project extend library service in communities
                        that are economically disadvantaged?</td>
        <td width="5%"><c:out value="${reviewFormBean.questioneleven}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commenteleven}"/></td>    
      </tr>
      
      <c:if test="${thisGrant.fycode>16}"><%--starting FY 2016-17 --%>
          <tr>
            <td width="10%">90.12 (e)(vi)</td>
            <td width="5%">12.</td>
            <td width="25%">Will this project have the capacity to deliver internet services at a 
            connectivity rate in accordance with recommended Program Guidelines?</td>
            <td width="5%"><c:out value="${reviewFormBean.questiontwelve}" /></td>
            <td width="50%"><c:out value="${reviewFormBean.commenttwelve}"/></td>    
          </tr>
      </c:if>
    </table>
                    
 
    </c:when>          
    <c:otherwise >  
  
  <html:errors/>
  <html:form action="/saveCnReviewForm">
  <table align="center" width="75%" border="1" summary="for layout only" class="boxtype">
    <tr>
        <td><b>Project Number:</b></td>
        <td>03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>
    <tr>
        <td><b>Member Library:</b></td>
        <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td>1. Cost of Project for Which Funding is Being Requested:</td>
        <td><fmt:formatNumber value="${reviewFormBean.amountRequested}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    
    
    <html:hidden property="fycode"/>
    <c:choose>
    <c:when test="${reviewFormBean.fycode <13}">
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            50% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (50% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </c:when>
    <c:otherwise>
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            75% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (75% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>   
    </c:otherwise>
    </c:choose>
    
    
    <tr>
        <td>4. System Allocation:</td>
        <td><fmt:formatNumber value="${reviewFormBean.initialAlloc}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>5. Total Amount Recommended by System to Members:</td>
        <td><fmt:formatNumber value="${reviewFormBean.tallyAmountRecommend}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <c:if test="${reviewFormBean.tallyAmountRecommend > reviewFormBean.initialAlloc}">
    <tr>
        <td colspan="2"><font color="Red">Warning: The Total Amount Recommended by System to Members
        is more than the System Allocation.</font></td>
    </tr>
    </c:if>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td><b>Project Recommended for Funding:</b></td>
        <td><html:radio property="projectrecommend" value="true"/>Yes&nbsp;&nbsp;
            <html:radio property="projectrecommend" value="false"/>No</td>
    </tr>
    <tr>
        <td><b>Amount Recommended (cannot be more than question #3 above):</b></td>
        <td><html:text property="amtrecommendedStr" /></td>
    </tr>
  </table>
  
  <br/>
  <table align="center" width="95%" border="1" summary="for layout only" class="boxtype">
      <tr bgcolor="Silver">
        <th colspan="3">Listed below are the criteria and requirements
                        extracted from Commissioner's Regulations &sect; 90.12</th>
        <th width="15%">Meet Criteria</th>
        <th>Evaluation Comments</th>
      </tr>
      
      <tr>
        <td width="10%">90.12 (b)(2)</td>
        <td width="5%">l.</td>
        <td width="25%">How will this project assist the applicant to provide
                        more effective service within the system's standards of
                        organization and service?</td>
        <td width="5%"><html:radio property="questionone" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionone" value="false" />No</td>
        <td width="50%"><html:textarea property="commentone" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(1)</td>
        <td width="5%">2.</td>
        <td width="25%">When this project is completed, will adequate operating
                        support and resources be available to sustain an
                        improved level of service?</td>
        <td width="5%"><html:radio property="questiontwo" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questiontwo" value="false" />No</td>
        <td width="50%"><html:textarea property="commenttwo" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">3.</td>
        <td width="25%">Is total cost of the project, minus the grant award
                        amount, available?</td>
        <td width="5%"><html:radio property="questionthree" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionthree" value="false" />No</td>
        <td width="50%"><html:textarea property="commentthree" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">4.</td>
        <td width="25%">Can this project be under contract to start within 180
                        days of project funding approval?</td>
        <td width="5%"><html:radio property="questionfour" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionfour" value="false" />No</td>
        <td width="50%"><html:textarea property="commentfour" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">5.</td>
        <td width="25%">Can this project be completed promptly in accordance
                        with the application?</td>
        <td width="5%"><html:radio property="questionfive" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionfive" value="false" />No</td>
        <td width="50%"><html:textarea property="commentfive" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(3) (c) (5)</td>
        <td width="5%">6.</td>
        <td width="25%">Is the project being conducted in accordance with all
                        applicable laws and are required competitive bidding
                        procedures being followed?</td>
        <td width="5%"><html:radio property="questionsix" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionsix" value="false" />No</td>
        <td width="50%"><html:textarea property="commentsix" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1)</td>
        <td width="5%">7.</td>
        <td width="25%">Will this project increase and improve building space
                        and capacity?</td>
        <td width="5%"><html:radio property="questionseven" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionseven" value="false" />No</td>
        <td width="50%"><html:textarea property="commentseven" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1) (d) (3)</td>
        <td width="5%">8.</td>
        <td width="25%">Will the project result in new library programs and
                        increased user accommodations including access by
                        individuals with disabilities?</td>
        <td width="5%"><html:radio property="questioneight" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questioneight" value="false" />No</td>
        <td width="50%"><html:textarea property="commenteight" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(2)</td>
        <td width="5%">9.</td>
        <td width="25%">Will the project result in such economies as increased
                        energy conservation and greater operational efficiency?</td>
        <td width="5%"><html:radio property="questionnine" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionnine" value="false" />No</td>
        <td width="50%"><html:textarea property="commentnine" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">10.</td>
        <td width="25%">Will the project extend library service in communities
                        that are geographically isolated?</td>
        <td width="5%"><html:radio property="questionten" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questionten" value="false" />No</td>
        <td width="50%"><html:textarea property="commentten" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">11.</td>
        <td width="25%">Will the project extend library service in communities
                        that are economically disadvantaged?</td>
        <td width="5%"><html:radio property="questioneleven" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questioneleven" value="false" />No</td>
        <td width="50%"><html:textarea property="commenteleven" cols="40" rows="5" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (e)(vi)</td>
        <td width="5%">12.</td>
        <td width="25%">Will this project have the capacity to deliver internet services at a 
        connectivity rate in accordance with recommended Program Guidelines?</td>
        <td width="5%"><html:radio property="questiontwelve" value="true" />Yes&nbsp;&nbsp;
                       <html:radio property="questiontwelve" value="false" />No</td>
        <td width="50%"><html:textarea property="commenttwelve" cols="40" rows="5" /></td>
      </tr>
    </table>
                       
        <p align="center">
        <html:hidden property="grantid"/>
        <html:hidden property="assignmentid"/>
        <html:hidden property="amountRequested"/>
        <html:hidden property="requestCost"/>
        <html:hidden property="maxRecommendAmt"/>
        <html:hidden property="initialAlloc"/>
        <html:hidden property="tallyAmountRecommend"/>
        <html:submit value="Save Evaluation"/></p>
 
    </html:form>
 
  </c:otherwise>
 </c:choose>   
    
    
    </body>
</html>
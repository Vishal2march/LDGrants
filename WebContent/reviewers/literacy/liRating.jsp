<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>  
  <br/><br/>
  
  <html:errors />
  <table align="center" width="90%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}">Adult</c:if>
      <c:if test="${thisGrant.fccode==42}">Family</c:if> Literacy Library Services Grant Rating Form</th>
    </tr>   
    <tr>
      <td width="60%"><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" /></td>
    </tr>
    <tr>
      <td><b>Sponsoring Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Title</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td colspan="2">View the Completed Rating Form in 
          <a href="ApcntFunctionsServlet?i=rating" target="_blank">PDF format</a></td>
    </tr>
    <tr>
      <td colspan="2"><a href="liReviewer.do?item=submit&p=li">Submit</a>  the Rating Form and Comments<br/>
          <p class="error">Reviews are due by <fmt:formatDate value="${revDue.enddate}" pattern="MM/dd/yyyy" /></p></td>
    </tr>
    <tr>
      <td colspan="2">Does the application include  1) Cover page  2) Board Certification  3) FS-10  4) Online application 5) Supporting Materials Y - N</td>
    </tr>
  </table>
   
    
    
    
    
    <c:choose >
    <c:when test="${RatingBean.ratingcomp=='true'}">      
    <%-- RATING FORM READ ONLY (AFTER SUBMIT)   --%>
    
    <c:url var="unsubURL" value="liReviewer.do">
      <c:param name="item" value="unsubmit" />
      <c:param name="id" value="${RatingBean.graid}" />
      <c:param name="assign" value="${RatingBean.grantassign}" />
    </c:url>
    
  <table width="90%" align="center" class="boxtype" summary="for layout only">      
    <tr>
      <td colspan="2" align="center"><font color="Red"> This Rating Form has been submitted.</font>
        <br/><b>
        <a href='<c:out value="${unsubURL}" />'>Unsubmit</a> the Rating Form to make corrections.</b></td>
    </tr>
    <tr>
      <td colspan="2"><b>1. Abstract (max 5 points)</b></td>
    </tr>
    <tr>
      <td>Clear, concise description of project purpose, target group that meets guidelines (5)</td>
      <td><c:out value="${RatingBean.abstractInt}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>2. Planning/Need/Target Audience/Collaboration (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Target group need, expected benefits, size of target audience, outreach plans are realistic (5)</td>
      <td><c:out value="${RatingBean.groupneed}" /></td>
    </tr>
    <tr>
      <td>Project relates to library long-range plan (5)</td>
      <td><c:out value="${RatingBean.longrange}" /></td>
    </tr>
    <tr>
      <td>Project will improve library's level of service to target group (5)</td>
      <td><c:out value="${RatingBean.levelservice}" /></td>
    </tr>
    <tr>
      <td>Describes role of project partners and cooperating organizations; role
of each agency in planning, implementation, evaluation; meaningful letters of support included (5)</td>
      <td><c:out value="${RatingBean.cooporganization}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="planval" value="${RatingBean.groupneed + RatingBean.longrange + RatingBean.levelservice + RatingBean.cooporganization}" />
          <font color="Navy"><b><c:out value="${planval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>3. Project Description (max. 25 points)</b></td>
    </tr>
    <tr>
      <td>Project goal and project objectives are realistic and relate to need (10)</td>
      <td><c:out value="${RatingBean.goal}" /></td>
    </tr>
    <tr>
      <td>Project activities to accomplish each objective are realistic and relate to objectives (10)</td>
      <td><c:out value="${RatingBean.activities}" /></td>
    </tr>
     <tr>
      <td>Timeline of project activities is realistic (5)</td>
      <td><c:out value="${RatingBean.timetable}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="descval" value="${RatingBean.goal + RatingBean.activities + RatingBean.timetable}" />
          <font color="Navy"><b><c:out value="${descval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>4. Evaluation / Outcomes (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Describes what will be measured to provide qualitative data about service or product outputs (5)</td>
      <td><c:out value="${RatingBean.output}" /></td>
    </tr>
    <tr>
      <td>Describes how the outputs will be measured (5)</td>
      <td><c:out value="${RatingBean.measureoutput}" /></td>
    </tr>
    <tr>
      <td>Describes what will be measured to show the outcomes or impacts on the target group (5)</td>
      <td><c:out value="${RatingBean.outcome}" /></td>
    </tr>
    <tr>
      <td>Describes how the outcomes will be measured (5)</td>
      <td><c:out value="${RatingBean.measureoutcome}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="eval" value="${RatingBean.output + RatingBean.measureoutput + RatingBean.outcome + RatingBean.measureoutcome}" />
          <font color="Navy"><b><c:out value="${eval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>5. Continuation and Statewide Dissemination of Results (max. 10 points)</b></td>
    </tr>
    <tr>
      <td>The successful project will be continued; partners and potential sources of funding described (5)</td>
      <td><c:out value="${RatingBean.continuation}" /></td>
    </tr>
    <tr>
      <td>Information will be shared within and outside library profession (5)</td>
      <td><c:out value="${RatingBean.sharing}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="contval" value="${RatingBean.continuation + RatingBean.sharing}" />
          <font color="Navy"><b><c:out value="${contval}" /></b></font></td>
    </tr>
     <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>6. Budget Narrative (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Describes importance of each budget item and how it contributes to project purpose (10)</td>
      <td><c:out value="${RatingBean.budget}" /></td>
    </tr>
    <tr>
      <td>In-kind services and other funds demonstrate commitment of library and partners (10)</td>
      <td><c:out value="${RatingBean.otherfund}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="bval" value="${RatingBean.budget + RatingBean.otherfund}" />
          <font color="Navy"><b><c:out value="${bval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><font color="Navy"><b>Grand Total</b></font></td>
      <td><font color="Navy"><b><c:out value="${RatingBean.abstractInt +planval +descval +eval +contval +bval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    
    <tr>
      <td colspan="2"><b>7. Rating of Entire Proposal</b></td>
    </tr>
    <tr>
      <td>Please rate the entire proposal on a scale of "1" to "10".  Highly Recommended (10, 9)  
      Satisfactory (8, 7, 6)  Unsatisfactory (5, 4, 3, 2)  Not Eligible (1)</td>
      <td><font color="Navy"><b><c:out value="${RatingBean.overallscore}" /></b></font></td>
    </tr>
       
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <th colspan="2">Comments</th>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${RatingBean.comment}" /></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><font color="Red">This Rating Form has been submitted.</font></td>
    </tr>
 </table>    
    
    
    </c:when>
    <c:otherwise >    
    <%-- RATING FORM DATA ENTRY  --%>
    
  <html:form action="/saveFlRating">
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td colspan="2"><b>1. Abstract (max 5 points)</b>
        <html:hidden property="grantassign" /> <html:hidden property="module" /></td>
    </tr>
    <tr>
      <td >Clear, concise description of project purpose, target group that meets guidelines (5)</td>
      <td><html:text property="abstractStr" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>2. Planning/Need/Target Audience/Collaboration (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Target group need, expected benefits, size of target audience, outreach plans are realistic (5)</td>
      <td><html:text property="groupneedStr" /></td>
    </tr>
    <tr>
      <td>Project relates to library long-range plan (5)</td>
      <td><html:text property="longrangeStr" /></td>
    </tr>
    <tr>
      <td>Project will improve library's level of service to target group (5)</td>
      <td><html:text property="levelserviceStr" /></td>
    </tr>
    <tr>
      <td>Describes role of project partners and cooperating organizations; role
of each agency in planning, implementation, evaluation; meaningful letters of support included (5)</td>
      <td><html:text property="cooporganizationStr" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="planval" value="${RatingBean.groupneed + RatingBean.longrange + RatingBean.levelservice + RatingBean.cooporganization}" />
          <font color="Navy"><b><c:out value="${planval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>3. Project Description (max. 25 points)</b></td>
    </tr>
    <tr>
      <td>Project goal and project objectives are realistic and relate to need (10)</td>
      <td><html:text property="goalStr" /></td>
    </tr>
    <tr>
      <td>Project activities to accomplish each objective are realistic and relate to objectives (10)</td>
      <td><html:text property="activitiesStr" /></td>
    </tr>
     <tr>
      <td>Timeline of project activities is realistic (5)</td>
      <td><html:text property="timetableStr" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><c:set var="descval" value="${RatingBean.goal + RatingBean.activities + RatingBean.timetable}" />
          <b><c:out value="${descval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>4. Evaluation / Outcomes (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Describes what will be measured to provide qualitative data about service or product outputs (5)</td>
      <td><html:text property="outputStr" /></td>
    </tr>
    <tr>
      <td>Describes how the outputs will be measured (5)</td>
      <td><html:text property="measureoutputStr" /></td>
    </tr>
    <tr>
      <td>Describes what will be measured to show the outcomes or impacts on the target group (5)</td>
      <td><html:text property="outcomeStr" /></td>
    </tr>
    <tr>
      <td>Describes how the outcomes will be measured (5)</td>
      <td><html:text property="measureoutcomeStr" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="eval" value="${RatingBean.outcome + RatingBean.measureoutcome + RatingBean.output + RatingBean.measureoutput}" />
          <font color="Navy"><b><c:out value="${eval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>5. Continuation and Statewide Dissemination of Results (max. 10 points)</b></td>
    </tr>
    <tr>
      <td>The successful project will be continued; partners and potential sources of funding described (5)</td>
      <td><html:text property="continuationStr" /></td>
    </tr>
    <tr>
      <td>Information will be shared within and outside library profession (5)</td>
      <td><html:text property="sharingStr" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="contval" value="${RatingBean.continuation + RatingBean.sharing}" />
          <font color="Navy"><b><c:out value="${contval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>6. Budget Narrative (max. 20 points)</b></td>
    </tr>
    <tr>
      <td>Describes importance of each budget item and how it contributes to project purpose (10)</td>
      <td><html:text property="budgetStr" /></td>
    </tr>
    <tr>
      <td>In-kind services and other funds demonstrate commitment of library and partners (10)</td>
      <td><html:text property="otherfundStr" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><c:set var="bval" value="${RatingBean.budget + RatingBean.otherfund}" />
          <font color="Navy"><b><c:out value="${bval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><font color="Navy"><b>Grand Total</b></font></td>
      <td><font color="Navy"><b>
      <c:out value="${RatingBean.abstractInt + planval +descval +eval +contval + bval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>7. Rating of Entire Proposal</b></td>
    </tr>
    <tr>
      <td>Please rate the entire proposal on a scale of "1" to "10".  Highly Recommended (10, 9)  
      Satisfactory (8, 7, 6)  Unsatisfactory (5, 4, 3, 2)  Not Eligible (1)</td>
      <td><html:text property="overallscoreStr"  /></td>
    </tr>
      
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <th colspan="2">Comments</th>
    </tr>
    <tr>
      <td colspan="2" align="center">
        You may draft and save any comments in a word processor (i.e. Microsoft Word) first and
        then copy/paste into the text box below.  <br/><br/>
        <html:textarea property="comment" rows="10" cols="80"></html:textarea></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><html:submit value="Save" /></td>
    </tr>
  </table>
  </html:form>
    
  </c:otherwise>
  </c:choose>
 
  
  </body>
</html>

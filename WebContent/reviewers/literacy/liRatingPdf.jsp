<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="400" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table width="100%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}">Adult</c:if>
      <c:if test="${thisGrant.fccode==42}">Family</c:if> Literacy Library Services Grant Rating Form</th>
    </tr>   
    <tr>
      <td width="70%"><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
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
      <td colspan="2"><hr/></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2">Does the application include  1) Cover page  2) Board Certification  3) FS-10  4) Online application 5) Supporting Materials Y - N</td>
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
  </table> 
  </font>
  
  </body>
</html>
</pd4ml:transform>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="700" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <font size="1">
  <table align="center" width="100%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2">NYS C/P Discretionary Grant Education Proposal Rating Form</th>
    </tr>   
    <tr>
      <td width="60%"><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
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
      <td colspan="2">Evaluate the proposal based on the information provided in the application, and
      rate each factor listed below on a scale of "0" to "3", with three being the best possible score.
      </td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
    <tr>
      <th colspan="2">Plan of Work</th>
    </tr>
    <tr>
      <td>Adequacy of timetable</td>
      <td><c:out value="${RatingBean.timetable}" /></td>
    </tr>
    <tr>
      <td>Technical soundness of proposed methods & techniques</td>
      <td><c:out value="${RatingBean.soundness}" /></td>
    </tr>
    <tr>
      <td>Soundness of proposed activities, methods, and techniques</td>
      <td><c:out value="${RatingBean.personnel}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Plan of Work Subtotal</b></font></td>
      <td><c:set var="pval" value="${RatingBean.timetable + RatingBean.soundness + RatingBean.personnel}" />
          <font color="Navy"><b><c:out value="${pval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X3</b>&nbsp;&nbsp;  (max 27)</font></td>
      <td><font color="Navy"><b><c:out value="${pval * 3}" /></b></font></td>
    </tr>
     <tr>
      <td height="20" />
    </tr>
    <tr>
      <td align="center" colspan="2"><b>Institutional Contribution to the Project</b><br/>
      (Note: Institutional Contribution category is for reviews prior to 2012-13 grant cycle.)</td>
    </tr>
    <tr>
      <td>Level of institutional staff support for the project</td>
      <td><c:out value="${RatingBean.staffsupport}" /></td>
    </tr>
    <tr>
      <td>Level of institutional financial support for the project</td>
      <td><c:out value="${RatingBean.financialsupport}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Institutional Contribution Subtotal</b>&nbsp;&nbsp;  (max 6)</font></td>
      <td><c:set var="contribval" value="${RatingBean.staffsupport + RatingBean.financialsupport}" />
          <font color="Navy"><b><c:out value="${contribval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Education Pre-Planning</th>
    </tr>
    <tr>
      <td>Need for proposed training</td>
      <td><c:out value="${RatingBean.groupneed}" /></td>
    </tr>
    <tr>
      <td>Training objectives</td>
      <td><c:out value="${RatingBean.goal}" /></td>
    </tr>
    <tr>
      <td>Publicity</td>
      <td><c:out value="${RatingBean.publicity}" /></td>
    </tr>
    <tr>
      <td>Information dissemination</td>
      <td><c:out value="${RatingBean.sharing}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Education Pre-Planning Subtotal</b></font></td>
      <td><c:set var="edval" value="${RatingBean.groupneed + RatingBean.goal + RatingBean.publicity + RatingBean.sharing}" />
          <font color="Navy"><b><c:out value="${edval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X3.25</b>&nbsp;&nbsp;  (max 39)</font></td>
      <td><font color="Navy"><b><fmt:formatNumber value="${edval * 3.25}" maxFractionDigits="2" /></b></font></td>
    </tr>
    
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Budget</th>
    </tr>
    <tr>
      <td>Consistency of budgeted activities with project description</td>
      <td><c:out value="${RatingBean.consistentdesc}" /></td>
    </tr>
    <tr>
      <td>Consistency of budgeted activities with eligible expenditures</td>
      <td><c:out value="${RatingBean.consistentexp}" /></td>
    </tr>
    <tr>
      <td>Overall cost effectiveness of project</td>
      <td><c:out value="${RatingBean.costeffective}" /></td>
    </tr>
     <tr>
      <td><font color="Navy"><b>Budget Subtotal</b></font></td>
      <td><c:set var="budgval" value="${RatingBean.consistentdesc + RatingBean.consistentexp + RatingBean.costeffective}" />
          <font color="Navy"><b><c:out value="${budgval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X2.23</b>&nbsp;&nbsp;  (max 20)</font></td>
      <td><font color="Navy"><b><fmt:formatNumber value="${budgval * 2.23}" maxFractionDigits="2" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Rating of Entire Proposal</th>
    </tr>
    <tr>
      <td >Please rate the entire proposal on a scale of "0" to "10", with "0" being unsatisfactory
      and "10" being Highly recommended. (For reviews prior to 2010-2011 grant cycle).</td>
      <td><c:out value="${RatingBean.overallscore}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:out value="${RatingBean.overallscore}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><font color="Navy"><b>Grand Total</b></font></td>
      <td><font color="Navy"><b><fmt:formatNumber value="${RatingBean.overallscore +contribval +edval*3.25 +budgval*2.23 +pval*3}" maxFractionDigits="2" /></b></font></td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <th colspan="2">Comments</th>
    </tr>
    <tr>
        <td colspan="2">Include comments you think will be helpful to successful applicants 
        in managing their projects, or to unsuccessful applicants seeking advice on improving 
        proposals.  Applicants are especially interested in comments that will explain low 
        ratings in particular parts of the project description and which will explain your 
        reservation about funding the proposal, or any part of it.</td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${RatingBean.comment}" /></td>
    </tr>
  </table>
  </font>
  
  </body>
</html>

</pd4ml:transform>

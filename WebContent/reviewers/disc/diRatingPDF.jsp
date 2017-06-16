<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Discretionary Aid Project Rating Form</title>
  </head>
  <body>  
  
  <font size="1">
  <table align="center" width="100%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2">NYS C/P Discretionary Grant Rating Form</th>
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
      <th colspan="2">Institutional Commitment to Conservation/Preservation Work</th>
    </tr>
    <tr>
      <td>Adequacy of institutional conservation/preservation activities </td>
      <td><c:out value="${RatingBean.instcp}" /></td>
    </tr>
    <tr>
      <td>Evidence that preserved materials will be adequately stored </td>
      <td><c:out value="${RatingBean.storage}" /></td>
    </tr>
    <tr>
      <td>Preparations for dealing with disasters </td>
      <td><c:out value="${RatingBean.disaster}" /></td>
    </tr>
    <tr>
      <td>Adequacy of security arrangements</td>
      <td><c:out value="${RatingBean.security}" /></td>
    </tr>
    <tr>
      <td>Participation and/or contribution to cooperative, regional or statewide conservation / preservation activities </td>
      <td><c:out value="${RatingBean.coopactivities}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Commitment Subtotal</b>&nbsp;&nbsp;  (max 15)</font></td>
      <td><c:set var="commitval" value="${RatingBean.instcp + RatingBean.storage+ RatingBean.disaster+ RatingBean.security+ RatingBean.coopactivities}" />
          <font color="Navy"><b><c:out value="${commitval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Accessibility of Collections to the Public</th>
    </tr>
    <tr>
      <td>Availability of materials to potential users </td>
      <td><c:out value="${RatingBean.availability}" /></td>
    </tr>
    <tr>
      <td>Extent and adequacy of bibliographic controls </td>
      <td><c:out value="${RatingBean.bibliographic}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Accessibility Subtotal</b>&nbsp;&nbsp;  (max 6)</font></td>
      <td><c:set var="accessval" value="${RatingBean.availability + RatingBean.bibliographic}" />
          <font color="Navy"><b><c:out value="${accessval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Research Value of Materials to be Preserved</th>
    </tr>
    <tr>
      <td>Appropriateness of materials for preservation with discretionary grant funds </td>
      <td><c:out value="${RatingBean.appropriate}" /></td>
    </tr>
    <tr>
      <td>Significance for research </td>
      <td><c:out value="${RatingBean.significance}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Research Value Subtotal</b></font></td>
      <td><c:set var="resval" value="${RatingBean.appropriate + RatingBean.significance}" />
          <font color="Navy"><b><c:out value="${resval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X3</b>&nbsp;&nbsp;  (max 18)</font></td>
      <td><font color="Navy"><b><c:out value="${resval*3}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Plan of Work</th>
    </tr>
    <tr>
      <td>Adequacy of timetable</td>
      <td><c:out value="${RatingBean.timetable}" /></td>
    </tr>
    <tr>
      <td>Soundness of proposed activities, methods, and techniques</td>
      <td><c:out value="${RatingBean.soundness}" /></td>
    </tr>
    <tr>
      <td>Qualifications of personnel and/or vendors</td>
      <td><c:out value="${RatingBean.personnel}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Plan of Work Subtotal</b></font></td>
      <td><c:set var="planval" value="${RatingBean.timetable + RatingBean.soundness + RatingBean.personnel}" />
          <font color="Navy"><b><c:out value="${planval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X3</b>&nbsp;&nbsp;  (max 27)</font></td>
      <td><font color="Navy"><b><c:out value="${planval*3}" /></b></font></td>
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
      <td><font color="Navy"><b><fmt:formatNumber value="${budgval *2.23}" maxFractionDigits="2" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Rating of Entire Proposal</th>
    </tr>
    <tr>
      <td >Please rate the entire proposal on a scale of "0" to "10", with "0" being unsatisfactory
      and "10" being Highly recommended.  (For reviews prior to 2010-2011 grant cycle).</td>
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
      <td><font color="Navy"><b><fmt:formatNumber value="${RatingBean.overallscore +commitval +accessval +contribval +budgval*2.23 +resval*3 + planval*3}" maxFractionDigits="2" /></b></font></td>
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
<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coRating.jsp
 * Creation/Modification History  :
 *
 *     SH       7/30/07Created    8/31/09 MODIFIED
 *
 * Description
 * This page allows the reviewer to enter scores for the Co rating sheet
 * and comments and save them to db. Uses actionForm to make sure scores are in
 * correct range, any strings or blanks are saved as 0.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
      <th colspan="2">New York State Program for the Conservation and Preservation of Library Research Materials<br/>
    Coordinated Grant Review Form</th>
    </tr>   
    <tr>
      <td><b>Project Number</b></td>
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
      <td colspan="2"><hr/></td>
    </tr>    
    <tr>
      <td>Coversheet, Budget: <a href="PrintAppServlet?i=revapp" target="_blank">HTML</a><br/>
          Narratives: <a href="PrintAppServlet?i=narr" target="_blank">HTML</a></td>
      <td>Coversheet, Budget: <a href="PrintAppServlet?i=revapppdf" target="_blank" >PDF</a><br/>
          Narratives: <a href="PrintAppServlet?i=narrpdf" target="_blank" >PDF</a></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2">View the Completed Rating Form in 
          <a href="ApcntFunctionsServlet?i=rating" target="_blank"> PDF format</a></td>
    </tr>
    <tr>
      <td colspan="2"><a href="coReviewerForms.do?item=submit&p=co">Submit</a> the Rating Form and Comments<br/>
        <p class="error">Reviews are due by <fmt:formatDate value="${revDue.enddate}" pattern="MM/dd/yyyy" /></p></td>
    </tr>
    <tr>
      <td colspan="2"><hr/></td>
    </tr>
    <tr>
      <td colspan="2">Based on the information provided in the application, provide an
      evaluation of the proposal using the following rating sheet.  Rate each factor 
      on a scale of "0" to "5", with "5" being the best possible score, as follows: 
      0 � Not addressed in application, 1 � Poor, 2 � Fair, 3 � Adequate, 4- Good, 
      5- Excellent.   If a rating factor does not apply 
      to this proposal, enter a zero for that box. You may only enter numbers, not 'N/A'. Remember to click the Save
      button before navigating away from this page. You may Save your rating information and return to this
      page at a later time. </td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
    <c:choose >
    <c:when test="${RatingBean.ratingcomp=='true'}">    
    
    
    <tr>
      <td colspan="2" align="center"><font color="Red"> This Rating Form has already been submitted.</font></td>
    </tr>
    <tr>
      <th colspan="2">Research Value of Materials to be Preserved</th>
    </tr>
    <tr>
      <td>Appropriateness of materials for preservation with grant funds</td>
      <td><c:out value="${RatingBean.appropriate}" /></td>
    </tr>
    <tr>
      <td>Significance for research</td>
      <td><c:out value="${RatingBean.significance}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="rval" value="${RatingBean.appropriate + RatingBean.significance}" />
                                <c:out value="${rval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Cooperative Planning and Operation</th>
    </tr>
    <tr>
      <td>Involvement of participating libraries</td>
      <td><c:out value="${RatingBean.involvement}" /></td>
    </tr>
    <tr>
      <td>Coordination of activities</td>
      <td><c:out value="${RatingBean.coordination}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="coopval" value="${RatingBean.involvement + RatingBean.coordination}" />
                                <c:out value="${coopval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Bibliographic Accessibility of Collections</th>
    </tr>
    <tr>
      <td>Extent and adequacy of bibliographic control</td>
      <td><c:out value="${RatingBean.bibliographic}" /></td>
    </tr>
    <tr>
      <td>Extent to which online databases will be used</td>
      <td><c:out value="${RatingBean.onlinedb}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="bibval" value="${RatingBean.bibliographic + RatingBean.onlinedb}" />
                                <c:out value="${bibval}" /></b></font></td>
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
      <td>Technical soundness of proposed methods & techniques</td>
      <td><c:out value="${RatingBean.soundness}" /></td>
    </tr>
    <tr>
      <td>Adequacy of special equipment and facilities</td>
      <td><c:out value="${RatingBean.equipment}" /></td>
    </tr>
    <tr>
      <td>Qualifications of personnel and/or vendors</td>
      <td><c:out value="${RatingBean.personnel}" /></td>
    </tr>
    <tr>
      <td>Evidence that preserved materials will be adequately stored</td>
      <td><c:out value="${RatingBean.storage}" /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="pwval" value="${RatingBean.timetable + RatingBean.soundness + RatingBean.equipment + RatingBean.personnel + RatingBean.storage }" />
                                <c:out value="${pwval}"/></b></font></td>
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
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="budval" value="${RatingBean.consistentdesc + RatingBean.consistentexp + RatingBean.costeffective}" />
                                <c:out value="${budval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X2</b></font></td>
      <td><font color="Navy"><b><fmt:formatNumber value="${budval * 2}" maxFractionDigits="0" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Rating of Entire Proposal (for 2008-2009 and earlier projects)</th>
    </tr>
    <tr>
      <td >Please rate the entire proposal on a scale of "0" to "10", with "0" being unsatisfactory
      and "10" being Highly recommended.</td>
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
      <td><font color="Navy"><b><c:out value="${RatingBean.overallscore + rval + coopval + bibval + pwval + (budval*2)}" /></b></font></td>
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
        reservation about funding the proposal, or any part of it.<hr/></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${RatingBean.comment}" /></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><font color="Red"> This Rating Form has already been submitted.</font></td>
    </tr>
    
    
    </c:when>
    <c:otherwise >
    
    
    
    <html:form action="/saveCoRating">
    <tr>
      <th colspan="2">Research Value of Materials to be Preserved</th>
      <html:hidden property="grantassign" /> <html:hidden property="module" />
    </tr>
    <tr>
      <td>Appropriateness of materials for preservation with grant funds</td>
      <td><html:text property="appropriateStr" /></td>
    </tr>
    <tr>
      <td>Significance for research</td>
      <td><html:text property="significanceStr"  /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="rval" value="${RatingBean.appropriate + RatingBean.significance}" />
                                <c:out value="${rval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Cooperative Planning and Operation</th>
    </tr>
    <tr>
      <td>Involvement of participating libraries</td>
      <td><html:text property="involvementStr"  /></td>
    </tr>
    <tr>
      <td>Coordination of activities</td>
      <td><html:text property="coordinationStr"  /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="coopval" value="${RatingBean.involvement + RatingBean.coordination}" />
                                <c:out value="${coopval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Bibliographic Accessibility of Collections</th>
    </tr>
    <tr>
      <td>Extent and adequacy of bibliographic control</td>
      <td><html:text property="bibliographicStr"  /></td>
    </tr>
    <tr>
      <td>Extent to which online databases will be used</td>
      <td><html:text property="onlinedbStr"  /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="bibval" value="${RatingBean.bibliographic + RatingBean.onlinedb}" />
                                <c:out value="${bibval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Plan of Work</th>
    </tr>
    <tr>
      <td>Adequacy of timetable</td>
      <td><html:text property="timetableStr"  /></td>
    </tr>
    <tr>
      <td>Technical soundness of proposed methods & techniques</td>
      <td><html:text property="soundnessStr"  /></td>
    </tr>
    <tr>
      <td>Adequacy of special equipment and facilities</td>
      <td><html:text property="equipmentStr"  /></td>
    </tr>
    <tr>
      <td>Qualifications of personnel and/or vendors</td>
      <td><html:text property="personnelStr"  /></td>
    </tr>
    <tr>
      <td>Evidence that preserved materials will be adequately stored</td>
      <td><html:text property="storageStr"  /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="pwval" value="${RatingBean.timetable + RatingBean.soundness + RatingBean.equipment + RatingBean.personnel + RatingBean.storage }" />
                                <c:out value="${pwval}" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th colspan="2">Budget</th>
    </tr>
    <tr>
      <td>Consistency of budgeted activities with project description</td>
      <td><html:text property="consistentdescStr"  /></td>
    </tr>
    <tr>
      <td>Consistency of budgeted activities with eligible expenditures</td>
      <td><html:text property="consistentexpStr"  /></td>
    </tr>
    <tr>
      <td>Overall cost effectiveness of project</td>
      <td><html:text property="costeffectiveStr"  /></td>
    </tr>
     <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:set var="budval" value="${RatingBean.consistentdesc + RatingBean.consistentexp + RatingBean.costeffective}" />
                                <c:out value="${budval}" /></b></font></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Weighted factor X2</b></font></td>
      <td><font color="Navy"><b><fmt:formatNumber value="${budval * 2}" maxFractionDigits="0" /></b></font></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <%-- removed 8/31/09 <tr>
      <th colspan="2">Rating of Entire Proposal</th>
    </tr>
    <tr>
      <td >Please rate the entire proposal on a scale of "0" to "10", with "0" being unsatisfactory
      and "10" being Highly recommended.</td>
      <td><html:text property="overallscoreStr"  /></td>
    </tr>
    <tr>
      <td><font color="Navy"><b>Subtotal</b></font></td>
      <td><font color="Navy"><b><c:out value="${RatingBean.overallscore}" /></b></font></td>
    </tr>--%>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td><font color="Navy"><b>Grand Total</b></font></td>
      <td><font color="Navy"><b><c:out value="${rval + coopval + bibval + pwval + (budval*2)}" /></b></font></td>
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
      <td colspan="2" align="center">
        <html:textarea property="comment" rows="10" cols="80"></html:textarea></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><html:submit value="Save" /></td>
    </tr>
    </html:form>
    
  </c:otherwise>
  </c:choose>
    
  </table>
  </body>
</html>

<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diAdminRevMail.jsp
 *
 * Description
 * This page was used by di admin to select reviewer email (request for participation, assignments, etc), 
 * and to confirm send mail. It was pre-sedems, did not allow editing/variables.  No longer used 3/27/13
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>    
    <STYLE>
	  .cpYearNavigation,.cpMonthNavigation { background-color:#C0C0C0; text-align:center; vertical-align:center; text-decoration:none; color:#000000; font-weight:bold; };
	  .cpDayColumnHeader, .cpYearNavigation,.cpMonthNavigation,.cpCurrentMonthDate,.cpCurrentMonthDateDisabled,.cpOtherMonthDate,.cpOtherMonthDateDisabled,.cpCurrentDate,.cpCurrentDateDisabled,.cpTodayText,.cpTodayTextDisabled,.cpText { font-family:arial; font-size:8pt; };
	  TD.cpDayColumnHeader { text-align:right; border:solid thin #C0C0C0;border-width:0px 0px 1px 0px; };
	  .cpCurrentMonthDate, .cpOtherMonthDate, .cpCurrentDate  { text-align:right; text-decoration:none; };
	  .cpCurrentMonthDateDisabled, .cpOtherMonthDateDisabled, .cpCurrentDateDisabled { color:#D0D0D0; text-align:right; text-decoration:line-through; };
	  .cpCurrentMonthDate, .cpCurrentDate { color:#000000; };	.cpOtherMonthDate { color:#808080; };
    TD.cpCurrentDate { color:white; background-color: #C0C0C0; border-width:1px; border:solid thin #800000; };
	  TD.cpCurrentDateDisabled { border-width:1px; border:solid thin #FFAAAA; };
	  TD.cpTodayText, TD.cpTodayTextDisabled { border:solid thin #C0C0C0; border-width:1px 0px 0px 0px;};
	  A.cpTodayText, SPAN.cpTodayTextDisabled { height:20px; };
	  A.cpTodayText { color:black; };	.cpTodayTextDisabled { color:#D0D0D0; };	.cpBorder { border:solid thin #808080; };
	 </STYLE>
   <script language="javascript" src="jscripts/calendar_popup.js"></script>
  </head>
  <body>
    
      
  <c:choose >
  <c:when test="${!empty param.todo}">
  <br/>
    
  <c:if test="${param.todo=='participate'}">
    <form action="MassMailServlet" method="POST">
      <input type="HIDDEN" name="etype" value="participate" />
      <input type="HIDDEN" name="mod" value="di" />
      Do you want to send a Request for Participation Email to all Active Discretionary Reviewers?<br/>
      <input type="SUBMIT" value="Send" />
      <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" />
    </form>
    
    The following information will be included in the body of the email:<br/><br/>
    <table width="80%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td>Dear FNAME LNAME,<BR/><BR/>          
          I would like to ask you to serve as a reviewer for the upcoming New York State 
          Conservation/Preservation Discretionary Grant Program.  The program has always relied 
          heavily on the good will of preservation librarians, archivists, and conservators to 
          provide professional review to help ensure that projects funded through the program are 
          well planned, cost effective, and productive.<Br/><br/>
        
         Please login to the Division of Library Development Online Grant System and fill out a 
         participation form indicating whether you would be willing to help review grant proposals 
         this year, and also update your contact information.  <br/>
         
         If you volunteer, you will shortly receive the specified number of proposals (or fewer, 
         depending on the number of volunteers) and instructions for evaluation.  The proposals and 
         rating forms will be accessed and submitted through the Division of Library Development 
         Online Grant System.<br/><br/>
         Please don't hesitate to contact me at barbara.lilley@nysed.gov or (518) 486-4864 if you want 
         to talk this over before agreeing to review proposals or if you have any questions. <br/><br/>
         Sincerely,<br/>Barbara Lilley, Conservation/Preservation Program Officer
        </td>
      </tr>
    </table>
  </c:if>   
    
    
    
  <c:if test="${param.todo=='partremind'}">
  <br/>
    <form action="MassMailServlet" method="POST">
      <input type="HIDDEN" name="etype" value="partremind" />
      <input type="HIDDEN" name="mod" value="di" />
      Do you want to send a <b>Request for Participation Reminder</b> to all Active Discretionary Reviewers?<br/>
      <input type="SUBMIT" value="Send" />   
      <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" />
    </form>
    
    <br/>
    The following information will be included in the body of the email. <br/><br/>
    <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td>Dear FNAME LNAME,<BR/><BR/>          
          You were recently contacted regarding the NYS 
          Conservation/Preservation Discretionary Grant Program.<br/><br/>
          
          I would like to ask you to serve as a reviewer for the upcoming NYS 
          Conservation/Preservation Discretionary Grant Program.        
         If you have not already done so, please login to the Division of Library Development Online Grant System and fill out a 
         participation form indicating whether you would be willing to help review grant proposals 
         this year, and also update your contact information.  <br/><br/>
         
           If you volunteer, you will shortly receive the specified number of proposals (or fewer, 
         depending on the number of volunteers) and instructions for evaluation.  The proposals and 
         rating forms will be accessed and submitted through the Division of Library Development 
         Online Grant System.<br/><br/>
         
         The url to the Online Grant System is URL<br/>
         Your username is USERNAME<br/><br/>
         Sincerely,<br/>Barbara Lilley, Conservation/Preservation Program Officer
        </td>
      </tr>
    </table>   
    
    </c:if>   
    
    
    <c:if test="${param.todo=='assign'}">
    <table align="center" width="80%" summary="for layout only" >
      <tr>
        <th colspan="3">The following reviewers were assigned Discretionary grants for this fiscal year</th>
      </tr>
      <tr>
        <td><b>Name</b></td><td><b>Project Number</b></td><td><b>Project Title</b></td>
      </tr>
      
      <c:forEach var="row" items="${allAssign}">
      <tr>
        <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
        <td>030<c:out value="${row.reviewerAssigns[0].fccode}" />-<fmt:formatNumber value="${row.reviewerAssigns[0].fycode}" minIntegerDigits="2"/>
          -<fmt:formatNumber value="${row.reviewerAssigns[0].projseqnum}" pattern="####" minIntegerDigits="4" /></td>
        <td><c:out value="${row.reviewerAssigns[0].title}" /></td>
      </tr>
      </c:forEach>
      
      <tr>
        <td height="20" />
      </tr>
      <form method="POST" action="MassMailServlet">
      <tr>  
        <td colspan="2" valign="top">Enter the due date for reviewer evaluations. This due date will be included in
        each reviewer assignment email. </td>
        <td valign="top"><script type="text/javascript"> var cal = new CalendarPopup('testdiv1'); cal.showNavigationDropdowns();</script>
   
            <input type="TEXT" size="8" name="duedate" />
            <A HREF="#" onClick="cal.select(duedate,'anchor1','M/dd/yyyy'); return false;" 
                    NAME="anchor1" ID="anchor1"><img src="images/calendar.png" height="30" width="30" border="0" /></A><div id="testdiv1"></div></td>
      </tr>
      <tr>
        <td colspan="3">Do you want to send assignment emails to these reviewers?</td>
      </tr>
      <tr>
        <td colspan="3"><input type="SUBMIT" value="Send" />
                        <input type="HIDDEN" name="fycode" value='<c:out value="${fy}" />' />
                        <input type="HIDDEN" name="etype" value="assignment" />
                        <input type="HIDDEN" name="mod" value="di" />
                        <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" /></td>
      </tr>
      </form>
    </table>
    </c:if>
  
  </c:when>
  <c:otherwise >
    
    
    
  
  <h4>Choose the email type to send to Discretionary Reviewers</h4>
  
  <p>These mass mailings will be sent to all Discretionary Reviewers in the Reviewer table who
  are designated as active. If the reviewer does not have a valid email address, they will not be 
  receiving the notification.</p>
  <br/>
    
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Request for Participation</b> Email will ask
      Discretionary Reviewers to login to the system and fill out the number of grant proposals they are
      willing to review for this fiscal year.  The email will also instruct them to update their
      contact information.</td>
    </tr>
    <tr>
      <td><input type="BUTTON" value="Send Participation Mail" onclick="document.location.href='DiAdminRevMail.do?todo=participate'" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Request for Participation - Reminder</b> Email will remind
      Discretionary Reviewers to login to the system and fill out the number of grant proposals they are
      willing to review for this fiscal year.  The email will also instruct them to update their
      contact information.</td>
    </tr>
    <tr>
      <td><input type="BUTTON" value="Send Participation Reminder Mail" onclick="document.location.href='DiAdminRevMail.do?todo=partremind'" /></td>
    </tr>
  </table>
  <br/><br/>
  

  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Reviewer Assignments</b> Email will inform
      Discretionary Reviewers of the grant project numbers they have been assigned to review.  The email will
      be sent to all reviewers who have received assignments for the selected fiscal year.</td>
    </tr>        
    
    <form method="POST" action="MassMailServlet?etype=getAssign">
    <tr>
      <td>Select a fiscal year &nbsp;
          <select name="fycode">
            <c:forEach var="row" items="${dropDownList}">
              <option value='<c:out value="${row.id}" />' >
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select><br/><br/>
          <input type="HIDDEN" name="mod" value="di" />
          <input type="SUBMIT" value="Assignments Email" />
      </td>
    </tr>
    </form>
  </table>
      
  
  </c:otherwise>
  </c:choose>  
  
  
  </body>
</html>

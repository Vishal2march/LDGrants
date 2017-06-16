<%--
 * @author  shusak
 * @version 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  cpAdminSelectRecipients.jsp
 *
 * Description
 * This page is for c/p admin to add recipients to an email template. recipients can be for programs
 * sa/co/di, and be the project managers of awarded/denied grants for given FY.
 * modified 3/20/13 to include adding reviewer recipient groups for co/di
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
    <script language="Javascript" src="jscripts/calendar_popup.js"></script>
    <script language="Javascript">
function markAllBoxes(Frm)
{ 
  //alert('here');
    var chkCnt = 0;    
    for (i=0; i<=Frm.length - 1; i++){//count how many checkboxes on form
      if (Frm[i].type == 'checkbox')
        chkCnt++;  
    }    
    //alert('num boxes '+chkCnt);
    //for each checkbox, mark it as checked
    for(j=0; j<chkCnt; j++){   
      //alert(document.getElementsByName("AssignCollectionBean.grantItem["+j+"].sendmail"));
      //var chkBox = document.getElementsByName("AssignCollectionBean.grantItem["+j+"].sendmail");
      //alert(chkBox.defaultChecked);
      document.getElementsByName("grantItem["+j+"].sendmail")["grantItem["+j+"].sendmail"]["checked"]="checked";
    }  
}     
    </script>
    <STYLE type="text/css">
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
  </head>
  <body>
  
    
  <h4>Choose a recipient group</h4>
  <form method="POST" action="adminMassEmail.do?item=searchrecips" >
  <table width="100%" summary="for layout only">
    <tr>
      <td>
      <table width="100%" summary="for layout only">
        <tr>
          <td>
              <b>Project Manager Roles:</b><br/>
              <input type="RADIO" name="type" value="Approve" checked="checked" />Project Manager
              Awarded Projects<br/>
              <input type="RADIO" name="type" value="Denied" />Project Manager Denied Projects
           </td>
           <td>
              <b>Reviewer Roles:</b><br/>
              <input type="radio" name="type" value="reviewer"/>All Active Reviewers<br/>
              
              <input type="radio" name="type" value="assignrev"/>Reviewer's Assigned to Projects      
          </td>
        </tr>
      </table>
      </td>
    </tr>
    <tr>
        <td><b>Program:</b>
            <select name="fc">
                <option value="6">C/P Statutory</option>
                <option value="7">C/P Coordinated</option>
                <option value="5">C/P Discretionary</option>
                <option value="20">State Aid: CJH & NYHS</option>
            </select></td>
    </tr>
    <tr>
      <td><b>Fiscal Year:</b>
          <select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select></td>
      </tr>
      <tr>
        <td><input type="HIDDEN" name="wtid" value='<c:out value="${wtid}"/>' />
        <input type="SUBMIT" value="View Recipients" /></td>
      </tr>  
      <tr>
        <td><hr/></td>
    </tr>
  </table>
  </form>  
  
  
  <logic:notEmpty name="AssignCollectionBean">  
  
  <html:form action="/cpAddRecipients"> 
  <table width="100%" summary="for layout only">
    <tr>
      <td colspan="6"><b>Recipients that match the selected search criteria:</b></td>
    </tr>
    <tr>
      <td colspan="6">
        <input type="BUTTON" value="Select All" onclick="markAllBoxes(AssignCollectionBean);"/></td>
    </tr>
    <tr>
      <td><b>Add Recipient</b></td>
      <td><b>Name</b></td>
      <td><b>Email Address</b></td>
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>
      <td><b>Program</b>(for Reviewers only)</td>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allPotentialGrants" >
    <logic:iterate name="AssignCollectionBean" property="allPotentialGrants" id="grantItem" >   
    
        <tr>
            <td><html:checkbox name="grantItem" property="sendmail" indexed="true"/></td>
            <td><c:out value="${grantItem.projectManager.fname}"/> <c:out value="${grantItem.projectManager.lname}" /></td>
            <td><c:out value="${grantItem.projectManager.email}"/></td>
            <td><c:if test="${grantItem.projseqnum!=0}">
              03<fmt:formatNumber value="${grantItem.fccode}" minIntegerDigits="2"/>
              -<fmt:formatNumber value="${grantItem.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${grantItem.projseqnum}" pattern="####" />
              </c:if></td>
            <td><c:out value="${grantItem.instName}"/></td>
            <td><c:out value="${grantItem.program}"/>
            <html:hidden name="grantItem" property="name" indexed="true"/>
            <html:hidden name="grantItem" property="email" indexed="true"/>
            <html:hidden name="grantItem" property="title" indexed="true"/>
            <html:hidden name="grantItem" property="grantid" indexed="true"/>
            <html:hidden name="grantItem" property="fycode" indexed="true"/>
            <html:hidden name="grantItem" property="fccode" indexed="true"/>
            <html:hidden name="grantItem" property="projseqnum" indexed="true"/>
            <%--added 3/21/13 for reviewer recip group --%>
            <html:hidden name="grantItem" property="userid" indexed="true"/>
            <html:hidden name="grantItem" property="reviewerId" indexed="true"/>
            <html:hidden name="grantItem" property="program" indexed="true"/>
            </td>
        </tr>    
    </logic:iterate>
    </logic:present>
    
    <tr>
        <td height="20"> </td>
    </tr>
    <tr>
        <td colspan="5">CC email address: (comma separated for multiple email addresses)</td>
        <td><html:text property="emailAddress"/></td>
    </tr>
    <tr>
        <td height="20"> </td>
    </tr>
    
    <c:if test="${AssignCollectionBean.approvalType=='assignrev'}">
      <%--for REVIEWER ASSIGN emails only --%>
      <tr>
          <td colspan="5">Due Date for Reviewer Evaluations: (Due date will be included in the reviewer
            assignments email.)</td>
            
          <td valign="top">
          
          <script type="text/javascript"> var cal = new CalendarPopup(); cal.showNavigationDropdowns();</script>
   
            <html:text property="duedate" size="8"/>
            <A HREF="#" onClick="cal.select(AssignCollectionBean.duedate,'anchor1','M/dd/yyyy'); return false;" 
                    NAME="anchor1" ID="anchor1">
                    <img src="images/calendar.png" height="30" width="30" border="0" /></A>
                    <div id="testdiv1"></div></td>
     
      </tr>
      <tr>
        <td height="20"> </td>
      </tr>
    </c:if>    
    <tr>
        <td colspan="6"><b>***Only click the 'Add Recipients' button once. </b> Each recipient
        record checked above will be added as a recipient of this email.  On a later screen, 
        you may confirm recipients, view variable substitutions, and send the email.</td>
    </tr>
    <tr>
        <td colspan="6"><html:hidden property="workingTemplateId"/><html:hidden property="approvalType"/>
                        <html:hidden property="fycode"/><html:hidden property="fccode"/>
                        <html:submit value="Add Recipients"/></td>
    </tr>    
    </table>
    </html:form>    
  </logic:notEmpty>
  
      
  </body>
</html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
    <script language="Javascript">
function markAllBoxes(Frm)
{ 
    var chkCnt = 0;    
    for (i=0; i<=Frm.length - 1; i++){//count how many checkboxes on form
      if (Frm[i].type == 'checkbox')
        chkCnt++;  
    }    
  
    //for each checkbox, mark it as checked
    for(j=0; j<chkCnt; j++){       
      document.getElementsByName("grantItem["+j+"].sendmail")["grantItem["+j+"].sendmail"]["checked"]="checked";
    }  
}     
    </script>
  </head>
  <body>
  
  <h5>Email Recipients</h5>
  
  <form method="POST" action="adminEmailNav.do?item=viewrecipients" >
  <table width="100%" summary="for layout only">
    <tr>
      <td><b>Choose a recipient group</b></td>
    </tr>
    <tr>
      <td>Role:<br/>
      <input type="RADIO" name="type" value="Reviewer" />Active Reviewers<br/>
      <input type="radio" name="type" value="ReviewerAssign"/>Reviewers assigned to panel for FY<br/>
      <input type="RADIO" name="type" value="Approve" checked="checked" />PM/RMO
      Awarded Projects<br/>
      <input type="RADIO" name="type" value="Denied" />PM/RMO Denied Projects</td>
    </tr>
    <tr>
      <td>Fiscal Year:
          <select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select> <hr/></td>
      </tr>
      <tr>
        <td><input type="HIDDEN" name="wtid" value='<c:out value="${wtid}"/>' />
        <input type="SUBMIT" value="View Recipients" /></td>
      </tr>  
  </table>
  </form>  
  
  
  <logic:notEmpty name="AssignCollectionBean">  
  
  <html:form action="lgAddRecipients"> 
  <table width="100%" summary="for layout only">
    <tr>
      <th colspan="4">Recipients that match the selected search criteria:</th>
    </tr>
    <tr>
      <td colspan="4">
        <input type="BUTTON" value="Select All" onclick="markAllBoxes(AssignCollectionBean)"/></td>
    </tr>
    <tr>
      <td><b>Send Mail</b></td>
      <td><b>Name</b></td>
      <td><b>Email Address</b></td>
      <td><b>Project Number (for PM/RMO)</b></td>
      <td><b>Institution</b></td>
      <td><b>Panel Name (for Reviewers)</b></td>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allPotentialGrants" >
    <logic:iterate name="AssignCollectionBean" property="allPotentialGrants" id="grantItem" >   
    
        <tr>
            <td><html:checkbox name="grantItem" property="sendmail" indexed="true"/></td>
            <td><c:out value="${grantItem.projectManager.fname}"/> <c:out value="${grantItem.projectManager.lname}" /></td>
            <td><c:out value="${grantItem.projectManager.email}"/></td>
            <td><c:if test="${grantItem.projseqnum!=0}">
              05<fmt:formatNumber value="${grantItem.fccode}" />
              -<fmt:formatNumber value="${grantItem.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${grantItem.projseqnum}" pattern="####" />
              </c:if></td>
            <td><c:out value="${grantItem.instName}"/>  
            <td><c:out value="${grantItem.title}"/></td>
            <html:hidden name="grantItem" property="name" indexed="true"/>
            <html:hidden name="grantItem" property="email" indexed="true"/>
            <html:hidden name="grantItem" property="userid" indexed="true"/>
            <html:hidden name="grantItem" property="title" indexed="true"/>
            <html:hidden name="grantItem" property="staffid" indexed="true"/>
            <html:hidden name="grantItem" property="grantid" indexed="true"/>
            <html:hidden name="grantItem" property="fycode" indexed="true"/>
            <html:hidden name="grantItem" property="fccode" indexed="true"/>
            <html:hidden name="grantItem" property="projseqnum" indexed="true"/></td>
        </tr>    
    </logic:iterate>
    </logic:present>
    
    <tr>
        <td colspan="2">CC email address: (comma separated for multiple email addresses)</td>
        <td><html:text property="emailAddress"/></td>
    </tr>
    <tr>
        <td height="20"> </td>
    </tr>
    <tr>
        <td colspan="4"><b>***Only click the 'Add Recipients' button once. </b></td>
    </tr>
    <tr>
        <td colspan="4"><html:hidden property="workingTemplateId"/><html:hidden property="fycode"/>
                        <html:submit value="Add Recipients"/></td>
    </tr>    
    </table>
    </html:form>
    
    <%--<c:forEach var="row" items="${selectgroup}">
      <tr>
        <td><c:out value="${row.projectManager.fname}"/> <c:out value="${row.projectManager.lname}" /></td>
        <td><c:out value="${row.projectManager.email}"/></td>
        <td><c:if test="${row.projseqnum!=0}">
          05<fmt:formatNumber value="${row.fccode}" />
          -<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
          </c:if></td>
        <td><c:out value="${row.instName}"/></td>
      </tr>
    </c:forEach>
    <tr>
      <td colspan="3"><hr/>Optional CC email address: <input type="TEXT" name="cc" /></td>
    </tr>
    <tr>
      <td colspan="3"><input type="HIDDEN" name="wtid" value='<c:out value="${wtid}"/>' />
      <input type="HIDDEN" name="fc" value='<c:out value="${fc}"/>' />
      <input type="HIDDEN" name="fy" value='<c:out value="${fy}"/>' />
      <input type="HIDDEN" name="atype" value='<c:out value="${atype}"/>' />
      <input type="SUBMIT" value="Add"> this Recipient Group to email</td>
    </tr>
  </table>
  </form>--%>
    
  </logic:notEmpty>
    
  </body>
</html>
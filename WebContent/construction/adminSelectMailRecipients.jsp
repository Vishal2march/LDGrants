<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Email Recipients</title>
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
  
  <a href="constructionRecipients.action">New Recipients</a>
  
    
  <h4>Email Recipients</h4>
  
  <form method="POST" action="cnAdminEmailNav.do?item=searchrecipients" >
  <table width="100%" summary="for layout only">
    <tr>
      <td><b>Choose a recipient group</b></td>
    </tr>
    <tr>
      <td>Role:<br/>
      <input type="RADIO" name="type" value="Approve" checked="checked" />Project Manager of
      Awarded Projects<br/>
      <input type="RADIO" name="type" value="Denied" />Project Manager of Denied Projects</td>
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
  
  <html:form action="cnAddMailRecipients"> 
  <table width="100%" summary="for layout only">
    <tr>
      <th colspan="8">Recipients that match the selected search criteria:</th>
    </tr>
    <tr>
      <td colspan="8">
        <input type="BUTTON" value="Select All" onclick="markAllBoxes(AssignCollectionBean)"/></td>
    </tr>
    <tr>
      <td><b>Send Mail</b></td>
      <td><b>Name</b></td>
      <td><b>Email Address</b></td>
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>    
      <td><b>System Email</b></td>
      <td><b>System</b></td>
      <td><b>Title</b></td>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allPotentialGrants" >
    <logic:iterate name="AssignCollectionBean" property="allPotentialGrants" id="grantItem" >   
    
        <tr>
            <td><html:checkbox name="grantItem" property="sendmail" indexed="true"/></td>
            <td><c:out value="${grantItem.projectManager.fname}"/> <c:out value="${grantItem.projectManager.lname}" /></td>
            <td><c:out value="${grantItem.projectManager.email}"/></td>
            <td><c:if test="${grantItem.projseqnum!=0}">
              03<fmt:formatNumber value="${grantItem.fccode}" />
              -<fmt:formatNumber value="${grantItem.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${grantItem.projseqnum}" pattern="####" />
              </c:if></td>
            <td><c:out value="${grantItem.instName}"/></td>
            <td><c:out value="${grantItem.plsEmail}"/></td>
            <td><c:out value="${grantItem.systemName}"/></td>
            <td><c:out value="${grantItem.title}"/>
           
            <html:hidden name="grantItem" property="name" indexed="true"/>
            <html:hidden name="grantItem" property="email" indexed="true"/>
            <html:hidden name="grantItem" property="plsEmail" indexed="true"/>
            <html:hidden name="grantItem" property="userid" indexed="true"/>
            <html:hidden name="grantItem" property="title" indexed="true"/>
            <html:hidden name="grantItem" property="instName" indexed="true"/>
            <html:hidden name="grantItem" property="buildingName" indexed="true"/>
            <html:hidden name="grantItem" property="staffid" indexed="true"/>
            <html:hidden name="grantItem" property="grantid" indexed="true"/>
            <html:hidden name="grantItem" property="fycode" indexed="true"/>
            <html:hidden name="grantItem" property="fccode" indexed="true"/>
            <html:hidden name="grantItem" property="projseqnum" indexed="true"/></td>
        </tr>    
    </logic:iterate>
    </logic:present>
    
    <tr>
        <td colspan="6">CC email address: (comma separated for multiple email addresses)</td>
        <td colspan="2"><html:text property="emailAddress"/></td>
    </tr>
    <tr>
        <td height="20"> </td>
    </tr>
    <tr>
        <td colspan="8"><b>***Only click the 'Add Recipients' button once. </b></td>
    </tr>
    <tr>
        <td colspan="8"><html:hidden property="workingTemplateId"/><html:hidden property="fycode"/>
                        <html:submit value="Add Recipients"/></td>
    </tr>    
    </table>
    </html:form>
    
    </logic:notEmpty>
  
  </body>
</html>
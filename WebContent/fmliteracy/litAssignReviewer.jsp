<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
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
      document.getElementsByName("assignItem["+j+"].revassign")["assignItem["+j+"].revassign"]["checked"]="checked";
    }  
}     
    </script>
  </head>
  <body>
   
  <h4>Assign Reviewer to Grant Proposals</h4>
  
  <form method="POST" action="litAdminRevNav.do?item=assignlist">
  <table width="60%" align="center" class="borderbox" summary="for layout only">
    <tr>
      <th>Program:</th>
      <td><input type="RADIO" name="fc" value="40" checked="checked" />Adult Literacy
          <br/><input type="RADIO" name="fc" value="42" />Family Literacy   </td>
    </tr>
    <tr>
      <th>Fiscal Year:</th>
      <td><select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select> </td>
    </tr>
    <tr>
      <th>Reviewer:</th>
      <td><select name="revid">
            <c:forEach var="rev" items="${allReviewers}">
              <option value='<c:out value="${rev.revid}" />'><c:out value="${rev.fname}" /> <c:out value="${rev.lname}" /></option>
            </c:forEach>
          </select></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="SUBMIT" value="View Assignment List" /></td>
    </tr>  
  </table>
  </form>
  <br/>
  
  
  <logic:notEmpty name="AssignCollectionBean">
  
  <html:form action="litSaveAssignment">  
  <table width="100%" class="borderbox" summary="for layout only">
    <tr>
      <th colspan="4">Reviewer Assignments for <c:out value="${AssignCollectionBean.reviewerName}" /></th>
    </tr>
    <tr>
      <td colspan="4">
        <input type="BUTTON" value="Select All" onclick="markAllBoxes(AssignCollectionBean)"/></td>
    </tr>
    <tr>
      <th>Assigned</th>
      <th>Project Number</th>
      <th>Title</th>
      <th>Institution</th>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allAssignRecords" >
    <logic:iterate name="AssignCollectionBean" property="allAssignRecords" id="assignItem" >   
    
      <tr>
        <td><html:checkbox name="assignItem" property="revassign" indexed="true" />
            <html:hidden name="assignItem" property="assignid" indexed="true" />
            <html:hidden name="assignItem" property="graid" indexed="true" />
            <html:hidden name="assignItem" property="revid" indexed="true" /></td>
        <td>03<fmt:formatNumber value="${assignItem.fccode}"  />-<fmt:formatNumber value="${assignItem.fycode}" />
        -<fmt:formatNumber value="${assignItem.projseqnum}" pattern="####"/></td>
      
        <td><c:out value="${assignItem.title}" /></td>
        <td><c:out value="${assignItem.instname}" /></td>
      </tr>
    
    </logic:iterate>
    </logic:present>
    
    <tr>
      <td colspan="4" align="center"><html:submit value="Save" /></td>
    </tr>
  </table>  
  </html:form>
  
  </logic:notEmpty>
  
  </body>
</html>

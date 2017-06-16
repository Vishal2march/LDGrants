<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  
  <h4>Admin - Additional Sources of Funding for this Construction Project</h4>
  <p>List all funding sources that contribute to this construction project.  Note: The
  Application Form must be completed first, before you can complete the Additional 
  Sources of Funding section.</p>
    
      
  <c:if test="${noBuildingError!=null && noBuildingError=='true'}">
    <p><font color="Red">The Application Form must be completed first. You cannot fill out
    the Additional Sources of Funding section until the Application Form is completed and
    saved.</font></p>
  </c:if>
      
      
  <form method="POST" action="otherFundsNav.do">
      <p><input type="HIDDEN" name="i" value="addrecord" />
      <input type="hidden" name="mod" value="admncn"/>
      <input type="submit" name="btn" value="Add" />        
       Please save any changes before adding a new record.</p>
   </form>    
   
  
  <html:errors />
  <html:form action="/adminSaveOtherFunds">

    <table width="90%" summary="for layout only">
      <tr>
        <th width="33%">Fund Source</th>
        <th width="33%">Description</th>
        <th width="33%">Amount</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allFundSources" >
    <logic:iterate name="BudgetCollectionBean" property="allFundSources" id="fundItem">   
                      
       <c:url var="deleteURL" value="CnAdminDeleteFunds.do">
        <c:param name="m" value="admncn"/>
        <c:param name="fid" value="${fundItem.buildingFundId}"/>
       </c:url>
                   
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td width="33%">
          <html:select name="fundItem" property="fundSourceId" indexed="true">
            <html:optionsCollection name="fundItem" property="allPossibleFundSources" value="id" label="description"/>
          </html:select>     
          <br/><br/>
          <a href='<c:out value="${deleteURL}" />' >Delete</a>
        </td>      
        <td width="33%"><html:textarea name="fundItem" property="description" rows="5" cols="35" indexed="true" /></td>
         
        <td width="33%"><html:text name="fundItem" property="amountReceivedStr" indexed="true" />
            <html:hidden name="fundItem" property="buildingFundId" indexed="true" />
            <html:hidden name="fundItem" property="grantBuildingId" indexed="true" />
            <html:hidden name="fundItem" property="grantId" indexed="true" /></td>        
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>    
    
        <p align="center">
            <input type="HIDDEN" name="mod" value="admncn"/>
            <html:submit value="Save"/></p>
  
    </html:form>    
     
     
  <p>Back to application 
        <c:url var="checklistURL" value="cnAdminNav.do">
            <c:param name="item" value="grant"/> 
            <c:param name="id" value="${grantid}"/>
        </c:url>
    <a href='<c:out value="${checklistURL}"/>'>checklist</a>
  </p>
  
  </body>
</html>
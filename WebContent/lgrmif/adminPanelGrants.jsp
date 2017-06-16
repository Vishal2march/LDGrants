<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminPanelGrants</title>    
  </head>
  <body>
  
  <h5>Search/Add Grant projects to Panel</h5>
  
  <table width="80%" align="center" summary="for layout only">
  <form method="post" action="lgAdminPanel.do?item=grantsearch">
    <tr>
       <td>
          Select a Project Category<br/>
          <select name="projcategoryId" >               
             <c:forEach items="${dropDownCategories}" var="row">
                <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
             </c:forEach>            
             <option value="0">DORIS applications</option>
         </select>
        </td>
    </tr>
    <tr>
       <td>
         <input type="hidden" name="pid" value='<c:out value="${workpid}"/>' />
         <input type="hidden" name="fy" value='<c:out value="${workfy}"/>' />
         <input type="submit" value="Search"/></td>
    </tr>
 </form>
 </table>
    
    
    
    
  <br/><br/><hr/><br/>
  <logic:notEmpty name="AssignCollectionBean">
  <table width="90%" align="center" summary="for layout only">
    <thead>
      <tr>
        <td><b>Add</b></td><td><b>Project Number</b></td>
        <td><b>Institution</b></td><td><b>Category</b></td>
        <td><b>Assigned to Different Panel</b></td>
      </tr>
    </thead>
    
    <html:form action="lgSavePanelGrant">
    <logic:present name="AssignCollectionBean" property="allPotentialGrants" >
    <logic:iterate name="AssignCollectionBean" property="allPotentialGrants" id="grantItem" >   
        
       <tr>
        <td><html:checkbox name="grantItem" property="assignpanel" indexed="true" />
            <html:hidden name="grantItem" property="panelId" indexed="true" />
            <html:hidden name="grantItem" property="grantid" indexed="true" />
            <html:hidden name="grantItem" property="panelgrantId" indexed="true" />
            <html:hidden name="grantItem" property="assigndiffpanel" indexed="true" /></td>
        
        <td>05<c:out value="${grantItem.fccode}" />-<c:out value="${grantItem.fycode}" />
        -<c:out value="${grantItem.projseqnum}" /></td>      
        <td><c:out value="${grantItem.instName}" /></td>
        <td><c:out value="${grantItem.projcategory}" /></td>
        <td><c:choose><c:when test="${grantItem.assigndiffpanel=='true'}" >
             <b><c:out value="${grantItem.assigndiffpanel}" /></b>
             </c:when><c:otherwise>
                <c:out value="${grantItem.assigndiffpanel}" />
             </c:otherwise></c:choose></td>
       </tr>
               
    </logic:iterate>
    </logic:present>
       <tr>
         <td colspan="4"><html:submit value="Save"/></td>
       </tr>
  </html:form>
  </table>
</logic:notEmpty>
    
  </body>
</html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h4>FS10A - Proposed Amendment to a State Project</h4>
  
  <p>Add a new FS10A record only if your approved budget has been modified 
    during the course of the year. Specify the budget category, reason for the budget
    amendment, and the increase or decrease to your approved budget amount.</p>
    
  <c:choose >
  <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.fs10aComp=='true'}" >  
  
    <table width="90%" summary="for layout only">
      <tr>
        <th width="25%">Budget Category</th>
        <th width="45%">Description</th>
        <th width="15%">Subtotal Increase</th>
        <th width="15%">Subtotal Decrease</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allAmendRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allAmendRecords" id="amendItem" >   
    
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td width="25%"><c:out value="${amendItem.expname}"/></td>      
        <td width="45%"><c:out value="${amendItem.description}" /></td>
        
        <td width="15%"><fmt:formatNumber value="${amendItem.amountincr}" type="currency" maxFractionDigits="0" /></td>
        
        <td width="15%"><fmt:formatNumber value="${amendItem.amountdecr}" type="currency" maxFractionDigits="0" /></td>        
      </tr>
      </table><br/><br/>    
    
    </logic:iterate>
    </logic:present>
  
  </c:when>
  <c:otherwise>
  
   <form method="POST" action="fsaTasks.do">
      <input type="HIDDEN" name="i" value="addrecord" />
      <input type="hidden" name="mod" value='<c:out value="${param.m}"/>'/>
      <p><input type="submit" name="btn" value="Add" />        
       Please save any changes before adding a new record.</p>
    </form>
    
  <html:errors />
  <html:form action="/saveFsaRecords" >

    <table width="90%" summary="for layout only">
      <tr>
        <th width="25%">Budget Category</th>
        <th width="45%">Description</th>
        <th width="15%">Subtotal Increase</th>
        <th width="15%">Subtotal Decrease</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allAmendRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allAmendRecords" id="amendItem" >   
      <c:choose>
      <c:when test="${param.m=='sa'}">      
              <c:url value="saApplicantForms.do" var="deleteURL">
                <c:param name="item" value="confirmamenddelete" />
                <c:param name="id" value="${amendItem.id}" />
                <c:param name="p" value="sa" />
              </c:url>      
        </c:when>
        <c:when test="${param.m=='di'}">
              <c:url value="diInitialForms.do" var="deleteURL">
                <c:param name="i" value="confirmamenddelete" />
                <c:param name="id" value="${amendItem.id}" />
                <c:param name="p" value="di" />
              </c:url>     
        </c:when>
        <c:otherwise>
              <c:url value="coApplicantForms.do" var="deleteURL">
                <c:param name="item" value="confirmamenddelete" />
                <c:param name="id" value="${amendItem.id}" />
                <c:param name="p" value="co" />
               </c:url>      
        </c:otherwise>
        </c:choose>        
      
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td>
          <html:select name="amendItem" property="expcode" indexed="true">
            <html:optionsCollection name="amendItem" property="allExpenseCodes" value="id" label="description"/>
          </html:select>     
          <br/><br/>
          <a href='<c:out value="${deleteURL}" />' >Delete</a>
        </td>      
        <td><html:textarea name="amendItem" property="description" rows="5" cols="35" indexed="true" /></td>
        
        <td><html:text name="amendItem" property="amountincrStr" indexed="true" /></td>
        
        <td><html:text name="amendItem" property="amountdecrStr" indexed="true" />
            <html:hidden name="amendItem" property="id" indexed="true" /></td>        
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>
    
    <p align="center"><input type="hidden" name="mod" value='<c:out value="${param.m}"/>'/>
                      <html:submit value="Save"/></p>
  </html:form>
    
  </c:otherwise>
  </c:choose>  
  
  
  <c:if test="${appStatus.fs10ayn=='true'}">
    <p>FS10A Budget Amendment has been approved by administrator.</p>
  </c:if>   
    
  <p>The following FS10A form must be mailed to NYSED for C/P <i>Discretionary</i> and C/P <i>Coordinated</i> Project Budget Amendments. 
  Please print and sign 3 copies of the form in blue ink, and mail to the Library Development address listed on the form.
  <br/><br/>The form does Not need to be mailed in for C/P <i>Statutory</i> Budget Amendments.<br/>
    <a href="FsFormServlet?i=fs10a" target="_blank">View FS10A form - HTML</a>(opens in new window)<br/>
    <a href="FsFormServlet?i=fs10apdf" target="_blank">View FS10A form - PDF</a>(opens in new window)
  </p>    
  
  </body>
</html>
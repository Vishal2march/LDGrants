<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
    <script language="javascript" type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
    </script>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  III. Employee Benefits (Code 80)</b><br/>
  List all project employees as described under "Salaries."  
  Provide the amount of funds to be used for each individual's benefits.
 </p><br/>
 
  
  <c:forEach var="row" items="${fyWarnings}" >      
    <c:if test="${row.warning=='true'}" >
      <p align="center" class="error"><c:out value="${row.alWarning}" /></p>
    </c:if>        
  </c:forEach>
  

  <a name="year1" />
  <html:errors />
  <html:form action="/saveAlBenefit">
  <table width="90%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
        <c:url var="add1" value="AddBudgetItem">
            <c:param name="tab" value="2"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="al"/>
            <c:param name="t" value="3"/>
        </c:url>
        
        <%--SH 9/27/12 no longer used; FY13-14
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a>
        --%>
        <b>NOTICE:  The Benefits budget category can no longer be used.  Please list 
        project budget expenses under the categories: Purchased Services, Supplies & Materials, 
        Equipment, or Travel.</b>
      </td>
    </tr>
    
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
        <c:if test="${thisGrant.fycode==benefitItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <%--<c:url value="AlDeleteItem.do" var="deleteURL">
            <c:param name="item" value="budget" />
            <c:param name="tab" value="2" />
            <c:param name="id" value="${benefitItem.id}" />
            <c:param name="desc" value="${benefitItem.name}" />
            <c:param name="p" value="al3" />
        </c:url>--%>
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="2" />
            <c:param name="id" value="${benefitItem.id}" />
            <c:param name="p" value="al3" />
        </c:url>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Name</td>   
            <td>Benefits Percentage (decimal)</td>
          </tr>      
          <tr>
            <td><html:text name="benefitItem" property="name" indexed="true" /></td>
            <td><html:text name="benefitItem" property="benpercentStr" indexed="true" /></td>        
          </tr>        
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">ExpApproved</td>
          </tr>
          <tr>
            <td ><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>                
          <tr>
            <html:hidden name="benefitItem" property="id" indexed="true" />
            <html:hidden name="benefitItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Benefits Button below to Save all budget records on this page</td>
          </tr>
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <hr/>  
  
  
  <a name="year2" />
  <table width="90%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
          <c:url var="add2" value="AddBudgetItem">
            <c:param name="tab" value="2"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="al"/>
            <c:param name="t" value="3"/>
        </c:url>
        <%--SH 9/27/12 no longer used; FY13-14
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a>
        --%>
        <b>NOTICE:  The Benefits budget category can no longer be used.  Please list 
        project budget expenses under the categories: Purchased Services, Supplies & Materials, 
        Equipment, or Travel.</b>
      </td>
    </tr>
   
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
        <c:if test="${thisGrant.fycode +1==benefitItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="2" />
            <c:param name="id" value="${benefitItem.id}" />
            <c:param name="p" value="al3" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Name</td>   
            <td>Benefits Percentage (decimal)</td>
          </tr>      
          <tr>
            <td><html:text name="benefitItem" property="name" indexed="true" /></td>
            <td><html:text name="benefitItem" property="benpercentStr" indexed="true"/></td>    
          </tr>        
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">ExpApproved</td>
          </tr>
          <tr>
            <td ><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>                
          <tr>
            <html:hidden name="benefitItem" property="id" indexed="true" />
            <html:hidden name="benefitItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Benefits Button below to Save all budget records on this page</td>
          </tr>
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <br/><br/>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
    <p align="center"><input type="HIDDEN" name="currtab" value="2"/>
                    <input type="HIDDEN" name="i" value="3" />
                    <input type="SUBMIT" value="Save Benefits Records" /></p>  
  </logic:notEmpty>

</html:form>
<br/><hr/><br/>
  
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="4" ><b>Totals for Year 1</b></td>
    </tr>
    <tr>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber type="currency" value='${totalsMap["1"].totAmtReq}' maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["1"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="4"><hr/></td>
    </tr>
    <tr>
      <td colspan="4"><b>Totals for Year 2</b></td>
    </tr>
    <tr>   
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>  
  <hr/><br/><br/>
 
 <p align="center">***Remember to save your budget records first before editing the narrative***</p>
 
 <html:form action="/flSaveNarrative" >     
 <table width="95%" align="center" class="boxtype" summary="for layout only" >
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td><c:out value="${projNarrative.narrativeDescr}" /><br/></td>
    </tr>      
    <tr>
      <td height="20" />
    </tr>           
     
    <c:choose >
    <c:when test="${lduser.prgal=='read' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
      <tr>   
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </c:when>
    <c:otherwise >
      <tr>
        <td align="center">
          <div name="myToolBars" class="mceToolbarExternal"></div>
          <html:textarea property="narrative" cols="80" rows="17" /> 
          <html:hidden property="id" /> <html:hidden property="narrTypeID" />
          <html:hidden property="module" value="alb"/><input type="hidden" name="litpge" value='3'/>
        </td>  
      </tr>            
    </c:otherwise>
    </c:choose>
    <tr>
      <td align="center"><html:submit value="Save Narrative" /></td>
    </tr>          
  </table>
 </html:form>
  
  </body>
</html>

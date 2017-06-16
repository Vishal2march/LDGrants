<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
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
  Travel Expenses (Code 46)</b><br/>
  List specific project expenses that relate to Travel.  
  All expenses listed in this section must be fully described in the Application Narrative.  
 </p><br/>
 
  
  <c:forEach var="row" items="${fyWarnings}" >      
    <c:if test="${row.warning=='true'}" >
      <p align="center" class="error"><c:out value="${row.flWarning}" /></p>
    </c:if>        
  </c:forEach>
    
  <html:errors />
  <html:form action="/saveFlTravel">
  <a name="year1" ></a>
  <table width="90%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1(<c:out value="${thisGrant.fiscalyear}" />)</b>
          <c:url var="add1" value="AddBudgetItem">
            <c:param name="tab" value="6"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="7"/>
        </c:url>
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a>
      </td>
    </tr>   
      
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode}">
        
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="6" />
            <c:param name="id" value="${travelItem.id}" />
            <c:param name="p" value="fl7" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td><html:text name="travelItem" property="description" indexed="true" /></td>
            <td><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>    
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="travelItem" property="grantrequestStr"  indexed="true"/></td>
            
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>       
          <html:hidden name="travelItem" property="id" indexed="true" />       
          <html:hidden name="travelItem" property="fycode" indexed="true" />
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
          
        </table>
        </c:when>
        <c:otherwise>
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="description" indexed="true" />
                <html:hidden name="travelItem" property="purpose" indexed="true" />
                <html:hidden name="travelItem" property="costsummary" indexed="true" />
                <html:hidden name="travelItem" property="fycode" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>     
  </table>
 <hr/>
  
  
  <a name="year2"></a>
  <table width="90%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
      <c:url var="add2" value="AddBudgetItem">
            <c:param name="tab" value="6"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="7"/>
        </c:url>
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a></td>
    </tr>
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode +1}">
        
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="6" />
            <c:param name="id" value="${travelItem.id}" />
            <c:param name="p" value="fl7" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td><html:text name="travelItem" property="description" indexed="true" /></td>
            <td><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
          <html:hidden name="travelItem" property="id" indexed="true" />
          <html:hidden name="travelItem" property="fycode" indexed="true" />
          
        </table>
       </c:when>
        <c:otherwise>
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="description" indexed="true" />
                <html:hidden name="travelItem" property="purpose" indexed="true" />
                <html:hidden name="travelItem" property="costsummary" indexed="true" />
                <html:hidden name="travelItem" property="fycode" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>    
  </table>
  <hr/>
  
  
  <a name="year3"></a>
  <table width="90%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b>
      <c:url var="add3" value="AddBudgetItem">
            <c:param name="tab" value="6"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="7"/>
        </c:url>
        <a href='<c:out value="${add3}"/>' >Add Year 3 Record</a></td>
    </tr>
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode +2}">
        
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="6" />
            <c:param name="id" value="${travelItem.id}" />
            <c:param name="p" value="fl7" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td><html:text name="travelItem" property="description" indexed="true" /></td>
            <td><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
          <html:hidden name="travelItem" property="id" indexed="true" />
          <html:hidden name="travelItem" property="fycode" indexed="true" />
          
        </table>
        </c:when>
        <c:otherwise>
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="description" indexed="true" />
                <html:hidden name="travelItem" property="purpose" indexed="true" />
                <html:hidden name="travelItem" property="costsummary" indexed="true" />
                <html:hidden name="travelItem" property="fycode" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>    
  </table>  
  
  
  <br/><br/>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
  <p align="center"><input type="HIDDEN" name="currtab" value="6"/>
                    <input type="HIDDEN" name="i" value="7" />
                    <html:submit value="Save Travel Records" /></p>
  </logic:notEmpty>
  
</html:form>
<br/><hr/><br/>
   
 <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="5" ><b>Totals for Year 1</b></td>
    </tr>
    <tr>
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["1"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber type="currency" value='${totalsMap["1"].totAmtReq}' maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["1"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="5"><hr/></td>
    </tr>
    <tr>
      <td colspan="5"><b>Totals for Year 2</b></td>
    </tr>
    <tr>   
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["2"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="5"><hr/></td>
    </tr>
    <tr>
      <td colspan="5"><b>Totals for Year 3</b></td>
    </tr>
    <tr>  
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["3"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
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
    <c:when test="${lduser.prgfl=='read' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
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
          <html:hidden property="module" value="flb"/><input type="hidden" name="litpge" value='7'/>
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

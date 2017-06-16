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
    
  <c:set var="littab" value="${param.t}" />
  <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${littab=='1'}">
    <b>I. Professional Salaries  (Code 15)</b><br/>
  </c:when>
  <c:when test="${littab=='2'}">
    <b>II. Support Staff Salaries  (Code 16)</b><br/>
  </c:when>
  </c:choose>
  <br/>
  Include only staff that are employees of the agency. Do not include consultants
  or per diem staff here.  They belong in the Purchased Services budget category. One 
  full-time equivalent (FTE) equals one person working an entire week for each week of the 
  project. Express partial FTE's in decimals, e.g., a teacher working one day per week equal .2 FTE. 
  </p>  
  <br/><br/>
  
  <c:forEach var="row" items="${fyWarnings}" >      
    <c:if test="${row.warning=='true'}" >
      <p align="center" class="error"><c:out value="${row.flWarning}" /></p>
    </c:if>        
  </c:forEach>
  
  
  <a name="year1" />
  <html:errors />
  <html:form action="/saveFlPersonal">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
        <c:url var="add1" value="AddBudgetItem">
            <c:param name="tab" value="1"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <%--SH 9/27/12 no longer used; FY13-14
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a>
        --%>
        <b>NOTICE:  The Salaries budget category can no longer be used.  Please list 
        project budget expenses under the categories: Purchased Services, Supplies & Materials, 
        Equipment, or Travel.</b>
      </td>
    </tr>
    
    <tr>
      <td>   
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${thisGrant.fycode==personalItem.fycode}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Name</td>
            <td>Title</td>
            <td>Salary</td>
            <td>FTE</td>    
          </tr>     
          <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="1" />
            <c:param name="id" value="${personalItem.id}" />
            <c:param name="p" value="fl${littab}" />
          </c:url>
        
          <tr>
            <td><html:text name="personalItem" property="name" indexed="true" /></td>
            <td><html:text name="personalItem" property="title" indexed="true" /></td>
            <td><html:text name="personalItem" property="salaryrate" indexed="true" /></td>
            <td><html:text name="personalItem" property="fteStr" indexed="true" /></td>
          </tr>        
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <td width="20%">ExpApproved</td>
          </tr>
          <tr>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          
          <tr>
            <html:hidden name="personalItem" property="id" indexed="true" />
            <html:hidden name="personalItem" property="fycode" indexed="true" />
            <html:hidden name="personalItem" property="module" indexed="true" value="fl" />
            <html:hidden name="personalItem" property="typeCode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="4">Use the Save Salaries Button below to Save all budget records on this page</td>
          </tr>
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
 <br/><hr/><br/>


   

  <a name="year2" />
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
           <c:url var="add2" value="AddBudgetItem">
            <c:param name="tab" value="1"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        
        <%--SH 9/27/12 no longer used; FY13-14
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a>
        --%>
        <b>NOTICE:  The Salaries budget category can no longer be used.  Please list 
        project budget expenses under the categories: Purchased Services, Supplies & Materials, 
        Equipment, or Travel.</b>
      </td>
    </tr>    
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${thisGrant.fycode+1==personalItem.fycode}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Name</td>
            <td>Title</td>
            <td>Salary</td>
            <td>FTE</td>     
          </tr>     
          <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="1" />
            <c:param name="id" value="${personalItem.id}" />
            <c:param name="p" value="fl${littab}" />
          </c:url>
          
          <tr>
            <td><html:text name="personalItem" property="name" indexed="true" /></td>
            <td><html:text name="personalItem" property="title" indexed="true" /></td>
            <td><html:text name="personalItem" property="salaryrate" indexed="true" /></td>
            <td><html:text name="personalItem" property="fteStr" indexed="true" /></td>
          </tr>        
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <td width="20%">ExpApproved</td>
          </tr>
          <tr>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          
          <tr>
            <html:hidden name="personalItem" property="id" indexed="true" />
            <html:hidden name="personalItem" property="fycode" indexed="true" />
            <html:hidden name="personalItem" property="module" indexed="true" value="fl" />
            <html:hidden name="personalItem" property="typeCode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="4">Use the Save Salaries Button below to Save all budget records on this page</td>
          </tr>
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table><br/>
 
 <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
      <p align="center"><input type="HIDDEN" name="currtab" value="1"/>
                        <input type="HIDDEN" name="i" value='<c:out value="${littab}" />' />
                        <input type="SUBMIT" value="Save Salaries" /></p>
 </logic:notEmpty>

</html:form>
<br/><hr/><br/>
 
 <%-- bottom section containing totals--%>
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
          <html:hidden property="module" value="flb"/><input type="hidden" name="litpge" value='<c:out value="${littab}"/>'/>
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

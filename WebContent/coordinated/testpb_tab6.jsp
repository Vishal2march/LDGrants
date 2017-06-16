<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
    
  <p class="bdgtitle"><b>Project Budget<br/>
  VI. Travel Expenses</b><br/>
  List specific project expenses that relate to Travel.  
  All expenses listed in this section must be fully described in the "Project Description."  
 </p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt"> 
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=co">I. Personal Services</a></td>                
   
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=co">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=co">III. Contracted Services</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=4&p=co">IV. Supplies Materials & Equipment</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=co">V. Other Expenses</a></td>                
    
    <td class="scbgtselect">VI. Travel Expenses</td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent"/>
<br/><br/>


  <html:errors />
  <html:form action="/updateTravelExp">
  <a name="year1" ></a>
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1(<c:out value="${thisGrant.fiscalyear}" />)</b>
          <a href="AddBudgetItem?tab=6&fy=0&p=co">Add Year 1 Record</a>
      </td>
    </tr>   
      
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="6" />
              <c:param name="id" value="${travelItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="travelItem" property="description" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>      
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="travelItem" property="grantrequestStr"  indexed="true"/></td>
            
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>       
          <html:hidden name="travelItem" property="id" indexed="true" />       
          <html:hidden name="travelItem" property="fycode" indexed="true" />
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
          
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>     
  </table>
 <hr/>
  
  
  <a name="year2"></a>
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
      <a href="AddBudgetItem?tab=6&fy=1&p=co">Add Year 2 Record</a></td>
    </tr>
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode +1}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="6" />
              <c:param name="id" value="${travelItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="travelItem" property="description" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>        
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="instcontStr" indexed="true"/></td>
            <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
          <html:hidden name="travelItem" property="id" indexed="true" />
          <html:hidden name="travelItem" property="fycode" indexed="true" />
          
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    
  </table>
  <hr/> 
  
  
 
  <a name="year3" ></a>
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b>
      <a href="AddBudgetItem?tab=6&fy=2&p=co">Add Year 3 Record</a></td>
    </tr>
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${travelItem.fycode==thisGrant.fycode +2}">
        
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="6" />
              <c:param name="id" value="${travelItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="travelItem" property="description" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="purpose" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" /></td>
          </tr>           
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="instcontStr" indexed="true"/></td>
            <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>      
            <html:hidden name="travelItem" property="id" indexed="true" />
             <html:hidden name="travelItem" property="fycode" indexed="true" />
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <tr>
        <td align="center"><input type="HIDDEN" name="currtab" value="6"/>
                           <html:submit value="Save Travel Expense Records" /></td>
      </tr>
    </logic:notEmpty>
  </table>
  </html:form>
  

 <table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6" ><b>Travel Expense Totals for all Years</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amt Requested</td>
    <td>Amt Approved</td>
    <td>Exp Submitted</td>
    <td>Exp Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0"/></td>    
  </tr>
  <tr>
    <td height="15" colspan="6"><hr/></td>
  </tr>
  <tr>
    <td colspan="6"><b>Grand Total for all Budget Categories</b></td>
  </tr>
   <tr>   
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amt Requested</td>
    <td>Amt Approved</td>
    <td>Exp Submitted</td>
    <td>Exp Approved</td>
  </tr>
  <tr>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
  </tr> 
</table>
  
  
  
  
  
  </body>
</html>

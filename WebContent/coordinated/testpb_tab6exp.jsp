<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
    
    <a href="BudgetSelect?tab=1&p=coe">I. Personal Services</a></td>                
   
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=coe" >II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=coe">III. Contracted Services</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=4&p=coe">IV. Supplies Materials & Equipment</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=coe">V. Other Expenses</a></td>                
        
    <td class="scbgtselect">VI. Travel Expenses</td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent"/>
<br/><br/>


  <html:errors />
  <html:form action="/updateFinalExpenses">
  <a name="year1" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode==travelItem.fycode}">   
       
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${travelItem.description}" /></td>
            <td colspan="2"><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>      
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>      
            <html:hidden name="travelItem" property="description" indexed="true" />
            <html:hidden name="travelItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="travelItem" property="instcont" indexed="true" />
            <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
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
  
  
  
  
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>   
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode +1 ==travelItem.fycode}">   
       
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${travelItem.description}" /></td>
            <td colspan="2"><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>      
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>      
            <html:hidden name="travelItem" property="description" indexed="true" />
            <html:hidden name="travelItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="travelItem" property="instcont" indexed="true" />
            <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
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
  
  
  
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
  <tr>
    <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
  </tr>    
  <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode +2 ==travelItem.fycode}">   
       
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Description</td>  
            <td colspan="2">Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${travelItem.description}" /></td>
            <td colspan="2"><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>      
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>      
            <html:hidden name="travelItem" property="description" indexed="true" />
            <html:hidden name="travelItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="travelItem" property="instcont" indexed="true" />
            <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
            <html:hidden name="travelItem" property="id" indexed="true" />       
            <html:hidden name="travelItem" property="fycode" indexed="true" />
        </table>        
         
        </c:if>
        </logic:iterate>
        </logic:present>
        
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <tr>
            <td align="center"><input type="button" value="Save Travel Expenses" disabled="disabled" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><input type="HIDDEN" name="currtab" value="6"/>
                               <html:submit value="Save Travel Expenses" /></td>
          </tr>    
        </c:otherwise>
        </c:choose>
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
    <td>Amt Amended</td>
    <td>Exp Submitted</td>
    <td>Exp Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0"/></td>
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
    <td>Amt Amended</td>
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
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0"/></b>
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

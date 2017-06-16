<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab5exp.jsp
 * Creation/Modification History  :
 *     SHusak       8/13/07     Modified
 *
 * Description
 * This is the Other/Misc. Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- OTHER EXPENSES -->
<html>
<head>
</head>
<body>

<p class="bdgtitle"><b>Project Budget<br/>
  V. Other Expenses</b><br/>
  List specific project expenses that do not fit into any other budget category.  
  All expenses listed in this section must be fully described in the "Project Description."  
</p>

<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt"> 
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=coe">I. Personal Services</a></td>                
   
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=coe">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=coe">III. Contracted Services</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=4&p=coe">IV. Supplies Materials & Equipment</a></td>                
    
    <td class="scbgtselect">V. Other Expenses</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=coe">VI. Travel Expenses</a></td>                
    
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
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >  
        <c:if test="${thisGrant.fycode==otherExpItem.fycode}">   
       
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><c:out value="${otherExpItem.description}" /></td>
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
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="otherExpItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>      
            <html:hidden name="otherExpItem" property="description" indexed="true" />
            <html:hidden name="otherExpItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="instcont" indexed="true" />
            <html:hidden name="otherExpItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="otherExpItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="otherExpItem" property="expapproved"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="id" indexed="true" />       
            <html:hidden name="otherExpItem" property="fycode" indexed="true" />
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
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >
        <c:if test="${otherExpItem.fycode==thisGrant.fycode +1}">   
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><c:out value="${otherExpItem.description}" /></td>
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
          <tr>
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="otherExpItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
            
            <html:hidden name="otherExpItem" property="description" indexed="true" />
            <html:hidden name="otherExpItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="instcont" indexed="true" />
            <html:hidden name="otherExpItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="otherExpItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="otherExpItem" property="expapproved"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="id" indexed="true" />       
            <html:hidden name="otherExpItem" property="fycode" indexed="true" />    
          </tr>                 
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
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >
        <c:if test="${otherExpItem.fycode==thisGrant.fycode +2}">   
         
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><c:out value="${otherExpItem.description}" /></td>
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
          <tr>
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="otherExpItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
            
            <html:hidden name="otherExpItem" property="description" indexed="true" />
            <html:hidden name="otherExpItem" property="projtotal"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="instcont" indexed="true" />
            <html:hidden name="otherExpItem" property="grantrequest"  indexed="true"/>           
            <html:hidden name="otherExpItem" property="amountapproved"  indexed="true"/> 
            <html:hidden name="otherExpItem" property="expapproved"  indexed="true"/>  
            <html:hidden name="otherExpItem" property="id" indexed="true" />       
            <html:hidden name="otherExpItem" property="fycode" indexed="true" />    
          </tr>              
       </table>
         
        </c:if>
        </logic:iterate>
        </logic:present>
        
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <tr>
            <td align="center"><input type="button" value="Save Other Expenses" disabled="disabled" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><input type="HIDDEN" name="currtab" value="5"/>
                               <html:submit value="Save Other Expenses" /></td>
          </tr>    
        </c:otherwise>
        </c:choose>
    </logic:notEmpty>
  </table>
  </html:form>

  
  

 <table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6" ><b>Other Expense Totals for all Years</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.othProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0"/></td>    
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

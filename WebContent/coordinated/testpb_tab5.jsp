<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab5.jsp
 * Creation/Modification History  :
 *     SHusak       8/10/07     Modified
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
    
    <a href="BudgetSelect?tab=1&p=co">I. Personal Services</a></td>                
   
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=co">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=co">III. Contracted Services</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=4&p=co">IV. Supplies Materials & Equipment</a></td>                
    
    <td class="scbgtselect">V. Other Expenses</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=co">VI. Travel Expenses</a></td>                
    
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent"/>
<br/><br/>



   <p><b>Notice:</b><br/>
     Due to new OSC requirements, the Other Expenses category can no longer be used.  
     Please list budget items under the budget tab categories Personal Services, 
     Employee Benefits, Contracted Services, Supplies,Materials & Equipment, or
     Travel Expenses.</p>
    <br/><br/>


  <html:errors />
  <html:form action="/updateOtherExp">
  <a name="year1" ></a>
  <table width="90%" align="center" summary="for layout only" >
    <!--<tr>
      <td><b>Budget Year 1(<c:out value="${thisGrant.fiscalyear}" />)</b>
          <a href="AddBudgetItem?tab=5&fy=0&p=co">Add Year 1 Record</a>
      </td>
    </tr>   -->
      
    <tr>
      <td> 
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >  
        <c:if test="${otherExpItem.fycode==thisGrant.fycode}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><html:text name="otherExpItem" property="description" indexed="true" /></td>
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
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="otherExpItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="grantrequestStr"  indexed="true"/></td>
            
            <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>       
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
  
  
  <a name="year2"></a>
  <table width="90%" align="center" summary="for layout only" >
    <!--<tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
      <a href="AddBudgetItem?tab=5&fy=1&p=co">Add Year 2 Record</a></td>
    </tr>-->
      <c:set var="ptot" value="0"/>
      <c:set var="incont" value="0"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >  
        <c:if test="${otherExpItem.fycode==thisGrant.fycode +1}"> 
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>    
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="5" />
              <c:param name="id" value="${otherExpItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
          
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><html:text name="otherExpItem" property="description" indexed="true"/></td>
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
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="otherExpItem" property="instcontStr" indexed="true"/></td>
            <td><html:text name="otherExpItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
          <html:hidden name="otherExpItem" property="id" indexed="true" />
          <html:hidden name="otherExpItem" property="fycode" indexed="true" />
          
        </table>
          <c:set var="ptot" value="${ptot + otherExpItem.projtotal}"/>
          <c:set var="incont" value="${incont + otherExpItem.instcont}"/>
          <c:set var="amtreq" value="${amtreq + otherExpItem.grantrequest}"/>
          <c:set var="amtappr" value="${amtappr + otherExpItem.amountapproved}"/>
          <c:set var="expsub" value="${expsub + otherExpItem.expsubmitted}"/>
          <c:set var="expappr" value="${expappr + otherExpItem.expapproved}"/>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    
  </table>
  <hr/> 
  
  
 
  <a name="year3" ></a>
  <table width="90%" align="center" summary="for layout only" >
    <!--<tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b>
      <a href="AddBudgetItem?tab=5&fy=2&p=co">Add Year 3 Record</a></td>
    </tr>-->
      <c:set var="ptot" value="0"/>
      <c:set var="incont" value="0"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allExpRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >  
        <c:if test="${otherExpItem.fycode==thisGrant.fycode +2}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="5" />
              <c:param name="id" value="${otherExpItem.id}" />
              <c:param name="p" value="co" />
          </c:url>       
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3">Service Type</td>    
          </tr>      
          <tr>
            <td colspan="3"><html:text name="otherExpItem" property="description" indexed="true" /></td>
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
            <td><fmt:formatNumber value="${otherExpItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="otherExpItem" property="instcontStr" indexed="true"/></td>
            <td><html:text name="otherExpItem" property="grantrequestStr" indexed="true"/></td>
            <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>      
            <html:hidden name="otherExpItem" property="id" indexed="true" />
             <html:hidden name="otherExpItem" property="fycode" indexed="true" />
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
          <c:set var="ptot" value="${ptot + otherExpItem.projtotal}"/>
          <c:set var="incont" value="${incont + otherExpItem.instcont}"/>
          <c:set var="amtreq" value="${amtreq + otherExpItem.grantrequest}"/>
          <c:set var="amtappr" value="${amtappr + otherExpItem.amountapproved}"/>
          <c:set var="expsub" value="${expsub + otherExpItem.expsubmitted}"/>
          <c:set var="expappr" value="${expappr + otherExpItem.expapproved}"/>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
      <tr>
        <td align="center"><input type="HIDDEN" name="currtab" value="5"/>
                           <html:submit value="Save Other Expense Records" /></td>
      </tr>
    </logic:notEmpty>
  </table>
  </html:form>
  

 <table width="90%" summary="for layout only" align="center">
  <tr>
    <td colspan="6" ><b>Other Expense Totals for all Years</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.othProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0"/></td>
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

<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab1.jsp
 * Creation/Modification History  :
 *     SHusak       7/16/07     Created
 *
 * Description
 * This is the Personal Service Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- PERSONAL SERVICES -->
<html>
<head>
  <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
  <title></title>  
</head>
<body>


<p class="bdgtitle"><b>Project Budget<br/>
  I. Personal Services</b><br/>
  List all persons to be employed by the project and their titles.  
  After each entry indicate the full-time annual salary rate (even if the position is not full-time) and FTE rate.
</p>
 
  
   <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgtselect">      
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      I. Personal Services</td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=2&p=co">II. Employee Benefits</a></td>
          
      <td class="scbgt">
      <a href="BudgetSelect?tab=3&p=co">III. Contracted Services</a></td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=4&p=co">IV. Supplies Materials & Equipment</a></td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=5&p=co">V. Other Expenses</a></td>
      
      <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=co">VI. Travel Expenses</a></td>                
    
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" />
  <br/><br/>
    
    
    
  <% int index =0; %>
  <a name="year1" />
  <html:errors />
  <html:form action="/updatePersonal">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
        <a href="AddBudgetItem?tab=1&fy=0&p=co" >Add Year 1 Record</a>
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
            <td >Salary/Wage</td>
            <td >FTE/Hours</td>    
            <td>Salary*FTE</td>
            <td>Type</td>
          </tr>     
          <%--<c:url value="CoDeleteItem.do" var="deleteURL">
              <c:param name="item" value="budget" />
              <c:param name="tab" value="1" />
              <c:param name="id" value="${personalItem.id}" />
              <c:param name="desc" value="${personalItem.name}" />
              <c:param name="p" value="co" />
          </c:url>--%>
          <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="1" />
              <c:param name="id" value="${personalItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
          
          <tr>
            <td><html:text name="personalItem" property="name" indexed="true" /></td>
            <td><html:text name="personalItem" property="title" indexed="true" /></td>
            <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
            <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
           
            <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:select name="personalItem" property="typeCode" indexed="true">
              <c:if test="${personalItem.typeCode==0}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>
              </c:if> 
              <c:if test="${personalItem.typeCode==3}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>  
              </c:if>
              <c:if test="${personalItem.typeCode==4}" >
                <option value="3">Professional</option>
                <option value="4" selected="selected">Support Staff</option>
              </c:if>                 
            </html:select></td>
          </tr>        
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="personalItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          
          <tr>
            <html:hidden name="personalItem" property="id" indexed="true" />
            <html:hidden name="personalItem" property="fycode" indexed="true" />
            <td colspan="6"><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
          <% index++; %>
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
           <a href="AddBudgetItem?tab=1&fy=1&p=co" >Add Year 2 Record</a>
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
            <td >Salary/Wage</td>
            <td >FTE/Hours</td>     
            <td>Salary*FTE</td>
            <td>Type</td>
          </tr>     
          <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="1" />
              <c:param name="id" value="${personalItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
          
          <tr>
            <td><html:text name="personalItem" property="name" indexed="true" /></td>
            <td><html:text name="personalItem" property="title" indexed="true" /></td>
            <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
            <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
           
            <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:select name="personalItem" property="typeCode" indexed="true">
              <c:if test="${personalItem.typeCode==0}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>
              </c:if> 
              <c:if test="${personalItem.typeCode==3}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>  
              </c:if>
              <c:if test="${personalItem.typeCode==4}" >
                <option value="3">Professional</option>
                <option value="4" selected="selected">Support Staff</option>
              </c:if>                 
            </html:select></td>
          </tr>        
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="personalItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          
          <tr>
            <html:hidden name="personalItem" property="id" indexed="true" />
            <html:hidden name="personalItem" property="fycode" indexed="true" />
            <td colspan="6"><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
           <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
 <br/><hr/><br/>
  
  

  <a name="year3" />
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b>
          <a href="AddBudgetItem?tab=1&fy=2&p=co" >Add Year 3 Record</a>
      </td>
    </tr>   
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${thisGrant.fycode+2==personalItem.fycode}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Name</td>
            <td>Title</td>
            <td >Salary/Wage</td>
            <td >FTE/Hours</td>    
            <td>Salary*FTE</td>
            <td>Type</td>
          </tr>     
          <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="1" />
              <c:param name="id" value="${personalItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
          <tr>
            <td colspan="2"><html:text name="personalItem" property="name" indexed="true" /></td>
            <td><html:text name="personalItem" property="title" indexed="true" /></td>
            <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
            <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
           
            <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:select name="personalItem" property="typeCode" indexed="true">
              <c:if test="${personalItem.typeCode==0}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>
              </c:if> 
              <c:if test="${personalItem.typeCode==3}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>  
              </c:if>
              <c:if test="${personalItem.typeCode==4}" >
                <option value="3">Professional</option>
                <option value="4" selected="selected">Support Staff</option>
              </c:if>                 
            </html:select></td>
          </tr>        
          <tr>
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="personalItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
          
          <tr>
            <html:hidden name="personalItem" property="id" indexed="true" />
            <html:hidden name="personalItem" property="fycode" indexed="true" />
            <td colspan="6"><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
            <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
      <tr>
        <td align="center"><input type="HIDDEN" name="currtab" value="1"/>
                           <input type="SUBMIT" value="Save Personal Records" /></td>
      </tr>
    </logic:notEmpty>
  </table>
  </html:form>
 
  
    
  <%-- bottom section containing totals for this expense and total expenses--%>
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="6" ><b>Personal Service Totals for all Years</b></td>
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
      <td><fmt:formatNumber value="${totalsBean.perProjTot}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perInstCont}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0"/></td> 
    </tr>
    <tr>
      <td height="15" colspan="6"><hr/></td>
    </tr>
    <tr>
      <td colspan="6" ><b>Grand Totals for all Budget Categories</b></td>
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



<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  <h5>Proposed Amendment to a State Project Budget (Data entered here populates 
        the FS-10-A Form)</h5>
  
  <p>Add a new record only if your approved budget has been modified 
    during the course of the year. Specify the budget category, reason for the budget
    amendment (Description), and the increase or decrease to your approved budget amount.
    <b>You must have prior approval of this request from your Regional Advisory Officer (RAO) 
    before submitting this budget amendment template and mailing the accompanying paper FS-10-A
    form, if required.</b></p>
  
  
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
      <input type="hidden" name="mod" value="lg"/>
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
      
      <c:url value="lgApplicant.do" var="deleteURL">
        <c:param name="i" value="confirmamenddelete" />
        <c:param name="id" value="${amendItem.id}" />
        <c:param name="p" value="lg" />
      </c:url>                  
      
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td>
          <html:select name="amendItem" property="expcode" indexed="true">
            <html:optionsCollection name="amendItem" property="allExpenseCodes" value="id" label="description"/>
          </html:select>     
          <br/><br/>
          <a href='<c:out value="${deleteURL}" />' >Delete</a>
        </td>      
        <td><html:textarea name="amendItem" property="description" rows="5" cols="35"  indexed="true" /></td>
        
        <td><html:text name="amendItem" property="amountincrStr" indexed="true" /></td>
        
        <td><html:text name="amendItem" property="amountdecrStr" indexed="true" />
            <html:hidden name="amendItem" property="id" indexed="true" /></td>        
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>
    
    <p align="center"><input type="hidden" name="mod" value="lg"/>
                      <html:submit value="Save"/></p>
  </html:form>
    
  </c:otherwise>
  </c:choose>  
  
  
  <c:if test="${appStatus.fs10ayn=='true'}">
    <p>FS10A Budget Amendment has been approved by administrator.</p>
  </c:if>   
    
  <p>The following FS-10-A form must be mailed to NYSED for LGRMIF project <b>formal</b> 
  budget amendments only. 
  Please print and sign 3 copies of the form in blue ink, and mail to:<br/><br/>
  New York State Archives<br/>
  Grants Administration Unit<br/>
  9A81, Cultural Education Center<br/>
  Albany, NY 12230<br/><br/>
  <b>Again, prior approval from your Regional Advisory Officer is required.</b>
  <br/><br/>
  
    <a href="FsFormServlet?i=fs10a" target="_blank">FS-10-A form  HTML</a> (opens in new window)<br/>
    <a href="FsFormServlet?i=fs10apdf" target="_blank">FS-10-A form PDF</a> (opens in new window)
  </p>    
  
  </body>
</html>
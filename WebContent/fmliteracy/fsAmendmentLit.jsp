<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  fsAmendmentLit.jsp
 * Creation/Modification History  :    
 *     SHusak  ? Created
 *     2/11/16 Modified
 *
 * Description
 * This is amendment summary form for al/fl.  Records saved to FS10_RECORDS table in db. 
 * Modified 2/11/16 to add FY (note: no other programs (cp/lgrmif/construction) need FY so it is 
 * optional db field.  Allow user to separate amendments by year; then print only selected year on FS10A form.
 *NOTE: the fycode dropdown has hardcoded options (kbalsen wants only last 2 cycles; with fyrange not year)
--%>
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
  
  <h4>Budget Amendment Summary</h4>
  <p>Please contact Carol A. Desch (carol.desch@nysed.gov) before submitting any amendment information.</p>
  <p>FS-10-A must be submitted by mid-May each year, in order to be considered.</p>
  <br/><br/><br/>
  
  
  <c:choose >
  <c:when test="${appStatus.fs10aComp=='true'}" >  
  
    <table width="90%" summary="for layout only">
      <tr>
        <th width="25%">Budget Category</th>
        <th width="45%">Description</th>
        <th width="15%">Subtotal Increase</th>
        <th width="15%">Subtotal Decrease</th>
        <th>Year</th>
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
        <td width="15%">20<fmt:formatNumber value="${amendItem.fycode}" /></td> 
        
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
        <th>Fiscal Year</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allAmendRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allAmendRecords" id="amendItem" >   
      <c:choose>
      <c:when test="${param.m=='al'}">      
              <c:url value="liFinalForms.do" var="deleteURL">
                <c:param name="item" value="confirmamenddelete" />
                <c:param name="id" value="${amendItem.id}" />
                <c:param name="p" value="al" />
              </c:url>      
        </c:when>
        <c:otherwise>
              <c:url value="liFinalForms.do" var="deleteURL">
                <c:param name="item" value="confirmamenddelete" />
                <c:param name="id" value="${amendItem.id}" />
                <c:param name="p" value="fl" />
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
        <td><html:select name="amendItem" property="fycode" indexed="true">
                <html:option value="14">2013-14</html:option>
                <html:option value="15">2014-15</html:option>
                <html:option value="16">2015-16</html:option>
                <html:option value="17">2016-17</html:option>
                <html:option value="18">2017-18</html:option>
                <html:option value="19">2018-19</html:option>
            </html:select></td>
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>
    
    <p align="center"><input type="hidden" name="mod" value='<c:out value="${param.m}"/>'/>
                      <html:submit value="Save"/></p>
  </html:form>
    
  </c:otherwise>
  </c:choose>  
  
  
  <br/><hr/>
  
  <c:if test="${appStatus.fs10ayn=='true'}">
    <p>FS10A Budget Amendment has been approved by administrator.</p>
  </c:if>   
    
  <p>3 copies completed and mailed only if there is an amendment to the approved project budget.<br/><br/>
   
Literacy Library Services Grant Program<br/>
New York State Library<br/>
Division of Library Development<br/>
Cultural Education Center<br/>
Albany, NY 12230 <br/>
Attn: Barbara Massago, Room 10B41
  <br/><br/>
  
  
   <%-- rmvd 9/12/12 per CMoesch; this version prints grand total; lit needs year 1/year2 totals
   <a href="FsFormServlet?i=fs10a" target="_blank">FS-10-A form  HTML</a> (opens in new window)<br/>
    <a href="FsFormServlet?i=fs10apdf" target="_blank">FS-10-A form PDF</a> (opens in new window)
  --%>
  
  <form method="POST" action="FsFormServlet" target="_blank">
  
    <select name="fya" id="fya" >
      <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
      <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
      <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
    </select>
    <br/>
    <input type="RADIO" name="i" value="fs10a">HTML<br/>
    <input type="RADIO" name="i" value="fs10apdf" checked="checked">PDF  (preferred)
    <br/>
    <input type="SUBMIT" value="View" />    
  </form>
  </p>    
  
  
  </body>
</html>
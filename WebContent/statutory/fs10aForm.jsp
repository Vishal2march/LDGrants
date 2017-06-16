<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  fs10aForm.jsp
 * Creation/Modification History  :
 *
 *     SHusak       5/1/07     Created
 *
 * Description
 * This page allows the applicant to enter any ammendments to their initial amount
 * approved budget.  This is in place of mailing in an FS10A form. 
 * NO LONGER BEING USED 6/24/10 -SEE fs10aFormNew.jsp
 *  $Id: fs10aForm.jsp 4689 2009-06-11 14:30:57Z shusak $
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Statutory Aid - FS10A</title>
  </head>
  <body>    
 
  <%
   java.util.LinkedHashMap map = new java.util.LinkedHashMap();
   map.put("1", "Personal Service");
   map.put("2", "Employee Benefits");
   map.put("3", "Contracted Services");
   map.put("4", "Supplies,Materials,Equipment");
   map.put("5", "Other Expenses");

   pageContext.setAttribute("typeMap", map);
%>
  
  <h4>FS10A - Proposed Amendment to a State Project</h4>
  
  <p>Add a new FS10A record only if your approved budget has been modified 
    during the course of the year. Specify the budget category, reason for the budget
    amendment, and the increase or decrease to your approved budget amount.
  </p>
    
  <c:choose >
  <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.amendsubmitted=='true' || lduser.prgsa=='read'}" >  
  
  
  </c:when>
  <c:otherwise>
    
    <form method="POST" action="fsaTasks.do">
      <input type="HIDDEN" name="i" value="addrecord" />
      <p><input type="submit" name="btn" value="Add" />        
       Please save any changes before adding a new record.
      </p>
    </form>
 
    
  <form method="POST" action="fsaTasks.do?i=updatefsa">
  <c:set value="0" var="countRows" />
  <c:forEach var="fsaBean" items="${allFSRecords}" >          
      
      <c:url value="DeleteItem.do" var="deleteURL">
        <c:param name="item" value="fs10a" />
        <c:param name="id" value="${fsaBean.id}" />
        <c:param name="desc" value="${fsaBean.description}" />
        <c:param name="p" value="sa" />
      </c:url>
      
      <table align="center" width="95%" class="boxtype" summary="for layout only">
      <tr>
        <th><label for="cat">Budget Category</label></th>
        <th><label for="desc">Description</label></th>
        <th><label for="inc">Subtotal Increase</label></th>
        <th><label for="dec">Subtotal Decrease</label></th>
      </tr>    
      <tr>      
        <td>
          <select name='fsCat_<c:out value="${countRows}"/>' id="cat" >
            <c:forEach items="${typeMap}" var="item">
              <c:choose >
              <c:when test="${item.key==fsaBean.expcode}" >
                <option value='<c:out value="${item.key}" />' selected="selected" /><c:out value="${item.value}" />
              </c:when>
              <c:otherwise >
                <option value='<c:out value="${item.key}" />' /><c:out value="${item.value}" />
              </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>        
          <br/><br/>
          <a href='<c:out value="${deleteURL}" />' >Delete</a>
        </td>      
        <td>
          <textarea name='fsDesc_<c:out value="${countRows}"/>' rows="5" cols="30" id="desc" ><c:out value="${fsaBean.description}" /></textarea>
        </td>
        <td>
          <input type="TEXT" name='fsIncr_<c:out value="${countRows}"/>' value='<fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" />' size="8" style="text-align:right" id="inc" /> 
        </td>
        <td>
          <input type="TEXT" name='fsDecr_<c:out value="${countRows}"/>' value='<fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" />' size="8" style="text-align:right" id="dec" />
          <input type="HIDDEN" name='fsID_<c:out value="${countRows}"/>' value='<c:out value="${fsaBean.id}" />'   />
        </td>        
      </tr>
      </table>    
      <c:set var="countRows" value="${countRows+1}" /><br/>
      </c:forEach>
    
  
    <p align="center"><input type="submit" name="btn" value="Save" />
       <input type="HIDDEN" name="countRows" value='<c:out value="${countRows}" />' />
    </p>
  </form>     
  
 
  <c:if test="${appStatus.fs10ayn=='true'}">
    <p>FS10A Budget Amendment has been approved by administrator.</p>
  </c:if>   
    
  <p>The following FS10A form for Coordinated grants does not need to be mailed to the NYS Library.
  It is for reference only.<br/>
    <a href="FsFormServlet?i=fs10a" target="_blank">View FS10A form - HTML</a>(opens in new window)<br/>
    <a href="FsFormServlet?i=fs10apdf" target="_blank">View FS10A form - PDF</a>(opens in new window)
  </p>    
  
  </c:otherwise>
  </c:choose>
  
  
  </body>
</html>



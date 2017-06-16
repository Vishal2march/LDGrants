<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminFS10.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the sa/co/di/lg/fl/al admin to view a read only version of the
 * FS10A budget ammendment information that was entered by the applicant.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS10A</title>
  </head>
  <body>
 
  <h4>Budget Amendment Summary</h4>
    
    
  <c:forEach var="fsaBean" items="${allFSRecords}" >    
    
    <table width="95%" class="boxtype" summary="for layout only">
     <tr>
      <th width="25%">Budget Category</th>
      <th width="45%">Description</th>
      <th width="15%">Subtotal Increase</th>
      <th width="15%">Subtotal Decrease</th>
    </tr>      
    <tr>      
      <td width="25%"><c:out value="${fsaBean.expname}"/></td>
      <td width="45%"><c:out value="${fsaBean.description}" /></td>
      <td width="15%">
        <fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" />
      </td>
      <td width="15%">
        <fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" />
      </td>
    </tr>       
    </table>
    <br/><br/>
  </c:forEach>    
   
   
   
  <br/><br/>
  <table width="50%" class="boxtype">
    <form method="POST" action="fsaTasks.do?i=approvefsa">
    <tr>
      <td><b>Approve the FS-10-A Budget Amendment Summary</b></td>
    </tr>
    <tr>
      <td >
        <c:choose >
        <c:when test="${appStatus.fs10ayn=='true'}">
          <input type="checkbox" value="Y" name="fs10Ckbx" checked="checked"/>
        </c:when>
        <c:otherwise >
          <input type="checkbox" value="Y" name="fs10Ckbx"/>
        </c:otherwise>
        </c:choose>          
        FS-10-A Approved
      </td>
    </tr>
    <tr>
      <td><input type="hidden" name="mod" value='<c:out value="${param.p}"/>'/>
            <input type="SUBMIT" value="Save" /></td>
    </tr>
    </form>
  </table>    
            
  <br/>
  <p>
  
  <c:choose>
  <c:when test="${param.p=='fl' || param.p=='al'}">
    
        <%-- literacy needs totals on fs10a form by FY --%>
        <form method="POST" action="FsFormServlet" target="_blank">
        View the FS10A Form:<br/>
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
  
  </c:when>
  <c:otherwise>
    <%--all other programs print grand total--%>
    <a href="FsFormServlet?i=fs10a" target="_blank">FS10A form HTML</a> (opens in new window)
    <br/><br/>
    <a href="FsFormServlet?i=fs10apdf" target="_blank">FS10A form PDF</a> (opens in new window)
  </c:otherwise>
  </c:choose>
  
  
    <br/><br/>
    <c:choose>
    <c:when test="${param.p=='lg'}">
        <c:url var="backURL" value="lgAdminNav.do">
            <c:param name="id" value="${grantid}"/>
            <c:param name="item" value="grant"/>
        </c:url>
    </c:when>
    <c:when test="${param.p=='sa'}"> 
        <c:url var="backURL" value="saAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
    </c:when>
    <c:when test="${param.p=='co'}">
        <c:url var="backURL" value="coAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
    </c:when>
    <c:when test="${param.p=='di'}">
        <c:url var="backURL" value="diAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
    </c:when>
    <c:when test="${param.p=='al'}">
        <c:url var="backURL" value="alAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
    </c:when>
    <c:when test="${param.p=='fl'}">
        <c:url var="backURL" value="flAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
    </c:when>
    </c:choose>
    <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
  </p>
   
  </body>
</html>

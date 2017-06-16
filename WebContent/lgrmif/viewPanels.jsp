<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <%--<script type="text/javascript" src="dwr/interface/panelsearchScript.js"></script>
    <script type="text/javascript" src="dwr/engine.js"></script>
    <script type="text/javascript" src="dwr/util.js"></script>
    <script language="javascript">
    function showPanelsYear(fyid) 
    {
        panelsearchScript.getPanelsByYear(fyid, displayLgPanels);
        document.getElementById("allpanels").focus();
    }
    
    var cellFunctions =
    [
      function(SearchResultBean) { return "<a href='lgAdminPanel.do?item=panelrecord&id="+SearchResultBean.panelid+"'>Update<//a>";},
      function(SearchResultBean) { return "<a href='LgPanels.do?del=true&id="+SearchResultBean.panelid+"&n="+SearchResultBean.affiliation+"'>Delete<//a>";},
      function(SearchResultBean) { return SearchResultBean.affiliation; },
      function(SearchResultBean) { return SearchResultBean.year; },
      function(SearchResultBean) { return SearchResultBean.description; }
    ];
    
    function displayLgPanels(panelList)
    { 
        dwr.util.removeAllRows("allpanels");
        dwr.util.addRows("allpanels", panelList, cellFunctions,{escapeHtml:false });
    }    
    </script> --%>
  </head>
  <body>
  
  <h4>LGRMIF Panels</h4>
  
    <c:choose>
    <c:when test="${param.del=='true'}">
    
    <form action="lgAdminPanel.do?item=deletepanel" method="POST">
      Are you sure you want to delete the panel <c:out value="${param.n}"/> ?<br/>
      <input type="hidden" name="id" value='<c:out value="${param.id}"/>'/>
      <input type="submit" value="Delete"/>
    </form>
           
    </c:when>    
    <c:otherwise>
    
    
    <c:if test="${deletestatus==0}">
    <p>The Panel could not be deleted. If the Panel is associated with Grants or 
    Reviewers, it cannot be deleted.</p>
    </c:if>
    
    
    <table width="90%" align="center" summary="for layout only">
    <form action="lgAdminPanel.do?item=search" method="POST">
      <tr>
        <td>Search Panels by Fiscal Year<br/>
        <%--<select name="fyid" onchange="showPanelsYear(this.value);">--%>
        <select name="fyid">
          <c:forEach items="${dropDownList}" var="row">
            <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>
        </select>
        <input type="SUBMIT" value="Search"/>
        </td>              
      </tr>
      </form>
      <tr>
        <td height="10" />
      </tr>
      <tr>
        <td><a href="lgAdminPanel.do?item=loadAdd">Add Panel</a></td>
      </tr>      
    </table>
    
    
    <br/><br/><hr/>
    <logic:notEmpty name="allPanels">    
    
        <table width="90%" align="center" summary="for layout only">
          <thead>
          <tr>
            <td><b>Update</b></td><td><b>Delete</b></td>
            <td><b>Available/Awarded</b></td><td><b>Name</b></td>
            <td><b>Year</b></td><td><b>Description</b></td>
          </tr>
          </thead>          
         <%-- <tbody id="allpanels"></tbody>--%>
          
         <c:forEach items="${allPanels}" var="row">
            <c:url var="updateurl" value="lgAdminPanel.do">
              <c:param name="item" value="panelrecord" />
              <c:param name="id" value="${row.panelid}" />
            </c:url>
            <c:url var="delurl" value="LgPanels.do">
              <c:param name="n" value="${row.affiliation}"/>
              <c:param name="id" value="${row.panelid}" />
              <c:param name="del" value="true"/>
            </c:url>
            <c:url value="lgAdminPanel.do" var="amturl">
                <c:param name="item" value="moneyamts"/>
                <c:param name="fy" value="${row.fycode}"/>
            </c:url>
            <tr>
              <td><a href='<c:out value="${updateurl}"/>'>Update</a></td>
              <td><a href='<c:out value="${delurl}"/>'>Delete</a></td>
              <td><a href='<c:out value="${amturl}"/>'>Amounts</a></td>
              <td><c:out value="${row.affiliation}"/></td>
              <td><c:out value="${row.year}"/></td>
              <td><c:out value="${row.description}"/></td>
            </tr>     
          </c:forEach>
        </table>

    </logic:notEmpty>
    
    </c:otherwise>
    </c:choose>
    
  </body>
</html>

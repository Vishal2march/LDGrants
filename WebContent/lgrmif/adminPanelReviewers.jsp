<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminPanelReviewers</title>
    <%--<script type="text/javascript" src="dwr/interface/revsearchScript.js"></script>
    <script type="text/javascript" src="dwr/engine.js"></script>
    <script type="text/javascript" src="dwr/util.js"></script>
    <script language="javascript">
    function showMatchReviewers(name, panelid, fyid) 
    {
        if (name.length <2){ 
            dwr.util.removeAllRows("reviewers");
            return;
        } 
        revsearchScript.getReviewerByName(name, panelid, fyid, displayReviewers);
    }
    
    var cellFunctions =
    [
      function(SearchResultBean) { return "<a href='lgAdminPanel.do?item=addpanelrev&rid="+SearchResultBean.revid+"&pid="+SearchResultBean.panelid+"&a="+SearchResultBean.assignpanel+"'>Add to Panel<//a>";},
      function(SearchResultBean) { return (SearchResultBean.fname +" "+ SearchResultBean.lname); },
      function(SearchResultBean) { return SearchResultBean.affiliation; },
      function(SearchResultBean) { return SearchResultBean.acceptreview;},
      function(SearchResultBean) { return SearchResultBean.assignpanel;}
    ];
    
    function displayReviewers(reviewerList)
    { 
        dwr.util.removeAllRows("reviewers");
        dwr.util.addRows("reviewers", reviewerList, cellFunctions,{escapeHtml:false });
    }    
    </script> --%>
  </head>
  <body>
  
  <h5>Add Reviewers to Panel</h5>
   
   <form action="lgAdminPanel.do?item=searchrevs" method="POST"> 
    Search by Reviewer Last Name:
    <%--<input type="text" id="txt1" onkeyup="showMatchReviewers(this.value, pid.value, fid.value);">--%>
    <input type="text" name="txt1"/>
   
    <br/>(type at least first 2 letters of last name)
    <input type="hidden" name="pid" value='<c:out value="${param.pid}"/>' />
    <input type="hidden" name="fid" value='<c:out value="${param.fid}"/>' />
    <input type="SUBMIT" value="Search" />
    </form>
    
    
    <table width="90%" align="center" summary="for layout only">
        <thead>
          <tr>
            <td><b>ID</b></td><td><b>Name</b></td><td><b>Affiliation</b></td>
            <td><b>Active Reviewer</b></td><td><b>Already Assigned to a Panel</b></td>
          </tr>        
        </thead>
        <%--<tbody id="reviewers">  </tbody>  --%>
        
        <c:forEach items="${allReviewers}" var="row">
        <c:url var="updateurl" value="lgAdminPanel.do">
          <c:param name="item" value="addpanelrev" />
          <c:param name="rid" value="${row.revid}" />
          <c:param name="pid" value="${row.panelid}" />
          <c:param name="a" value="${row.assignpanel}" />
          <c:param name="r" value="${row.acceptreview}" />
        </c:url>

        <tr>
          <td><a href='<c:out value="${updateurl}"/>'>Add to Panel</a></td>
          <td><c:out value="${row.fname}"/> <c:out value="${row.lname}"/></td>
          <td><c:out value="${row.affiliation}"/></td>
          <td><c:out value="${row.acceptreview}"/></td>
          <td><c:out value="${row.assignpanel}"/></td>
        </tr>     
      </c:forEach>
                
    </table>
    
  </body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Panel Information</h4>
  
  <html:errors />
  
  <html:form action="/savePanel">
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <td>Panel Name</td>
      <td><html:text property="name" /></td>
    </tr>
    <tr>
      <td>Fiscal Year</td>
      <td><html:select property="fycode">
            <html:optionsCollection name="dropDownList" value="id" label="description" />
          </html:select></td>
    </tr>
    <tr>
      <td>Amount Available</td>
      <td><html:text property="amtavailable" /></td>
    </tr>
    <tr>
        <td>Access to Deliberation/Decision Notes</td>
        <td><html:select property="status">
                <html:option value="f">Full</html:option>
                <html:option value="p">Partial</html:option>
                <html:option value="n">None</html:option>        
            </html:select></td>
    </tr>
    <tr>
        <td colspan="2">Full: Reviewer's may edit the final deliberation decision notes, recommended amount, and score.  Reviewer
        evaluations/comments are accessible to all reviewers in the panel.<br/><br/>
Partial: RAO'S and Reviewer's with 'report access' may edit the decision notes and justification fields. Reviewer 
evaluations/comments are accessible to all reviewers in the panel.<br/><br/>
None: The deliberation/decision notes are not editable, nor is any other information.  The reviewer 
evaluations/comments are not accessible to other reviewers.</td>
    </tr>
    <tr>
      <td>Description</td>
      <td><html:textarea property="descr" rows="2" cols="20" /></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><html:hidden property="id" /><html:submit value="Save" /></td>
    </tr>  
  </table>
  </html:form>
  <br/>
  
  <logic:notEmpty name="panelReviewers">
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="4">Reviewers/Members of Panel</th>
    </tr>
    <tr>
      <td><b>Delete</b></td><td><b>Name</b></td><td><b>Title</b></td><td><b>Affiliation</b></td>
    </tr>
    <c:forEach var="row" items="${panelReviewers}">
        <c:url var="delurl" value="lgAdminPanel.do">
            <c:param name="item" value="delpanelrev"/>
            <c:param name="id" value="${row.panelreviewerId}"/>
            <c:param name="pid" value="${PanelBean.id}"/>
        </c:url>
        <tr>
          <td><a href='<c:out value="${delurl}"/>'>Delete from Panel</a></td>
          <td><c:out value="${row.fname}"/> <c:out value="${row.lname}"/></td>
          <td><c:out value="${row.title}"/></td>
          <td><c:out value="${row.affiliation}"/></td>
        </tr>
    </c:forEach>  
  </table>  
  </logic:notEmpty>
  
  <br/><hr/><br/>
  
  <logic:notEmpty name="panelGrants">
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="4">Grants Assigned to Panel</th>
    </tr>
    <tr>
      <td><b>Delete</b></td><td><b>Project Number</b></td><td><b>Institution</b></td><td><b>Category</b></td>
    </tr>
    <c:forEach var="row" items="${panelGrants}">
        <c:url var="delurl" value="lgAdminPanel.do">
            <c:param name="item" value="delpanelgra"/>
            <c:param name="id" value="${row.panelgrantId}"/>
            <c:param name="pid" value="${PanelBean.id}"/>
        </c:url>
        <tr>
          <td><a href='<c:out value="${delurl}"/>'>Delete from Panel</a></td>
          <td>05<c:out value="${row.fccode}"/>-<c:out value="${row.fycode}"/>
                -<fmt:formatNumber value="${row.projseqnum}" pattern="####"/></td>
          <td><c:out value="${row.instName}"/></td>
          <td><c:out value="${row.projcategory}"/></td>
        </tr>
    </c:forEach>  
  </table>  
  </logic:notEmpty>
  
  
  
  <br/><br/><br/>
  <c:url var="searchurl" value="LgPanelRevSearch.do">
    <c:param name="pid" value="${PanelBean.id}"/>
    <c:param name="fid" value="${PanelBean.fycode}"/>
  </c:url>
  <c:url var="grasearch" value="lgAdminPanel.do">
    <c:param name="item" value="loadgrasearch"/>
    <c:param name="pid" value="${PanelBean.id}"/>
    <c:param name="fid" value="${PanelBean.fycode}"/>
  </c:url>
  <a href='<c:out value="${searchurl}" />'>Search Reviewers</a> to add as members of panel<br/>
  <a href='<c:out value="${grasearch}"/>'>Search Grants</a> to add to panel
  </body>
</html>

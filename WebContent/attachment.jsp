<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.prog=='lg'}">
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Attachments help</a>
  <br/>
  </c:if>
  
  <h4>List of Attachments</h4>  
  
  <c:choose >
  <c:when test="${param.confirm=='true'}">

    <form method="POST" action="handleAttachment.do?i=delete" >
    
      <table width="60%" align="center" summary="for layout only">
        <tr>
          <td><input type="HIDDEN" name="prog" value='<c:out value="${docBean.program}" />' />
              <input type="hidden" name="docid" value='<c:out value="${docBean.id}" />' />
              Do you want to delete the attachment?
          </td>
        </tr>
        <tr>
          <td><input type="SUBMIT" value="Delete" />      
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" /></td>
        </tr>
      </table>
    </form>


  </c:when>
  <c:otherwise >
  
  
  <table border="1" class="graygrid" align="center" width="95%" summary="for layout only">
    <tr>
      <td colspan="7">These are the documents that have been attached to this grant application.<br/>
        Click on the Document Name to download the document, or click Delete to remove the
        document from this grant application.</td>
    </tr>
    <tr>
      <th>Action</th>
      <th>Document Name</th>
      <th>Description</th>
      <th>Document Type</th>
      <th>Document Size</th>
      <th>Date Attached</th>
      <th>Attached By</th>
    </tr>
    
    <c:forEach var="row" items="${allDocs}" >
    <tr>    
      <c:url value="DownloadServlet" var="downloadURL" >
        <c:param name="doc_id" value="${row.id}" />
      </c:url>      
      
      <c:url value="handleAttachment.do" var="deleteURL">
        <c:param name="i" value="confirmdel" />
        <c:param name="docid" value="${row.id}" />
        <c:param name="prog" value="${param.prog}" />
      </c:url>      
      
      <td><c:if test="${lduser.readaccess != 'true'}">  
              <a href='<c:out value="${deleteURL}" />'>Delete</a>
          </c:if>       
      </td>
      <td><a href='<c:out value="${downloadURL}" />'><c:out value="${row.name}" /></a></td>
      <td><c:out value="${row.description}" /></td>
      <td><c:out value="${row.docType}" /></td>
      <td><c:out value="${row.docSize}" /> bytes</td>
      <td><fmt:formatDate value="${row.dateCreated}" pattern="MM/dd/yyyy" /></td>
      <td><c:out value="${row.createdBy}" /></td>
    </tr>    
    </c:forEach>
  
    <tr>
      <td height="30" colspan="7" />
    </tr>
    <tr>
      <td colspan="7">
        <c:choose >
        <c:when test="${param.prog=='di'}">
          <a href="DiAddAttachment.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='lg'}">
          <a href="LgAddAttachment.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='co'}">
          <a href="CoAddDocs.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='fl'}">
          <a href="FlAddAttachment.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='al'}">
          <a href="AlAddAttachment.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='lgradmin'}">
          <a href="LgAdminAddAttach.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='litadmin'}">
          <a href="LitAdminAddAttach.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='cpadmin'}">
          <a href="CpAdminAddAttach.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='cn'}">
          <a href="constructionAddAttachment.do">Add an Attachment</a>
        </c:when>
        <c:when test="${param.prog=='cnreview'}">
          <a href="constructionAddAttachmentReview.do">Add an Attachment</a>
        </c:when>   
        <c:when test="${param.prog=='conadmin'}">
          <a href="CnAdminAddAttach.do">Add an Attachment</a>
        </c:when>   
        <c:when test="${param.prog=='staid'}">
          <a href="StateAidAddAttach.do">Add an Attachment</a>
        </c:when>
        </c:choose>
      </td>
    </tr>
  </table>
  
    
    
  </c:otherwise>
  </c:choose>
  
  </body>
</html>

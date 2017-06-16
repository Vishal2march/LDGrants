<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  downloadDocuments.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       7/23/07     Modified
 *
 * Description
 * This page allows admin and participating CO, DI, FL/AL apcnts,
 * read-only cn system reviewers, and dasny to view and
 * open documents that were attached to the grant app.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Attachments to Application</h4>
    
  <p>These are the documents that have been attached to this grant application.  You must
      have the appropriate software to open the document type.</p>
    
    
  <table width="98%" border="1" align="center" summary="for layout only"> 
    <tr>
      <th>Action</th>
      <th>Document Name</th>
      <th>Description</th>
      <th>Document Type</th>
      <th>Size</th>
      <th>Date Attached</th>
      <th>Attached By</th>
    </tr>
    
    <c:forEach var="row" items="${allDocs}" >
    <tr>
      <c:url value="DownloadServlet" var="downloadURL" >
        <c:param name="doc_id" value="${row.id}" />
      </c:url>
          
      <td><a href='<c:out value="${downloadURL}" />'>View</a></td>
      <td><c:out value="${row.name}" /></td>
      <td><c:out value="${row.description}" /></td>
      <td><c:out value="${row.docType}" /></td>
      <td><c:out value="${row.docSize}" /> bytes</td>
      <td><fmt:formatDate value="${row.dateCreated}" pattern="MM/dd/yyyy" /></td>
      <td><c:out value="${row.createdBy}" /></td>
    </tr>    
    </c:forEach>
  </table>  
      
  
  </body>
</html>

<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revGrantSummary.jsp
 * Creation/Modification History  :
 *
 *     SH       8/1/07     Created
 *
 * Description
 * This page allows the co/di/lit reviewer to download any documents that were attached
 * to the grant app. links to html and pdf versions of application and narratives.
 * link to rating sheet for this grant.
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
  
  <h4>Grant Proposal Information</h4>
  
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>
        <td><b>Title</b></td>
      </tr>
      <tr>
        <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
          </td>
        <td><c:out value="${thisGrant.instName}" /></td>
        <td><c:out value="${thisGrant.title}" /></td>
      </tr>
    </table>
    
    <br/>
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td>        
        <c:choose >
        <c:when test="${param.p=='co'}">
            <c:url var="ratingURL" value="coReviewerForms.do">
              <c:param name="item" value="rating" />
              <c:param name="id" value="${thisGrant.grantid}" />
              <c:param name="assign" value="${assignid}" />
              <c:param name="p" value="co" />
            </c:url>
            <c:url var="submitURL" value="coReviewerForms.do">
              <c:param name="item" value="submit" />
              <c:param name="p" value="co" />
            </c:url>
        </c:when>
        <c:when test="${param.p=='di'}">
            <c:url var="ratingURL" value="diReviewerForms.do">
              <c:param name="item" value="rating" />
              <c:param name="id" value="${thisGrant.grantid}" />
              <c:param name="assign" value="${assignid}" />
              <c:param name="p" value="di" />
            </c:url>
            <c:url var="submitURL" value="diReviewerForms.do">
              <c:param name="item" value="submit" />
              <c:param name="p" value="di" />
            </c:url>
        </c:when>
        <c:when test="${param.p=='li'}">
            <c:url var="ratingURL" value="liReviewer.do">
              <c:param name="item" value="rating" />
              <c:param name="id" value="${thisGrant.grantid}" />
              <c:param name="assign" value="${assignid}" />
              <c:param name="p" value="li"/>
            </c:url>
            <c:url var="submitURL" value="liReviewer.do">
              <c:param name="item" value="submit" />
              <c:param name="p" value="li" />
            </c:url>
        </c:when>
        </c:choose>
        Fill out a <a href='<c:out value="${ratingURL}" />'>Rating Form</a> for this Grant Proposal</td>
      </tr>
      <tr>
        <td class="error">Reviews are due by <fmt:formatDate value="${revDue.enddate}" pattern="MM/dd/yyyy" /></td>
      </tr>
      <tr>
        <td>When the Rating Form for this grant proposal is complete, you may submit it.  
        Once the Rating Form has been submitted, you will
          NOT be able to update scores or comments for this grant proposal. </td>
      </tr>
      <tr>
        <td><a href='<c:out value="${submitURL}" />'>Submit the Rating Form and Comments</a></td>
      </tr>
      </table>
      <br/>
     
      <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td colspan="2">The following links contain the coversheet, budget and narratives for 
        this grant proposal.  The files are accessable in HTML or PDF format (the file will open in a new window).
        </td>
      </tr>
      <tr>
        <td height="10" />
      </tr>
      <c:choose >
      <c:when test="${param.p=='di'}">
          <tr>
            <td><a href="PrintAppServlet?i=cover" target="_blank">Coversheet - HTML</a></td>
            <td><a href="PrintAppServlet?i=coverpdf" target="_blank" >Coversheet - PDF</a></td>
          </tr>
          <tr>       
            <td><a href="PrintAppServlet?i=revapp" target="_blank">Budget - HTML</a></td>
            <td><a href="PrintAppServlet?i=revapppdf" target="_blank" >Budget - PDF</a></td>
          </tr> 
      </c:when>
      <c:when test="${param.p=='co'}">
          <tr>
            <td><a href="PrintAppServlet?i=revapp" target="_blank">Coversheet, Budget - HTML</a></td>
            <td><a href="PrintAppServlet?i=revapppdf" target="_blank" >Coversheet, Budget- PDF</a></td>
          </tr>
      </c:when>
      <c:when test="${param.p=='li'}">
          <tr>
            <td><a href="liReviewer.do?item=fs">FS Forms</a></td>
          </tr>
          <tr>
            <td><a href="PrintAppServlet?i=cover" target="_blank">Cover page - HTML</a></td>
            <td><a href="PrintAppServlet?i=coverpdf" target="_blank" >Cover page - PDF</a></td>
          </tr>
          <tr>       
            <td><a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget - HTML</a></td>
            <td><a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank" >Budget - PDF</a></td>
          </tr>           
      </c:when>
      </c:choose>
      <tr>       
        <td><a href="PrintAppServlet?i=narr" target="_blank">Narratives - HTML</a></td>
        <td><a href="PrintAppServlet?i=narrpdf" target="_blank" >Narratives - PDF</a></td>
      </tr> 
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2">The following documents were attached to the grant proposal. To download a document,
        click on the View link. You must have the appropriate software to open the document type.</td>
      </tr>
      <tr>
        <td colspan="2">
          <table border="1" class="graygrid" align="center" width="90%" summary="for layout only">
          <tr>
            <th>Action</th>
            <th>Document Name</th>
            <th>Description</th>
            <th>Document Type</th>
            <th>Size</th>
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
          </tr>    
          </c:forEach>
          </table>  
        </td>
      </tr>
    </table>
    
    
  </body>
</html>

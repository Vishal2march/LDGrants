<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h5>Grant Application Information</h5>
  
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td><b>Project Number</b></td>
        <td><b>Institution</b></td>
        <td><b>Category</b></td>
      </tr>
      <tr>
        <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
          </td>
        <td><c:out value="${thisGrant.instName}" /></td>
        <td><c:out value="${thisGrant.projcategory}" /></td>
      </tr>
    </table>
    
    
    <br/>
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td>
        <c:url var="ratingURL" value="lgReviewer.do">
            <c:param name="item" value="rating" />
            <c:param name="id" value="${thisGrant.grantid}" />
            <c:param name="assign" value="${assignid}" />
            <c:param name="p" value="lg" />
          </c:url>
        Fill out an <a href='<c:out value="${ratingURL}" />'>Evaluation Form</a> for this Grant Proposal</td>
      </tr>
      <tr>
        <td height="10"/></tr>
      <tr>
        <td>Reviews are due by <fmt:formatDate value="${revDue.enddate}" pattern="MM/dd/yyyy" /></td>
      </tr>
      <tr>
        <td height="10"/></tr>
      <tr>
        <td>When the Evaluation Form for this grant proposal is complete, you may submit it.
        Once the Evaluation Form has been submitted, you will
          NOT be able to update any scores or comments for this grant proposal. </td>
      </tr>
      <tr>
        <td><a href="lgReviewer.do?item=submit&p=lg">Submit the Evaluation Form</a></td>
      </tr>      
      </table>
      <br/><br/><hr/>
     
      <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td colspan="2">The following links contain information about this grant proposal. The Application Sheet,
        Project Narrative (includes Budget Narrative), Project Budget, and FS-20
        are in separate files. The files are accessible in HTML or PDF format 
        (the files will open in a new window).
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td><a href="PrintAppServlet?i=cover" target="_blank">Application Sheet - HTML</a></td>
        <td><a href="PrintAppServlet?i=coverpdf" target="_blank" >Application Sheet - PDF</a></td>
      </tr>
      <tr>       
        <td><a href="PrintAppServlet?i=narr" target="_blank">Project Narrative - HTML</a></td>
        <td><a href="PrintAppServlet?i=narrpdf" target="_blank" >Project Narrative - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=budget&a=true" target="_blank">Project Budget - HTML</a></td>
        <td><a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >Project Budget - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=vq" target="_blank">LG-VQ Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=vqpdf" target="_blank" >LG-VQ Form - PDF</a></td>
      </tr> 
      
      <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
      <c:if test="${thisGrant.fycode<15}">
      <tr>       
        <td><a href="PrintAppServlet?i=im" target="_blank">LG-IM Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=impdf" target="_blank" >LG-IM Form - PDF</a></td>
      </tr> 
      </c:if>
      
      <tr><%--per DM 7/10/14, change from fs20 to fs10long --%>
        <td><a href="FsFormServlet?i=fs10&fy=0" target="_blank">Proposed Budget Summary (FS-10) - HTML</a></td>
        <td><a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">Proposed Budget Summary (FS-10) - PDF</a></td>        
      </tr>  
      <tr>
        <td><a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/Ineligible Expenditures & Required Forms - HTML</a></td>
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

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h3>Application Printouts</h3>  
  
  <b>Please use the following links to <i>print</i> or <i>save</i> 
  your application to your desktop:</b><br/><br/>
                  
  
  <a href="PrintAppServlet?i=app&a=false" target="_blank">Complete Application HTML</a>
  ( Application Sheet, Narratives, Budget, VQ-Form)
  <br/><br/><br/>
                  
    <a href="PrintAppServlet?i=cover" target="_blank">Application Sheet HTML</a><br/>
    <a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a> (Project, Budget & Final)<br/>
    <a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a><br/>
    <a href="PrintAppServlet?i=vq" target="_blank">VQ Form HTML</a><br/>
     <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
    <c:if test="${thisGrant.fycode<15}">
        <a href="PrintAppServlet?i=im" target="_blank">IM Form HTML</a><br/>
    </c:if>
    <a href="FsFormServlet?i=fs10&fy=0" target="_blank">FS10 Budget Summary HTML</a><br/><br/>
                                          
                                                                             
                                                                             
    <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Sheet PDF</a><br/>
    <a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a> (Project, Budget & Final)<br/>
    <a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a><br/>
    <a href="PrintAppServlet?i=vqpdf" target="_blank">VQ Form PDF</a> <br/>
     <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
    <c:if test="${thisGrant.fycode<15}">
        <a href="PrintAppServlet?i=impdf" target="_blank">IM Form PDF</a><br/>
    </c:if>
    <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">FS10 Budget Summary PDF</a>
  
  </body>
</html>
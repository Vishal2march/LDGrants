<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page isErrorPage="true" import="java.util.*, java.lang.Throwable, java.io.*" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Grant Application Error</title>
  </head>
  <body>
 
  <h3> You have reached this page because of the following error:</h3>
  <p><c:out value="${param.errormsg}" /></p>
  <p><c:out value="${errormsg}" /></p>
  
  
  
  <%   if(exception !=null){  %>
  
  <p>The Error name is: <%= exception.getClass().toString() %>. <br/><br/>
  The description is: <%= exception.getMessage().toString() %>. <br/><br/>
  
  The full stack trace is:<br/>
  <%
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    PrintWriter printWriter = new PrintWriter(charArrayWriter, true);
    exception.printStackTrace(printWriter);
    out.println(charArrayWriter.toString());
  %>  
 </p><br/>

<%  }  %>
  
  
  <c:if test="${param.sessionTime=='true'}" >
    <h3>
      Your session has expired. Please log in again.<br/>
      <%--for testing link to testlogin.jsp, for deployment link to index.jsp--%>
      <a href="index.jsp">Login</a>
    </h3>
  </c:if>
  
  
  </body>
</html>

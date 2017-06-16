<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>Construction Application Submission Complete</h4>
  
  <p>
  The Public Library Construction grant application for project number 
  03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
    has been submitted to your Public Library System. <br/><br/>
  
  <br/><br/>
  
  <form method="POST" action="constructionNav.do?item=homepage">
    <input type="submit" value="Construction Home Page"/>
    <input type="hidden" name="m" value="cn"/>
  </form>
  
  </p>
  
  </body>
</html>
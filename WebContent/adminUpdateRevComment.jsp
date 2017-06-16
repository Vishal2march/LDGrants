<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminUpdateRevComment.jsp
 * Creation/Modification History  :
 *
 *     SHusak       1/18/08     Created
 *
 * Description
 * This page allows the admin to update a reviewer comment. Used for CO and DI
 * reviewer comments.  Saves to struts action.  
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
    <h4>Edit Comment</h4>
    
    <html:errors />
    <html:form action="/saveRevComment">
    <table width="80%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td align="center">
          <html:textarea property="comment" rows="10" cols="50" /></td>
      </tr>
      <tr>
        <td align="center"><html:hidden property="module" />
          <html:hidden property="id" /><html:submit value="Save" /></td>
      </tr>
    </table>
    </html:form>
    
  </body>
</html>

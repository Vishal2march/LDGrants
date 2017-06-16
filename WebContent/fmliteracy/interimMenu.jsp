<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  interimMenu.jsp
 * Creation/Modification History  :
 *
 *     SHusak   Created
 *
 * Description
 * This page contains links to all interim narratives for al/fl appcnt.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.p=='fl'}" >
  
  <table class="boxtype" summary="for layout only">
    <tr>
      <th>Interim Report Narratives</th>
    </tr> 
    <tr>
      <td>        
        <a class="discnarr" href="liNarrative.do?t=irpt&id=52">I. Project Changes</a>       
      </td>
    </tr>
    <tr>
      <td height="10"/>
    </tr>
    <tr>
      <td>         
        <a class="discnarr" href="liNarrative.do?t=irpt&id=53">II. Expended Funds</a>          
      </td>
    </tr>
    <tr>
      <td height="10"/>
    </tr>
    <tr>
      <td>         
        <a class="discnarr" href="liNarrative.do?t=irpt&id=54">III. Anecdote</a>
      </td>
    </tr>
  </table>  
  </c:if>
  
  
  
  <c:if test="${param.p=='al'}" >
  
  <table class="boxtype" summary="for layout only">
    <tr>
      <th>Interim Report Narratives</th>
    </tr> 
    <tr>
      <td>        
        <a class="discnarr" href="liNarrative.do?t=airpt&id=52">I. Project Changes</a>       
      </td>
    </tr>
    <tr>
      <td height="10"/>
    </tr>
    <tr>
      <td>         
        <a class="discnarr" href="liNarrative.do?t=airpt&id=53">II. Expended Funds</a>          
      </td>
    </tr>
    <tr>
      <td height="10"/>
    </tr>
    <tr>
      <td>         
        <a class="discnarr" href="liNarrative.do?t=airpt&id=54">III. Anecdote</a>
      </td>
    </tr>
  </table>
  
  </c:if>
  </body>
</html>

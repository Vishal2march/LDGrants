<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  litAppEdlawTotal.jsp
 * Creation/Modification History  :    
 *     SHusak  2/8/16 Created
 *
 * Description
 * This is new literacy report requested by kbalsen. Lists all projects with proj#, title, library name, 
 and the 3 year total of edlaw amounts.  
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>litAppEdlawTotal</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <th>Adult/Family Literacy Report: applications with abstract and 3 year total Education Law amount</th>
    </tr>
  </table>
  <br/><br/>
  
  
  
  <display:table name="sessionScope.allGrants" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
   
    <display:column property="instName" title="System" sortable="true" headerClass="sortable"/>
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="title" />
           
    <display:column  property="totalRecommend3Year" format="$ {0,number,0,000}" title="3-Year EdLaw Total" total="true"/>
     
    <display:column property="summaryDescr" title="Abstract"/>
     
  </display:table>    
  
  
  </body>
</html>
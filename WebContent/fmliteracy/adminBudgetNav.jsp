<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.p=='fl'}">
  <%--FAMILY LITERACY LINKS--%>
  
  <c:choose>
  <c:when test="${thisGrant.fycode<14}">  
  
      <%--prior FY grants--%>  
      <table width="100%" summary="for layout only">
        <tr>
          <td class="litbgt">      
          <%--this link allows Jaws to skip over menu items--%>
          <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
          <a href="AdminBudgetSelect?tab=1&p=fl">I. Professional Salaries</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=2&p=fl">II. Support Staff Salaries</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=3&p=fl">III. Employee Benefits</a></td>
              
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=4&p=fl">IV. Purchased Services</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=5&p=fl">V. Supplies Materials</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=6&p=fl">VI. Equipment</a></td>
                
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=7&p=fl">VII. Travel Expenses</a></td> 
        </tr>
      </table>
      <a name="bdgcontent" id="bdgcontent" />
  
  </c:when>
  <c:otherwise>
    
      <%--starting FY2013-14--%>
      <table width="100%" summary="for layout only">
        <tr>
          <td class="litbgt">      
          <%--this link allows Jaws to skip over menu items--%>
          <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
          <a href="AdminBudgetSelect?tab=4&p=fl">Purchased Services</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=5&p=fl">Supplies Materials</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=6&p=fl">Equipment</a></td>
                
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=7&p=fl">Travel Expenses</a></td> 
        </tr>
      </table>
      <a name="bdgcontent" id="bdgcontent" />
      
  </c:otherwise>
  </c:choose>
    
  </c:if>
  
  
  <c:if test="${param.p=='al'}">  
  <%--ADULT LITERACY LINKS--%>
  
  <c:choose>
  <c:when test="${thisGrant.fycode<14}">  
      <%--prior FY grants--%>      
      <table width="100%" summary="for layout only">
        <tr>
          <td class="adminbgt">      
          <%--this link allows Jaws to skip over menu items--%>
          <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
          <a href="AdminBudgetSelect?tab=1&p=al">I. Professional Salaries</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=2&p=al">II. Support Staff Salaries</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=3&p=al">III. Employee Benefits</a></td>
              
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=4&p=al">IV. Purchased Services</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=5&p=al">V. Supplies Materials</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=6&p=al">VI. Equipment</a></td>
                
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=7&p=al">VII. Travel Expenses</a></td> 
        </tr>
      </table>
      <a name="bdgcontent" id="bdgcontent" />
   
  </c:when>
  <c:otherwise>
    
     <%--starting FY2013-14--%>
     <table width="100%" summary="for layout only">
        <tr>
          <td class="adminbgt">      
          <%--this link allows Jaws to skip over menu items--%>
          <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
          <a href="AdminBudgetSelect?tab=4&p=al">Purchased Services</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=5&p=al">Supplies & Materials</a></td>
          
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=6&p=al">Equipment</a></td>
                
          <td class="litbgt">
          <a href="AdminBudgetSelect?tab=7&p=al">Travel Expenses</a></td> 
        </tr>
      </table>
      <a name="bdgcontent" id="bdgcontent" />     
     
  </c:otherwise>
  </c:choose>
    
  </c:if>
    
  </body>
</html>

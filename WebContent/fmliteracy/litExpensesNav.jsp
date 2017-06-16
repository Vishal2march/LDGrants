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
        <a href="BudgetSelect?tab=1&p=fle">I. Professional Salaries</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=2&p=fle">II. Support Staff Salaries</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=3&p=fle">III. Employee Benefits</a></td>
            
        <td class="litbgt">
        <a href="BudgetSelect?tab=4&p=fle">IV. Purchased Services</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=5&p=fle">V. Supplies Materials</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=6&p=fle">VI. Equipment</a></td>
              
        <td class="litbgt">
        <a href="BudgetSelect?tab=7&p=fle">VII. Travel Expenses</a></td> 
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
            <a href="BudgetSelect?tab=4&p=fle">Purchased Services</a></td>
            
            <td class="litbgt">
            <a href="BudgetSelect?tab=5&p=fle">Supplies & Materials</a></td>
            
            <td class="litbgt">
            <a href="BudgetSelect?tab=6&p=fle">Equipment</a></td>
                  
            <td class="litbgt">
            <a href="BudgetSelect?tab=7&p=fle">Travel Expenses</a></td> 
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
        <td class="litbgt">      
        <%--this link allows Jaws to skip over menu items--%>
        <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
        <a href="BudgetSelect?tab=1&p=ale">I. Professional Salaries</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=2&p=ale">II. Support Staff Salaries</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=3&p=ale">III. Employee Benefits</a></td>
            
        <td class="litbgt">
        <a href="BudgetSelect?tab=4&p=ale">IV. Purchased Services</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=5&p=ale">V. Supplies Materials</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=6&p=ale">VI. Equipment</a></td>
        
        <td class="litbgt">
        <a href="BudgetSelect?tab=7&p=ale">VII. Travel Expenses</a></td> 
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
            <a href="BudgetSelect?tab=4&p=ale">Purchased Services</a></td>
            
            <td class="litbgt">
            <a href="BudgetSelect?tab=5&p=ale">Supplies & Materials</a></td>
            
            <td class="litbgt">
            <a href="BudgetSelect?tab=6&p=ale">Equipment</a></td>
            
            <td class="litbgt">
            <a href="BudgetSelect?tab=7&p=ale">Travel Expenses</a></td> 
          </tr>
        </table>
        <a name="bdgcontent" id="bdgcontent" /> 
    
    </c:otherwise>
    </c:choose>
    
  </c:if>
  
  
  </body>
</html>

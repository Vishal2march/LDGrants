<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  welcomePage.jsp
 * Creation/Modification History  :
 *     SLowe                  Created
 *      SH         9/4/07     Modified 
 *      GH         02/06/2017 Modified
 *
 * Description
 * This is the initial page after logging into LDGrants.
 * It will have links to the grant programs user is eligible to apply/administer/review.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*,java.util.*,mypackage.UserBean" errorPage="error.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>NYS Office of Cultural Education Online Grant System</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"  />
  </head>
<body>

<%
//get user information
HttpSession sess = request.getSession();
UserBean ub = (UserBean) sess.getAttribute("lduser");
 
// Get the file as an input stream
// The path is relative to application context,file is placed under public_html/docs
ArrayList fileLines = new ArrayList();

//announcements for sa and co apcnts and co reviewers
if(ub.getPrgsa()!=null || ub.getPrgco()!=null || ub.isReviewercoor() || ub.isTypeadmin() )
{
    InputStream is = config.getServletContext().getResourceAsStream("/docs/announcesc.txt");
    BufferedReader bis = null; 
    if(is !=null)
    {
      bis = new BufferedReader(new InputStreamReader(is));
      
      String line="";
      while((line=bis.readLine())!=null){
       fileLines.add(line);
      }
    }    
    if(is!=null)
      is.close();
    if(bis!=null)
      bis.close();
}
//--------------------------------------------------------------------
/*
* commented out 8/31/11-combining disc/lit announcements with construct into 1 document
//announcements for di apcnts and reviewers
if(ub.getPrgdi()!=null || ub.isReviewerdisc() || ub.isTypeadmin())
{
    InputStream is = config.getServletContext().getResourceAsStream("/docs/announced.txt");
    BufferedReader bis = null; 
    if(is !=null)
    {
      bis = new BufferedReader(new InputStreamReader(is));
      
      String line="";
      while((line=bis.readLine())!=null){
       fileLines.add(line);
      }
    }    
    if(is!=null)
      is.close();
    if(bis!=null)
      bis.close();  
}
//--------------------------------------------------------------------
//announcements for fl/al apcnts
if(ub.getPrgfl()!=null || ub.getPrgal()!=null || ub.isTypeadmin())
{
    InputStream is = config.getServletContext().getResourceAsStream("/docs/announcelit.txt");
    BufferedReader bis = null; 
    if(is !=null)
    {
      bis = new BufferedReader(new InputStreamReader(is));
      
      String line="";
      while((line=bis.readLine())!=null){
       fileLines.add(line);
      }
    }    
    if(is!=null)
      is.close();
    if(bis!=null)
      bis.close();  
}*/

//announce for disc/al/fl/cn/lgrmif 8/31/11
    InputStream is = config.getServletContext().getResourceAsStream("/docs/announceld.txt");
    BufferedReader bis = null; 
    if(is !=null)
    {
      bis = new BufferedReader(new InputStreamReader(is));
      
      String line="";
      while((line=bis.readLine())!=null){
       fileLines.add(line);
      }
    }    
    if(is!=null)
      is.close();
    if(bis!=null)
      bis.close();  
%>

<table width="100%" class="boxtype" summary="for layout only">
  <tr>
    <td>New York State Education Department<br/>
        Office of Cultural Education<br/>
        <a href="http://eservices.nysed.gov/logoff/logoff.jsp?appName=LDGrants&link=https://eservices.nysed.gov/ldgrants">Logout</a>
    </td>
    <td align="right">
      <a href="http://www.archives.nysed.gov" target="_blank">
        <img src="images/nysa.jpg" border="0" alt="NYS Archives"/>
      </a>&nbsp&nbsp
      <a href="http://www.nysl.nysed.gov/" target="_blank">
        <img src="images/nysllogo.jpg" border="0" alt="NYS Library"/>
      </a> <br/><font size="1">Opens in new window.</font>
    </td>        
  </tr>
</table><br/>

 
  <table width="100%" summary="for layout only">
    <tr>
      <td colspan="2" height="15" />
    </tr>
    <tr>
      <td valign="top" >
        <table width="100%" class="boxtype" summary="for layout only">
          <tr>
            <td><b>Announcements</b></td>
          </tr>
          <% for(int i=0; i<fileLines.size(); i++) { %>
          
            <tr>
              <td><%= (String) fileLines.get(i)   %></td>
            </tr>
            
          <% }//end for loop   %>            
        </table>
      </td>        
      
      
      <td width="70%" valign="top" >
        <table width="80%" summary="for layout only">
          <tr>
            <td align="center"><h3>Welcome to the New York State Office of Cultural 
            Education Online Grant System: <c:out value="${lduser.userid}" /></h3></td>
          </tr>
             
        <ul>
         <c:if test="${lduser.typeapcnt=='true'}">  
             <tr>
               <td><b>These are the grant programs you may apply for:</b></td>
             </tr>
             
              <c:if test="${lduser.prgNycStateaid != null}" >
              <tr>
                <td>
                  <li><A href="stateaidNav.do?item=homepage&m=staid">State Aid: Center for Jewish History & NY Historical Society</A></li>
                </td>
              </tr>
              </c:if>   
           
              <c:if test="${lduser.prgsa != null}" >
              <tr>
                <td>
                  <li><A href="statutoryNav.do?item=homepage&m=sa">Conservation/Preservation - Statutory Application</A></li>
                </td>
              </tr>
              </c:if>        
              
              <c:if test="${lduser.prgco != null}" > 
              <tr>
                <td><li>
                 <a href="coordinatedNav.do?item=homepage&m=co">Conservation/Preservation - Coordinated Application</a></li>
                </td>
              </tr>
              </c:if>
              
              <c:if test="${lduser.prgdi != null}" > 
              <tr>
                <td><li>
                  <a href="discretionaryNav.do?item=homepage&m=di">Conservation/Preservation - Discretionary Application</a></li>
                </td>
              </tr>
              </c:if>    
              
              <c:if test="${lduser.prgfl != null}" > 
              <tr>
                <td><li>
                  <%-- <a href="fmliteracyApplication.do?item=homepage&m=f">Family Literacy</a> --%>
                  <a href="familylitlandingpage.action">Family Literacy</a></li>
                </td>
              </tr>
              </c:if>    
              
              <c:if test="${lduser.prgal != null}" > 
              <tr>
                <td><li>
                  <a href="adultlitlandingpage.action">Adult Literacy</a></li>
                </td>
              </tr>
              </c:if>    
             
             <c:if test="${lduser.prglg != null}" >
              <tr>
                <td><li><a href="lgrmifNav.do?item=homepage&m=lg">LGRMIF</a></li>
                <br/>
              <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">LGRMIF Application help</a>
            </td>
              </tr>
             </c:if>
             
             <c:if test="${lduser.prgconstruction != null}">
             <tr><td><li>
                <a href="constructionNav.do?item=homepage&m=cn">Library Construction Application</a> 
             </li></td></tr> 
             </c:if>
        </c:if>
            
          
          
        <c:if test="${lduser.typeadmin=='true'}">
            <tr>
              <td><br/><b>These are the grant programs you may view as Administrator:</b></td>
            </tr>
            
            <c:if test="${lduser.adminstat != null}" >
                <tr>
                  <td><li>
                    <A href="saAdminNav.do?item=home&m=6">Conservation & Preservation - Statutory</A></li>
                  </td>
                </tr>
                
                <tr><%--11/13/14 adding stateaid admin access to cp admin (reusing entitlement attribute) --%>
                  <td><li>
                    <A href="staidAdminNav.do?item=home&m=20">State Aid Admin: Center for Jewish History & NY Historical Society</A></li>
                  </td>
                </tr>
            </c:if>        
            
            <c:if test="${lduser.admincoor != null}" > 
            <tr>
              <td><li>
                 <a href="coAdminNav.do?item=home&m=7">Conservation & Preservation - Coordinated</a></li>
              </td>
            </tr>
            </c:if>
            
            <c:if test="${lduser.admindisc != null}" > 
            <tr>
              <td><li>
               <a href="diAdminNav.do?item=home&m=5">Conservation & Preservation - Discretionary</a></li></td>
            </tr>          
            </c:if>       
            
            <c:if test="${lduser.adminal != null}" > 
            <tr>
              <td><li><%-- <a href="alAdminNav.do?item=loadLitAdminHome&m=40">Adult Literacy</a>  --%>
              		<a href="adultLiteracyAdminLandingPage.action">Adult Literacy</a></li></td>
            </tr>   
            </c:if>    
            
            <c:if test="${lduser.adminfl != null}" > 
            <tr>
              <td><li><%--<a href="flAdminNav.do?item=loadLitAdminHome&m=42">Family Literacy</a> --%>
              		<a href="familyLiteracyAdminLandingPage.action">Family Literacy</a></li></td>
            </tr> 
            </c:if>    
            
            <c:if test="${lduser.lgadmin==true}">
            <tr>
              <td><li><a href="lgAdminNav.do?item=home&m=80">LGRMIF</a></li></td>
            </tr>
            </c:if>
            
            <c:if test="${lduser.admconstruction != null}" > 
            <tr>
              <td><li>
                <a href="cnAdminNav.do?item=home">Construction</a></li></td>
            </tr>     
            </c:if>     
            
            <c:if test="${lduser.dasnyReviewer==true}">
            <tr>
              <td><li>
                <a href="cnDasnyNav.do?task=home">DASNY - Public Library Construction</a></li></td>
            </tr>     
            </c:if>
            
        </c:if>
          
        
        
        <c:if test="${lduser.typerev=='true'}">
            <tr>
              <td><br/><b>These are the grant programs you may Review:</b></td>
            </tr>
            
            <c:if test="${lduser.reviewercoor==true}">
              <tr>
                <td><li>
                  <a href="coReviewerForms.do?item=home">Conservation & Preservation - Coordinated Reviewer</a></li></td>
              </tr>
            </c:if>
            
            <c:if test="${lduser.reviewerdisc==true}" >
              <tr>
                <td><li><a href="diReviewerForms.do?item=home">Conservation & Preservation - Discretionary Reviewer</a></li></td>
              </tr>
            </c:if>      
            
            <c:if test="${lduser.reviewerfl==true}" >
              <tr>
                <td><li><a href="liReviewer.do?item=home">Family/Adult Literacy Reviewer</a></li></td>
              </tr>
            </c:if>
            
            <c:if test="${lduser.lgreviewer==true}">
            <tr>
              <td><li><a href="lgReviewer.do?item=home">LGRMIF Reviewer</a></li></td>
            </tr>
            </c:if>
            
            <c:if test="${lduser.reviewconstruction==true}">
            <tr>
              <td><li><a href="cnReviewNav.do?item=memberapps">Construction -Library System Reviewer</a></li></td>
            </tr>
            </c:if>
         </c:if>         
        
         </ul>
         <tr>
          <td height="20" />
        </tr>       
        <tr>
          <td>Conservation/Preservation Grant Program <a href="grantUpdates.jsp">Updates</a></td>
        </tr> 
        <tr>
          <td>Construction Grant Program <a href="constructUpdates.jsp">Updates</a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
  
<br/><br/><br/><br/><br/><br/><hr/>
<p align="center">Cultural Education Center, Albany, New York 12230. <br/>
Library Phone: (518) 474-7890  &nbsp;&nbsp;&nbsp;   Archives Phone:  (518) 474-6926</p>
<Br/>
Version 11g

</body>
</html>

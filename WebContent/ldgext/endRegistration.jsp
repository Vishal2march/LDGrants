<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <c:choose>
    <c:when test="${islgrmif=='false'}"><%--LIBRARY PAGE--%>
    <link href="http://usny.nysed.gov/css_js/nysedmain.css" rel="stylesheet" type="text/css" />
    <link href="http://usny.nysed.gov/css_js/content.css" rel="stylesheet" type="text/css" />
    <link href="http://www.nysl.nysed.gov/css_js/nysl.css" rel="stylesheet" type="text/css" />   
    <link href="http://www.nysl.nysed.gov/libdev/css_js/libdev.css" rel="stylesheet" type="text/css" />
    </c:when>
    <c:otherwise>
    <link href="../css/a.css" rel="stylesheet" media="screen" type="text/css" />
    </c:otherwise>
    </c:choose>
    <title>Registration Completed</title>
  </head>
  <body>
  
<c:choose>
<c:when test="${islgrmif=='false'}"><%--LIBRARY PAGE--%>
    
<div id="subpage_container">
  <div id="header"> 		
    <div id="nysedlogo"><a href="http://www.nysed.gov"><img src="http://usny.nysed.gov/images/nysedlogosub.jpg" alt="NYSED Logo" /></a><a href="#content_column" class="skip">Skip To Content</a></div>
    <div id="deptlogo"><a href="http://www.nysl.nysed.gov"><img src="http://www.nysl.nysed.gov/images/nysllogo.gif" alt="NYSL LOGO" width="77" height="49" /></a></div>
    <div id="header_wrapper">
      <div id="depthead"><h1><a href="http://www.nysl.nysed.gov/">New York State Library</a></h1></div>
      <div id="darkbox"></div>  
    </div> 
  </div>
	
	<div id="content">	   	  
	  <div id="sub_body">
      <div id="breadcrumb">
        <a href="http://www.nysed.gov/"><acronym title="NYS Education Department">NYSED</acronym></a> / <a href="http://www.oce.nysed.gov/"><acronym title="Office of Cultural Education">OCE</acronym></a> / <a href="http://www.nysl.nysed.gov/"><acronym title="New York State Library">NYSL</acronym></a> / <a href="http://www.nysl.nysed.gov/libdev/">Library Development</a> /  
        <!-- InstanceBeginEditable name="Edit_breadcrumb1" -->Online Grant Registration<!-- InstanceEndEditable -->					
        <hr/>
      </div>
      
      <div id="content_column"> 
  
      <p>Your registration form has been submitted to the Division of Library Development.  You will receive
      an email containing your user account within 48 hours. 
      <br/><br/>
      The user account information email will be sent from email address LIBDEVGRANTS@nysed.gov 
      Add the email address to your list of contacts, otherwise the email may be marked as spam.</p>
  
      </div><!--end contentcolumn-->
    </div><!--end subbody-->    
  </div><!--end content-->
    
  <div id="footer">
		<a href="http://www.nysl.nysed.gov/libdev/ldroster.htm">Contact</a> | <a href="http://www.nysl.nysed.gov/libdev/ldteam.htm">FAQ</a>
    <h3>The New York State Library is a unit within the</h3>
    <h3>University of the State of New York - New York State Education Department</h3>
  </div> 
</div>
  
  
</c:when>
<c:otherwise><%--ARCHIVES PAGE--%>


  <div id="headerholder">
    <div class="topnav" id="headerpg">
    <div class="logo"><a href="http://www.nysed.gov"><img src="../images/nysed_white.gif" alt="NYSED.gov" width="92" height="20" border="0" /></a></div>

    <div class="topnavlist">
    <a href="http://www.nysarchivestrust.org/aptindex.shtml">Archives Partnership Trust</a> | <a href="http://www.oce.nysed.gov/">Office of Cultural Education</a> | <a href="http://nysl.nysed.gov/uhtbin/cgisirsi/Fri+Sep+22+13:16:33+EDT+2000/0/0/57/49?user_id=apublic&amp;password=">Online Catalog </a> | <a href="http://www.archives.nysed.gov/a/about/about_search.shtml">Search</a> | <a href="http://www.archives.nysed.gov/aindex.shtml">Home</a>      
    </div>
    </div>
    </div>

    <div id="linetopholder">
    <div id="linetoppg"><img src="../images/bump.bmp" width="1" height="2" alt=" " /></div>
    </div>

    <div id="bannerholder">
    <div id="banner"><img src="../images/banner_fade.jpg" alt="New York State Archives: Where History Goes on Record" /></div>
  </div>
    
    
    
  <div id="contentholder">      
  <div class="content" id="content">
  
  <br/><br/>
  <p>Your registration form has been submitted to the State Archives.  You will receive
      an email containing your user account within 48 hours. </p>
  <br/><br/>
  <p>For questions regarding the LGRMIF Grant Program please contact the 
  State Archives Grants Administration Unit at archgrants@nysed.gov</p>
  
  </div>
  </div>
    
  <div class="footer">
    <p align="center">Cultural Education Center, Albany, New York 12230. Phone: (518) 474-6926</p>
  </div>

</c:otherwise>
</c:choose>


  </body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link href="http://usny.nysed.gov/css_js/nysedmain.css" rel="stylesheet" type="text/css" />
    <link href="http://usny.nysed.gov/css_js/content.css" rel="stylesheet" type="text/css" />
    <link href="http://www.nysl.nysed.gov/css_js/nysl.css" rel="stylesheet" type="text/css" />   
    <link href="http://www.nysl.nysed.gov/libdev/css_js/libdev.css" rel="stylesheet" type="text/css" />
    <title>Online Grant System Account Registration</title>
  </head>
  <body>
  
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

  <h1 class="paragraph_heading">Online Grant System Account Registration Form</h1>
  
  <p>In order to access the Division of Library Development's Online Grant System,
      you <b>must</b> have a NYS Directory Service account.  If you
      do not have a NYS Directory Service account, then complete the form below to register
      for an account.  The Online Grant System contains applications for Conservation/Preservation, 
      Adult Literacy, and Family Literacy grant programs.</p>
 
 <p>If you already have a NYS Directory Service account, proceed to the Online Grant System.<br/>
    <a href="https://eservices.nysed.gov/ldgrants">Login to Online Grant System</a></p>
      

  <table width="90%" border="1" align="center" summary="for layout only"> 
    <html:form method="POST" action="/ldgext/litRegistration">
    <tr>
      <td colspan="2" align="center"><h2>Register for a NYS Directory Service account</h2></td>
    </tr>
    <tr>
      <td colspan="2" class="urgentmessage">Fields marked with an (*) are required.</td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>First Name</td>
      <td><html:text property="fname" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Last Name</td>
      <td><html:text property="lname" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Title</td>
      <td><html:text property="title" /></td>
    </tr>
    <tr>
      <td colspan="2">An email will be sent to the Work Email listed below containing the account
      name/password for the Online Grant System.</td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Work Email</td>
      <td><html:text property="email" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Work Phone</td>
      <td><html:text property="phone" /></td>
    </tr>    
    <tr>  
      <td><font color="Red">*</font>Institution Name</td>
      <td><html:text property="instName" /></td>
    </tr>
    <tr>  
      <td>Library Name</td>
      <td><html:text property="libName" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Institution Address<br/>
      Physical address, not PO Box number</td>
      <td><html:text property="addr1" /></td>
    </tr>
    <tr>
      <td>Address Line 2</td>
      <td><html:text property="addr2" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>City</td>
      <td><html:text property="city" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>State</td>
      <td><html:text property="state" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Zip Code</td>
      <td><html:text property="zipcd" /></td>
    </tr>
    <tr>
      <td colspan="2">Enter the county, school district, and Federal ID of the institution you represent. If
      unknown, enter N/A in the corresponding box. </td>
    </tr>
    <tr>  
      <td>County</td>
      <td><html:text property="county" /></td>
    </tr>
    <tr>  
      <td>School District</td>
      <td><html:text property="schdistrict" /></td>
    </tr>
    <tr>  
      <td>Federal ID Number</td>
      <td><html:text property="fedid" /></td>
    </tr>
    <tr>
      <td>Which grant program should the account have access to?<br/>
      You <b>MUST</b> choose at least one grant program.</td>
      <td><html:checkbox property="cpdiscretionary" />Conservation/Preservation Discretionary<br/>
          <html:checkbox property="fmliteracy" />Family Literacy<br/>
          <html:checkbox property="alliteracy" />Adult Literacy<br/>
          <html:checkbox property="plconstruction" />Public Library Construction</td>
    </tr>
    <tr>
      <td colspan="2">Should this account have read, edit or submit access to online grant applications?<br/>
          <html:checkbox property="readaccess" />Read<br/>
          <html:checkbox property="editaccess" />Edit<br/>
          <html:checkbox property="submitaccess" />Submit</td>
    </tr>
    <tr>  
      <td colspan="2"><html:hidden property="grantprogram" value="lit"/>
          <html:reset value="Reset" /><html:submit value="Submit" /></td>
    </tr>
    </html:form>
  </table>

   <p>For questions regarding the Conservation/Preservation Discretionary grant 
      program please contact the Conservation/Preservation Program Administrator Barbara 
      Lilley: barbara.lilley@nysed.gov or 518-486-4864</p>
      
  <p>For questions regarding the Adult/Family Literacy program please contact 
  Carol A Desch: 518-474-7196 or carol.desch@nysed.gov</p>
  
  <p>For questions regarding the Public Library Construction grant program please contact 
  Mary Linda Todd: marylinda.todd@nysed.gov or 518-486-4858</p>
  
  <p>For username/password questions or help with login, please contact 
   LibDevGrants@nysed.gov</p>
  
      </div><!--end contentcolumn-->
    </div><!--end subbody-->    
  </div><!--end content-->

    
  <div id="footer">
		<a href="http://www.nysl.nysed.gov/libdev/ldroster.htm">Contact</a> | <a href="http://www.nysl.nysed.gov/libdev/ldteam.htm">FAQ</a> 
    <h3>The New York State Library is a unit within the</h3>
    <h3>University of the State of New York - New York State Education Department</h3>
  </div> 

  </div>
  </body>
</html>

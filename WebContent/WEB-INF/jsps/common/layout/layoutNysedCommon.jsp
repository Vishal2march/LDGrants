<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
    <head>
        <title><tiles:insertAttribute name="title"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <!--  USE THE COMMON NYSED BOOTSTRAP CSS -->
        <!--  Including this will get you all the standard styles including colors and customizations -->
        <%--<link href="https://eservicest.nysed.gov/nysed-assets/stylesheets/1.0/nysed-bootstrap.css" media="all" rel="stylesheet"/>--%>
		<link href="css/nysed-bootstrap.css" media="all" rel="stylesheet"/> 
        
        <!--  You need to link in font awesome if you want to use the font awesome icons on menus and such.
              Don't forget to include the font-awesome directories in your web app as well (css and fonts). 
              You can also link in a CDN like we do below.-->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">  


		<!--  You need to link in jQuery if you want a dropdown like the example below. -->
      
		<%-- SH: using local versions below
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" crossorigin="anonymous"></script>  
		--%>
		
		<script src="jscripts/jquery-2.2.4.min.js"></script>
        <script src="jscripts/bootstrap.3.3.7.min.js"></script>  
		<!--  accessiblity fixes -->
<style>
.alert-warning {
	color: #6a3f00 !important;
}
</style>


    </head>

    <body>
        <nav id="top-nav" class="navbar navbar-fixed-top">
         <!--  This div is standard bootstrap code to get the three bar menu link to display on smaller screens. -->
		  <div class="container">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"> 
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		    </div>
		    <div class="collapse navbar-collapse" id="navbar">
		      <div class="pull-left">
		      <ul class="nav navbar-nav pull-left header-links">
		        <li><a href="http://www.nysed.gov/">NYSED Home</a></li>
		        <!--  Add a title for your application here - this is the same as 'Acme Site Title' in the upper left corner of the screenshot.
                        This text doubles as a link back to the main page of your application. -->
		        <li><a class="site-title" href="welcomePage.jsp">Online Application System</a></li>
		      </ul>
		      </div>
		      <ul class="nav navbar-nav navbar-right header-links">
		        <!-- BASIC DROP DOWN =================================================================================== -->
		        <!--  This code will create a basic dropdown-like item under the main link (in this case the user name)
		        	 Requires jQuery to be included in the head section.                                             -->
		      	
		      	<!--SH: if no user in session; show login -->
		      	<s:if test="#session.lduser==null">
		      		<li>
		         	<a href="/ldgrants">Log in</a>
		      	</li>
		      	</s:if>
		      	<s:else>
			      	<li>
			           <a class="dropdown-toggle" data-toggle="dropdown" href="#">
			           		<i class="fa fa-user"></i>&nbsp;<s:property value="#session.lduser.userid"/>
			           		<span class="caret"></span>
			           </a>
			       		<ul class="dropdown-menu header-menu">		            	
			            	<li><a href="help.action">Help</a></li>
			            	<li class="divider"></li>
			            	<li><a href="logout.action">Logout</a></li>
			           </ul>
			     	</li>
		     	</s:else>
		        <!--  PRINT =================================================================================== -->
		        <li>
		         	<a href="#" onclick="print(); return false;">
		            	<i class="fa fa-print"></i>&nbsp;&nbsp;&nbsp;Print
		        	</a>
		      	</li>
		      </ul>
		    </div>
		  </div>
        </nav>

        <!-- PAGE HEADER: NYSED LOGO, APP TITLE AND SUB-TITLE -->
        <div class="container">
            <!-- The header-container class pulls in the NYSED logo. and sets up the header size, etc -->
            <div class="header-container">
            	<!--  Add the app title and sub-title here.  This is the same as 'Acme App Name' in the upper right corner of the screenshot. -->
                <h1 class="site-title">                    
	            	<span class="hidden-sm hidden-md hidden-lg">NYSED </span>
	            	<span class="hidden-xs hidden-sm">Online Application System</span>
                    <div class="subtitle">&nbsp;</div>
                </h1>
            </div>
        </div>

        <div class="hero-content container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <%--<div class="panel panel-primary">
                        <div class="panel-heading">Project Information</div>
                        <div class="panel-body">

                            <!-- MAIN CONTENT GOES HERE
                            The hero-content class defines how the main box (gray area) is laid out.
                            -->
                            
							  
                            <br/><br/><br/><br/><br/><br/><br/><br/>
                        </div>
                    </div>
                    --%>
                    <tiles:insertAttribute name="grantinfo" ignore="true"/>
                    <tiles:insertAttribute name="menu" ignore="true"/>
				    <tiles:insertAttribute name="budgetTabs" ignore="true"/>
				    <tiles:insertAttribute name="body" />
                    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                </div>
            </div>
        </div>
        <div class="container-fluid">
			<div class="row">
		        <div class="col-md-12">
					<ul class="list-inline text-center footer-links">
						<li><a href="http://www.nysed.gov">NYSED Home</a></li>
						<li><a href="http://www.nysed.gov/terms-of-use#Accessibility">Accessibility</a></li>
						<li><a href="http://www.nysed.gov/privacy-policy">Privacy Statement</a></li>
						<li><a href="http://www.nysed.gov/terms-of-use#Disclaimers">Disclaimer</a></li>
						<li><a href="http://www.nysed.gov/terms-of-use">Terms of Use</a></li>
					</ul>
		        </div>
		    </div>
		</div>
    </body>
</html>
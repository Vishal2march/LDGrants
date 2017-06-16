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
    <link href="../css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
    
  
  <table align="center" summary="for layout only">
    <tr>
      <th>$14 Million Public Library Construction Program</th>
    </tr>
  </table>
  
  
  <h4>Checklist</h4>
  
  <table align="center" width="90%" border="1" class="boxtype" summary="for layout only">
    <tr>
        <td><input type="checkbox"/> Application Form (required)</td> 
    </tr>
    <tr>
        <td><input type="checkbox"/> Additional Funding Sources (required)</td>
    </tr>    
    <tr>
        <td><input type="checkbox"/> Project Narratives (required)</td>
    </tr>
    <tr>
        <td><input type="checkbox"/> Budget (required)</td>
    </tr>
    <tr>
        <td><input type="checkbox"/> FS-10 Forms (required) (3 original forms signed in blue ink and mailed to your PLS)</td> 
    </tr>
    <tr>
        <td><input type="checkbox"/> Payee Information Form (required) (1 original form signed in blue ink and mailed to your PLS)</td> 
    </tr>
    <tr>
        <td><input type="checkbox"/> Attachments (required)</td>
    </tr>
    <tr>
      <td colspan="2">
            <table align="right" width="95%" summary="for layout only">
                <tr>
                    <td><input type="checkbox"/> Assurances (required)</td>
                </tr>
                <tr>
                    <td><input type="checkbox"/> Certificate/Proof of Available Funds to Finance Project (required)</td>
                </tr>
                <tr>
                    <td><input type="checkbox"/> Short (or Full) Environmental Assessment Form (required)</td>
                </tr>
                <tr>
                    <td><input type="checkbox"/> Pre-Construction Building Photographs (required)</td>
                </tr>
                <tr>
                    <td><input type="checkbox"/> Smart Growth Form (required)</td> 
                </tr>
                <tr>
                    <td><input type="checkbox"/> Certificate of 10 year minimum lease/legal agreement and project approval
                        from building owner<br/>
                        (if building/site is leased or otherwise legally available)</td> 
                </tr>
                <tr>
                  <td><input type="checkbox"/> Office of Facilities Planning approval<br/>
                  (if building is owned by school district
                  and project over $10,000)</td>
                </tr>
                <tr>
                  <td><input type="checkbox"/> State Historic Preservation Office (SHPO) Approval Documentation<br/>
                  (optional)</td>
                </tr>    
                <tr>
                  <td><input type="checkbox"/> Vendor quotes, cost estimates</td>
                </tr> 
                <tr>
                  <td><input type="checkbox"/> Municipal Consent for Site/Building Acquisition Projects (optional)</td>
                </tr> 
                <tr>
                  <td><input type="checkbox"/> M/WBE (Minority and Women-Owned Business Enterprises) Requirement</td>
                </tr>
            </table>
      </td>
    </tr>
  </table>
  <br/><br/>
  
  
  <h4>Application Form</h4>   
  
  <table align="center" width="90%" border="1" class="boxtype" summary="for layout only">
    <tr>
        <td width="30%">Library or System Name:</td>
        <td width="70%">&nbsp;</td>
      </tr>
      <tr>
        <td>Mailing Address:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>Address:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td >City, State, Zip:</td>
        <td >&nbsp;</td>
      </tr>
      <tr>
        <td>County:</td>
        <td>&nbsp;</td>
    </tr>
      <tr>
        <td>Director of Institution:</td>
        <td>&nbsp;
        </td>
      </tr>
      <tr>
        <td>Title:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>Email:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>State Judicial District: &nbsp;</td>
        <td>State Assembly Districts: &nbsp;</td>
      </tr>
      <tr>
        <td>State Senate Districts: &nbsp;</td>
        <td>State Congressional Districts: &nbsp;</td>
      </tr>
      <tr>
        <td>FEIN #: &nbsp;</td>
        <td>School District: &nbsp;</td>
      </tr>        
      <tr>
        <td>Public Library System: </td>
        <td>&nbsp;</td>
    </tr>
  </table>
  <br/>
      
      
  <table align="center" width="90%" border="1" class="boxtype" summary="for layout only">
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"/>
    </tr>
    <tr>
        <td colspan="2"><b>Building Information</b> - Provide the following information for the building 
        (existing or proposed) or site that is the subject of this application.</td>
    </tr>  
    <tr>
        <td width="60%">Building Name:</td>
        <td width="40%">&nbsp;</td>
    </tr>
    <tr>
        <td>Street Address:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>City:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>State:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Zip Code:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2"><b>Building Type</b></td>
    </tr>  
    <tr>
        <td>Main Library</td>
        <td>Branch Library</td>
    </tr>
    <tr>
        <td>System Headquarters</td>
        <td>Other</td>
    </tr>
    <tr>
        <td height="20" bgcolor="Silver" colspan="2"/>
    </tr>
    <tr>
        <td colspan="2"><b>Library building is or will be accessible to persons with disabilities:</b></td>
    </tr>
    <tr>
        <td>Physical access:</td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td>Program access:</td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Library building is:</b></td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />Owned by applicant </td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />
        Leased by applicant with a minimum of 10 years lease term from the date of anticipated completion</td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />Otherwise legally available (i.e., located in a municipal building)</td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Library site is:</b></td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />Owned by applicant </td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />
            Leased by applicant with a minimum of 10 years lease term from the date of anticipated completion</td>
    </tr>
    <tr>
        <td colspan="2"><input type="radio"  />Otherwise legally available (i.e., located in a municipal building)</td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td>Year of initial construction of library building (yyyy)</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Number of floors.</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Square footage of building.</td>
        <td>&nbsp; </td>
    </tr>
    <tr>
        <td>The building is designated a historic landmark.</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>The building is in a historic district.</td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td>The building is over 50 years old. </td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
      <td>Does your Project involve ground disturbance?</td>
      <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td>If your project is exempt from SHPO according to Appendix A, please state the reason and cite the language from Appendix A which provides evidence for the exemption.  If you are unsure that your project activity is exempt please contact SHPO.</td>
        <td>&nbsp;
            </td>
    </tr>
    <tr>
        <td colspan="2"><font color="navy">Note:</font> If your library building is 50 years old or older, please 
        see the SHPO information page to determine if your project requires a SHPO approval. If appropriate, an 
        approval letter from SHPO must be attached your construction grant application as a signed PDF.</td> 
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td>This library building is owned by a school District.</td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td>Which school district? (if applicable)</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>The total cost of this project will exceed $10,000.</td> 
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td colspan="2"><font color="navy">Note:</font> If the library building is owned by a school district and the cost
        of this project will exceed $10,000, the applicant must contact SED Office of
        Facilities Planning and include an OFP Certificate of Project Approval 
        with this application.</td> 
    </tr>
     <tr>
        <td height="20" colspan="2" bgcolor="Silver"/>
    </tr>
    <tr>
        <td colspan="2"><b>Project Details</b></td> 
    </tr> 
    <tr>
        <td>Project Title</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2"><b>Construction Project Manager</b> (must be Library Staff or Board Member)</td> 
    </tr> 
    <tr>
        <td>First Name:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Last Name:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Phone:</td>
        <td>&nbsp;</td>
    </tr>
     <tr>
        <td>Phone Extension:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>E-mail:</td>
        <td>&nbsp;</td>
    </tr>
    
    <tr>
        <td colspan="2"><b>Library Director</b></td> 
    </tr> 
    <tr>
        <td>First Name:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Last Name:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Phone:</td>
        <td>&nbsp;</td>
    </tr>
     <tr>
        <td>Phone Extension:</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>E-mail:</td>
        <td>&nbsp;</td>
    </tr>
    
     <tr>
        <td height="20" bgcolor="Silver" colspan="2"/>
    </tr>
    <tr>
        <td colspan="2"><b>Construction project application is for</b> (select all that apply):</td>
    </tr>
    <tr>
        <td>New Construction </td>
        <td>Energy Conservation </td>
    </tr>
    <tr>
         <td>Building Expansion </td>
         <td>Accessibility </td>  
    </tr>
    <tr>
        <td>Site Acquisition </td>
        <td>Other</td>
    </tr>
    <tr>
        <td>Renovation/Rehabilitation </td>
        <td>Broadband</td>
    </tr>
    <tr>
        <td>Will the library's completed project require a local Certificate of Occupancy?</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td bgcolor="Silver" colspan="2" height="20"/>
    </tr>
    <tr>
        <td colspan="2"><b>Estimated Project Costs</b> (also see Project Budget)</td>
    </tr>
    <tr>
        <td>a. Total Project Cost ($)<br/><b>NOTE:</b> If the project for which funding is being requested (b) is part 
        of a larger comprehensive project, list the Total Project Cost of the larger project in "a". If the project for 
        which funding is being requested (b) is not part of a larger project, "a" will equal "b".</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>b. Cost of Project for Which Funding is Being Requested<br/><b>NOTE:</b>
            This system will populate this field with the total 'Cost' of all budget
            records entered on the Project Budget pages.</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>c. Amount of Public Library Construction Funds requested for this Project (cannot be more than 50% of the 
        amount in question b or 75% if you qualify for the Reduced Match.) Note: Contact your System regarding eligibility 
        for reduced match.</td>
        <td>&nbsp;</td>
    </tr>
    <tr>     
        <td>d. This project is or was funded, in whole or in part by funds
            secured through the issuance of tax exempt bonds, bond anticipation notes, or revenue anticipation notes.</td>
        <td>Yes&nbsp;&nbsp;&nbsp;
            No</td>
    </tr>
    <tr>
        <td bgcolor="Silver" colspan="2" height="20"/>
    </tr>
    <tr>
        <td colspan="2"><b>Timeframe</b> (date format MUST BE mm/dd/yyyy)</td>
    </tr>
    <tr>
        <td>This project is expected to start on or before</td>
        <td>&nbsp;</td>
    </tr>    
    <tr>
        <td colspan="2">This project was/will be started on _____________ and is not complete at the time
            of this application. </td>
    </tr>
    <tr>
        <td>This project is expected to be completed by:</td>
        <td>&nbsp;</td>
    </tr>
  </table>
  <br/><br/>
  

  <h4>Additional Funding Sources</h4>
  
  <table width="90%" align="center" class="boxtype" border="1" summary="for layout only">
    <tr>
      <th colspan="3">List all funding sources that contribute to this construction project.</th>
    </tr>
    <tr>
        <th width="33%">Fund Source</th>
        <th width="33%">Description</th>
        <th width="33%">Amount</th>
    </tr> 
    <tr>
        <td width="33%">NYSED - State Education Department <br/>
                        NYSOITS - State Office of Information Technology Services<br/>
                        Special Legislative Grants (member items)<br/>
                        Private Funding/ Private Donations<br/>
                        USDA - US Department of Agriculture<br/>
                        RUS - Rural Utilities Service<br/>
                        Library Capital Funds<br/>
                        Public Library System Funds<br/>
                        NYSERDA- State Energy Research & Development Authority<br/>
                        Other<br/>
                        Library Operating Funds</td>
        <th width="33%">&nbsp;</th>
        <th width="33%">&nbsp;</th>
    </tr> 
  </table>
  <br/><br/>
  
  
   <h4>Project Narratives</h4>
   
   
  <table width="90%" align="center" class="boxtype" border="1" summary="for layout only">
    <tr>
      <th>Project Abstract</th>
    </tr>
    <tr>
      <td>Provide a brief description of the construction project. Note: The 
      Project Abstract field is limited to a maximum of 150 characters, including spaces.<hr/></td>
    </tr>
    <tr>
      <th>Description of Project</th>
    </tr>
    <tr>
      <td>Include a complete description of the project for which applicant is 
      requesting funding. If this project is part of a larger project during this 
      grant funding period (July 1, 2015 - June 30, 2018), please describe the entire 
      project. When a project is part of a larger project identify both clearly so 
      that the application project can be easily identified within the larger project 
      description. Describe construction activities including the intended physical 
      alteration or improvement to the building.<hr/></td>
    </tr>
    <tr>
      <th>Impact of Project</th>
    </tr>
    <tr>
      <td>Describe how the project will address one or more of the following Public Library Construction Grant Program priorities: 
        <br/>-increased effectiveness of library service due to increased and/or improved building space and capacity 
        <br/>-more efficient utilization of the building such areas as energy conservation and increased staff efficiency 
        <br/>-improved access to and use of building services by all library users, including those with physical disabilities 
        <br/>-provision of library services to geographically isolated or economically disadvantaged communities <hr/></td>
    </tr>
    <tr>
      <th>Timetable</th>
    </tr>
    <tr>
      <td>The timetable should be as specific as possible, indicating the projected 
      beginning date for the project; the duration of the proposed construction/renovation, 
      and the projected beginning and ending dates for all contractual services; and 
      schedules for all other significant activities impacting the project. The timetable 
      should list all related project activities taking place during the grant funding period (July 1, 2015
      - June 30, 2018), broken down by year.<hr/></td>
    </tr>
    <tr>
      <th>Budget Narrative</th>
    </tr>
    <tr>
      <td>Description of budget requests, vendor costs. Please associate the proposed vendor with the
      construction or renovation work and cost.  Describe all items entered on the Project Budget pages.  The Budget 
      Narrative should reflect the Project Budget entries and attached quotes. If the vendor quotes contain options, 
      the narrative must indicate those options and the associated dollar value.<hr/></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <h4>Project Budget</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <th>Contracted Services Expenses (Code 40)</th>
      </tr>
      <tr>
        <td>List all services to be purchased for the project, arranged, as appropriate, 
        under Consultant Services or Contracted Services. Attach detailed cost 
        estimates supplied by vendors, quotes and/or bids, or other supporting 
        data in an appendix.<br/><br/>
- Consultant Services: include professional and technical advice that will be 
provided by individuals or groups of individuals. Consultants are normally 
retained for a short period to provide advice about specific aspects of the 
project. Consultants are normally expected to provide a report of their 
activities, usually at a time agreed upon before the consultancy begins. 
Architectural services are not eligible. Please see Construction regulations 
for eligible/ineligible costs.<br/><br/>
- Contracted Services: include professional or technical activities that will 
be performed by commercial vendors or qualified individuals. Contractual services 
are normally used for project activities that cannot be carried out by the 
institution, or for those activities that can be more economically performed 
by firms or individuals specializing in a particular service. <br/><br/>
* Cost is the Cost of project for which funding is being requested.
         </td>
      </tr>
      <tr>
        <td height="10" />
      </tr>       
      <tr>
        <td>                
            <table width="100%">
            <tr>
              <td>Service Type</td><td>Consultant/Vendor</td>
              <td colspan="2">Description</td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td>Cost</td><td>AmtApproved</td>
              <td>ExpSubmitted</td><td>ExpApproved</td>
            </tr>  
            </table><hr/>
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>       
      <tr>
        <td height="20" />
      </tr>       
      <tr>
        <th>Supplies & Materials Expenses (Code 45)</th>
      </tr>
      <tr>
        <td>List all supplies and materials to be purchased for use during the project. 
        Do not include supplies to be purchased by your vendor--the vendor's cost 
        estimate will include the cost of materials as well as labor. Equipment
        items under $5,000 should be budgeted under "Supplies and Materials." <br/><br/>
* Cost is the Cost of project for which funding is being requested. 
        </td>
      </tr>
      <tr>
        <td height="10" />
      </tr>       
      <tr>
        <td>               
            <table width="100%">
            <tr>
              <td>Quantity</td><td>Description</td>
              <td>Unit Price</td><td>Vendor</td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td>Cost</td><td>AmtApproved</td>
              <td>ExpSubmitted</td><td>ExpApproved</td>
            </tr>  
            </table><hr/>
        </td>
      </tr>  
      <tr>
        <td height="20" />
      </tr>   
      <tr>
        <td height="20" />
      </tr>       
      <tr>
        <th>Equipment Expenses (Code 20)</th>
      </tr>
      <tr>
        <td>List all equipment that has a unit cost of $5,000 or more that will be 
        purchased for use during the project. Equipment items under $5,000 should 
        be budgeted under "Supplies and Materials." Include cost estimates, bids, 
        or other supporting data as an attachment. All equipment to be purchased 
        should be described in the 'Budget Narrative'.<br/><br/>
Equipment purchased in this category pertains to equipment purchased by the library, 
and not a contractor or vendor. Equipment purchased by a contractor/vendor should 
be included in the Purchased Services category.<br/><br/>
* Cost is the Cost of project for which funding is being requested.        
        </td>
      </tr>
      <tr>
        <td height="10" />
      </tr>       
      <tr>
        <td>               
            <table width="100%">
            <tr>
              <td>Quantity</td><td>Description</td>
              <td>Unit Price</td><td>Vendor</td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td>Cost</td><td>AmtApproved</td>
              <td>ExpSubmitted</td><td>ExpApproved</td>
            </tr>  
            </table><hr/>
        </td>
      </tr>  
  </table>
  <br/><br/>
  
  
  <h4>Assurances</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
        <td>The applicant hereby gives assurances of the following: (check all boxes that apply)</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><input type="checkbox"/> The applicant will comply with the Minority or Women Business Equity Initiative (M/WBE) established through NYS Executive Law, Article 15-1, NYCRR Part 143 and submit all required paperwork in a timely manner during the period beginning February 1st through thirty days after receiving the official award notice issued by the State Education Department. (This requirement applies only to grant awards in the amount of $25,000 and over.)
      </td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td><input type="checkbox"/> It possesses the legal authority to submit this 
        application including all understandings and assurances contained herein and 
        to direct and authorize the person identified as the construction project 
        manager to act as the official representative of the applicant in connection 
        with this application and to provide such additional information as may be 
        required.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td><input type="checkbox"/> The project will begin land acquisition, 
        construction, or continue construction work in the case of a project that has 
        begun but is not complete as of the date of application to the library system, 
        within 180 days after receipt of written notification from the State Education 
        Department that state aid construction funds have been approved for the 
        project, and that the conditions of the funding have been met.</td>
    </tr>
     <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td><input type="checkbox"/> The project will be conducted in accordance with all
        applicable Federal, State, and local laws and regulations.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td><input type="checkbox"/> In the event the library building or site of the 
        construction project is leased by the applicant or otherwise legally available, 
        the lease on the building or site or other legal agreement is for a minimum 
        of 10** years from the date of the anticipated completion of construction, 
        the owner is aware of and approves the proposed construction project, and the 
        building is open to the public.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td>AUTHENTICATION OF APPLICATION<br/><br/>
	This application completed in the preceding pages and accompanying documents 
        for a public library construction grant to be administered in accordance with 
        the requirements of Education Law §273-a (as Amended by Chapter 57 of the Laws 
        of 2007) and Commissioner's Regulations §90.12 was read and duly adopted by the 
        Board of Trustees of the Library at a legal meeting on _______________(date).</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td>Signature of President, Library Board of Trustees:</td>
    </tr>
     <tr>
        <td>Name of President (type or print):</td>
    </tr>
  </table>
  
  
  </body>
</html>
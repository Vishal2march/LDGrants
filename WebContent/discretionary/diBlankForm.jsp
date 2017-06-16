<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diBlankForm.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/5/08     Created
 *
 * Description
 * This is DI admin page which lists all parts of DI application in 1 file. Requested by
 * LDeitz b/c needed to show to contracts unit for approval. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
     <link href="../css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>C/P Discretionary Applicataion</title>
  </head>
  <body>
  
  <table align="center" summary="for layout only">
    <tr>
      <th>Conservation Preservation Program Grant Application
          <br/>The University of the State of New York 
          <br/>The State Education Department 
          <br/>Division of Library Development 
          <br/>Discretionary Grants
      </th>
    </tr>
  </table>
  
  
  <h4>Cover Sheet</h4> 
      
    <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td width="30%" >Sponsoring Institution:</td>
        <td width="70%" ></td>
      </tr>
      <tr>
        <td>Mailing Address:</td>
        <td></td>
      </tr>
      <tr>
        <td>Address:</td>
        <td ></td>
      </tr>
      <tr>
        <td >City, State, Zip:</td>
        <td ></td>
      </tr>
      <tr>
        <td>Director of Library:</td>
        <td></td>
      </tr>
      <tr>
        <td>Title:</td>
        <td></td>
      </tr>
      <tr>
        <td>State Judicial District: </td>
        <td>State Assembly Districts:</td>
      </tr>
      <tr>
        <td>State Senate Districts: </td>
        <td>State Congressional Districts: </td>
      </tr>
      <tr>
        <td>FEIN #: </td>
        <td>School District: </td>
      </tr>        
      <tr>
        <td>Institution Type:</td>
        <td></td>
      </tr>
      <tr>
        <td>Is Institution Affiliated with Religious Denomination?</td>
        <td></td>
      </tr>
      <tr>
        <td>Institutional Eligibility</td>
        <td><ul>
            <li type="circle">Chartered by the Board of Regents of NYS</li>
            <li type="circle">accepted by the Board of Regents of the State of New York for filing under the not-for-
            profit section (Section 216) of the Education Law</li>
            <li type="circle">Registered with the Office of Charities of the NYS Department of State</li>
            <li type="circle">Granted not-for-profit status under section 501(c)(3) of the United States Internal Revenue Code</li>
            <li type="circle">Other</li></ul>
        </td>
      </tr>
    </table>
    
    
    
    <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager</th>
      </tr>          
      <tr>
        <td width="30%">Name:</td>
        <td width="70%"></td>
      </tr>
      <tr>
        <td>Title:</td>
        <td></td>
      </tr>        
      <tr>
        <td>Phone:</td>
        <td></td>
      </tr>
      <tr>
        <td>Email:</td>
        <td></td>
      </tr>
      <tr>
        <td>Project Title:</td>
        <td></td>
      </tr>
    </table>
    
  
  <table width="90%" class="boxtype" align="center" summary="for layout only">
    <tr>
      <th colspan="2" bgcolor="Silver">Participating Institutions</th>
    </tr>   
    <tr>
      <td height="10"></td>
    </tr>
  </table>        
    
    
  <h4>Project Narratives</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th>Summary Description</th>
    </tr>
    <tr>
      <td>Summary description of proposed preservation activities: (5-10 sentences).
          The summary should be brief, but should provide a clear, publishable statement of 
          how you intend to use State Aid funds.<hr/></td>
    </tr>
    <tr>
      <th>Size of Institutions Operation</th>
    </tr>
    <tr>
      <td>Include information on the institution's annual budget for staff, materials, 
      operations, etc., and the total number of staff in full time equivalents (FTE). 
      Indicate the number of FTE professional and non-professional staff and the number 
      of volunteers who regularly serve in the institution.<hr/></td>
    </tr>    
    <tr>
      <th>Total Collection of Library Research Materials</th>
    </tr>
    <tr>
      <td>Include information on: the size of the collection and types of materials held, 
      collecting policy and sources of materials acquired, number of items acquired and 
      expenditure for acquisitions last year, other relevant background information on the 
      nature and use of the collection.<hr/></td>
    </tr>    
    <tr>
      <th>C/P Activities Performed</th>
    </tr>
    <tr>
      <td>Describe current as well as long-range plans for conservation/preservation activities, 
      operations, and priorities. Indicate the amount of institutional funding used to support 
      conservation/preservation activities in the past year, and the institutional funding that
      will be available during the period for which discretionary grant funds are being sought.
      Also identify other sources of funding for conservation/preservation activities, 
      including grants from other government or private agencies.<hr/></td>
    </tr>    
    <tr>
      <th>Environmental Conditions</th>
    </tr>
    <tr>
      <td>Indicate the extent to which temperature, humidity, light levels, and air quality 
      are monitored and controlled in the areas where materials preserved with grant funds 
      will be housed. If improving environmental conditions for storage is the focus of the 
      grant application, technical specifications for the proposed equipment must be included. 
      These must include specific information on the levels of temperature, humidity and air 
      quality control possible with the equipment. In addition, a description of how conditions
      will be monitored must be included.<hr/></td>
    </tr>    
    <tr>
      <th>Preparation for Disasters</th>
    </tr>
    <tr>
      <td>A comprehensive, written plan covering disaster preparedness, response, and salvage 
      of all library, archival, or record resources is recommended. A typical plan would include 
      a summary of emergency procedures; lists of persons to be called in case of disaster, 
      including local, regional and national consultants, as appropriate; supplies and services 
      available; and other information specific to the institution. When a written plan has 
      not been produced, the applicant should indicate how emergencies affecting library 
      materials will be dealt with, noting particularly what regional resources are available 
      and what arrangements have been made to take advantage of them.<hr/></td>
    </tr>    
    <tr>
      <th>Security arrangements for Protecting Collections</th>
    </tr>
    <tr>
      <td>Describe measures taken to avoid theft, loss, mutilation, or inappropriate 
      use of materials.<hr/></td>
    </tr>    
    <tr>
      <th>Participation in Cooperative C/P Activities</th>
    </tr>
    <tr>
      <td>Cooperative activities may include sharing conservation/preservat¬ion staff or 
      facilities with other institutions; contributing microform records to NUC, OCLC or
      RLIN, or other shared bibliographic databases; using the services of regional 
      conservation centers; participating in cooperative microfilming projects, regional 
      or statewide disaster assistance networks, or other regional, state or national 
      preservation activities or organizations.<hr/></td>
    </tr>    
    <tr>
      <th>Access policies and practices of Institution</th>
    </tr>
    <tr>
      <td>Indicate the level of service provided (e.g., hours open, number of patrons, number 
      of items loaned or used on-site, participation in cooperative access programs such as 
      interlibrary loan and regional databases) and any restrictions placed on use of materials.<hr/></td>
    </tr>    
    <tr>
      <th>Bibliographic Control</th>
    </tr>
    <tr>
      <td>Discuss the overall availability of materials by documenting the kind of cataloging or 
      other form of bibliographic control used to enable potential users to locate materials. 
      The extent to which the institution makes use of regional databases, OCLC, RLIN, or other 
      on-line bibliographic networks should be described.<hr/></td>
    </tr>    
    <tr>
      <th>Ownership of Materials</th>
    </tr>
    <tr>
      <td>Indicate whether or not the materials preserved during the project are owned by 
      the institution. If the materials are on deposit, you must provide some evidence of 
      deposit, indicating a clear and continuing legal relationship with the owner of the 
      materials that will assure they will remain permanently accessible. A copy of the 
      relevant agreement should be included in an appendix to the project description.<hr/></td>
    </tr>    
    <tr>
      <th>Description of Materials</th>
    </tr>
    <tr>
      <td>Indicate the subject area or content, format (book, manuscript, photograph, map, etc.), 
      quantity, condition, and specific preservation problems. Indicate in what way the 
      materials form a coherent body of research materials, and how they relate to the 
      institution's overall collecting policy. <hr/></td>
    </tr>    
    <tr>
      <th>Significance of Materials</th>
    </tr>
    <tr>
      <td>Indicate how the materials to be preserved fit in the institution's overall 
      collecting policy. Describe the kinds of information and/or research needs the 
      material meets, whether they are of local, regional, national or international 
      significance, the level of demand for the material, and whether it is valuable 
      primarily for the information it provides, or as a physical artifact. Compare the 
      material to be preserved with similar collections elsewhere, and describe the research 
      that will benefit from the project, or the kinds of users who need access to the material.<hr/></td>
    </tr>    
    <tr>
      <th>Timetable of the Project</th>
    </tr>
    <tr>
      <td>The timetable should be as specific as possible, indicating the projected beginning 
      date for the project; hiring dates and duration of work for personnel to be hired with 
      discretionary grant funds; schedules for existing staff who will contribute some portion 
      of their time to the project; consulting schedules, including due dates of reports; 
      projected beginning and ending dates for all contractual services; and schedules for 
      all other significant activities proposed for the project.<hr/></td>
    </tr>    
    <tr>
      <th>C/P Activities</th>
    </tr>
    <tr>
      <td>Provide a detailed description of all the activities that make up the project. 
      Vendors' treatment proposals and cost estimates must be included. The treatment proposal 
      should describe the work to be performed, the materials and techniques to be used, the 
      estimated number of hours required to complete the work, the cost per hour, or some 
      equivalent breakdown of the cost estimate.<hr/></td>
    </tr>    
    <tr>
      <th>Personnel and Vendors</th>
    </tr>
    <tr>
      <td>Indicate the name, position, and qualifications of the person who will have 
      responsibility for the day-to-day operation of the project (Project Manager). 
      Include a list of all personnel to be hired for the project and those on the institution's
      staff who will perform project activities. Describe the specific project responsibilities 
      of all personnel. List the name of any consultants to be used in the project. Explain 
      what role the consultants will have in the project and which project activities they 
      will perform. List the names and addresses of all vendors to be used in the project 
      and the specific services they will provide.<hr/></td>
    </tr>    
    <tr>
      <th>Staff Contributions</th>
    </tr>
    <tr>
      <td>Indicate the amount of time existing staff will contribute directly to the project. 
      Also include any time volunteered by outside professionals or other non-institutional 
      staff. Reviewers will also expect to find evidence of appropriate staff to provide 
      ongoing care for collections once the project is completed. Such care includes maintenance,
      appropriate updating of catalogs or bibliographies, and reference service or other user 
      support appropriate to the materials and the overall purposes of the institution.<hr/></td>
    </tr>    
    <tr>
      <th>Financial Contributions</th>
    </tr>
    <tr>
      <td>Indicate the amount of financial support the institution will contribute directly 
      towards the project, including funds to be used for materials, equipment, new storage 
      or shelving space, environmental controls, bibliographic activities, etc. Additional 
      sources of funds used to support the project--grants from other agencies, special 
      gifts or endowments, etc.--should be discussed here.<hr/></td>
    </tr>    
    <tr>
      <th>Need for the proposed training.</th>
    </tr>
    <tr>
      <td>Provide a justification for why this training is needed.  Where local institutions 
      surveyed on their preservation education needs?  Have institutions already committed 
      to sending staff?  <hr/></td>
    </tr>   
    <tr>
      <th>Training objectives.</th>
    </tr>
    <tr>
      <td>What information should the attendees leave the training with?  Are there activities
      they will conduct after the workshop?  For example, if the workshop is on disaster 
      preparedness are the attendees expected to have a written disaster plan completed after
      the workshop?  How will that be verified and reported?<hr/></td>
    </tr>
    <tr>
      <th>Publicity.</th>
    </tr>
    <tr>
      <td>How will the training be publicized to potential attendees?  Will listservs, mailing
      lists, newsletters be used? How will wide publicity to all eligible institutions be 
      achieved?<hr/></td>
    </tr>
    <tr>
      <th>Information dissemination.</th>
    </tr>
    <tr>
      <td>How will information materials created during the training be disseminated after 
      the training period?  Will a publication be created; will the training materials be 
      shared on a web site?  <hr/></td>
    </tr>
    
  </table>
  
  
  <h4>Project Budget</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
  <tr>
    <th>Personal Service Expenses</th>
  </tr>
  <tr>
    <td>
        <table width="100%">
        <tr>
          <td colspan="2">Name</td><td colspan="2">Title</td>
          <td>AnnualSalary</td><td>FTE</td>
        </tr>
        <tr>
          <td colspan="2"></td>
          <td colspan="2"></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td width="16%">Proj Total</td><td width="16%">Inst'l Contrib.</td>
          <td width="16%">AmtRequested</td><td width="16%">AmtApproved</td>
          <td width="16%">ExpSubmitted</td><td width="16%">ExpApproved</td>
        </tr>         
        </table><hr/>
    </td>
  </tr>   
  <tr>
    <td height="20" />
  </tr>
   <tr>
   <th>Employee Benefit Expenses</th>
  </tr>  
  <tr>
    <td>
        <table width="100%">
        <tr>
          <td colspan="3">Name</td>
          <td colspan="3">Benefits percentage</td>
        </tr>
        <tr>
          <td colspan="3"></td>
          <td colspan="3"></td>
        </tr>
        <tr>
          <td width="16%">Proj Total</td><td width="16%">Inst'l Contrib.</td>
          <td width="16%">AmtRequested</td><td width="16%">AmtApproved</td>
          <td width="16%">ExpSubmitted</td><td width="16%">ExpApproved</td>
        </tr>  
        </table><hr/>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  <tr>
    <th>Contracted Services Expenses</th>
  </tr>
  <tr>
    <td>                
        <table width="100%">
        <tr>
          <td colspan="2">Service Type</td><td colspan="2">Recipient</td>
          <td colspan="2">Description</td>
        </tr>
        <tr>
          <td colspan="2"></td>
          <td colspan="2"></td>
          <td colspan="2"></td>
        </tr>
        <tr>
          <td width="16%">Proj Total</td><td width="16%">Inst'l Contrib.</td>
          <td width="16%">AmtRequested</td><td width="16%">AmtApproved</td>
          <td width="16%">ExpSubmitted</td><td width="16%">ExpApproved</td>
        </tr>  
        </table><hr/>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>       
  <tr>
    <th>Supplies, Materials and Equipment Expenses</th>
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
          <td width="16%">Proj Total</td><td width="16%">Inst'l Contrib.</td>
          <td width="16%">AmtRequested</td><td width="16%">AmtApproved</td>
          <td width="16%">ExpSubmitted</td><td width="16%">ExpApproved</td>
        </tr>  
        </table><hr/>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  <tr>
    <th>Travel Expenses</th>
  </tr>
  <tr>
    <td>               
        <table width="100%">
        <tr>
          <td colspan="2">Purpose</td>
          <td colspan="2">Description</td>
        </tr>
        <tr>
          <td colspan="2"></td>
          <td colspan="2"></td>
        </tr>
        <tr>
          <td width="16%">Proj Total</td><td width="16%">Inst'l Contrib.</td>
          <td width="16%">AmtRequested</td><td width="16%">AmtApproved</td>
          <td width="16%">ExpSubmitted</td><td width="16%">ExpApproved</td>
        </tr>  
        </table><hr/>
    </td>
  </tr>  
  </table>
  
  
  
  
  <h4>Final Report Narrative</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th>Final Report</th>
    </tr>
    <tr>
      <td> The Final Report should correspond closely to the plan of work that 
        you submitted.  It should begin with a chronological timetable 
        recording the beginning date for the year; hiring dates and duration of work for 
        personnel hired with these funds; consultant's schedules and dates when their reports 
        were received; beginning and ending dates for all contractual services; and dates 
        of all other significant activities carried out during the year.  
        The second part of the narrative should provide a description 
        of every aspect of the expenditure of the funds and how they were accomplished. 
        You should note particularly any changes from your original plan of work. </td>
    </tr>    
  </table>
  <br/>
  
  <h4>Institutional Authorization</h4>
  
  <table align="center" width="90%" class="boxtype" summary="for layout only"  >
    <tr >
      <td colspan="2">I hereby certify that I am the applicant’s chief administrative officer and that the information contained in this application is, to the best of my knowledge, complete and accurate.   I further certify, to the best of my knowledge, that any ensuing program and activity will be conducted in accordance with all applicable Federal and State laws and regulations, application guidelines and instructions, Assurances, Certifications, Appendix A, Appendix A-1G and that the requested budget amounts are necessary for the implementation of this project.  All materials whose preservation is supported by funds from the State are, or will be, made available for reference, on-site examination and/or loan.  It is understood by the applicant that this application constitutes an offer and, if accepted by the NYS Education Department or renegotiated to acceptance, will form a binding agreement.  It is also understood by the applicant that immediate written notice will be provided to the grant program office if at any time the applicant learns that its certification was erroneous when submitted or has become erroneous by reason of changed circumstances.   </td>
    </tr>
    <tr>
      <td width="40%">Date:</td>
      <td>Director of Library/Archives:</td>
    </tr>
    <tr>
      <td width="40%">Date:</td>
      <td>PRESIDENT OF APPLICANT INSTITUTION:</td>
    </tr>
  </table>
  
  <br/><br/><br/>
  
  
  
  </body>
</html>

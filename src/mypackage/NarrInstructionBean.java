package mypackage;

public class NarrInstructionBean 
{
  public NarrInstructionBean()
  {
  }
  
  //these used for html/pdf narrative "print view"
  int[] diNarrTypes = {2,3,6,7,34,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,66,67,68};
  int[] coNarrTypes = {1,2,6,7,8,9,10,11,12,13,14,15,16,17,18,66,67,68,34};
  int[] litNarrTypes ={1,3,5,8,12,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,81,82,119,121};//81and82 added 5/26/10
  //for literacy, added 119 and 121 on 1/19/16 per KBALSEN
  
  int[] litInterimFinalTypes={52,53,54,41,42,43,44,45,46,47,48,49,50,51,55,56,57,58,59,60,61,62,63,64,65};
  int[] litFinal3YearTypes={41,42,43,44,45,46,47,48,49,50,51,55,56,57,58,59,60,61,62,63,64,65,96,97,98,99,100,101,102,103,104,105,106,118,47};
  int[] litFamFinal3YearTypes={41,44,45,46,48,49,50,51,55,58,59,61,62,63,64,65,96,99,100,102,103,104,105,106,111,112,113,114,115,116,118,47};
  int[] saNarrTypes ={1,2,3,4,5};
  int[] staidNarrTypes ={2,3,93};
  int[] lgNarrTypes ={69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88};
  int[] conNarrTypes = {94,12,91,93,95};
  //construction 94=proj descr, 91=impact, 95=abstract, 12=timetable, 93=budget

  public String getNarrType19()
  {
    //SIZE OF INSTITUTION'S OPERATION
    String desc = "Include information on the institution's annual budget for staff, materials, "+
    "operations, etc., and the total number of staff in full time equivalents (FTE).  "+
    "Indicate the number of FTE professional and non-professional staff and the number of volunteers who "+
    "regularly serve in the institution.  It is not necessary for an agency that is one part of a "+
    "larger institution--the library or archives of a college, for example--to provide full information on the "+
    "entire institution.  Reviewers will be most interested in information on the part of the agency in which the "+
    "project will be carried out.  Include additional information on the parent institution only if you think it will provide a more complete background for the project.";
    return desc;
  }
  
  
  public String getNarrType20()
  {
    //TOTAL COLLECTION OF RESEARCH MATERIALS
    String desc = "Include information on: 1. the size of the collection and types of materials "+
    "held- Number of titles (monographs, serials, microforms, etc.), bound volumes, unbound "+
    "materials, maps, photographs, sound recordings, linear feet of manuscripts, etc.  Give "+
    "as complete a quantitative description of the collection as possible, paying particular attention "+
    "to the collection that contains the materials to be preserved.  2. collecting policy and "+
    "sources of materials acquired -Describe collecting policies of the institution or agency "+
    "applying for grant funds.  Pay particular attention to policies affecting the materials to be preserved.  "+
    "Indicate the method by which materials are usually acquired, whether by purchase or donation.  3. number of items "+
    "acquired and expenditure for acquisitions last year -Indicate the quantity of materials added to the collections in "+
    "the most recent year for which figures are available, and the amount of institutional funds expended for acquisitions. "+
    "4. other relevant background information on the nature and use of the collection.";
    return desc;
  }
  
  
  public String getNarrType3()
  {
    //INSTITUTIONAL C/P ACTIVITIES
    String desc = "Describe current as well as long-range plans for conservation/preservation "+
    "activities, operations, and priorities.  If a general preservation survey has been conducted to "+
    "determine collection care needs and priorities, the survey and its conclusions should be described.  You may also include a copy of the survey " +
    "as an attachment to the application if you think this is appropriate.  Indicate the amount of institutional funding used to "+
    "support conservation/preservation activities in the past year, and the institutional funding "+
    "that will be available during the period for which discretionary grant funds are being sought.  "+
    "Also identify other sources of funding for conservation/preservation activities, including grants from other government or private agencies.";
    return desc;
  }
  
  
  public String getNarrType3sa()
  {
    //activities - statutory aid
    String desc = "A. Describe in detail the activities you plan to accomplish this fiscal year "+
    "using NEW YORK STATE AID for conservation/preservation.  Indicate the specific types of "+
    "activities to be performed (see 'Fundable Activities' section of guidelines), and how they will be accomplished.";
    return desc;
  }
  
    
   public String getNarrType4sa()
  {
    //overall plan - statutory aid
    String desc = "B. Describe how these activities relate to those planned for the institution's "+
    "overall preservation program this fiscal year.";
    return desc;
  }
  
   public String getNarrType5sa()
  {
    //five year plan - statutory aid
    String desc = "C. Relate the activities to be performed with State Aid to your five-year plan.";
    return desc;
  }
   
   
   
   
  public String getNarrType3staid()
  {
    //activities - stateaid cjh/nyhs
    String desc = "<b>Description of Activities:</b>  Describe in detail the activities you plan to accomplish this fiscal year "+
    "using NEW YORK STATE AID.  Indicate the specific types of "+
    "activities to be performed, and how they will be accomplished.";
    return desc;
  }
     
  public String getNarrType93staid()
  {
   //budget narrative - stateaid cjh/nyhs
   String desc = "<b>Budget Narrative:</b>  Provide a brief narrative, no more than five hundred (500) words, explaining how "+
                 " expenditures in the proposed budget application attain the institution's service goals for the "+
                 "funding year. ";
   return desc;
  }
  
  
  
  public String getNarrType16()
  {
    //environmental conditions
    String desc = "Indicate the extent to which temperature, humidity, light levels, and air quality "+
    "are monitored and controlled in the areas where materials preserved with grant funds will be housed. "+
    "If improving environmental conditions for storage is the focus of the grant application, "+
    "technical specifications for the proposed equipment must be included.  These must include specific "+
    "information on the levels of temperature, humidity and air quality control possible with the equipment.  "+
    "In addition, a description of how conditions will be monitored must be included.";
    return desc;
  }
  
  public String getNarrType16Co()
  {
    //CO  ENVIRONMENTAL CONDITIONS
    String desc="Indicate the extent to which temperature, humidity, light levels, and air quality "+
    "are monitored and controlled in the areas where materials preserved with grant funds will be housed.";
    return desc;
  }
  
  public String getNarrType2Co()
  {
    //CO  final report
    String desc="The Final Report should correspond closely to the plan of work that you "+
    "submitted.  It should begin with a chronological timetable recording the beginning "+
    "date for the year; hiring dates and duration of work for personnel hired with these "+
    "funds; consultant's schedules and dates when their reports were received; beginning "+
    "and ending dates for all contractual services; and dates of all other significant "+
    "activities carried out during the year. The second part of the narrative should "+
    "provide a description of every aspect of the expenditure of the funds and how they "+
    "were accomplished.  You should note particularly any changes from your original plan "+
    "of work.";
    return desc;
  }
  
  public String getNarrType2Di()
  {
    //DI  final report
    String desc="The Final Report should correspond closely to the plan of work that you "+
    "submitted.  It should begin with a chronological timetable recording the beginning date "+
    "for the project; hiring dates and duration of work for personnel hired with grant funds; "+
    "consultant's schedules and copies of their reports; beginning and ending dates for all "+
    "contractual services; and dates of all other significant activities carried out during "+
    "the project. The second part of the narrative should provide a description of every aspect "+
    "of the expenditure of the funds and how they were accomplished. You should note "+
    "particularly any changes from your original plan of work. <br/><br/>The second part of "+
    "the narrative should provide a description of every aspect of the project and how it was "+
    "accomplished.  You should note particularly any changes from your original plan of work.  "+
    "Be generous with examples, and be specific about the names and significance of the items "+
    "or collections that benefited from your project.  If you have published any articles "+
    "about your project please append copies of these to your report.";
    return desc;
  }
  
  public String getNarrType21()
  {
    //PREPARATIONS FOR DISASTERS
    String desc = "A comprehensive, written plan covering disaster preparedness, response, and salvage "+
    "of all library, archival, or record resources is recommended.  A typical plan would include a summary "+
    "of emergency procedures; lists of persons to be called in case of disaster, including local, regional "+
    "and national consultants, as appropriate; supplies and services available; and other information "+
    "specific to the institution. When a written plan has not been produced, the applicant should indicate "+
    "how emergencies affecting library materials will be dealt with, noting particularly what regional "+
    "resources are available and what arrangements have been made to take advantage of them.";
    return desc;
  }
  
  
  public String getNarrType22()
  {
    //security arrangements
    String desc ="Describe measures taken to avoid theft, loss, mutilation, or inappropriate use of materials.";
    return desc;
  }
  
  public String getNarrType23()
  {
    //PARTICIPATION IN COOPERATIVE, REGIONAL C/P ACTIVITIES
    String desc = "Cooperative activities may include sharing conservation/preservation staff or "+
    "facilities with other institutions; contributing microform records to NUC, OCLC or RLIN, or other "+
    "shared bibliographic databases; using the services of regional conservation centers; participating "+
    "in cooperative microfilming projects, regional or statewide disaster assistance networks, or other "+
    "regional, state or national preservation activities or organizations.  In this section reviewers are "+
    "interested in cooperative regional or statewide conservation/preservation activities only.";
    return desc;
  }
  
  public String getNarrType24()
  {
    //ACCESS POLICIES, PRACTICES OF INST
    String desc= "Indicate the level of service provided (e.g., hours open, number of patrons, number "+
    "of items loaned or used on-site, participation in cooperative access programs such as interlibrary "+
    "loan and regional databases) and any restrictions placed on use of materials.";
    return desc;
  }
  
  public String getNarrType10()
  {
    //cataloging or BIBLIOGRAPHIC CONTROL
    String desc= "Discuss the overall availability of materials by documenting the kind of cataloging "+
    "or other form of bibliographic control used to enable potential users to locate materials.  The "+
    "extent to which the institution makes use of regional databases, OCLC, RLIN, or other on-line "+
    "bibliographic networks should be described.  Use the 'Guidelines' link below for more detailed instructions "+
    " on published and unpublished materials. ";
    return desc;
  }
  
  public String getNarrType10Co()
  {
    //CO  BIBLIOGRAPHIC CONTROL
    String desc="Describe the overall availability of the materials to be preserved by documenting "+
    "the kind of cataloging or other form of bibliographic control used.";
    return desc;
  }
  
  public String getNarrType25()
  {
    //ownership of materials
    String desc= "Indicate whether or not the materials preserved during the project are owned by the "+
    "institution.  If the materials are on deposit, you must provide some evidence of deposit, "+
    "indicating a clear and continuing legal relationship with the owner of the materials that will "+
    "assure they will remain permanently accessible.  A copy of the relevant agreement should be "+
    "included as an attachment to the application.";
    return desc;
  }
  
  public String getNarrType6()
  {
    //descr of materials to be preserved
    String desc= "Indicate the subject area or content, format (book, manuscript, photograph, map, etc.), "+
    "quantity, condition, and specific preservation problems.  Indicate in what way the materials form a "+
    "coherent body of research materials, and how they relate to the institution's overall collecting policy. "+
    "Insofar as possible, describe the type of research for which the materials are likely to be used. ";
    return desc;
  }
  
  public String getNarrType6Co()
  {
    //CO descrip of materials
    String desc="Indicate the subject area or content, format (book, manuscript, photograph, map, "+
    "etc.), quantity, condition, and specific preservation problems, if known. Indicate in what way "+
    "the materials form a coherent body of research materials. Insofar as possible describe the type "+
    "of research for which the materials are likely to be used.";
    return desc;
  }
  
  public String getNarrType7()
  {
    //SIGNIFICANCE OF MATERIALS FOR RESEARCH
    String desc= "Indicate how the materials to be preserved fit in the institution's overall "+
    "collecting policy.  Describe the kinds of information and/or research needs the material meets, "+
    "whether they are of local, regional, national or international significance, the level of demand "+
    "for the material, and whether it is valuable primarily for the information it provides, or as a "+
    "physical artifact.	Compare the material to be preserved with similar collections elsewhere, "+
    "and describe the research that will benefit from the project, or the kinds of users who need "+
    "access to the material.  If planning to microfilm a collection, monographic or serial titles must be searched in "+
    "RLIN and OCLC to avoid duplication.	Describe the project in terms of the long-range plans and priorities of the institution. "+
    "Explain why priority was given to these rather than to other items in the collection that also need preservation.  "+
    " Use the 'Guidelines' link below for more detailed instructions regarding 'significance of materials'.";
    return desc;
  }
  
  public String getNarrType7Co()
  {
    //CO significane of materials
    String desc="Describe the kinds of information and/or research needs the materials meets, whether "+
    "they are of local, regional, national or international significance, the level of demand for the "+
    "materials, and whether it is valuable primarily for the information it provides, or as physical "+
    "artifacts.";
    return desc;
  }
  
  public String getNarrType12()
  {
    //TIMETABLE
    String desc= "The timetable should be as specific as possible, indicating the projected beginning "+
    "date for the project; hiring dates and duration of work for personnel to be hired with "+
    "discretionary grant funds; schedules for existing staff who will contribute some portion of their "+
    "time to the project; consulting schedules, including due dates of reports; projected beginning and "+
    "ending dates for all contractual services; and schedules for all other significant activities "+
    "proposed for the project.  Reviewers will look for evidence of the institution's capacity to manage and "+
    "complete the project in the established timetable.";
    return desc;
  }
  
  public String getNarrType12Co()
  {
    //CO   TIMETABLE
    String desc="The timetable may be either graphic or narrative. It should be as specific as possible, "+
    "indicting the projected beginning date for the project; hiring dates and duration of work for "+
    "personnel to be hired with grant funds; schedules for existing staff who will contribute some portion "+
    "of their time to the project; consulting schedules, including due dates of reports; projected "+
    "beginning and ending dates for all contractual services; and schedules for all other significant "+
    "activities proposed for the project. Multi-year project proposal must have a projected timetable "+
    "for the entire project.";
    return desc;
  }
  
  public String getNarrType13()
  {
    //c/p activities to be carried out during the project
    String desc= "Provide a detailed description of all the activities that make up the project.  Applications "+
    "should demonstrate a degree of familiarity with current theory and practice in the field "+
    "to assure reviewers that the project will be carried out in accordance with currently "+
    "recognized technical standards and/or procedures used in well-established preservation "+
    "programs.	The application must describe the project in sufficient detail to enable "+
    "reviewers to assess the technical soundness of the proposed techniques, the appropriateness "+
    "of materials and methods to be employed, and the suitability of the plan of work for "+
    "the preservation of the specific library materials to be preserved.  Vendors' "+
    "treatment proposals and cost estimates must be included.  The treatment proposal should describe the "+
    "work to be performed, the materials and techniques to be used, the estimated number of hours required "+
    "to complete the work, the cost per hour, or some equivalent breakdown of the cost estimate.";
    return desc;
  }
  
  public String getNarrType13Co()
  {
    // CO  C/P ACTIVITIES
    String desc="Provide a detailed description of how the materials will be preserved, including all "+
    "the activities that make up the project. Specific production goals and units costs should be "+
    "included. In the case of multi-year projects these goals and costs must be correlated with "+
    "previous and/or future years of the project. Vendors’ treatment proposals and/or cost estimates "+
    "must be included. The treatment proposal should describe the work to be performed, the materials "+
    "and techniques to be used, the estimated number of hours required to complete the work, the cost "+
    "per hour, or some equivalent breakdown of the cost estimate.";
    return desc;
  }
  
  public String getNarrType15()
  {
    //personnel and vendors
    String desc= "Indicate the name, position, and qualifications of the person who will have responsibility "+
    "for the day-to-day operation of the project (Project Manager).  Include a list of all "+
    "personnel to be hired for the project and those on the institution's staff who will perform "+
    "project activities.  Describe the specific project responsibilities of all personnel. Job descriptions "+
    "and resumes must be included for positions to be paid for with grant funds. List the "+
    "name of any consultants to be used in the project.  Explain what role the consultants will have "+
    "in the project and which project activities they will perform.  Consultant resumes and workplans for the project must be included.  "+
    "List the names and addresses of all vendors to be used in the project and the specific services they will provide.  "+
    "Include vendors written estimates for the services they will provide.  The estimates must "+
    "be obtained and be included in the application.  At least two estimates must be obtained. "+
    "This does not mean the low bid must automatically be accepted.  Justification must be given for the vendor chosen. "+
    "Use the 'Guidelines' link below for more detailed instruction regarding personnel and vendors.";
    return desc;
  }
  
  public String getNarrType15Co()
  {
    //CO  PERSONNEL AND VENDORS
    String desc="Indicate the name and position of the person who will have responsibility for the "+
    "day-to-day operation of the project. Indicate all other personnel to be hired for the project "+
    "and those on the institution’s staff who will perform project activities. Describe the specific "+
    "project responsibilities of all personnel. List the name of any consultants to be used in the "+
    "project and include their resume and qualifications. List the name and addresses of all vendors "+
    "to be used in the project and the specific services they will provide.";
    return desc;
  }
  
  public String getNarrType17()
  {
    //staff contributions
    String desc= "Indicate the amount of time existing staff will contribute directly to the project.  "+
    "Also include any time volunteered by outside professionals or other non-institutional staff.  "+
    "For survey projects, a minimum of 10 staff/volunteer hours must be contributed; for all other types of projects, the minimum is 20 staff/volunteer hours.  "+
    "Reviewers will also expect to find evidence of appropriate staff to provide ongoing care for "+
    "collections once the project is completed.  Such care includes maintenance, appropriate updating "+
    "of catalogs or bibliographies, and reference service or other user support appropriate to the "+
    "materials and the overall purposes of the institution.";
    return desc;
  }
  
  public String getNarrType17Co()
  {
    //CO  STAFF CONTRIBUTIONS
    String desc="Indicate the amount of time existing staff in each participating institution will "+
    "contribute directly to the project.";
    return desc;
  }
  
  
  public String getNarrType18()
  {
    //financial contributions
    String desc= "Each institution should contribute at least 10% of the cost of the project. Indicate the amount of financial support the institution will contribute directly "+
    "towards the project, including funds to be used for materials, equipment, new storage or shelving "+
    "space, environmental controls, bibliographic activities, staff time, etc.  Additional sources of funds used to "+
    "support the project--grants from other agencies, special gifts or endowments, etc.--should be discussed here.  "+
    "These costs will be documented in the project budget.";
    return desc;
  }
  
  public String getNarrType66()
  {
    //need for proposed training
    String desc= "Provide a justification for why this training is needed.  Were local "+
    "institutions surveyed on their preservation education needs?  Have institutions already committed to sending staff?";
    return desc;
  }
  
  public String getNarrType67()
  {
    //training objectives
    String desc= "What information should the attendees leave the training with?  Are there "+
    "activities they will conduct after the workshop?  For example, if the workshop is on "+
    "disaster preparedness are the attendees expected to have a written disaster plan completed after the workshop?  How will that be verified and reported?";
    return desc;
  }
  
  public String getNarrType68()
  {
    //publicity
    String desc= "How will the training be publicized to potential attendees?  Will listservs, "+
    "mailing lists, newsletters be used? How will wide publicity to all eligible institutions be achieved?";
    return desc;
  }
  
  public String getNarrType34()
  {
    //information dissemination
    String desc= "How will information materials created during the training be disseminated after the "+
    "training period?  Will a publication be created; will the training materials be shared on a web site?";
    return desc;
  }

  public String getNarrType1()
  {
    //SUMMARY DESCRIPTION -CO
    String desc="Provide a summary description of the project.  The summary should not exceed "+
    "one page in length, and should briefly describe the purpose of the project and how it will be "+
    "accomplished.  Although the remainder of the project description section will provide specific "+
    "details, it is important that your summary provide a clear and concise description.";
    return desc;
  }
  
  public String getNarrType1Li()
  {
    //SUMMARY DESCRIPTION -Li
    String desc="Write a brief one-paragraph synopsis of the project's purpose and target group. Do not describe activities here.  "+
    "The Summary Description field is limited to a <b>maximum of 2000 characters</b>, including spaces.";
    return desc;
  }
  
  public String getNarrType26Li()
  {
    //LI PROJECT NEED/TARGET AUDIENCE
    String desc="Need: Provide documentation of a need in the community for such a project.  "+
    "This information can come from authorities in the field, as well as your own "+
    " experience.<br/><br/>Target Audience: Please identify your target audience(s) for each year and how you will reach them.";
    return desc;
  }
  
  public String getNarrType5Li()
  {
    //LI LONG RANGE PLAN
    String desc="Explain how the project relates to the system's Plan of Service and Library's "+
    " long-range plan.  The library's long range plan should have literacy goals that support "+
    " your NYS Family or Adult Literacy project. The long range plan is part of Minimum Standards "+
    " for Public Libraries in New York State "+
    "(see <a href='http://www.nysl.nysed.gov/libdev/ministan.htm' target='_blank'>http://www.nysl.nysed.gov/libdev/ministan.htm</a>)";
    return desc;
  }
  
  public String getNarrType27Li()
  {
    //LI PROGRAMMING
    String desc="How will the project improve or expand current library programming?";
    return desc;
  }
  
  public String getNarrType8Li()
  {
    //LI cooperating libraries
    String desc="List partner literacy providers and other organizations cooperating in "+
    "this project and outline the role and relationship of each agency in planning, "+
    "implementation, and evaluation. Attach letters of support.";
    return desc;
  }
  
  public String getNarrType28Li(int fccode)//different for AL vs FL 1/19/16
  {
    //LI PROJECT OBJECTIVES
    String desc="";
    if(fccode==40)//adult
        desc="What is the project's goal(s)? Please list goal(s) and project objectives(s) associated with each goal.";
    else//family
        desc="What is the project's goal(s)? Please list goal(s) and project objectives(s) associated with each goal. One goal and/or objective must relate to participation of libraries and library staff in Ready to Read at New York Libraries: Early Childhood Public Library Staff Development Program.";
    return desc;
  }
  
   public String getNarrType3Li()
  {
    //LI ACTIVITIES
    String desc="Briefly describe the activities planned to accomplish each objective.";
    return desc;
  }
  
   public String getNarrType12Li()
  {
    //LI TIMETABLE
    String desc="Provide a time-line of project activities for all three years.";
    return desc;
  }
  
   public String getNarrType29Li(int fccode)
  {
    //LI PROJEC TOUTPUT
    //modified 1/19/16 different for AL vs FL per KBALSEN
    String desc = "";
    if(fccode==40)//ADULT
        desc="What will you measure to provide quantitative data about your project’s service "+
        "or product outputs? (NOTE: Outputs are a direct program product, typically measured in numbers, "+
        "such as participants served, workshops given, materials developed, etc.)";
    else//FAMILY
        desc="In addition to the NYSL prescribed training Outputs (described in IV a above), what will you measure to provide quantitative data about your project’s service "+
        "or product outputs? (NOTE: Outputs are a direct program product, typically measured in numbers, "+
        "such as participants served, workshops given, materials developed, etc.)";
    return desc;
  }
  
   public String getNarrType30Li()
  {
    //LI MEASURE OUTPUT
    String desc="How will you measure the outputs?";
    return desc;
  }
  
   public String getNarrType31Li(int fccode)
  {
    //LI PROJECT OUTCOME/IMPACT
    //modified 1/19/16 different for AL vs FL per KBALSEN
    String desc = "";
    if(fccode==40)//ADULT
        desc="What will you measure to show the outcomes or impact of your services or "+
        "products on the identified target population? (NOTE: Outcomes involve changes in the "+
        "target audience’s skills, attitudes, knowledge, behavior, status, etc. as a result of this project.)";
    else//FAMILY
        desc="In addition to the NYSL prescribed training Outcomes (described in IV a above), what will you measure to show the outcomes or impact of your services or " + 
        " products on the identified target population? (NOTE: Outcomes involve changes in the " + 
        " target audience’s skills, attitudes, knowledge, behavior, status, etc. as a result of this project.)";
    return desc;
  }
  
  public String getNarrType32Li()
  {
    //LI MEASURE OUTCOME
    String desc="How will you measure the outcomes listed in 'Project Outcomes'?";
    return desc;
  }
  
  public String getNarrType33Li()
  {
    //LI PROJECT CONTINUATION
    String desc="Briefly describe how, if successful, this project will be continued after the "+
    "grant project ends. Include information on partners and sources of funding.";
    return desc;
  }
  
  public String getNarrType34Li()
  {
    //LI SHARE INFORMATION 
    String desc="Please indicate how you and your project partners will share information with "+
    "libraries and other interested organizations in New York State about the project results. "+
    "Ex. Library or System Website, NYLINE, NYLA presentation, poster session, sharing session "+
    "Library or System and other professional meetings, Articles in newsletters, journals, etc., Other (please describe)";
    return desc;
  }
  
  public String getNarrType36Li()
  {
    //LI other funding
    String desc="List the dollar amount and source of other funds or in-kind services, for each of the three years, that will be used to carry out the project.";
    return desc;
  }
  
  public String getNarrType35Li()
  {
    //LI project budget
    String desc="Describe how the positions identified on the 'Salary' budget tab will support the "+
    "project activities and contribute to program goals.  NOTE:  The budget narrative may be "+
    "entered and edited in the Application Narrative or in the Project Budget section of the "+
    "application.   Identical text will appear in both locations.";
    return desc;
  }
  
   public String getNarrType37Li()
  {
    //LI project budget
    String desc="Explain the importance of items listed on 'Benefits' budget tab and how it "+
    "contributes to program goals.  NOTE:  The budget narrative may be "+
    "entered and edited in the Application Narrative or in the Project Budget section of the "+
    "application.   Identical text will appear in both locations.";
    return desc;
  }
  
  public String getNarrType38Li()
  {
    //LI project budget - purchased
    String desc="Describe how funds will be used to support the project activities and contribute to program goals. In the boxes provided below, Purchased Services Budget Summary, enter the dollar amount anticipated for each of the 3 years of the program, the Service Type, and Consultant/vendor (if known).";
    return desc;
  }
  
   public String getNarrType39Li()
  {
    //LI project budget - supplies/materials starting 16-19 (previously was supplies/equipment)
    String desc="Describe how these funds will be used to support the project activities and contribute to program goals. In the boxes provided below, Supply/Material Budget Summary, enter the dollar amount anticipated for each of the 3 years of the program, description of the item, and Vendor (if known).";
    return desc;
  }
  
   public String getNarrType40Li()
  {
    //LI project budget - travel
    String desc="Describe how these funds will be used to support the project activities and contribute to program goals. In the boxes provided below, Travel Budget Summary, enter the dollar amount anticipated for each of the 3 years of the program.";
    return desc;
  }
   
  public String getNarrType121Li()
  {
   //LI project budget - equipment. new 1/25/16
   String desc="Equipment is described as individual items each over $500 in cost. Describe how these funds will be used to support the project activities and contribute to program goals. In the boxes provided below, Equipment Budget Summary, enter the dollar amount anticipated for each of the 3 years of the program, description of the item, and Vendor (if known).";
   return desc;
  }
  
  public String getNarrType122Li()
  {
   //LI goals/objectives/activities. new 2/22/16 for final rpts 2016-19
   String desc="List each goal/objective followed by the activities to meet those goals/objectives carried out during this reporting year (July – June).";
   return desc;
  }
  
  public String getNarrType125Li()
  {
   //LI evaluation outcomes/outputs. new 2/22/16 for final rpts 2016-19
   String desc="Summary of Evaluation Outcomes/Outputs for this year";
   return desc;
  }
    
   public String getNarrType41Li()
  {
    //LI Synopsis
    String desc="Give a brief (1 paragraph) synopsis of the project purpose, activities, and results";
    return desc;
  }
  
   public String getNarrType42Li()
  {
    //LI need
    String desc="Briefly summarize the need for this project.";
    return desc;
  }
  
  public String getNarrType43Li()
  {
    //LI target audience
    String desc="Briefly describe the target audience.";
    return desc;
  }
  
  public String getNarrType44Li()
  {
    //LI coordination
    String desc="List the cooperating agencies in this project and identify their role(s) in "+
    "the project including 1) Planning; 2) Project Activities; 3) Evaluation";
    return desc;
  }
  
  public String getNarrType45Li()
  {
    //LI accomplishments
    String desc="Using the OBJECTIVES and the ACTIVITIES as they were stated in the original "+
    "project application, describe project accomplishments.";
    return desc;
  }
  
  public String getNarrType46Li(int fccode, int fycode)
  {
    //LI evaluation
    String desc="";
    if(fccode==42 && fycode>13){//family lit starting fy 2013-14
      desc="You are expected to provide an Outcome-Based evaluation (OBE) of your project, "+
       "involving outputs and outcomes. (More information regarding OBE can be found at: "+
       "http://www.nysl.nysed.gov/libdev/obe/index.html) Briefly describe evaluation "+
       "methods used. Include a summary of the quantitative and qualitative evaluation "+
       "results and user satisfaction data. Please provide a minimum of two outcomes and "+
       "how they were measured. Please, also, attach a minimum of two samples of your "+
       "library or system’s evaluation instruments.";
    }else{
      desc="Briefly describe evaluation methods used. Include a summary of the "+
      "quantitative evaluation results and user satisfaction data. Please attach samples "+
      "of your library or system’s evaluation instruments.";
    }
    return desc;
  }
  
  public String getNarrType47Li()
  {
    //LI continuation
    String desc="Does the library system plan to continue this project after this three year literacy program cycle is complete? If the answer is YES, how will the project be continued? Or if the answer is NO, what would you need to continue this project?";
    return desc;
  }
  
  public String getNarrType48Li(int fccode, int fycode)
  {
    //LI share results
    String desc="";
    if(fccode==42 && fycode>13){//family lit starting fy 2013-14
      desc="How was the project publicized? How effective were these methods?";
    }else{
      desc="How has your library or system shared project results with others in your region and statewide?";
    }
    return desc;
  }
  
  public String getNarrType49Li()
  {
    //LI problems
    String desc="Describe any problems or surprises you encountered in working toward the "+
    "objectives for this project. What would you do differently?";
    return desc;
  }
  
  public String getNarrType50Li(int fccode, int fycode)
  {
    //LI budget changes
    String desc="";
    if(fycode>16){//starting fy 2016-19 per kbalsen
        desc = "Explain changes to the Application’s original budget.";      
    }
    else if(fccode==42 && fycode>13){//family lit starting fy 2013-14
        desc="What increases/decreases would you recommend in budget spending?  Do you "+
        "need to make changes to the next year’s FS-10? Please contact Carol A. Desch at "+
        "518-474-7196 or carol.desch@nysed.gov. ";
    }else{
      desc="Explain any increases/decreases in budget expenses. Describe any changes "+
      " to the original project budget, such as change in vendors, supplies, materials & equipment, etc.";
    }
    return desc;
  }
  
  public String getNarrType51Li()
  {
    //LI budget changes
    String desc="List additional funds used for this project and where funds were obtained. "+
    "Estimate the in-kind costs incurred by the library and cooperating agencies.";
    return desc;
  }
  
  public String getNarrType111Li()
  {
    //LI finalrpt FL 2013-14; planning experience
    String desc="How has planning for the upcoming summer reading season been impacted "+
                "by activities carried out during the previous summer reading season? "+
                "(Ex. Adjustment to planning and training for 2014 based on information "+
                "learned during the 2013 summer reading season.)";
    return desc;
  }
  
  public String getNarrType114Li()
  {
    //LI finalrpt FL 2013-14; family component
    String desc="How did the family component impact the program?";
    return desc;
  }
  
  public String getNarrType118Li(int fycode)
  {
    //LI finalrpt new summary narrative 7/7/15 per KBALSEN
      String desc="";
    if(fycode<16)
      desc="Please add here a brief summary of activities and outcomes of the entire 3 year cycle of the program (not to exceed 2000 characters).";
    else
      desc="Please add a brief summary of goals/objectives and  activities and outcomes for the entire 3 year cycle of the program (2000 characters).";
    return desc;
  }
  
    public String getNarrType119Li()
    {
      //LI new FL training plan 1/19/16 per KBALSEN
      String desc="Please describe the 3-year training plan the system will implement to train library staff in the components of Ready to Read at New York Libraries: Early Childhood Public library Staff Development Program as well as any additional early literacy training offered to library staff. ";
      return desc;
    }
    
    public String getNarrType120Li()
    {
      //LI new FL nysl prescribed training 1/19/16 per KBALSEN
      String desc="Following are NYSL prescribed final evaluation quantitative (output) and qualitative (outcomes) questions for reporting at the end of each year of the program. ";
      return desc;
    }
  
  public String getNarrType52Li()
  {
    //LI project changes
    String desc="Please describe any changes to timeline, project director, project partners, project "+
    " objectives, or project activities.";
    return desc;
  }
  
  public String getNarrType53Li()
  {
    //LI expended funds
   /* modified wording 4/3/12 per ChrisMoesch - even though wording makes no sense after 2 emails for clarification
    * String desc="Will all funds be expended by December 31, 2010? If not, please explain. (Reminder: "+
    "There is no roll-over of funds to year two.)";*/
    String desc="If not, please explain. (Reminder: "+
    "There is no roll-over of funds to year two.)";    
    return desc;
  }
  
  public String getNarrType54Li()
  {
    //LI anecdote
    String desc="If you have an anecdote that demonstrates how this project is affecting people who "+
    " received services, please report it here. Such anecdotes are very helpful in advocating for "+
    " continuation of funds.";
    return desc;
  }
  
  
  
  
  
  
  
  public String getNarrType8Co()
  {
    //CO- PARTICIPATING LIBRARIES
    String desc="Describe the project activities of each of the participating libraries, and how this "+
    "project will service their preservation needs.";
    return desc;
  }

  public String getNarrType9Co()
  {
    //CO  COORDINATED ACTIVITIES
    String desc="Describe the steps taken to ensure that the preservation effort has been coordinated "+
    "with other comprehensive research libraries and will not unnecessarily duplicate efforts elsewhere.";
    return desc;
  }

  public String getNarrType11Co()
  {
    //CO  ONLINE DATABASES
    String desc="Describe the extent to which the participating libraries will make use of national "+
    "databases to indicate intent to film, record preservation information, etc. Indicate the "+
    "projected number of records to be entered as a result of project activities.";
    return desc;
  }

  public String getNarrType14Co()
  {
    //CO  SPECIAL EQUIP
    String desc="Describe any equipment to be purchased with grant funds and indicate its specific "+
    "function as it relates to the goals of the project. If special facilities are required "+
    "(microfilm labs, sound recording studios, research labs, treatment facilities, etc.) describe "+
    "how they are or will be made adequate to perform the work outlined in the project.";
    return desc;
  }
  
//=====================================================================================
  public String getNarrType69()
  {
    //LG PROBLEM DESCRIBED
    String desc="1a.  Describe the specific records management problem the project will address, "+
    "providing qualitative descriptions and quantitative data about the problem.  Please also "+
    "explain why the project is considered a high priority.  (10 points)<br/>" +
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></p>";
    return desc;
  }
  
  
  public String getNarrType69New()
  {
    //LG PROBLEM DESCRIBED
    String desc="1a.  Describe the specific records management problem this proposed project will address, " +
        "provide qualitative descriptions and quantitative data about the problem, and explain why this particular project " +
        "is a high priority for your records management program. <b>Do not discuss any proposed solutions here, only " +
        "the problems. Discuss solutions in Intended Results (2a).  (10 points)</b><br/>" +
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></p>";
    return desc;
  }
  
  
  public String getNarrType69NewDm()
  {
    //LG PROBLEM DESCRIBED
    String desc="<b>1a. Defining the Problem:</b> Describe the specific records management problem this proposed project will address, provide qualitative descriptions and quantitative data about the problem, and explain why this particular project is a high priority for your records management program. <b>Do not discuss any proposed solutions here, only the problems. Discuss solutions in Intended Results (2a).</b> (5 points)   </b><br/>" +
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></p>";
    return desc;
  }
  
  
  public String getNarrType70()
  {
    //LG Records Identified
    String desc="1b.  Identify the specific records that will be involved, and any previous "+
    "grant-funded projects related to these records and this project.  (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  public String getNarrType70New()
  {
    //LG Records Identified
    String desc="1b.  Identify the specific records that will be involved in this project, and include the records "+
                "series titles, retention periods, and volume of each records series. Identify any previous "+
                "grant-funded projects related to these records and this project, identifying the ranges of records "+
                "involved and why this project would not replicate work already completed and would not constitute an "+
                "ineligible request for maintenance. If your government has not received any past projects relevant to "+
                "your current application, indicate so. If the proposed project includes imaging or microfilming, provide "+
                "the required description and condition of the records in this section.  (10 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType70NewDm()
  {
    //LG Records Identified
    String desc="<b>1b. Defining Records Involved and Previous Grants:</b> Identify the specific records that will be involved in this project, and include the series titles, retention periods, and volume of each records series. If the proposed project includes imaging or microfilming, provide the required description and condition of each records series in this section. Identify any previous grant-funded projects related to these records series and this project, identifying the names and date ranges of records involved and why this project would not replicate work already completed and, thus, would not constitute an ineligible request for maintenance. If your government has not received any past projects relevant to your current application, indicate so. (10 points)  <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  
  public String getNarrType71()
  {
    //LG Funding Essential 
    String desc="1c.  Explain why funding from this program is essential to accomplishing the project. "+
    "For example, explain why you need funding if you’ve previously received funding for a similar "+
    "project.  (5 points)<br/><div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType72()
  {
    //LG Intended Results and Benefits
    String desc="2a. Identify each intended result (specific products, time and cost savings, or "+
    "services), and describe the anticipated benefits of the project.  (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType72New()
  {
    //LG Intended Results and Benefits
    String desc="2a. Explain why the methodology you chose to solve your records management problem was the best "+
                "one. Explain what other methodologies you considered, detail why these were rejected, and "+
                "demonstrate why the chosen methodology was the best. (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  } 
  
  
  public String getNarrType72NewDm()
  {
    //LG Intended Results and Benefits
    String desc="<b>2a. Methodology:</b> Explain why the methodology you chose to solve your records management problem was the best one. Explain what other methodologies you considered, detail why these were rejected, and demonstrate why the chosen methodology was the best.  (10 points) <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  } 
  
  
  public String getNarrType73()
  {
    //LG Contribution to RM Program
    String desc="2b.  Describe in detail how the project will contribute to the development of a "+
    "Records Management (RM) program.  (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType73New()
  {
    //LG Contribution to RM Program
    String desc="2b.  Identify each intended result or anticipated benefit of this project and your chosen "+
                "solution, including specific products, time and cost savings, and service improvements. Describe "+
                "how the anticipated benefits of this project will contribute to the development of a records "+
                "management program or enhance an already existing program. Provide both qualitative and "+
                "quantitative data to support your arguments about the benefits of this project.  (10 points) <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
    
  public String getNarrType73NewDm()
  {
    //LG Contribution to RM Program
    String desc="<b>2b. Anticipated Benefits:</b> Identify each intended result or anticipated benefit of this project and your chosen solution, including specific products, time and cost savings, and service improvements. Describe how the anticipated benefits of this project will contribute to the development of a records management program or enhance an already existing program. Provide both qualitative and quantitative data to support your arguments about the benefits of this project. (10 points)  <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  public String getNarrType74()
  {
    //LG Outline and Timetable
    String desc="3a.  Provide a detailed outline of the proposed work activities and a timetable that "+
    "shows when each phase of the project will be completed, demonstrates the soundness of the "+
    "method proposed, and demonstrates the project’s goals are attainable by 30 June 2014.  (15 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType74New()
  {
    //LG Outline and Timetable
    String desc="3a.  Provide a detailed outline of the proposed work activities including a detailed description of "+
                "each workstep and a timetable that shows when each phase of the project will be completed. Show how you "+
                "calculated estimated work rates to prove that your local government can attain all of the project’s "+
                "goals by 30 June 2015.  (15 points) <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType74NewDm()
  {
    //LG Outline and Timetable
    String desc="<b>3a. Project Outline:</b> Provide a detailed outline of the proposed work activities including a detailed description of each workstep and a timetable that shows when each phase of the project will be completed. Show how you calculated estimated work rates to prove that your local government can attain all the project’s goals by the end of the grant project period. (15 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  
   public String getNarrType75()
  {
    //LG Requirements Addressed
    String desc="3b.  Address each of the requirements of the relevant project category.  (10 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
   
  public String getNarrType75New()
  {
   //LG Requirements Addressed
   String desc="3b.  Address each of the general application, project type, and project category requirements.  If "+
               "your application combines elements of two or more grant categories, address the requirements of each. (10 points)<br/>"+
   "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
   return desc;
  }
  
  public String getNarrType75NewDm()
  {
   //LG Requirements Addressed
   String desc="<b>3b. Grant Requirements:</b> Address each of the general application, project type, and project category requirements. If your application combines elements of two or more grant categories, address the requirements of each. (15 points)  <br/>"+
   "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
   return desc;
  }
  

   public String getNarrType76()
  {
    //LG Responsibilities and Qualifications
    String desc="3c.  Explain who will be responsible for performing each project activity, including "+
    "project management.  Indicate the qualifications of key project staff (including consultants "+
    "and vendors) in terms of education, training, and experience.  (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
   
   
  public String getNarrType76New()
  {
   //LG Responsibilities and Qualifications
   String desc="3c.  Explain who will perform each project activity, including project management. Indicate the "+
               "qualifications of key project staff (including consultants and vendors) and explain how and why "+
               "they are qualified to conduct their assigned tasks for this project.  (5 points)<br/>"+
   "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
   return desc;
  }
  
  
  public String getNarrType77()
  {
    //LG Contributions Demonstrated
    String desc="4a.  It is important to demonstrate your commitment to records management.  Types of "+
    "support may include government funds, staffing, supplies, or the allocation of space.  "+
    "Provide specific budget amounts whenever possible.  Include information only on the support "+
    "your local government provided and will provide with its own funds.  Projects completely "+
    "funded by the LGRMIF do not constitute local support. (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  public String getNarrType77New()
  {
    //LG Contributions Demonstrated
    String desc="4a.  Demonstrate your local government’s contributions to this project, including funds, staffing, "+
                "equipment, supplies, or the allocation of space. Also, demonstrate your local government’s "+
                "contributions to its records management program, demonstrating its commitment to records "+
                "management. Provide specific budget amounts whenever possible. Include only the financial and other "+
                "support your local government has provided and will provide with its own funds.  <b>Note that grant "+
                "projects funded by " +
        "the LGRMIF do not constitute local support and must not be listed in this section.</b> (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType77NewDm()
  {
    //LG Contributions Demonstrated
    String desc="<b>4a. Previous Records Management and Current Project Support:</b> Demonstrate your local government’s contributions to this project, including funds, staffing, equipment, supplies, or the allocation of space. Also, demonstrate your local government’s contributions to its records management program, demonstrating its commitment to records management. Provide specific budget amounts whenever possible. Include only the financial and other support your local government has provided and will provide with its own funds. Note that previous grant projects funded by the LGRMIF do not constitute local support and must not be listed in this section. (5 points)  <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  public String getNarrType78()
  {
    //LG Program Maintenance
    String desc="4b.  Describe how this project and records management in general will be maintained "+
    "over the long term.   (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  
  public String getNarrType78New()
  {
    //LG Program Maintenance
    String desc="4b.  Provide concrete information to demonstrate how you will maintain the results of this project "+
                "long term without additional LGRMIF grant funding. If additional funding will be required in the "+
                "short term, explain why.  (5 points)<br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
  public String getNarrType78NewDm()
  {
    //LG Program Maintenance
    String desc="<b>4b. Future Program Support:</b> Provide concrete information to demonstrate how you will maintain the results of this project long term without additional LGRMIF grant funding. If additional funding will be required in the short term, explain why. (5 points)   <br/>"+
    "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
    return desc;
  }
  
    public String getNarrTypeLg88()
    {
      //LG contributions public
      String desc="2c.  Describe in detail how the project will improve local "+
      "government services to the public.  (5 points)<br/>"+
      "<div align='center'><a href='docs/lgrmif/guidelines.htm' target='_blank'>Category Requirements (General & Specific)</a></div>";
      return desc;
    }
  
  public String getNarrTypeLg35()
  {
    //LG prof/suport salaries
    String desc="Justify in detail the need for these positions and clearly outline the responsibilities of the "+
    "positions. Demonstrate why the requested number of hours is needed. Explain how the project staff will "+
    "support project activities and goals.   "+
    "<a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg82()
  {
    //LG equip
    String desc="Describe how the requested equipment will be used to support project activities and goals, and "+
    "demonstrate why this particular equipment is critical to the project’s success. Demonstrate how such equipment "+
    "will be used on an ongoing basis after the grant to support records management.   "+
    "<a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg79()
  {
    //LG remodeling
    String desc="Justify the need for the particular remodeling requested and why it is essential to the project.   <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>"+
    "Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  
  public String getNarrTypeLg38()
  {
    //LG purchased serv
    String desc="Describe how each of the purchased services supports the project’s activities and goals. Clearly "+
    "explain and justify the consultant or vendor’s role in and time spent on the project, and demonstrate that "+
    "the consultant or vendor is qualified to conduct this work. List purchased services from a BOCES "+
                "under Code 49.   "+
    " <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>"+
    "Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg80()
  {
    //LG purch serv boces
    String desc="Describe how each of the purchased services with BOCES supports project activities and goals. Clearly "+
    "explain and justify the consultant or vendor’s role in and time spent on the project, and demonstrate that "+
    "the BOCES is qualified to conduct this work.   <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg39()
  {
    //LG supp/materials
    String desc="Describe how all the supplies and materials requested will support the project activities and "+
    "goals and why they are essential to the project.     <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>"+
    "Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg40()
  {
    //LG travel
    String desc="Explain how the proposed travel will help achieve the intended results outlined in the "+
    "application and why it is essential to the project.   <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg37()
  {
    //LG benefits
    String desc="Justify the need for using grant funds to pay staff benefits. Provide justification for any fringe "+
    "benefits that exceed 35% of the cost of the salaries requested.   <a href='docs/lgrmif/budgetEligibility.htm' target='_blank'>"+
    "Eligible/Ineligible Expenditures and Other Required Forms</a>";
    return desc;
  }
  
  public String getNarrTypeLg2()
  {
    //LG final narr
    String desc="The Final Project Narrative should correspond closely to the plan of work that you "+
    "submitted. It should begin with a chronological timetable recording the beginning date for "+
    "the project; hiring dates and duration of work for personnel hired with grant funds; consultant's "+
    "schedules and copies of their reports; beginning and ending dates for all contractual services; "+
    "and dates of all other significant activities carried out during the project. <br/><br/>The "+
    "second part of the narrative should provide a description of every aspect of the project and "+
    "how it was accomplished.  You should note particularly any changes from your original plan of "+
    "work.  Be generous with examples, and be specific about the names and significance of the items "+
    "or collections that benefited from your project.<br/><br/>The final part of the narrative "+
    "should detail the most important outcome and specific benefits of the project, and the plans "+
    "in place to maintain the local government’s records management program.";
    return desc;
  }
  
  
  
  public String getNarrTypeLg2New()
  {
    //LG final narr starting fy 2014-15; but also for 2013-14
    String desc="The Final Project Narrative should correspond closely to the plan of work that you submitted. " +
        "It should contain details relevant to the following questions: <br/><br/> " + 
    "1.  What was the records management problem you were trying to solve? <br/>" + 
    "2.  How did you solve this problem, and what was the timeline for the project?<br/>" + 
    "3.  What were you not able to accomplish? <br/>" + 
    "4.  How did you support this project, and how will you maintain the results long term? <br/>" + 
    "5.  What are the anticipated future cost savings from this project? (Show cost savings calculations) <br/>";
                
    return desc;
  }
  
   public String getNarrTypeCon94()
   {
      //construct summary descr
      String desc="Include a complete description of the project for which applicant is "+
      "requesting funding. If this project is part of a larger project during this grant funding "+
      "period (July 1, 2015 - June 30, 2018), please describe the entire project. "+
      " When a project is part of a larger project identify both clearly so that the application "+
      " project can be easily identified within the larger project description.  "+
      " Describe construction activities including the intended "+
      "physical alteration or improvement to the building.";
      return desc;
   }
   
    public String getNarrTypeCon95()
    {
       //construct abstract
       String desc="Provide a brief description of the construction project. Note: "+
       " The Project Abstract field is limited to a <b>maximum of 150 characters</b>, including spaces.";
       return desc;
    }
   
    public String getNarrTypeCon91()
    {
       //construct impact
       String desc="Describe how the project will address one or more of the following "+
       "Public Library Construction Grant Program priorities: <br/>" + 
       "-increased effectiveness of library service due to increased and/or improved building space and capacity <br/>" + 
       "-more efficient utilization of the building such areas as energy conservation and increased staff efficiency <br/>" + 
       "-improved access to and use of building services by all library users, including those with physical disabilities <br/>" + 
       "-provision of library services to geographically isolated or economically disadvantaged communities <br/>";
       return desc;
    }
    
    //SH 5/2/11 NOT BEING USED
    public String getNarrTypeCon92()
    {
       //construct phases
       String desc="If this is a part of a multi-year project application, please "+
       "identify which phase this application refers to in your description.";
       return desc;
    }
    
    public String getNarrTypeCon12()
    {
       //construct timetable
       String desc="The timetable should be as specific as possible, indicating the "+
       "projected beginning date for the project; the duration of the proposed "+
       "construction/renovation, and the projected beginning and ending dates for all "+
       "contractual services; and schedules for all other significant activities impacting the project. "+
       " The timetable should list all related project activities taking place during the grant funding period "+
       " (July 1, 2015 - June 30, 2018), broken down by year.";
       return desc;
    }
    
    public String getNarrTypeCon93()
    {
       //construct budget narr
       String desc="Description of budget requests, vendor costs. Please associate the "+
       "proposed vendor with the construction or renovation work and cost.  Describe all "+
       " items entered on the Project Budget pages.  The Budget Narrative should reflect the Project Budget entries and attached quotes. If the vendor quotes contain options, "+
       " the narrative must indicate those options and the associated dollar value.";
       return desc;
    }
    
  
  public void setDiNarrTypes(int[] diNarrTypes)
  {
    this.diNarrTypes = diNarrTypes;
  }

  public int[] getDiNarrTypes()
  {
    return diNarrTypes;
  }


  public void setCoNarrTypes(int[] coNarrTypes)
  {
    this.coNarrTypes = coNarrTypes;
  }


  public int[] getCoNarrTypes()
  {
    return coNarrTypes;
  }


  public void setLitNarrTypes(int[] litNarrTypes)
  {
    this.litNarrTypes = litNarrTypes;
  }


  public int[] getLitNarrTypes()
  {
    return litNarrTypes;
  }


  public void setSaNarrTypes(int[] saNarrTypes)
  {
    this.saNarrTypes = saNarrTypes;
  }


  public int[] getSaNarrTypes()
  {
    return saNarrTypes;
  }


  public void setLgNarrTypes(int[] lgNarrTypes)
  {
    this.lgNarrTypes = lgNarrTypes;
  }


  public int[] getLgNarrTypes()
  {
    return lgNarrTypes;
  }

    public void setLitInterimFinalTypes(int[] litInterimFinalTypes) {
        this.litInterimFinalTypes = litInterimFinalTypes;
    }

    public int[] getLitInterimFinalTypes() {
        return litInterimFinalTypes;
    }
    
    public int[] determineLitInterimFinalTypes(int fycode, int fccode) {
        
        if(fycode<14){
            return litInterimFinalTypes;
        }
        else if(fccode==42){
          return litFamFinal3YearTypes;//starting fy2013-14 3yr finals FAMILYLIT
        }
        else{
            return litFinal3YearTypes;//fy2013-14 no interims, and 3yr finals ADULTLIT
        }       
    }

    public void setConNarrTypes(int[] conNarrTypes) {
        this.conNarrTypes = conNarrTypes;
    }

    public int[] getConNarrTypes() {
        return conNarrTypes;
    }

    public void setLitFinal3YearTypes(int[] litFinal3YearTypes) {
        this.litFinal3YearTypes = litFinal3YearTypes;
    }

    public int[] getLitFinal3YearTypes() {
        return litFinal3YearTypes;
    }

    public void setStaidNarrTypes(int[] staidNarrTypes) {
        this.staidNarrTypes = staidNarrTypes;
    }

    public int[] getStaidNarrTypes() {
        return staidNarrTypes;
    }
}

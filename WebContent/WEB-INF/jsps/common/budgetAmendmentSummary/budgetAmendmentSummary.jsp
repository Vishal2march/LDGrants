<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link href="css/budgetamendment/budgetAmendment.css" rel="stylesheet"
	media="screen" type="text/css" />
<script src="jscripts/budgetamendment/fs10a.js?v=1"
	type="text/javascript"></script>

</head>
<body>



	<div class="row">
		<div class="col-md-offset-1 col-md-10">


			<div class="panel panel-primary">


				<div class="panel-heading">
					<h4>
						<i class="fa fa-calculator" aria-hidden="true"></i>Budget
						Amendment Summary
					</h4>
				</div>

				<div class="panel  alert-info" role="alert">
					<strong>&nbsp;&nbsp; <span
						class="glyphicon glyphicon-info-sign"></span>
						</strong> 
					<ul>
						<li>Please contact <strong>Carol A. Desch
								(carol.desch@nysed.gov)</strong> before submitting any amendment
							information.
						</li>
						<li>FS-10-A must be submitted by mid-May each year, in order
							to be considered.</li>
						<li><em>Once completed and approved by Carol A. Desch the System
							Director will submit the FS-10-A by going to the <strong>Amendment
								Summary Signoff.</strong></em>
						</li>
					</ul>
				</div>

				<div class="panel-body">
					<!------ SCREEN READER ONLY LABELS -->
					<s:label cssClass="sr-only" for="expId"
						value="Select
														Budget Category"></s:label>
					<s:label cssClass="sr-only" for="descrId"
						value="Please describe the changes to the program"></s:label>
					<s:label cssClass="sr-only" for="adjId"
						value="Select
														Increase or Decrease"></s:label>
					<s:label cssClass="sr-only" for="amountId"
						value="Enter Amount of Adjustment"></s:label>
					<s:label cssClass="sr-only" for="fyId" value=" Enter Fiscal Year"></s:label>
					<!------ SCREEN READER ONLY LABELS END-->





					<!-- ADD SECTION -->
					<div class="row rmvbottommarg">
						<div class="col-md-12">

							<div class="alert alert-warning" role="alert">
								<p>
								<h4>
									<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
									Warning!
								</h4>
								</br>Please save any changes before adding new record. There should
								be a separate record for each category where there is a decrease
								and a separate record for each category where there is an
								increase.



							</div>

							<div class="col-md-4 ">
								<s:url action="addnewfsrecord.action" var="addnewrecord" />
								<s:a type="submit" href="%{addnewrecord}"
									class="btn btn-lg btn-success addbutton form-control"
									role="button" name="add">
									<i class="fa fa-plus-circle" aria-hidden="true"></i> Add New
								</s:a>
							</div>




						</div>
					</div>
					<br>
					<!-- ADD SECTION END-->

					<!--  START OF NEW RECORDS-->
					<div class="row">
						<div class="col-md-12">


							<s:form mehtod="Post" action="savefs10arecords.action">

								<s:iterator var="fs10Record" status="fs10"
									value="fs10RecordList">

									<s:hidden name="fs10RecordList[%{#fs10.index}].id"
										value="%{id}" />
									<s:hidden name="fs10RecordList[%{#fs10.index}].graId"
										value="%{graId}" />

									<s:hidden name="fs10RecordList[%{#fs10.index}].expCode"
										value="%{expCode}" />

									<table class="table table-responsive">

										<thead>

											<tr class="success">
												<th class="col-md-3 text-center">Budget Category</th>
												<th class="col-md-4 text-center">Please describe the
													changes to the program.</th>
												<th class="col-md-3 text-center">Subtotal
													Increase/Decrease</th>
												<th class="col-md-2 text-center">Fiscal Year</th>
											</tr>
										</thead>
										<tfoot>
											<tr class="success">

												<td></td>
												<td></td>
												<td class="text-right"><s:submit type="button"
														action="savefs10arecords" class="btn btn-md btn-primary"
														name="add">
														<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </s:submit></td>
												<td><s:url action="deletefsrecord.action"
														var="deleterecord">
														<s:param name="fsId" value="%{id}" />
													</s:url> <s:a type="button" href="%{deleterecord}"
														class="btn btn-md btn-danger form-control" id="delete-btn">
														<i class="fa fa-minus-square" aria-hidden="true"></i> Delete
														
													</s:a></td>
											</tr>
										</tfoot>
										<tbody>
											<tr>
												<td><s:select
														name="fs10RecordList[%{#fs10.index}].expenditureTypeId"
														headerKey=""
														headerValue="---- Select Budget Category ----"
														list="expenditureTypeList" listKey="id"
														listValue="ExpenditureName" value="%{expenditureTypeId}"
														class="form-control" required="true" id="expId" />
												<td><s:textarea
														name="fs10RecordList[%{#fs10.index}].description"
														placeholder="Enter Description" class="form-control"
														rows="8" value="%{description}" required="required"
														id="descrId" /></td>
												<td><s:if
														test="%{#fs10Record.adjustment == 'INCREASE'}">
														<s:set var="setup" value="amountIncr" />
													</s:if> <s:else>
														<s:set var="setup" value="amountDecr" />
													</s:else> <s:select name="fs10RecordList[%{#fs10.index}].adjustment"
														list="#{'DECREASE':'Decrease', 'INCREASE':'Increase'}"
														value="%{adjustment}" class="form-control"
														required="required" id="adjId" /><br>
													<div class="input-group">
														<div class="input-group-addon">$</div>
														<s:textfield
															pattern="^\\$?(([1-9](\\d*|\\d{0,2}(,\\d{3})*))|0)$"
															cssStyle="max-width: 100%;" placeholder="Enter Amount"
															name="fs10RecordList[%{#fs10.index}].amountIncr"
															class="form-control currencyMask" value="%{setup}"
															required="required" id="amountId" />
														<div class="input-group-addon">.00</div>
													</div></td>
												<td><s:select
														name="fs10RecordList[%{#fs10.index}].fyCode" headerKey=""
														headerValue="--Select Fiscal Year--" list="fiscalYearList"
														listKey="code" listValue="displayValue" value="%{fyCode}"
														class="form-control" required="true" id="fyId" /></td>
											</tr>

										</tbody>

									</table>

									<hr>
								</s:iterator>

							</s:form>
						</div>
					</div>
					<!--  END OF NEW RECORDS-->
					<!--  START OF ACCORDION-->

					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-inverse">
							<div class="panel-heading" role="tab" id="headingOne">
								<h4 class="panel-title">
									<a role="button" class="accordion-toggle"
										data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne" aria-expanded="true"
										aria-controls="collapseOne"> (2016-2017) Budget Year 1 </a>
									<div class="pull-right">
										<span id="BudgetYearOneSubmitted" class="label label-warning "
											style="display: none;"> <i class="fa fa-folder-open-o"
											aria-hidden="true"></i>Submitted
										</span><span id="BudgetYearOneApproved" class="label label-success"
											style="display: none; margin-left: 5px"> <i
											class="fa fa-check-square-o" aria-hidden="true"></i>Approved
										</span>

										<s:if test="%{fs10ALocked == true}">
											<input type="hidden" id="fs10ALocked" value="true" />
											<span id="BudgetYearOneLocked" class="label label-primary"
												style="margin-left: 5px"> <i class="fa fa-lock"
												aria-hidden="true"></i>Locked
											</span>
										</s:if>
										<s:else>
											<span id="BudgetYearOneUnLocked" class="label label-primary"
												style="margin-left: 5px"> <i class="fa fa-unlock"
												aria-hidden="true"></i>Unlocked
											</span>
										</s:else>
									</div>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse in"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body">

									<div class="row">
										<div class="col-md-12" id="budget-one-body">

											<s:form mehtod="Post" action="savefs10arecords.action">

												<s:iterator var="fs10Record" status="fs10"
													value="budgetYearOneList">

													<s:hidden name="fs10RecordList[%{#fs10.index}].id"
														value="%{id}" />
													<s:hidden name="fs10RecordList[%{#fs10.index}].graId"
														value="%{graId}" id="grantIdOne" />

													<s:hidden name="fs10RecordList[%{#fs10.index}].expCode"
														value="%{expCode}" />
													<table class="table table-responsive">

														<thead>

															<tr class="active text-primary">
																<th class="col-md-3 text-center">Budget Category</th>
																<th class="col-md-4 text-center">Please describe
																	the changes to the program.</th>
																<th class="col-md-3 text-center">Subtotal
																	Increase/Decrease</th>
																<th class="col-md-2 text-center">Fiscal Year</th>
															</tr>
														</thead>
														<tfoot>
															<tr class="active text-primary">

																<td></td>
																<td></td>
																<td class="text-right"><s:submit type="button"
																		action="savefs10arecords"
																		class="btn btn-md btn-primary" name="add">
																		<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </s:submit></td>
																<td><s:url action="deletefsrecord.action"
																		var="deleterecord">
																		<s:param name="fsId" value="%{id}" />
																	</s:url> <s:a type="button" href="%{deleterecord}"
																		class="btn btn-md btn-danger form-control"
																		id="delete-btn">
																		<i class="fa fa-minus-square" aria-hidden="true"></i> Delete
														
													</s:a></td>
															</tr>
														</tfoot>
														<tbody>
															<tr>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].expenditureTypeId"
																		headerKey=""
																		headerValue="---- Select Budget Category ----"
																		list="expenditureTypeList" listKey="id"
																		listValue="ExpenditureName"
																		value="%{expenditureTypeId}" class="form-control"
																		required="true" id="expId" />
																<td><s:textarea
																		name="fs10RecordList[%{#fs10.index}].description"
																		placeholder="Enter Description" class="form-control"
																		rows="8" value="%{description}" required="required"
																		id="descrId" /></td>
																<td><s:if
																		test="%{#fs10Record.adjustment == 'INCREASE'}">
																		<s:set var="setup" value="amountIncr" />
																	</s:if> <s:else>
																		<s:set var="setup" value="amountDecr" />
																	</s:else> <s:select
																		name="fs10RecordList[%{#fs10.index}].adjustment"
																		list="#{'DECREASE':'Decrease', 'INCREASE':'Increase'}"
																		value="%{adjustment}" class="form-control"
																		required="required" id="adjId" /><br>
																	<div class="input-group">
																		<div class="input-group-addon">$</div>
																		<s:textfield
																			pattern="^\\$?(([1-9](\\d*|\\d{0,2}(,\\d{3})*))|0)$"
																			cssStyle="max-width: 100%;"
																			placeholder="Enter Amount"
																			name="fs10RecordList[%{#fs10.index}].amountIncr"
																			class="form-control currencyMask" value="%{setup}"
																			required="required" id="amountId" />
																		<div class="input-group-addon">.00</div>
																	</div></td>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].fyCode"
																		headerKey="" headerValue="--Select Fiscal Year--"
																		list="fiscalYearList" listKey="code"
																		listValue="displayValue" value="%{fyCode}"
																		class="form-control" required="true" id="fyId" /></td>
															</tr>

														</tbody>

													</table>

													<hr>
												</s:iterator>


												<s:if test="%{budgetYearOneList.isEmpty()}">
													<div class="alert alert-danger">No Records.</div>
												</s:if>
											</s:form>
										</div>
									</div>


								</div>
							</div>
						</div>

						<div class="panel panel-inverse">
							<div class="panel-heading" role="tab" id="headingTwo">
								<h4 class="panel-title">
									<a class="collapsed accordion-toggle" " role="button"
										data-toggle="collapse" data-parent="#accordion"
										href="#collapseTwo" aria-expanded="false"
										aria-controls="collapseTwo"> (2017-2018) Budget Year 2 </a>
									<div class="pull-right">
										<span id="BudgetYearTwoSubmitted" class="label label-warning "
											style="display: none;"> <i class="fa fa-folder-open-o"
											aria-hidden="true"></i>Submitted
										</span> <span id="BudgetYearTwoApproved" class="label label-success"
											style="display: none; margin-left: 5px"> <i
											class="fa fa-check-square-o" aria-hidden="true"></i>Approved
										</span> <span id="BudgetYearOneLocked" class="label label-primary"
											style="margin-left: 5px"> <i class="fa fa-lock"
											aria-hidden="true"></i>Locked
										</span>
									</div>
								</h4>
							</div>
							<div id="collapseTwo" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingTwo">
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12" id="budget-two-body">

											<s:form mehtod="Post" action="savefs10arecords.action">
												<s:iterator var="fs10Record" status="fs10"
													value="budgetYearTwoList">

													<s:hidden name="fs10RecordList[%{#fs10.index}].id"
														value="%{id}" />
													<s:hidden name="fs10RecordList[%{#fs10.index}].graId"
														value="%{graId}" />

													<s:hidden name="fs10RecordList[%{#fs10.index}].expCode"
														value="%{expCode}" />
													<table class="table table-responsive">

														<thead>

															<tr class="active text-primary">
																<th class="col-md-3 text-center">Budget Category</th>
																<th class="col-md-4 text-center">Please describe
																	the changes to the program.</th>
																<th class="col-md-3 text-center">Subtotal
																	Increase/Decrease</th>
																<th class="col-md-2 text-center">Fiscal Year</th>
															</tr>
														</thead>
														<tfoot>
															<tr class="active text-primary">

																<td></td>
																<td></td>
																<td class="text-right"><s:submit type="button"
																		action="savefs10arecords"
																		class="btn btn-md btn-primary" name="add">
																		<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </s:submit></td>
																<td><s:url action="deletefsrecord.action"
																		var="deleterecord">
																		<s:param name="fsId" value="%{id}" />
																	</s:url> <s:a type="button" href="%{deleterecord}"
																		class="btn btn-md btn-danger form-control"
																		id="delete-btn">
																		<i class="fa fa-minus-square" aria-hidden="true"></i> Delete
														
													</s:a></td>
															</tr>
														</tfoot>
														<tbody>
															<tr>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].expenditureTypeId"
																		headerKey=""
																		headerValue="---- Select Budget Category ----"
																		list="expenditureTypeList" listKey="id"
																		listValue="ExpenditureName"
																		value="%{expenditureTypeId}" class="form-control"
																		required="true" id="expId" />
																<td><s:textarea
																		name="fs10RecordList[%{#fs10.index}].description"
																		placeholder="Enter Description" class="form-control"
																		rows="8" value="%{description}" required="required"
																		id="descrId" /></td>
																<td><s:if
																		test="%{#fs10Record.adjustment == 'INCREASE'}">
																		<s:set var="setup" value="amountIncr" />
																	</s:if> <s:else>
																		<s:set var="setup" value="amountDecr" />
																	</s:else> <s:select
																		name="fs10RecordList[%{#fs10.index}].adjustment"
																		list="#{'DECREASE':'Decrease', 'INCREASE':'Increase'}"
																		value="%{adjustment}" class="form-control"
																		required="required" id="adjId" /><br>
																	<div class="input-group">
																		<div class="input-group-addon">$</div>
																		<s:textfield
																			pattern="^\\$?(([1-9](\\d*|\\d{0,2}(,\\d{3})*))|0)$"
																			cssStyle="max-width: 100%;"
																			placeholder="Enter Amount"
																			name="fs10RecordList[%{#fs10.index}].amountIncr"
																			class="form-control currencyMask" value="%{setup}"
																			required="required" id="amountId" />
																		<div class="input-group-addon">.00</div>
																	</div></td>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].fyCode"
																		headerKey="" headerValue="--Select Fiscal Year--"
																		list="fiscalYearList" listKey="code"
																		listValue="displayValue" value="%{fyCode}"
																		class="form-control" required="true" id="fyId" /></td>
															</tr>

														</tbody>

													</table>

													<hr>
												</s:iterator>


												<s:if test="%{budgetYearTwoList.isEmpty()}">
													<div class="alert alert-danger">No Records.</div>
												</s:if>
											</s:form>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-inverse">
							<div class="panel-heading" role="tab" id="headingThree">
								<h4 class="panel-title">
									<a class="collapsed accordion-toggle" role="button"
										data-toggle="collapse" data-parent="#accordion"
										href="#collapseThree" aria-expanded="false"
										aria-controls="collapseThree"> (2018-2019) Budget Year 3 </a>
									<div class=" pull-right">
										<span id="BudgetYearOneSubmitted" class="label label-warning "
											style="display: none;"> <i class="fa fa-folder-open-o"
											aria-hidden="true"></i>Submitted
										</span> <span id="BudgetYearOneApproved" class="label label-success"
											style="display: none; margin-left: 5px"> <i
											class="fa fa-check-square-o" aria-hidden="true"></i>Approved
										</span> <span id="BudgetYearThreeLocked" class="label label-primary"
											style="margin-left: 5px"> <i class="fa fa-lock"
											aria-hidden="true"></i>Locked
										</span>
									</div>
								</h4>
							</div>
							<div id="collapseThree" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingThree">
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12" id="budget-three-body">
											<s:form mehtod="Post" action="savefs10arecords.action">

												<s:iterator var="fs10Record" status="fs10"
													value="budgetYearThreeList">

													<s:hidden name="fs10RecordList[%{#fs10.index}].id"
														value="%{id}" />
													<s:hidden name="fs10RecordList[%{#fs10.index}].graId"
														value="%{graId}" />

													<s:hidden name="fs10RecordList[%{#fs10.index}].expCode"
														value="%{expCode}" />
													<table class="table table-responsive">

														<thead>

															<tr class="active text-primary">
																<th class="col-md-3 text-center">Budget Category</th>
																<th class="col-md-4 text-center">Please describe
																	the changes to the program.</th>
																<th class="col-md-3 text-center">Subtotal
																	Increase/Decrease</th>
																<th class="col-md-2 text-center">Fiscal Year</th>
															</tr>
														</thead>
														<tfoot>
															<tr class="active text-primary">

																<td></td>
																<td></td>
																<td class="text-right"><s:submit type="button"
																		action="savefs10arecords"
																		class="btn btn-md btn-primary" name="add">
																		<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </s:submit></td>
																<td><s:url action="deletefsrecord.action"
																		var="deleterecord">
																		<s:param name="fsId" value="%{id}" />
																	</s:url> <s:a type="button" href="%{deleterecord}"
																		class="btn btn-md btn-danger form-control"
																		id="delete-btn">
																		<i class="fa fa-minus-square" aria-hidden="true"></i> Delete
														
													</s:a></td>
															</tr>
														</tfoot>
														<tbody>
															<tr>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].expenditureTypeId"
																		headerKey=""
																		headerValue="---- Select Budget Category ----"
																		list="expenditureTypeList" listKey="id"
																		listValue="ExpenditureName"
																		value="%{expenditureTypeId}" class="form-control"
																		required="true" id="expId" />
																<td><s:textarea
																		name="fs10RecordList[%{#fs10.index}].description"
																		placeholder="Enter Description" class="form-control"
																		rows="8" value="%{description}" required="required"
																		id="descrId" /></td>
																<td><s:if
																		test="%{#fs10Record.adjustment == 'INCREASE'}">
																		<s:set var="setup" value="amountIncr" />
																	</s:if> <s:else>
																		<s:set var="setup" value="amountDecr" />
																	</s:else> <s:select
																		name="fs10RecordList[%{#fs10.index}].adjustment"
																		list="#{'DECREASE':'Decrease', 'INCREASE':'Increase'}"
																		value="%{adjustment}" class="form-control"
																		required="required" id="adjId" /><br>
																	<div class="input-group">
																		<div class="input-group-addon">$</div>
																		<s:textfield
																			pattern="^\\$?(([1-9](\\d*|\\d{0,2}(,\\d{3})*))|0)$"
																			cssStyle="max-width: 100%;"
																			placeholder="Enter Amount"
																			name="fs10RecordList[%{#fs10.index}].amountIncr"
																			class="form-control currencyMask" value="%{setup}"
																			required="required" id="amountId" />
																		<div class="input-group-addon">.00</div>
																	</div></td>
																<td><s:select
																		name="fs10RecordList[%{#fs10.index}].fyCode"
																		headerKey="" headerValue="--Select Fiscal Year--"
																		list="fiscalYearList" listKey="code"
																		listValue="displayValue" value="%{fyCode}"
																		class="form-control" required="true" id="fyId" /></td>
															</tr>

														</tbody>

													</table>
													<hr>

												</s:iterator>
												<s:if test="%{budgetYearThreeList.isEmpty()}">
													<div class="alert alert-danger">No Records.</div>
												</s:if>
											</s:form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--  END OF ACCORDION-->


		</div>
		<!-- END BUDGET AMENDMENT PANEL BODY  -->

	</div>
	</div>
	</div>


	<div class="row">
		<div class="col-md-offset-1 col-md-10">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<i class="fa fa-file-pdf-o" aria-hidden="true"></i>
				</div>


				<div class="panel  alert-info" role="alert">
					<strong>&nbsp;&nbsp; <span
						class="glyphicon glyphicon-info-sign"></span>
						</strong>
					<ul>
						<li>Please contact <strong>Carol A. Desch
								(carol.desch@nysed.gov)</strong> before submitting any amendment
							information.
						</li>
						<li>FS-10-A must be submitted by mid-May each year, in order
							to be considered.</li>
						<li>Once completed and approved by Carol A. Desch the System
							Director will submit the FS-10-A by going to the <strong>Amendment
								Summary Signoff.</strong>
						</li>
					</ul>
				</div>
				<br>
				<div class="panel-body text-center">

					<div class="row">
						<div class="col-md-offset-3 col-md-6">
							<div class="jumbotron">
								<address>
									<strong>Literacy Library Services Grant Program</strong><br>
									New York State Library<br> Division of Library Development<br>
									Cultural Education Center<br> Albany, NY 12230 <br> <abbr
										title="Attention">Attn:</abbr> Carol A. Desch, Program Director;
									<br>Barbara Massago, Budget Review
									<br /> <br />
								</address>
							</div>
							<form method="POST" action="FsFormServlet" target="_blank">
								<label class="sr-only" for="fya">Select Budget Year</label> <select
									name="fya" id="fya" class="form-control">
									<option value='<c:out value="${grant.fyCode}" />'>Budget
										Year 1</option>
									<option value='<c:out value="${grant.fyCode +1}" />'>Budget
										Year 2</option>
									<option value='<c:out value="${grant.fyCode +2}" />'>Budget
										Year 3</option>
								</select>
								<div class="fprm-group">
									<div class="radio">
										<label> <input type="radio" name="i" value="fs10a">HTML<br />
										</label>
									</div>
									<div class="radio">
										<label> <input type="radio" name="i" value="fs10apdf"
											checked="checked">PDF (preferred)
										</label>
									</div>
								</div>
								<button type="SUBMIT" role="button"
									class="btn btn-lg btn-primary form-control addbutton">
									<i class="fa fa-file-pdf-o" aria-hidden="true"></i> View FS-10-A
								</button
							</form>
							</p>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

</body>



</html>
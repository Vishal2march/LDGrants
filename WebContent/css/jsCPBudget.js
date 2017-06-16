/*
 * @author  STEFANIE HUSAK
 *
 * Development Environment        :  JDeveloper 10.1.2
 * Name of the Application        :  jsConservPreserv.js
 * Creation/Modification History  :
 *     shusak      5/5/08      Created
 *
 * Description 
 * Javascript file containing the functions for c/p budget pages. Code formerly
 * in each jsp, now jsp includes this javascript file. 
*/



//ProjectBudget Tab 1 PersonalServices
//calculates the salary/wage times the fte/hours
function calcSalary(rowid)
{ 
  var salary = document.getElementsByName("personalItem["+rowid+"].salaryrate")["personalItem["+rowid+"].salaryrate"]["value"];
  
  var fte = document.getElementsByName("personalItem["+rowid+"].fteStr")["personalItem["+rowid+"].fteStr"]["value"];
  
 //need to filter out comma and dollar sign, otherwise NaN error
  salary=salary.replace(/,/g,"");
  salary=salary.replace(/\$/g,"");
  
  var anstemp = salary * fte;  
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  document.getElementsByName("personalItem["+rowid+"].cost")["personalItem["+rowid+"].cost"]["value"] = ans;
} 


//ProjectBudget Tab 1 PersonalServices
//calculates the salary/wage times the fte/hours FOR LGRMIF and puts calc into amtrequested
function calcLgSalary(rowid)
{ 
  var salary = document.getElementsByName("personalItem["+rowid+"].salaryrate")["personalItem["+rowid+"].salaryrate"]["value"];
  
  var fte = document.getElementsByName("personalItem["+rowid+"].fteStr")["personalItem["+rowid+"].fteStr"]["value"];
  
 //need to filter out comma and dollar sign, otherwise NaN error
  salary=salary.replace(/,/g,"");
  salary=salary.replace(/\$/g,"");
  
  var anstemp = salary * fte;  
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  document.getElementsByName("personalItem["+rowid+"].cost")["personalItem["+rowid+"].cost"]["value"] = ans;
  document.getElementsByName("personalItem["+rowid+"].grantrequestStr")["personalItem["+rowid+"].grantrequestStr"]["value"] = ans;
} 


//Project Budget Tab 2 Employee Benefits
//calculates the benefits percentage times salary
function calcBenefits(rowid)
{ 
  var percent = document.getElementsByName("benefitItem["+rowid+"].benpercentStr")["benefitItem["+rowid+"].benpercentStr"]["value"];
  
  var salary = document.getElementsByName("benefitItem["+rowid+"].salary")["benefitItem["+rowid+"].salary"]["value"];
    
  //need to filter out comma and dollar sign, otherwise NaN error
  salary=salary.replace(/,/g,"");
  salary=salary.replace(/\$/g,"");  
  
  var anstemp = salary * percent;  
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  document.getElementsByName("benefitItem["+rowid+"].cost")["benefitItem["+rowid+"].cost"]["value"] = ans;
}


//Project Budget Tab 4 Supply Material Equipment
//calculates the unit price times quantity
function calcCost(rowid)
{
  var quantity = document.getElementsByName("supplyItem["+rowid+"].quantity")["supplyItem["+rowid+"].quantity"]["value"];
     
  var price = document.getElementsByName("supplyItem["+rowid+"].unitpriceStr")["supplyItem["+rowid+"].unitpriceStr"]["value"];
    
  //need to filter out comma and dollar sign, otherwise NaN error
  price=price.replace(/,/g,"");
  price=price.replace(/\$/g,"");
  
  var anstemp = quantity * price; 
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  document.getElementsByName("supplyItem["+rowid+"].cost")["supplyItem["+rowid+"].cost"]["value"] = ans;
}    

//Project Budget Tab 4 Supply Material Equipment
//calculates the unit price times quantity for LGRMIF
function calcLgCost(rowid)
{
  var quantity = document.getElementsByName("supplyItem["+rowid+"].quantity")["supplyItem["+rowid+"].quantity"]["value"];
     
  var price = document.getElementsByName("supplyItem["+rowid+"].unitpriceStr")["supplyItem["+rowid+"].unitpriceStr"]["value"];
    
  //need to filter out comma and dollar sign, otherwise NaN error
  price=price.replace(/,/g,"");
  price=price.replace(/\$/g,"");
  
  var anstemp = quantity * price; 
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  document.getElementsByName("supplyItem["+rowid+"].cost")["supplyItem["+rowid+"].cost"]["value"] = ans;
  document.getElementsByName("supplyItem["+rowid+"].grantrequestStr")["supplyItem["+rowid+"].grantrequestStr"]["value"] = ans;
}    


//------------------------------------------------------------------------------
//    FORMATTING FUNCTIONS
function CurrencyFormatted(amount)
{
	var i = parseFloat(amount);
	if(isNaN(i)) { i = 0.00; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	i = parseInt((i + .005) * 100);
	i = i / 100;
	s = new String(i);
	if(s.indexOf('.') < 0) { s += '.00'; }
	if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
	s = minus + s;
	return s;
}// end of function CurrencyFormatted()


function CommaFormatted(amount)
{
	var delimiter = ","; // replace comma if desired
	var a = amount.split('.',2)
	var d = a[1];
	var i = parseInt(a[0]);
	if(isNaN(i)) { return ''; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3)
	{
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if(n.length > 0) { a.unshift(n); }
	n = a.join(delimiter);
	if(d.length < 1) { amount = n; }
	else { amount = n + '.' + d; }
	amount = minus + amount;
	return amount;
}// end of function CommaFormatted() 
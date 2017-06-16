/*
 * @author  STEFANIE HUSAK
 *
 * Development Environment        :  JDeveloper 10.1.2
 * Name of the Application        :  jsConservPreserv.js
 * Creation/Modification History  :
 *     shusak      5/5/08      Created
 *
 * Description 
 * Javascript file containing the functions for FS worksheet totals.
*/


function calcFsTotal(theForm)
{ 
  var profess = theForm.professional.value;
  var supstaff = theForm.supportstaff.value;
  var purchase = theForm.purchased.value;
  var supplies = theForm.supplies.value;
  var travel = theForm.travel.value;
  var benefits = theForm.benefits.value;
  var remodeling = theForm.remodeling.value;
  var equipment = theForm.equipment.value;  
  var reqtotal = theForm.reqtotal.value;
  
 //need to filter out comma and dollar sign, otherwise NaN error
  profess=profess.replace(/,/g,"");
  profess=profess.replace(/\$/g,"");
  supstaff=supstaff.replace(/,/g,"");
  supstaff=supstaff.replace(/\$/g,"");
  purchase=purchase.replace(/,/g,"");
  purchase=purchase.replace(/\$/g,"");
  supplies=supplies.replace(/,/g,"");
  supplies=supplies.replace(/\$/g,"");
  travel=travel.replace(/,/g,"");
  travel=travel.replace(/\$/g,"");
  benefits=benefits.replace(/,/g,"");
  benefits=benefits.replace(/\$/g,"");
  remodeling=remodeling.replace(/,/g,"");
  remodeling=remodeling.replace(/\$/g,"");
  equipment=equipment.replace(/,/g,"");
  equipment=equipment.replace(/\$/g,"");
  reqtotal=reqtotal.replace(/,/g,"");
  reqtotal=reqtotal.replace(/\$/g,"");
  
  //convert to numbers
  profess = Number(profess);
  supstaff = Number(supstaff);
  purchase = Number(purchase);
  supplies = Number(supplies);
  travel = Number(travel);
  benefits = Number(benefits);
  remodeling = Number(remodeling);
  equipment = Number(equipment);  
  reqtotal = Number(reqtotal);
  
  var anstemp =profess + supstaff + purchase + supplies + travel + benefits + remodeling + equipment;  
  
  //change color/visibility of links depending on value comparison
  if(anstemp < reqtotal)
  {
    document.getElementById('total').style.color = 'red';
    setVisibility('fslinks', false);
  }
  else if( anstemp > reqtotal)
  {
    document.getElementById('total').style.color = 'blue';
    setVisibility('fslinks', false);
  }
  else if(anstemp == reqtotal)
  {
    document.getElementById('total').style.color = 'black';
    setVisibility('fslinks', true);
  }
  
  //format the number
  var ans = CurrencyFormatted(anstemp);
  ans = CommaFormatted(ans);
  //display to screen
  document.getElementById('total').innerHTML = ans;
}


//Setting the visibility of an object
//code from JAvitabile Cis515
function setVisibility(obj,vis){
    var theObj = document.getElementById(obj);
    if (vis == true || vis=='visible' || vis=='y'){
           theObj.style.visibility = "visible";
    }else{
           theObj.style.visibility = "hidden";
    }
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
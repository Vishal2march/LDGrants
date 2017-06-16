// email kireol at yahoo.com
// autoMask - an adaption of anyMask

function clearField(field) {
	field.value = "";
	field.focus;
}

function beginEntry(field) {
	if(field.value == "mm/dd/yyyy") {clearField(field);}
}

function autoMask(field, event, strMask) {
    var KeyTyped = String.fromCharCode(getKeyCode(event));
    var targ = getTarget(event);
    keyCount = targ.value.length;

	  if(keyCount == strMask.length) {
      return false;
	  }	  
	  else {
      if (strMask.charAt(keyCount+1) != '#') {
        field.value = field.value + KeyTyped + strMask.charAt(keyCount+1);
        if((keyCount == 9) && (!validateDate(field))) {
          alert(field.value + " is not a valid date!");
          clearField(field);
        }
        return false;
      }

      if (strMask.charAt(keyCount) == KeyTyped) {return true;}
      else if ((strMask.charAt(keyCount) == '#') && isNumeric(KeyTyped)) {
        return true;
      }
      
      if (KeyTyped.charCodeAt(0) < 32) {return true;}
        return false;
      }
}

function getTarget(e) {
  // IE5
	if (e.srcElement) {return e.srcElement;}
  if (e.target) {return e.target;}
}

function getKeyCode(e) {
  //IE5
  if (e.srcElement) {return e.keyCode;}
  // NC5
  if (e.target) {return e.which;}
}

function isNumeric(c) {
  var sNumbers = "01234567890";
  if (sNumbers.indexOf(c) == -1) {return false;}
	else {return true;}
}

function validateDate(field) {
  var month = field.value.substring(0,2);
  var day = field.value.substring(3,5);
  var year = field.value.substring(6,10);
  var fdate = field.value;

  if((month <= 0) || (month > 12)) {return false;}
  else if( 
		((month==01)||(month==03)||(month==05)||(month==07)||(month==08)||(month==10)||(month==12) ) 
		&& 
		(day > 0) 
		&& 
		(day <= 31)
	    ) {return true;}
  else if(
		((month==04)||(month==06)||(month==09)||(month==11))
		&& 
		(day > 0)
		&&
		(day <= 30)
	    ) {return true;}
  else if(
		(month==02)
		&&
		((year % 4) == 0)
		&&
		(day > 0)
		&&
		(day <= 29)
	    ) {return true;}
  else if(
		(month==02)
		&&
		((year % 4) != 0)
		&&
		(day > 0)
		&&
		(day <= 28)
	    ) {return true;}
  else {return false;}
}

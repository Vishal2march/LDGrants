var startCount = 0;
var endCount = 0;
var strLimit = 10;
today = new Date();
sDate = new Date();
var month = today.getMonth()+1;
var day = today.getDate();
var year = today.getYear();
var strSTART_DATE = "mm/dd/yyyy";
var strEND_DATE = "mm/dd/yyyy";
var keyString = "";

function setDATES(myForm) {
  setSTART_DATE(myForm);
  setEND_DATE(myForm);
}
function setSTART_DATE(myForm) {
  myForm.START_DATE.value = strSTART_DATE;
}
function setEND_DATE(myForm) {
  myForm.END_DATE.value = strEND_DATE;
}

/*
 * backspace()
 * This function takes no input, it simply deletes the last number of
 * the date string and replaces it with a _.
 */
function startBackspace(myForm) {
  if((startCount==3)||(startCount==6)) {
    strSTART_DATE = strSTART_DATE.substring(0,(startCount-2)) + "_" + strSTART_DATE.substring(startCount-1,strLimit);
    startCount-=2;
  }
  else if(startCount!=0) {
    strSTART_DATE = strSTART_DATE.substring(0,(startCount-1)) + "_" + strSTART_DATE.substring(startCount,strLimit);
    startCount--;
  }
  myForm.START_DATE.value = strSTART_DATE;
  window.event.keyCode = 0;
}
function endBackspace(myForm) {
  if((endCount==3)||(endCount==6)) {
    strEND_DATE = strEND_DATE.substring(0,(endCount-2)) + "_" + strEND_DATE.substring(endCount-1,strLimit);
    endCount-=2;
  }
  else if(endCount!=0) {
    strEND_DATE = strEND_DATE.substring(0,(endCount-1)) + "_" + strEND_DATE.substring(endCount,strLimit);
    endCount--;
  }
  myForm.END_DATE.value = strEND_DATE;
  window.event.keyCode = 0;
}

/*
 * resetCount()
 * Takes a form as input, resets the textbox to the value of strSTART_DATE
 * and sets count=0.
 */
function resetStartCount(myForm) {
  myForm.START_DATE.value = strSTART_DATE;
  myForm.START_DATE.select();
  startCount = 0;
}
function resetEndCount(myForm) {
  myForm.END_DATE.value = strEND_DATE;
  myForm.END_DATE.select();
  endCount = 0;      
}

/* 
 * resetDate()
 * Takes a form as input, resets strSTART_DATE to "__/__/__", and calls resetCount().
 */
function resetStartDate(myForm) {
  strSTART_DATE = "__/__/____";
  resetStartCount(myForm);
}
function resetEndDate(myForm) {
  strEND_DATE = "__/__/____";
  resetEndCount(myForm);
}

/*
 * addChar()
 * Takes a form as input and adds the last keyed in char to strSTART_DATE.
 */
function addStartChar(myForm) {
  startCount++;
  /* splits strSTART_DATE at the point of the last character entered
   * into the string, inserts the last keyed in character and pieces
   * the string back together.
   */
  if(startCount <= strLimit) {
    strSTART_DATE = strSTART_DATE.substring(0,startCount-1) + keyString + strSTART_DATE.substring(startCount,strLimit);
    myForm.START_DATE.value = strSTART_DATE;
  }
  /* inserts inserts "/" into strSTART_DATE between days and months
   * and between days and years.
   */
  if(((startCount+1)==3)||((startCount+1)==6)) {
    keyString = "/";
    addStartChar(myForm);
  }
}
function addEndChar(myForm) {
  endCount++;
  /* splits strEND_DATE at the point of the last character entered
   * into the string, inserts the last keyed in character and pieces
   * the string back together.
   */
  if(endCount <= strLimit) {
    strEND_DATE = strEND_DATE.substring(0,endCount-1) + keyString + strEND_DATE.substring(endCount,strLimit);
    myForm.END_DATE.value = strEND_DATE;
  }
  /* inserts inserts "/" into strEND_DATE between days and months
   * and between days and years.
   */
  if(((endCount+1)==3)||((endCount+1)==6)) {
    keyString = "/";
    addEndChar(myForm);
  }
}

/*
 * getKey()
 * Takes a form and the code of the last keystroke as input.  Decodes the keyCode into
 * the corresponding character, and sends that character to addChar() iff it is a number.
 * Calls backspace() if backspace is the last keyCode.
 */
function getStartKey(myForm, keyStroke) {
  var keyCode = (document.layers) ? keyStroke.which : event.keyCode;
  if(startCount < 10) {
    if((keyCode >= 96) && (keyCode <= 105)) {keyCode -= 48;}
    if((keyCode >= 48) && (keyCode <= 57)) {
      keyString = String.fromCharCode(keyCode).toLowerCase();
      addStartChar(myForm);
    }
  }
  if(keyCode == 8) {
    startBackspace(myForm);
    myForm.START_DATE.focus();
  }
  if(keyCode==46) {
    resetStartDate(myForm);
  }
}
function getEndKey(myForm, keyStroke) {
  var keyCode = (document.layers) ? keyStroke.which : event.keyCode;
  if(endCount < 10) {
    if((keyCode >= 96) && (keyCode <= 105)) {keyCode -= 48;}
    if((keyCode >= 48) && (keyCode <= 57)) {
      keyString = String.fromCharCode(keyCode).toLowerCase();
      addEndChar(myForm);
    }
  }
  if(keyCode == 8) {
    endBackspace(myForm);
    myForm.END_DATE.focus();
  }
  if(keyCode==46) {
    resetEndDate(myForm);
  }
}
function AllowNoDups()
{
   var cookie_ls = document.cookie;
   if (cookie_ls.indexOf(document.location) > -1) 
   {
      alert("You've already submitted your selection. Can not save more than once!  ");
      return false;
   }
   else
   	{
      var myDate=new Date()
      myDate.setDate(myDate.getSeconds()+1)
      document.cookie = window.location.href + " from " + document.referrer + "; path=/; expires=" + myDate  ;
      return true;
   };
   };
  //-->
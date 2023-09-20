//$(function () {
//    // ACTIVATION DU DATEPICKER 
//    $('.datepicker').datepicker({
//        clearBtn: true,
//        format: "dd/mm/yyyy"
//    });
//});
//
//$('.datepicker').on('changeDate', function(ev){
//    $(this).datepicker('hide');
//});



function includeHTML() {
  var z, i, elmnt, file, xhttp;
  /* Loop through a collection of all HTML elements: */
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    /*search for elements with a certain atrribute:*/
    file = elmnt.getAttribute("w3-include-html");
    if (file) {
      /* Make an HTTP request using the attribute value as the file name: */
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
          /* Remove the attribute, and call this function once more: */
          elmnt.removeAttribute("w3-include-html");
          includeHTML();
        }
      }
      xhttp.open("GET", file, true);
      xhttp.send();
      /* Exit the function: */
      return;
    }
  }
}



// Attach a submit handler to the form
//$( "#searchForm" ).change(function( event ) {
function submitFilters() {
 

  // Get some values from elements on the page:
  var animal = $("#animal").val();
  var frameSize = $("#frameSize").val();
  var frameWood = $("#frameWood").val();
  var available = $("#available")[0].checked;
  
  // Send the data using post
  var posting = $.post( "/changeTableFilters", 
  	{ 
		animal: animal,
	  	frameSize: frameSize,
	  	frameWood: frameWood,
	  	available: available 
	});
 
  // Put the results in a div
  posting.done(function( data ) {
    $( "#mainTable" ).html( data );
  });
};
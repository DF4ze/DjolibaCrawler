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
	  	available: available//,
//	  	
//	  	moFrame: moFrame,
//	  	moSkin: moSkin,
//	  	moBuild: moBuild,
//	  	marge: marge,
//	  	nbMaxFrame: nbMaxFrame,
//	  	skinBorder: skinBorder 
	});
 
  // Put the results in a div
  posting.done(function( data ) {
    $( "#mainTable" ).html( data );
  });
};



function submitRules(){
  var moFrame = $("#moFrame").val();
  var moSkin = $("#moSkin").val();
  var moBuild = $("#moBuild").val();
  var marge = $("#marge").val();
  var nbMaxFrame = $("#nbMaxFrame").val();
  var skinBorder = $("#skinBorder").val();
  
  // Send the data using post
  var posting = $.post( "/updateRules", 
  	{ 
		moFrame: moFrame,
	  	moSkin: moSkin,
	  	moBuild: moBuild,
	  	marge: marge,
	  	nbMaxFrame: nbMaxFrame,
	  	skinBorder: skinBorder 
	});
	
  // resubmit filters to rebuild table
  posting.done(function( data ) {
    submitFilters()
  });
}

$(document).ready(function(){
	$(".autoSubmit").keypress(function(event) {
	    if (event.keyCode === 13) {
	        event.preventDefault(); // Prevent the default behavior of the Enter key
	        submitRules();
	    }else 
	    if (event.which === 13) {
	        event.preventDefault(); // Prevent the default behavior of the Enter key
	        submitRules();
	    }
	});
});















$(document).ready(function(){
    // Sample initial data
    var books = [
        { id: 1, title: "Book 1", author: "Author A" },
        { id: 2, title: "Book 2", author: "Author B" }
    ];

    // Function to render the book list
    function renderList() {
        var bookList = $('#bookList');
        bookList.empty();

        books.forEach(function(book) {
            bookList.append(`
                <li data-id="${book.id}">
                    ${book.title} by ${book.author} 
                    <button class="editButton">Edit</button>
                </li>
            `);
        });
    }

    // Initial render
    renderList();

    // Create a new book
    $('#createForm').submit(function(event){
        event.preventDefault();
        var newTitle = $('#newTitle').val();
        var newAuthor = $('#newAuthor').val();

        var newBook = {
            id: books.length + 1,
            title: newTitle,
            author: newAuthor
        };

        books.push(newBook);
        renderList();
        $('#createForm')[0].reset(); // Reset the form
    });

    // Edit a book
    $('#bookList').on('click', '.editButton', function() {
        var bookId = $(this).closest('li').data('id');
        var book = books.find(b => b.id === bookId);
        
        $('#bookId').val(book.id);
        $('#updateTitle').val(book.title);
        $('#updateAuthor').val(book.author);

        $('#createForm').hide();
        $('#updateForm').show();
        $('#deleteButton').show();
    });

    // Cancel Update
    $('#cancelUpdate').click(function() {
        $('#createForm').show();
        $('#updateForm').hide();
        $('#deleteButton').hide();
    });

    // Update a book
    $('#updateForm').submit(function(event){
        event.preventDefault();
        var bookId = $('#bookId').val();
        var updatedTitle = $('#updateTitle').val();
        var updatedAuthor = $('#updateAuthor').val();

        var book = books.find(b => b.id == bookId);
        if (book) {
            book.title = updatedTitle;
            book.author = updatedAuthor;
            renderList();
            $('#createForm').show();
            $('#updateForm').hide();
            $('#deleteButton').hide();
        }
    });

    // Delete a book
    $('#deleteButton').click(function() {
        var bookId = $('#bookId').val();
        books = books.filter(b => b.id != bookId);
        renderList();
        $('#createForm').show();
        $('#updateForm').hide();
        $('#deleteButton').hide();
    });
});


const maxRecordsPerPage = 6;

let numOfResults = -1,
    numOfPages = -1,
    booksRemainder = -1,
    currPage = 0,
    i = -1, 
    resultsStr = "";




// mock search results for books
let bookResults = [
    {bookId:"0-7571-8456-1", title:"A Brief History of Time", authorName:"Stephen Hawking"},
    {bookId:"0-6689-4076-X", title:"The Graveyard Book", authorName:"Neil Gaiman"},
    {bookId:"0-8409-4805-0", title:"How to Survive", authorName:"John Hudson"},
    {bookId:"0-6041-3580-7", title:"How to Win Friends & Influence People", authorName:"Dale Carnegie"},
    {bookId:"0-8005-4109-X", title:"The Machinery of Life", authorName:"David S. Goodsell"},
    {bookId:"0-7877-1746-0", title:"How the South Won the Civil War", authorName:"Heather Cox Richardson"},
    {bookId:"0-1032-2966-3", title:"Harry Potter and the Sorcerer's Stone", authorName:"J.K. Rowling"},
    {bookId:"0-8188-0077-1", title:"The Way of Kings", authorName:"Brandon Sanderson"},
    {bookId:"0-5692-4392-0", title:"The Snowball", authorName:"Alice Schroeder"},
    {bookId:"0-8458-1910-0", title:"Calculus: Early Transcendentals", authorName:"James Stewart"},
    {bookId:"0-7867-0641-4", title:"1984", authorName:"George Orwell"},
    {bookId:"0-6893-6155-6", title:"The Blue Book of Grammar and Punctuation", authorName:"Jane Straus"},
    {bookId:"0-1613-8608-3", title:"Outliers", authorName:"Malcolm Gladwell"},
    {bookId:"0-2742-3731-8", title:"HTML and CSS: Design and Build Websites", authorName:"Jon Duckett"},
    {bookId:"0-9334-9838-1", title:"The Eye of the World", authorName:"Robert Jordan"},
    {bookId:"0-4801-1567-2", title:"Mistborn: The Final Empire", authorName:"Brandon Sanderson"},
    {bookId:"0-8419-6716-4", title:"Biology: A Guide to the Natural World", authorName:"David Krogh"},
    {bookId:"0-9818-5508-3", title:"Common Stocks and Uncommon Profits", authorName:"Philip A. Fisher"},
    {bookId:"0-5679-2896-9", title:"Sleeping Beauties", authorName:"Owen King"},
    {bookId:"0-1100-5460-1", title:"Fahrenheit 451", authorName:"Ray Bradbury"}
];

numOfResults = bookResults.length;
numOfPages = Math.floor(numOfResults / maxRecordsPerPage) + 1;
booksRemainder = numOfResults % maxRecordsPerPage;


function loadResults() {
    let paginationCode = "",
        previousLink = "";

   // Document.getElementById("resultsArea").innerHTML = resultsStr;

    document.getElementById("searchLabel").innerHTML = "<h4>" + numOfResults + " Search Results Found: </h4>";
    pgNumLink(1);
    
}

function pgNumLink(pg) {
    let paginationCode = "",
        resultsCode = "",
        p = -1,
        prevPage = -1,
        nextPage = -1,
        startResult = -1,
        endResult = -1;

    currPage = pg;  // set curret page to clicked on page link number
    startResult = (pg * maxRecordsPerPage) - maxRecordsPerPage;
    endResult = (pg * maxRecordsPerPage) - 1;


    paginationCode = '<ul class="pagination pagination-lg pt-3 justify-content-center">';
    if (currPage === 1) {
        paginationCode += '<li class="page-item disabled"><a class="page-link" href="#">\<</a></li>';
    } else{
        prevPage = currPage - 1;
        paginationCode += '<li class="page-item"><a class="page-link" onclick="pgNumLink(' + prevPage + ')" href="#">\<</a></li>';
    }

    for (p = 1; p <= numOfPages; p++) {
        if (currPage === p) {
            paginationCode += '<li class="page-item active"><a class="page-link" '
                + 'onclick="pgNumLink(' + p + ')">' 
                + p + '</a></li>';
        } else  {
            paginationCode += '<li class="page-item"><a class="page-link" '
                + 'onclick="pgNumLink(' + p + ')" href="#">' 
                + p + '</a></li>';
        }
        
    }
    
    if (currPage === numOfPages) {
        paginationCode += '<li class="page-item disabled"><a class="page-link" href="#">\></a></li></ul>';
    } else {
        nextPage = currPage + 1;
        paginationCode += '<li class="page-item"><a class="page-link" onclick="pgNumLink(' + nextPage + ')" href="#">\></a></li></ul>';
    }
        



    document.getElementById("paginationArea").innerHTML = paginationCode;


    if (currPage === numOfPages)    {
        endResult = numOfResults - 1;
    }
    for (r = startResult; r <= endResult; r++) {
        resultsCode += '<p>' + (r + 1) + ') "' + bookResults[r].title + '" by ' 
            + bookResults[r].authorName 
            + '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ISBN #: ' + bookResults[r].bookId + '</p>';
    }
    document.getElementById("resultsArea").innerHTML = resultsCode;
} // end function
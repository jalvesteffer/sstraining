const maxRecordsPerPage = 6; // up to 6 records can be shown on a page

let numOfResults = -1, // number of book results return
    numOfPages = -1, // number of pages needed to display results
    booksRemainder = -1, // number of books on last page of results
    currPage = 0; // current page of results shown to user

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

// get number of book results to show
numOfResults = bookResults.length;

// calculate total number of page results needed
numOfPages = Math.floor(numOfResults / maxRecordsPerPage) + 1;

// number of books shown on last page
booksRemainder = numOfResults % maxRecordsPerPage;

// when page is loaded, this runs
function loadResults() {
    let searchLabelElement = document.getElementById("searchLabel");
    
    // clear search label nodes
    removeChildNodes(searchLabelElement);

    // create a new search label nodes that include number of results found
    let h4Element = document.createElement('h4');
    let node = document.createTextNode(numOfResults + " Search Results Found: ");
    h4Element.appendChild(node);
    searchLabelElement.appendChild(h4Element);
    
    // user starts at page 1 of results
    pgNumLink(1);
}

// This function shows the results for a given page number
function pgNumLink(pg) {
    let p = -1, // for loop counter
        r = -1, // for loop counter
        prevPage = -1, // keep track of prev page number
        nextPage = -1, // keep track of next page number
        startResult = -1, // result number to start page with
        endResult = -1, // result number to end page with
        liElement = "", // holds newly created li DOM element
        aElement = ""; // holds newly created a DOM element

    currPage = pg;  // set curret page to clicked on page link number

    // from the page number, calculate which results to begin and end the page with
    startResult = (pg * maxRecordsPerPage) - maxRecordsPerPage;
    endResult = (pg * maxRecordsPerPage) - 1;

    // get location of pagination area
    let paginationAreaElement = document.getElementById("paginationArea");

    // call function to clear the current pagination nodes
    removeChildNodes(paginationAreaElement);

    // create new pagination nodes and set their attributes
    let ulElement = document.createElement("ul");
    let ulElementAtt = document.createAttribute("class");
    ulElementAtt.value = "pagination pagination-lg pt-3 justify-content-center";
    ulElement.setAttributeNode(ulElementAtt);

    // create previous navigation button.  Its disabled on the 1st page of results
    if (currPage === 1) {
        liElement = createLiNode("page-item disabled");
        aElement = createANode("", "#searchLabel", "\<");
        liElement.appendChild(aElement);
    } else{
        prevPage = currPage - 1;
        liElement = createLiNode("page-item");
        aElement = createANode("pgNumLink(" + prevPage + ")", "#searchLabel", "\<");
        liElement.appendChild(aElement);
    }

    // append the previous naviation button nodes to DOM
    ulElement.appendChild(liElement);

    // for each page of results, create pagination nodes.  The current page
    // button will be set to active
    for (p = 1; p <= numOfPages; p++) {
        if (currPage === p) {
            liElement = createLiNode("page-item active");
            aElement = createANode("pgNumLink(" + p + ")", "", p);
        } else  {
            liElement = createLiNode("page-item");
            aElement = createANode("pgNumLink(" + p + ")", "#searchLabel", p);
        }
        liElement.appendChild(aElement);
        ulElement.appendChild(liElement);
    }

    // create next navigation button.  Its disabled on the last page of results
    if (currPage === numOfPages) {
        liElement = createLiNode("page-item disabled");
        aElement = createANode("", "#searchLabel", "\>");
    } else {
        nextPage = currPage + 1;
        liElement = createLiNode("page-item");
        aElement = createANode("pgNumLink(" + nextPage + ")", "#searchLabel", "\>");
    }
    liElement.appendChild(aElement);
    ulElement.appendChild(liElement);

    // update the DOM to show new pagination
    paginationAreaElement.appendChild(ulElement);


    
    // COD TO SHOW THE BOOK RESULTS

    // account for less than a full page of results on last page of results
    if (currPage === numOfPages)    {
        endResult = numOfResults - 1;
    }

    let para, node; // to hold newly created elements

    // remember results area
    let resultsAreaElement = document.getElementById("resultsArea");

    // call function to clear current result nodes
    removeChildNodes (resultsAreaElement);
    
    // for each search result, create new nodes and append to results area DOM element
    for (r = startResult; r <= endResult; r++) {
        para = document.createElement("p");
        node = document.createTextNode((r + 1) + ') "' + bookResults[r].title + '" by ' 
            + bookResults[r].authorName 
            + ', ISBN #: ' + bookResults[r].bookId);
        para.appendChild(node);
        resultsAreaElement.appendChild(para);
    }
} // end function

// this function removes all child nodes of a parent node
function removeChildNodes(parentNode)   {
    while (parentNode.firstChild)   {
        parentNode.removeChild(parentNode.lastChild);
    }
}

// this function creates a new List Item node
// parameters:
//   classAttStr - string for class attributes value
function createLiNode(classAttStr) {
    let liElement = document.createElement("li");
    let liElementAtt = document.createAttribute("class");
    liElementAtt.value = classAttStr;
    liElement.setAttributeNode(liElementAtt);
    
    return liElement;
}

// this function creates a new A node as well as a contained
//   child text node
// parameters:
//   onClickStr - string for onclick event attribute value
//   hrefStr - string for link attribute value
//   textStr - string for contained text node value
function createANode(onClickStr, hrefStr, textStr) {
    let aElement = document.createElement("a");
    let aElementAtt1 = document.createAttribute("class");

    aElementAtt1.value = "page-link";
    aElement.setAttributeNode(aElementAtt1);

    if (onClickStr != "") {
        let aElementAtt2 = document.createAttribute("onclick");
        aElementAtt2.value = onClickStr;
        aElement.setAttributeNode(aElementAtt2);
    }

    if (hrefStr != "") {
        let aElementAtt3 = document.createAttribute("href");
        aElementAtt3.value = hrefStr;
        aElement.setAttributeNode(aElementAtt3);
    }
    
    let textNode = createTextNode(textStr);
    aElement.appendChild(textNode);

    return aElement;
}

// this function creates a new text node containing passed textStr value
function createTextNode(textStr) {
    let newElement = document.createTextNode(textStr);

    return newElement;
}
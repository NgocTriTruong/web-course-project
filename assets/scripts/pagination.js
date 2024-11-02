// $(document).ready(function() {
//     // Function to show the specified page and hide others
//     function showPage(pageNumber) {
//         // Hide all pages and remove 'active' class from pagination buttons
//         $('.product-page').hide();
//         $('.page-button').removeClass('active');
//
//         // Show the selected page and add 'active' class to the corresponding button
//         $(`#page${pageNumber}`).show();
//         $(`.page-button[data-page=${pageNumber}]`).addClass('active');
//     }
//
//     // Event listener for pagination buttons
//     $('.page-button').click(function() {
//         const pageNumber = $(this).data('page');
//         showPage(pageNumber);
//     });
//
//     // Initial setup: show the first page by default
//     showPage(1);
// });

$(document).ready(function () {
    // Detect current category and page number from the URL
    const match = window.location.pathname.match(/product_([a-zA-Z]+)(\d*)\.html/);
    const currentCategory = match ? match[1] : null;
    const currentPage = match && match[2] ? parseInt(match[2]) : 1; // Defaults to page 1 if no number

    // Highlight the active page button based on the current category and page
    $('.page-button').each(function () {
        const page = $(this).data('page');
        const category = $(this).data('category');

        if (category === currentCategory && page === currentPage) {
            $(this).addClass('active');
        } else {
            $(this).removeClass('active');
        }
    });

    // Event listener for pagination button click
    $('.page-button').click(function () {
        const page = $(this).data('page'); // Get the target page number
        const category = $(this).data('category'); // Get the target category

        // Construct the target URL
        const pageSuffix = page > 1 ? page : ''; // No suffix for page 1
        const targetUrl = `product_${category}${pageSuffix}.html`;
        window.location.href = targetUrl; // Redirect to the target page
    });
});

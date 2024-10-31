$(document).ready(function() {
    // Function to show the specified page and hide others
    function showPage(pageNumber) {
        // Hide all pages and remove 'active' class from pagination buttons
        $('.product-page').hide();
        $('.page-button').removeClass('active');

        // Show the selected page and add 'active' class to the corresponding button
        $(`#page${pageNumber}`).show();
        $(`.page-button[data-page=${pageNumber}]`).addClass('active');
    }

    // Event listener for pagination buttons
    $('.page-button').click(function() {
        const pageNumber = $(this).data('page');
        showPage(pageNumber);
    });

    // Initial setup: show the first page by default
    showPage(1);
});
$(document).ready(function () {
    // Detect current category and page number from the URL
    const match = window.location.pathname.match(/([a-zA-Z_]+)(\d*)\.html$/);
    const currentCategory = match ? match[1] : null;
    const currentPage = match && match[2] ? parseInt(match[2], 10) : 1; // Defaults to page 1 if no number

    console.log('Matched URL parts:', match);
    console.log('Current Category:', currentCategory);
    console.log('Current Page:', currentPage);

    // Highlight the active page button based on the current category and page
    $('.page-button').each(function () {
        const page = $(this).data('page');
        const category = $(this).data('category');

        if (category === currentCategory && page === currentPage) {
            $(this).addClass('active');
            console.log('Active button found:', this);
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
        const targetUrl = `${category}${pageSuffix}.html`;
        window.location.href = targetUrl; // Redirect to the target page
    });
});

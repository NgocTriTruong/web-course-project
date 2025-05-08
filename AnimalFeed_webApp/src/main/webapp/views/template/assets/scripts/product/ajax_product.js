$(document).ready(function() {
  // Handle pagination for main product listing
  $(document).on('click', '#productAll .pagination .page-link', function(e) {
    handlePagination(e, this, updateMainProductList);
  });

  // Handle pagination for discount products
  $(document).on('click', '#sale-products .pagination .page-link', function(e) {
    handlePagination(e, this, updateDiscountProducts);
  });

  // Handle pagination for new products
  $(document).on('click', '#new-products .pagination .page-link', function(e) {
    handlePagination(e, this, updateNewProducts);
  });

  // Handle pagination for best-selling products
  $(document).on('click', '#best-products .pagination .page-link', function(e) {
    handlePagination(e, this, updateBestSellingProducts);
  });

  // General pagination handler
  function handlePagination(event, element, updateCallback) {
    event.preventDefault(); // Prevent default link behavior

    // Get URL from the link
    const url = $(element).attr('href');
    if (!url || $(element).parent().hasClass('disabled')) return; // Skip if link is disabled

    // Extract query parameters from URL
    const urlParams = new URLSearchParams(url.split('?')[1]);
    const categoryId = urlParams.get('categoryId') || '';

    // Extract the specific page parameter based on the container
    let pageParam = {};
    if ($(element).closest('#sale-products').length) {
      pageParam = { discountPage: urlParams.get('discountPage') || 1 };
    } else if ($(element).closest('#new-products').length) {
      pageParam = { newPage: urlParams.get('newPage') || 1 };
    } else if ($(element).closest('#best-products').length) {
      pageParam = { sellingPage: urlParams.get('sellingPage') || 1 };
    } else {
      pageParam = { page: urlParams.get('page') || 1 };
    }

    // Prepare data for AJAX request
    const data = { categoryId: categoryId, ...pageParam };

    // Show loading state
    const containerSelector = $(element).closest('.container');
    showLoading(containerSelector);

    // Send AJAX request
    $.ajax({
      url: contextPath + '/list-product',
      type: 'GET',
      data: data,
      success: function(response) {
        // Call the appropriate update function
        updateCallback(response, containerSelector);

        // Update URL in browser history without reloading
        const newUrl = window.location.pathname + '?' + new URLSearchParams(data).toString() +
            ($(element).closest('#sale-products').length ? '#sale-products' :
                $(element).closest('#new-products').length ? '#new-products' :
                    $(element).closest('#best-products').length ? '#best-products' : '');
        window.history.pushState({}, '', newUrl);
      },
      error: function(xhr, status, error) {
        console.error('Error loading data: ', error);
        $(containerSelector).append('<div class="alert alert-danger">Unable to load products. Please try again.</div>');
      },
      complete: function() {
        hideLoading(containerSelector);
      }
    });
  }

  // Update functions for different product sections
  function updateMainProductList(response, container) {
    const parsedResponse = $(response);
    $('#product_all_one .row').html(parsedResponse.find('#product_all_one .row').html());
    $('#productAll .pagination').html(parsedResponse.find('#productAll .pagination').html());
  }

  function updateDiscountProducts(response, container) {
    const parsedResponse = $(response);
    $('#sale-products .row').html(parsedResponse.find('#sale-products .row').html());
    $('#sale-products .pagination').html(parsedResponse.find('#sale-products .pagination').html());
  }

  function updateNewProducts(response, container) {
    const parsedResponse = $(response);
    $('#new-products .row').html(parsedResponse.find('#new-products .row').html());
    $('#new-products .pagination').html(parsedResponse.find('#new-products .pagination').html());
  }

  function updateBestSellingProducts(response, container) {
    const parsedResponse = $(response);
    $('#best-products .row').html(parsedResponse.find('#best-products .row').html());
    $('#best-products .pagination').html(parsedResponse.find('#best-products .pagination').html());
  }

  // Show loading state
  function showLoading(container) {
    $(container).append('<div class="loading-overlay"><div class="spinner-border text-success" role="status"><span class="visually-hidden">Loading...</span></div></div>');
  }

  // Hide loading state
  function hideLoading(container) {
    $(container).find('.loading-overlay').remove();
  }

  // Store context path for use in AJAX requests
  const contextPath = '${pageContext.request.contextPath}';

  // Handle tab switching with history state
  $('.product_sale_new div').click(function() {
    const targetTabId = $(this).attr('id');
    const currentURL = new URL(window.location.href);

    // Update URL with the selected tab
    if (targetTabId === 'sale-tab') {
      currentURL.hash = 'sale-products';
    } else if (targetTabId === 'new-tab') {
      currentURL.hash = 'new-products';
    } else if (targetTabId === 'best-tab') {
      currentURL.hash = 'best-products';
    }

    window.history.pushState({}, '', currentURL.toString());
  });

  // Handle browser back/forward navigation
  $(window).on('popstate', function() {
    const hash = window.location.hash;
    if (hash === '#sale-products') {
      $('#sale-tab').trigger('click');
    } else if (hash === '#new-products') {
      $('#new-tab').trigger('click');
    } else if (hash === '#best-products') {
      $('#best-tab').trigger('click');
    }
  });

  // Check hash on page load to show the correct tab
  $(function() {
    const hash = window.location.hash;
    if (hash === '#sale-products') {
      $('#sale-tab').trigger('click');
    } else if (hash === '#new-products') {
      $('#new-tab').trigger('click');
    } else if (hash === '#best-products') {
      $('#best-tab').trigger('click');
    }
  });
});
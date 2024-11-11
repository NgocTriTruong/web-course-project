$(document).ready(function() {
    // Function to apply filters based on selected group and brand
    function applyFilters() {
        const selectedGroup = $('#group').val();
        const selectedBrand = $('#brand').val();

        $('.product-card').each(function() {
            const cardGroup = $(this).data('group');
            const cardBrand = $(this).data('brand');
            const groupMatch = selectedGroup === "" || cardGroup === selectedGroup;
            const brandMatch = selectedBrand === "" || cardBrand === selectedBrand;

            // Show or hide product cards based on filters
            $(this).closest('.col').css('display', groupMatch && brandMatch ? 'flex' : 'none');
        });
    }

    // Apply filters whenever the group or brand selection changes
    $('#group, #brand').on('change', applyFilters);

    // Initial call to show all products on page load
    applyFilters();
});


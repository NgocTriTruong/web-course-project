// JavaScript for Filtering and Rearrangement
$(document).ready(function() {
    $('#group, #brand').on('change', function() {
        const selectedGroup = $('#group').val();
        const selectedBrand = $('#brand').val();

        // Loop through each product card to determine visibility
        $('.product-card').each(function() {
            const cardGroup = $(this).data('group');
            const cardBrand = $(this).data('brand');
            const groupMatch = selectedGroup === "" || cardGroup === selectedGroup;
            const brandMatch = selectedBrand === "" || cardBrand === selectedBrand;

            // Show or hide based on combined group and brand conditions
            if (groupMatch && brandMatch) {
                $(this).closest('.col').css('display', 'flex'); // Show matched cards
            } else {
                $(this).closest('.col').css('display', 'none'); // Hide unmatched cards
            }
        });
    });

    // Reset filters to show all items when clearing selections
    $('#group, #brand').on('change', function() {
        if ($('#group').val() === "" && $('#brand').val() === "") {
            $('.product-card').closest('.col').css('display', 'flex'); // Show all cards when no filter is selected
        }
    });
});

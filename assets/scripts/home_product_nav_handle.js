document.addEventListener("DOMContentLoaded", () => {
    const menuLinks = document.querySelectorAll(".home_icon a");
    const productSections = document.querySelectorAll(".list-products");

    menuLinks.forEach((link) => {
        link.addEventListener("click", (event) => {
            event.preventDefault(); // Prevent default anchor behavior
            const targetId = link.getAttribute("href").substring(1); // Get the target ID without the '#'
            const targetSection = document.getElementById(targetId);

            if (targetSection) {
                // Remove 'active' class from all sections
                productSections.forEach(section => section.classList.remove("active"));

                // Add 'active' class to the target section
                targetSection.classList.add("active");

                // Scroll to the section smoothly
                targetSection.scrollIntoView({
                    behavior: "smooth",
                    block: "center"
                });
            }
        });
    });
});

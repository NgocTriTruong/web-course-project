document.addEventListener("DOMContentLoaded", () => {
    const saleTab = document.getElementById("sale-tab");
    const newTab = document.getElementById("new-tab");
    const saleProducts = document.getElementById("sale-products");
    const newProducts = document.getElementById("new-products");

    saleTab.addEventListener("click", () => {
        saleTab.classList.add("active-tab");
        newTab.classList.remove("active-tab");
        saleProducts.style.display = "block";
        newProducts.style.display = "none";
    });

    newTab.addEventListener("click", () => {
        newTab.classList.add("active-tab");
        saleTab.classList.remove("active-tab");
        newProducts.style.display = "block";
        saleProducts.style.display = "none";
    });
});

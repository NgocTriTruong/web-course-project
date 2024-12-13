document.addEventListener("DOMContentLoaded", () => {
    const saleTab = document.getElementById("sale-tab");
    const newTab = document.getElementById("new-tab");
    const bestTab = document.getElementById("best-tab");
    const saleProducts = document.getElementById("sale-products");
    const newProducts = document.getElementById("new-products");
    const bestProducts = document.getElementById("best-products");

    saleTab.addEventListener("click", () => {
        saleTab.classList.add("active-tab");
        newTab.classList.remove("active-tab");
        bestTab.classList.remove("active-tab");
        saleProducts.style.display = "block";
        newProducts.style.display = "none";
        bestProducts.style.display = "none";
    });

    newTab.addEventListener("click", () => {
        saleTab.classList.remove("active-tab");
        newTab.classList.add("active-tab");
        bestTab.classList.remove("active-tab");
        saleProducts.style.display = "none";
        newProducts.style.display = "block";
        bestProducts.style.display = "none";
    });

    bestTab.addEventListener("click", () => {
        saleTab.classList.remove("active-tab");
        newTab.classList.remove("active-tab");
        bestTab.classList.add("active-tab");
        saleProducts.style.display = "none";
        newProducts.style.display = "none";
        bestProducts.style.display = "block";
    });

});

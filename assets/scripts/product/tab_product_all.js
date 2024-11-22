document.addEventListener("DOMContentLoaded", () => {
    const productOne = document.getElementById("product_all_one");
    const productTwo = document.getElementById("product_all_two");
    const productThree = document.getElementById("product_all_three");
    const productOneTab = document.getElementById("product-tab-one");
    const productTwoTab = document.getElementById("product-tab-two");
    const productThreeTab = document.getElementById("product-tab-three");

    productOneTab.addEventListener("click", () => {
        productOneTab.classList.add("product-active");
        productTwoTab.classList.remove("product-active");
        productThreeTab.classList.remove("product-active");
        productOne.style.display = "block";
        productTwo.style.display = "none";
        productThree.style.display = "none";
    });

    productTwoTab.addEventListener("click", () => {
        productTwoTab.classList.add("product-active");
        productOneTab.classList.remove("product-active");
        productThreeTab.classList.remove("product-active");
        productTwo.style.display = "block";
        productOne.style.display = "none";
        productThree.style.display = "none";
    });

    productThreeTab.addEventListener("click", () => {
        productThreeTab.classList.add("product-active");
        productOneTab.classList.remove("product-active");
        productTwoTab.classList.remove("product-active");
        productThree.style.display = "block";
        productOne.style.display = "none";
        productTwo.style.display = "none";
    });

});

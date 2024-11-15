// Sales Chart
var ctxSales = document.getElementById('salesChart').getContext('2d');
var salesChart = new Chart(ctxSales, {
    type: 'line',
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
            label: 'Hàng đã bán',
            data: [100, 120, 130, 140, 160, 180, 200],
            borderColor: 'rgba(54, 162, 235, 1)',
            fill: false
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                beginAtZero: true
            }
        }
    }
});

// User Statistics Chart
var ctxUser = document.getElementById('userChart').getContext('2d');
var userChart = new Chart(ctxUser, {
    type: 'bar',
    data: {
        labels: ['Active Users', 'Inactive Users'],
        datasets: [{
            label: 'Thống kê Người dùng',
            data: [350, 50],
            backgroundColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)']
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});

// Product Statistics Chart
var ctxProduct = document.getElementById('productChart').getContext('2d');
var productChart = new Chart(ctxProduct, {
    type: 'pie',
    data: {
        labels: ['Product A', 'Product B', 'Product C'],
        datasets: [{
            label: 'Thống kê Tổng Sản phẩm',
            data: [30, 40, 30],
            backgroundColor: ['rgba(255, 159, 64, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 99, 132, 1)']
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});


document.addEventListener("DOMContentLoaded", function () {
    // Mock data for products and users
    let products = [
        { id: 1, name: "Sản phẩm 1", price: "100,000 VND" },
        { id: 2, name: "Sản phẩm 2", price: "200,000 VND" },
    ];
    let users = [
        { id: 1, name: "Người dùng 1", email: "user1@example.com" },
        { id: 2, name: "Người dùng 2", email: "user2@example.com" },
    ];

    // Function to render products table
    function renderProducts() {
        const productTableBody = document.querySelector("#product-table tbody");
        productTableBody.innerHTML = "";
        products.forEach((product) => {
            const row = document.createElement("tr");
            row.innerHTML = `
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.price}</td>
        <td>
          <button class="btn btn-warning btn-sm" onclick="editProduct(${product.id})">Sửa</button>
          <button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.id})">Xóa</button>
        </td>
      `;
            productTableBody.appendChild(row);
        });
    }

    // Function to render users table
    function renderUsers() {
        const userTableBody = document.querySelector("#user-table tbody");
        userTableBody.innerHTML = "";
        users.forEach((user) => {
            const row = document.createElement("tr");
            row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>
          <button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Sửa</button>
          <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Xóa</button>
        </td>
      `;
            userTableBody.appendChild(row);
        });
    }

    // Function to add product
    document.getElementById("add-product-btn").addEventListener("click", function () {
        const productName = prompt("Nhập tên sản phẩm:");
        const productPrice = prompt("Nhập giá sản phẩm:");
        const newProduct = {
            id: products.length + 1,
            name: productName,
            price: productPrice,
        };
        products.push(newProduct);
        renderProducts();
    });

    // Function to delete product
    window.deleteProduct = function (id) {
        products = products.filter((product) => product.id !== id);
        renderProducts();
    };

    // Function to edit product
    window.editProduct = function (id) {
        const product = products.find((product) => product.id === id);
        const newName = prompt("Sửa tên sản phẩm:", product.name);
        const newPrice = prompt("Sửa giá sản phẩm:", product.price);
        product.name = newName;
        product.price = newPrice;
        renderProducts();
    };

    // Function to add user
    document.getElementById("add-user-btn").addEventListener("click", function () {
        const userName = prompt("Nhập tên người dùng:");
        const userEmail = prompt("Nhập email người dùng:");
        const newUser = {
            id: users.length + 1,
            name: userName,
            email: userEmail,
        };
        users.push(newUser);
        renderUsers();
    });

    // Function to delete user
    window.deleteUser = function (id) {
        users = users.filter((user) => user.id !== id);
        renderUsers();
    };

    // Function to edit user
    window.editUser = function (id) {
        const user = users.find((user) => user.id === id);
        const newName = prompt("Sửa tên người dùng:", user.name);
        const newEmail = prompt("Sửa email người dùng:", user.email);
        user.name = newName;
        user.email = newEmail;
        renderUsers();
    };

    // Function to handle menu item click
    document.getElementById("product-link").addEventListener("click", function () {
        showSection("product-management");
    });
    document.getElementById("user-link").addEventListener("click", function () {
        showSection("user-management");
    });

    // Show the selected section and hide the others
    function showSection(sectionId) {
        const sections = document.querySelectorAll(".content-section");
        sections.forEach((section) => {
            section.classList.remove("active");
        });
        document.getElementById(sectionId).classList.add("active");
    }

    // Initial rendering
    renderProducts();
    renderUsers();
});

// Function to show and hide sections based on menu click
document.getElementById('product-management-link').addEventListener('click', function() {
    toggleSection('product-management');
});

document.getElementById('user-management-link').addEventListener('click', function() {
    toggleSection('user-management');
});

document.getElementById('sales-statistics-link').addEventListener('click', function() {
    toggleSection('sales-statistics');
});

document.getElementById('user-statistics-link').addEventListener('click', function() {
    toggleSection('user-statistics');
});

document.getElementById('product-statistics-link').addEventListener('click', function() {
    toggleSection('product-statistics');
});

// Function to toggle visibility of a section
function toggleSection(sectionId) {
    const sections = ['product-management', 'user-management', 'sales-statistics', 'user-statistics', 'product-statistics'];
    sections.forEach(id => {
        const section = document.getElementById(id);
        if (id === sectionId) {
            section.style.display = 'block';
        } else {
            section.style.display = 'none';
        }
    });
}

// Logout functionality
document.getElementById('logout-btn').addEventListener('click', function() {
    // Redirect to the login page when the logout button is clicked
    window.location.href = 'login_admin.html';
});
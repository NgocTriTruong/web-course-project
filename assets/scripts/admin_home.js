// Hiển thị các mục khi click menu
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
}

// Quản lý sản phẩm
let productList = [
    {
        product_id: "P001",
        category_id: "CAT001",
        name: "Thức ăn cho tôm",
        price: 200000,
        desc: "Hàng chính hãng Việt Nam",
        quantity: 50,
        status: "active",
        type: "Loại A",
        saleType: "no_sale",
        date: "2024-03-20",
        image: "https://via.placeholder.com/100x100",
        brand_name: "BEIKESU ",
        // sale_id: ""
    },
];

function addProduct() {
    const product_id = document.getElementById("product-id").value;
    const category_id = document.getElementById("category-id").value;
    const name = document.getElementById("product-name").value;
    const price = document.getElementById("product-price").value;
    const desc = document.getElementById("product-desc").value;
    const quantity = document.getElementById("product-quantity").value;
    const status = document.getElementById("product-status").value;
    const type = document.getElementById("product-type").value;
    const saleType = document.getElementById("sale-type").value;
    const brand_name = document.getElementById("brand-name").value;
    // const sale_id = document.getElementById("sale-id").value;
    const date = document.getElementById("product-date").value;
    const imageInput = document.getElementById("product-image");
    const image = imageInput.files[0];

    if (product_id && category_id && name && price && desc && quantity && status && type && saleType && date && image && brand_name) {
        const reader = new FileReader();
        reader.onload = function (e) {
            productList.push({
                product_id,
                category_id,
                name,
                price,
                desc,
                quantity,
                status,
                type,
                saleType,
                date,
                image: e.target.result,
                brand_name
            });
            clearProductInputs();
            renderProductList();
        };
        reader.readAsDataURL(image);
    } else {
        alert("Vui lòng điền đầy đủ thông tin sản phẩm và chọn ảnh!");
    }
}

function clearProductInputs() {
    document.getElementById("product-id").value = "";
    document.getElementById("category-id").value = "";
    document.getElementById("product-name").value = "";
    document.getElementById("product-price").value = "";
    document.getElementById("product-desc").value = "";
    document.getElementById("product-quantity").value = "";
    document.getElementById("product-status").value = "";
    document.getElementById("product-type").value = "";
    document.getElementById("sale-type").value = "";
    document.getElementById("brand-name").value = "";
    // document.getElementById("sale-id").value = "";
    document.getElementById("product-date").value = "";
    document.getElementById("product-image").value = "";
}

function editProduct(index) {
    const product = productList[index];
    const newProductId = prompt("Mã sản phẩm:", product.product_id);
    const newCategoryId = prompt("Mã danh mục:", product.category_id);
    const newName = prompt("Tên sản phẩm:", product.name);
    const newPrice = prompt("Giá tiền:", product.price);
    const newDesc = prompt("Mô tả:", product.desc);
    const newQuantity = prompt("Số lượng:", product.quantity);
    const newStatus = prompt("Trạng thái:", product.status);
    const newType = prompt("Loại sản phẩm:", product.type);
    const newSaleType = prompt("Loại giảm giá:", product.saleType);
    const newBrandName = prompt("Tên thương hiệu:", product.brand_name);
    // const newSaleId = prompt("Mã giảm giá:", product.sale_id);
    const newDate = prompt("Ngày tạo:", product.date);

    const newImageInput = document.createElement("input");
    newImageInput.type = "file";
    newImageInput.accept = "image/*";

    newImageInput.onchange = function () {
        const file = newImageInput.files[0];
        const reader = new FileReader();
        reader.onload = function (e) {
            const newImage = e.target.result;
            if (newProductId && newCategoryId && newName && newPrice && newDesc && newQuantity && newStatus && newType && newSaleType && newDate && newBrandName) {
                productList[index] = {
                    product_id: newProductId,
                    category_id: newCategoryId,
                    name: newName,
                    price: newPrice,
                    desc: newDesc,
                    quantity: newQuantity,
                    status: newStatus,
                    type: newType,
                    saleType: newSaleType,
                    date: newDate,
                    brand_name: newBrandName,
                    // sale_id: newSaleId,
                    image: newImage || product.image
                };
                renderProductList();
            } else {
                alert("Vui lòng điền đầy đủ thông tin!");
            }
        };
        reader.readAsDataURL(file);
    };

    if (confirm("Bạn có muốn thay đổi ảnh?")) {
        newImageInput.click();
    } else {
        if (newProductId && newCategoryId && newName && newPrice && newDesc && newQuantity && newStatus && newType && newSaleType && newDate && newBrandName) {
            productList[index] = {
                product_id: newProductId,
                category_id: newCategoryId,
                name: newName,
                price: newPrice,
                desc: newDesc,
                quantity: newQuantity,
                status: newStatus,
                type: newType,
                saleType: newSaleType,
                date: newDate,
                brand_name: newBrandName,
                // sale_id: newSaleId,
                image: product.image
            };
            renderProductList();
        } else {
            alert("Vui lòng điền đầy đủ thông tin!");
        }
    }
}

function renderProductList() {
    const list = document.getElementById("product-list");
    list.innerHTML = "";
    productList.forEach((product, index) => {
        list.innerHTML += `
      <li>
        <img src="${product.image}" alt="Product Image" style="width: 100px; height: 100px; object-fit: cover;">
        <span><strong>Mã SP:</strong> ${product.product_id}</span>
        <span><strong>Mã danh mục:</strong> ${product.category_id}</span>
        <span><strong>Tên:</strong> ${product.name}</span>
        <span><strong>Giá:</strong> ${product.price.toLocaleString()} VNĐ</span>
        <span><strong>Mô tả:</strong> ${product.desc}</span>
        <span><strong>Số lượng:</strong> ${product.quantity}</span>
        <span><strong>Trạng thái:</strong> ${product.status}</span>
        <span><strong>Loại:</strong> ${product.type}</span>
        <span><strong>Giảm giá:</strong> ${product.saleType}</span>
        <span><strong>Thương hiệu:</strong> ${product.brand_name}</span>
        <span><strong>Ngày tạo:</strong> ${product.date}</span>
        <div>
          <button onclick="editProduct(${index})">Sửa</button>
          <button onclick="deleteProduct(${index})">Xóa</button>
        </div>
      </li>
    `;
    });
}

function deleteProduct(index) {
    productList.splice(index, 1);
    renderProductList();
}

// Mock Data for charts
const topSellingData = {
    labels: ['Sản phẩm A', 'Sản phẩm B', 'Sản phẩm C'],
    datasets: [{
        label: 'Sản phẩm bán chạy',
        data: [100, 150, 80],
        backgroundColor: ['#4CAF50', '#FF9800', '#F44336'],
        borderColor: ['#388E3C', '#F57C00', '#D32F2F'],
        borderWidth: 1
    }]
};

const newProductsData = {
    labels: ['Sản phẩm X', 'Sản phẩm Y', 'Sản phẩm Z'],
    datasets: [{
        label: 'Sản phẩm mới',
        data: [50, 70, 30],
        backgroundColor: ['#2196F3', '#9C27B0', '#FF5722'],
        borderColor: ['#1976D2', '#7B1FA2', '#F4511E'],
        borderWidth: 1
    }]
};


// Quản lý người dùng
let userList = [];

function addUser() {
    const userName = document.getElementById("user-name").value;
    if (userName) {
        userList.push({ name: userName, locked: false });
        document.getElementById("user-name").value = "";
        renderUserList();
    } else {
        alert("Vui lòng nhập tên người dùng!");
    }
}

function editUser(index) {
    const newName = prompt("Nhập tên người dùng mới:", userList[index].name);
    if (newName) {
        userList[index].name = newName;
        renderUserList();
    }
}

function lockUser(index) {
    userList[index].locked = !userList[index].locked;
    renderUserList();
}

function deleteUser(index) {
    userList.splice(index, 1);
    renderUserList();
}

function renderUserList() {
    const list = document.getElementById("user-list");
    list.innerHTML = "";
    userList.forEach((user, index) => {
        list.innerHTML += `
            <li>
                <span style="${user.locked ? 'color: red; font-weight: bold;' : ''}">
                    ${user.name} ${user.locked ? '(Bị khóa)' : ''}
                </span>
                <div>
                    <button onclick="editUser(${index})">Sửa</button>
                    <button class="lock-btn" onclick="lockUser(${index})">
                        ${user.locked ? 'Mở khóa' : 'Khóa'}
                    </button>
                    <button onclick="deleteUser(${index})">Xóa</button>
                </div>
            </li>
        `;
    });
}

//Quan li don hang
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.classList.remove('active'));

    const activeSection = document.getElementById(sectionId);
    if (activeSection) {
        activeSection.classList.add('active');
    }
}

// Mảng giả lập lưu trữ đơn hàng
let orders = [];

// Chức năng: Thêm Đơn hàng
function addOrder() {
    const orderId = document.getElementById('order-id').value;
    const customerName = document.getElementById('customer-name').value;
    const orderStatus = document.getElementById('order-status').value;

    if (!orderId || !customerName || !orderStatus) {
        alert("Vui lòng nhập đầy đủ thông tin đơn hàng.");
        return;
    }

    // Thêm đơn hàng vào mảng
    orders.push({ id: orderId, customer: customerName, status: orderStatus });

    // Xóa dữ liệu input
    document.getElementById('order-id').value = '';
    document.getElementById('customer-name').value = '';
    document.getElementById('order-status').value = '';

    // Hiển thị danh sách đơn hàng
    renderOrderList();
}

// Chức năng: Lấy danh sách đơn hàng
function fetchOrders() {
    if (orders.length === 0) {
        alert("Hiện chưa có đơn hàng nào.");
    } else {
        renderOrderList();
    }
}

// Chức năng: Cập nhật trạng thái đơn hàng
function updateOrderStatus(index) {
    const newStatus = prompt(
        "Nhập trạng thái mới (1: Đang chuẩn bị hàng, 2: Đang vận chuyển, 3: Hoàn tất giao hàng, 4: Đã hủy):"
    );

    if (newStatus === "1") {
        orders[index].status = "Đang chuẩn bị hàng";
    } else if (newStatus === "2") {
        orders[index].status = "Đang vận chuyển";
    } else if (newStatus === "3") {
        orders[index].status = "Hoàn tất giao hàng";
    } else if (newStatus === "4") {
        orders[index].status = "Đã hủy";
    } else {
        alert("Trạng thái không hợp lệ.");
        return;
    }

    renderOrderList();
}

// Chức năng: Hiển thị danh sách đơn hàng
function renderOrderList() {
    const tableBody = document.querySelector('#order-table tbody');
    tableBody.innerHTML = ''; // Xóa nội dung cũ

    orders.forEach((order, index) => {
        let statusClass = '';
        if (order.status === 'Đang chuẩn bị hàng') statusClass = 'status-preparing';
        if (order.status === 'Đang vận chuyển') statusClass = 'status-shipping';
        if (order.status === 'Hoàn tất giao hàng') statusClass = 'status-completed';
        if (order.status === 'Đã hủy') statusClass = 'status-cancelled';

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${order.id}</td>
            <td>${order.customer}</td>
            <td class="${statusClass}">${order.status}</td>
            <td>
                <button onclick="updateOrderStatus(${index})">Cập nhật</button>
                <button onclick="deleteOrder(${index})" style="background-color: #dc3545;">Xóa</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Chức năng: Xóa đơn hàng
function deleteOrder(index) {
    orders.splice(index, 1);
    renderOrderList();
}

let shippers = [];

// them shipper moi
function addShipper() {
    const shipperName = document.getElementById('shipper-name').value;
    if (shipperName) {
        shippers.push(shipperName);
        document.getElementById('shipper-name').value = ''; // Clear input
        displayShippers();
    }
}

// lay ra het shipper
function displayShippers() {
    const shipperList = document.getElementById('shipper-list');
    shipperList.innerHTML = ''; // Clear current list
    shippers.forEach((shipper, index) => {
        const li = document.createElement('li');
        li.textContent = shipper;
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Xóa';
        deleteButton.onclick = () => deleteShipper(index);
        li.appendChild(deleteButton);
        shipperList.appendChild(li);
    });
}

// xoa shipper
function deleteShipper(index) {
    shippers.splice(index, 1);
    displayShippers();
}

//trang tin tuc
let newsArticles = [];

// them trang tin tuc mới
function addNews() {
    const title = document.getElementById('news-title').value;
    const content = document.getElementById('news-content').value;
    if (title && content) {
        newsArticles.push({ title, content });
        document.getElementById('news-title').value = ''; // Clear input
        document.getElementById('news-content').value = ''; // Clear input
        displayNews();
    }
}

// lay ra tat ca tin tuc
function displayNews() {
    const newsList = document.getElementById('news-list');
    newsList.innerHTML = ''; // Clear current list
    newsArticles.forEach((article, index) => {
        const li = document.createElement('li');
        li.innerHTML = `<strong>${article.title}</strong><p>${article.content}</p>`;
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Xóa';
        deleteButton.onclick = () => deleteNews(index);
        li.appendChild(deleteButton);
        newsList.appendChild(li);
    });
}

// xoa tin tuc
function deleteNews(index) {
    newsArticles.splice(index, 1);
    displayNews();
}

//logout
function logout() {
    window.location.href = "../home.html"; // Thay đổi đường dẫn đến trang đăng nhập của bạn
}
// Hiển thị các mục khi click menu
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
}

// Quản lý sản phẩm
let productList = [];

function addProduct() {
    const name = document.getElementById("product-name").value;
    const price = document.getElementById("product-price").value;
    const desc = document.getElementById("product-desc").value;
    const type = document.getElementById("product-type").value;
    const saleType = document.getElementById("sale-type").value;
    const date = document.getElementById("product-date").value;
    const imageInput = document.getElementById("product-image");
    const image = imageInput.files[0];

    if (name && price && desc && type && saleType && date && image) {
        const reader = new FileReader();
        reader.onload = function (e) {
            productList.push({
                name,
                price,
                desc,
                type,
                saleType,
                date,
                image: e.target.result,
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
    document.getElementById("product-name").value = "";
    document.getElementById("product-price").value = "";
    document.getElementById("product-desc").value = "";
    document.getElementById("product-type").value = "";
    document.getElementById("sale-type").value = "";
    document.getElementById("product-date").value = "";
    document.getElementById("product-image").value = "";
}

function editProduct(index) {
    const product = productList[index];
    const newName = prompt("Tên sản phẩm:", product.name);
    const newPrice = prompt("Giá tiền:", product.price);
    const newDesc = prompt("Mô tả:", product.desc);
    const newType = prompt("Loại sản phẩm:", product.type);
    const newSaleType = prompt("Loại giảm giá:", product.saleType);
    const newDate = prompt("Ngày tạo:", product.date);

    // Yêu cầu tải lên ảnh mới
    const newImageInput = document.createElement("input");
    newImageInput.type = "file";
    newImageInput.accept = "image/*";

    // Khi người dùng chọn ảnh mới
    newImageInput.onchange = function () {
        const file = newImageInput.files[0];
        const reader = new FileReader();
        reader.onload = function (e) {
            const newImage = e.target.result;
            if (newName && newPrice && newDesc && newType && newSaleType && newDate) {
                productList[index] = {
                    name: newName,
                    price: newPrice,
                    desc: newDesc,
                    type: newType,
                    saleType: newSaleType,
                    date: newDate,
                    image: newImage || product.image, // Giữ ảnh cũ nếu không chọn mới
                };
                renderProductList();
            } else {
                alert("Vui lòng điền đầy đủ thông tin!");
            }
        };
        reader.readAsDataURL(file);
    };

    // Nếu người dùng muốn sửa ảnh, hiện file chọn
    if (confirm("Bạn có muốn thay đổi ảnh?")) {
        newImageInput.click();
    } else {
        // Giữ nguyên ảnh cũ
        if (newName && newPrice && newDesc && newType && newSaleType && newDate) {
            productList[index] = {
                name: newName,
                price: newPrice,
                desc: newDesc,
                type: newType,
                saleType: newSaleType,
                date: newDate,
                image: product.image,
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
        <span><strong>Tên:</strong> ${product.name}</span>
        <span><strong>Giá:</strong> ${product.price} VNĐ</span>
        <span><strong>Mô tả:</strong> ${product.desc}</span>
        <span><strong>Loại:</strong> ${product.type}</span>
        <span><strong>Giảm giá:</strong> ${product.saleType}</span>
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
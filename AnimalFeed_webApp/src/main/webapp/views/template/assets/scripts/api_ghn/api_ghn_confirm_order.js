let provincesData = [];
let districtsData = [];
let wardsData = [];

document.addEventListener("DOMContentLoaded", function () {
    loadProvinces();
    const choseLocation = document.querySelector('.chose_location');
    if (choseLocation) {
        choseLocation.addEventListener('click', openAddressForm);
    } else {
        console.error("Element with class 'chose_location' not found.");
    }
    const closeAddressFormBtn = document.getElementById('closeAddressForm');
    if (closeAddressFormBtn) {
        closeAddressFormBtn.addEventListener('click', closeAddressForm);
    } else {
        console.error("Element with ID 'closeAddressForm' not found.");
    }
    const saveAddressBtn = document.getElementById('saveAddressButton');
    if (saveAddressBtn) {
        saveAddressBtn.addEventListener('click', () => {
            saveAddress();
            calculateShippingFee();
        });
    } else {
        console.error("Element with ID 'saveAddressButton' not found.");
    }
});

//load provinces
async function loadProvinces() {
    try {
        const response = await fetch(`${window.location.origin}/AnimalFeed_webApp/api/ghn/provinces`, {
            headers: { 'Content-Type': 'application/json' }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Response is not JSON. Check your API endpoint.");
        }

        provincesData = await response.json();
        setupProvinceAutocomplete();
    } catch (error) {
        console.error("Error loading provinces:", error);
    }
}

function setupProvinceAutocomplete() {
    const provinceInput = document.getElementById('provinceInput');
    const suggestions = document.getElementById('provinceSuggestions');
    if (!provinceInput || !suggestions) return;

    function showSuggestions(filteredData) {
        suggestions.innerHTML = '';
        if (!filteredData || !Array.isArray(filteredData)) {
            console.warn("No valid province data to display");
            suggestions.classList.remove('show');
            return;
        }
        filteredData.forEach(province => {
            const item = document.createElement('div');
            item.classList.add('dropdown-item');
            item.textContent = province.ProvinceName;
            item.addEventListener('click', () => {
                provinceInput.value = province.ProvinceName;
                document.getElementById('provinceCode').value = province.ProvinceID;
                suggestions.innerHTML = '';
                suggestions.classList.remove('show');
                loadDistricts(province.ProvinceID);
            });
            suggestions.appendChild(item);
        });
        if (filteredData.length > 0) suggestions.classList.add('show');
        else suggestions.classList.remove('show');
    }

    provinceInput.addEventListener('focus', () => {
        const query = provinceInput.value.trim().toLowerCase();
        const filtered = query.length === 0 ? provincesData : provincesData.filter(p => p.ProvinceName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    provinceInput.addEventListener('input', () => {
        const query = provinceInput.value.trim().toLowerCase();
        const filtered = provincesData.filter(p => p.ProvinceName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    provinceInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

//load districts
async function loadDistricts(provinceId) {
    const districtInput = document.getElementById('districtInput');
    districtInput.disabled = false;
    districtInput.value = '';
    try {
        const response = await fetch(`${window.location.origin}/AnimalFeed_webApp/api/ghn/districts`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ province_id: provinceId })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        districtsData = await response.json();
        setupDistrictAutocomplete();
    } catch (error) {
        console.error("Error loading districts:", error);
    }
}

function setupDistrictAutocomplete() {
    const districtInput = document.getElementById('districtInput');
    const suggestions = document.getElementById('districtSuggestions');
    if (!districtInput || !suggestions) return;

    function showSuggestions(filteredData) {
        suggestions.innerHTML = '';
        if (!filteredData || !Array.isArray(filteredData)) {
            console.warn("No valid district data to display");
            suggestions.classList.remove('show');
            return;
        }
        filteredData.forEach(district => {
            const item = document.createElement('div');
            item.classList.add('dropdown-item');
            item.textContent = district.DistrictName;
            item.addEventListener('click', () => {
                districtInput.value = district.DistrictName;
                document.getElementById('districtCode').value = district.DistrictID;
                suggestions.innerHTML = '';
                suggestions.classList.remove('show');
                loadWards(district.DistrictID);
            });
            suggestions.appendChild(item);
        });
        if (filteredData.length > 0) suggestions.classList.add('show');
        else suggestions.classList.remove('show');
    }

    districtInput.addEventListener('focus', () => {
        const query = districtInput.value.trim().toLowerCase();
        const filtered = query.length === 0 ? districtsData : districtsData.filter(d => d.DistrictName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    districtInput.addEventListener('input', () => {
        const query = districtInput.value.trim().toLowerCase();
        const filtered = districtsData.filter(d => d.DistrictName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    districtInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

//load wards
async function loadWards(districtId) {
    const wardInput = document.getElementById('wardInput');
    wardInput.disabled = false;
    wardInput.value = '';
    try {
        const response = await fetch(`${window.location.origin}/AnimalFeed_webApp/api/ghn/wards`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ district_id: districtId })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        wardsData = await response.json();
        setupWardAutocomplete();
    } catch (error) {
        console.error("Error loading wards:", error);
    }
}

function setupWardAutocomplete() {
    const wardInput = document.getElementById('wardInput');
    const suggestions = document.getElementById('wardSuggestions');
    if (!wardInput || !suggestions) return;

    function showSuggestions(filteredData) {
        suggestions.innerHTML = '';
        if (!filteredData || !Array.isArray(filteredData)) {
            console.warn("No valid ward data to display");
            suggestions.classList.remove('show');
            return;
        }
        filteredData.forEach(ward => {
            const item = document.createElement('div');
            item.classList.add('dropdown-item');
            item.textContent = ward.WardName;
            item.addEventListener('click', () => {
                wardInput.value = ward.WardName;
                document.getElementById('wardCode').value = ward.WardCode;
                suggestions.innerHTML = '';
                suggestions.classList.remove('show');
            });
            suggestions.appendChild(item);
        });
        if (filteredData.length > 0) suggestions.classList.add('show');
        else suggestions.classList.remove('show');
    }

    wardInput.addEventListener('focus', () => {
        const query = wardInput.value.trim().toLowerCase();
        const filtered = query.length === 0 ? wardsData : wardsData.filter(w => w.WardName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    wardInput.addEventListener('input', () => {
        const query = wardInput.value.trim().toLowerCase();
        const filtered = wardsData.filter(w => w.WardName.toLowerCase().includes(query));
        showSuggestions(filtered);
    });

    wardInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

//tinh phí vận chuyển
async function calculateShippingFee() {
    const province = document.getElementById('provinceInput').value;
    const district = document.getElementById('districtInput').value;
    const ward = document.getElementById('wardInput').value;
    const wardCode = document.getElementById('wardCode').value;

    if (!province || !district || !ward || !wardCode) {
        alert("Vui lòng chọn đầy đủ địa chỉ giao hàng.");
        return;
    }

    try {
        const response = await fetch(`${window.location.origin}/AnimalFeed_webApp/calculate-shipping-fee`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ province, district, ward, wardCode })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        if (data.success) {
            document.getElementById('summaryShippingFee').textContent = `${data.shippingFee.toLocaleString()} đ`;
            document.getElementById('shippingFeeHidden').value = data.shippingFee;

            const cartTotalPrice = parseFloat(document.getElementById('cartTotalPrice')?.value || 0);
            const totalPayment = cartTotalPrice + data.shippingFee;
            document.getElementById('totalPayment').textContent = `${totalPayment.toLocaleString()} đ`;
        } else {
            alert("Không thể tính phí vận chuyển: " + data.message);
        }
    } catch (error) {
        console.error("Error calculating shipping fee:", error);
        alert("Lỗi khi tính phí vận chuyển.");
    }
}

//hiển thị modal địa chỉ
function openAddressForm() {
    document.getElementById('addressFormModal').style.display = 'block';
    document.body.classList.add('modal-open');
    resetAddressFields();
}

//đóng modal địa chỉ
function closeAddressForm() {
    document.getElementById('addressFormModal').style.display = 'none';
    document.body.classList.remove('modal-open');
}

function resetAddressFields() {
    document.getElementById('savedAddresses').selectedIndex = 0;
    document.getElementById('provinceInput').value = '';
    document.getElementById('districtInput').value = '';
    document.getElementById('districtInput').disabled = true;
    document.getElementById('wardInput').value = '';
    document.getElementById('wardInput').disabled = true;
    document.getElementById('addressDetails').value = '';
}

function fillAddressFromSaved() {
    const savedAddresses = document.getElementById('savedAddresses');
    const selectedOption = savedAddresses.options[savedAddresses.selectedIndex];

    if (selectedOption.value === "") {
        resetAddressFields();
        return;
    }

    const province = selectedOption.getAttribute('data-province');
    const district = selectedOption.getAttribute('data-district');
    const ward = selectedOption.getAttribute('data-ward');
    const detail = selectedOption.getAttribute('data-detail');

    document.getElementById('provinceInput').value = province || '';
    document.getElementById('districtInput').value = district || '';
    document.getElementById('wardInput').value = ward || '';
    document.getElementById('addressDetails').value = detail || '';
}

function saveAddress() {
    const provinceInput = document.getElementById('provinceInput');
    const districtInput = document.getElementById('districtInput');
    const wardInput = document.getElementById('wardInput');
    const addressDetailsInput = document.getElementById('addressDetails');

    if (!provinceInput || !districtInput || !wardInput || !addressDetailsInput) {
        console.error('Required elements not found');
        return;
    }

    const provinceName = provinceInput.value.trim();
    const districtName = districtInput.value.trim();
    const wardName = wardInput.value.trim();
    const addressDetails = addressDetailsInput.value.trim();

    if (!provinceName || !districtName || !wardName) {
        alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
        return;
    }

    document.getElementById('provinceHidden').value = provinceName;
    document.getElementById('districtHidden').value = districtName;
    document.getElementById('wardHidden').value = wardName;
    document.getElementById('addressDetailsHidden').value = addressDetails;

    const addressParts = [];
    if (addressDetails) addressParts.push(addressDetails);
    if (wardName) addressParts.push(wardName);
    if (districtName) addressParts.push(districtName);
    if (provinceName) addressParts.push(provinceName);

    const fullAddress = addressParts.length > 0 ? addressParts.join(', ') : '';
    const displayField = document.querySelector('.chose_location');
    if (displayField && fullAddress) {
        displayField.value = fullAddress;
    }

    closeAddressForm();
}

document.getElementById("orderForm").addEventListener("submit", function (event) {
    event.preventDefault();
    let paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    if (confirm("Bạn có chắc chắn muốn đặt hàng không?")) {
        if (paymentMethod === "COD") {
            this.submit();
        } else if (paymentMethod === "VNPAY") {
            this.action = `${window.location.origin}/AnimalFeed_webApp/payment`;
            this.submit();
        }
    }
});
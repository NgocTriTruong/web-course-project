async function loadProvinces() {
  try {
    const response = await fetch('https://provinces.open-api.vn/api/p/');
    const provinces = await response.json();
    const provinceSelect = document.getElementById('province');
    provinceSelect.innerHTML = '<option value="">--Chọn Tỉnh--</option>';
    provinces.forEach(province => {
      provinceSelect.innerHTML += `<option value="${province.code}">${province.name}</option>`;
    });
  } catch (error) {
    console.error("Error loading provinces:", error);
    alert('Không thể tải danh sách Tỉnh. Vui lòng kiểm tra kết nối internet.');
  }
}

async function loadDistricts() {
  const provinceId = document.getElementById('province').value;
  if (!provinceId) {
    // Reset district and ward if no province is selected
    document.getElementById('district').innerHTML = '<option value="">--Chọn Huyện--</option>';
    document.getElementById('ward').innerHTML = '<option value="">--Chọn Xã--</option>';
    return;
  }

  try {
    const response = await fetch(`https://provinces.open-api.vn/api/p/${provinceId}?depth=2`);
    const data = await response.json();
    const districts = data.districts;
    const districtSelect = document.getElementById('district');
    districtSelect.innerHTML = '<option value="">--Chọn Huyện--</option>';
    districts.forEach(district => {
      districtSelect.innerHTML += `<option value="${district.code}">${district.name}</option>`;
    });

    // Reset wards when province changes
    document.getElementById('ward').innerHTML = '<option value="">--Chọn Xã--</option>';
  } catch (error) {
    console.error("Error loading districts:", error);
    alert('Không thể tải danh sách Huyện. Vui lòng kiểm tra kết nối internet.');
  }
}

async function loadWards() {
  const districtId = document.getElementById('district').value;
  if (!districtId) {
    // Reset ward if no district is selected
    document.getElementById('ward').innerHTML = '<option value="">--Chọn Xã--</option>';
    return;
  }

  try {
    const response = await fetch(`https://provinces.open-api.vn/api/d/${districtId}?depth=2`);
    const data = await response.json();
    const wards = data.wards;
    const wardSelect = document.getElementById('ward');
    wardSelect.innerHTML = '<option value="">--Chọn Xã--</option>';
    wards.forEach(ward => {
      wardSelect.innerHTML += `<option value="${ward.code}">${ward.name}</option>`;
    });
  } catch (error) {
    console.error("Error loading wards:", error);
    alert('Không thể tải danh sách Xã. Vui lòng kiểm tra kết nối internet.');
  }
}

function openAddressForm() {
  document.getElementById('addressFormModal').style.display = 'block';
}

function closeAddressForm() {
  document.getElementById('addressFormModal').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', function() {
  const saveButton = document.getElementById('saveAddressButton');
  saveButton.type = 'button'; // Change button type to prevent form submission
});

document.addEventListener('DOMContentLoaded', function() {
  const closeButton = document.getElementById('closeAddressForm');
  closeButton.type = 'button'; // Change button type to prevent form submission
});

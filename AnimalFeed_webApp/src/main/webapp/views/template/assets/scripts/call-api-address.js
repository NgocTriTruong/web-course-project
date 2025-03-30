// call-api-address.js
let provincesData = [];
let districtsData = [];
let wardsData = [];

async function loadProvinces() {
  try {
    const response = await fetch('https://provinces.open-api.vn/api/p/');
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
      item.textContent = province.name;
      item.addEventListener('click', () => {
        provinceInput.value = province.name;
        document.getElementById('provinceCode').value = province.code;
        suggestions.innerHTML = '';
        suggestions.classList.remove('show');
        loadDistricts(province.code);
      });
      suggestions.appendChild(item);
    });
    if (filteredData.length > 0) suggestions.classList.add('show');
    else suggestions.classList.remove('show');
  }

  provinceInput.addEventListener('focus', () => {
    const query = provinceInput.value.trim().toLowerCase();
    const filtered = query.length === 0 ? provincesData : provincesData.filter(p => p.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  provinceInput.addEventListener('input', () => {
    const query = provinceInput.value.trim().toLowerCase();
    const filtered = provincesData.filter(p => p.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  provinceInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

async function loadDistricts(provinceCode) {
  const districtInput = document.getElementById('districtInput');
  districtInput.disabled = false;
  districtInput.value = '';
  try {
    const response = await fetch(`https://provinces.open-api.vn/api/p/${provinceCode}?depth=2`);
    districtsData = (await response.json()).districts;
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
      item.textContent = district.name;
      item.addEventListener('click', () => {
        districtInput.value = district.name;
        document.getElementById('districtCode').value = district.code;
        suggestions.innerHTML = '';
        suggestions.classList.remove('show');
        loadWards(district.code);
      });
      suggestions.appendChild(item);
    });
    if (filteredData.length > 0) suggestions.classList.add('show');
    else suggestions.classList.remove('show');
  }

  districtInput.addEventListener('focus', () => {
    const query = districtInput.value.trim().toLowerCase();
    const filtered = query.length === 0 ? districtsData : districtsData.filter(d => d.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  districtInput.addEventListener('input', () => {
    const query = districtInput.value.trim().toLowerCase();
    const filtered = districtsData.filter(d => d.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  districtInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

async function loadWards(districtCode) {
  const wardInput = document.getElementById('wardInput');
  wardInput.disabled = false;
  wardInput.value = '';
  try {
    const response = await fetch(`https://provinces.open-api.vn/api/d/${districtCode}?depth=2`);
    wardsData = (await response.json()).wards;
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
      item.textContent = ward.name;
      item.addEventListener('click', () => {
        wardInput.value = ward.name;
        document.getElementById('wardCode').value = ward.name;
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
    const filtered = query.length === 0 ? wardsData : wardsData.filter(w => w.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  wardInput.addEventListener('input', () => {
    const query = wardInput.value.trim().toLowerCase();
    const filtered = wardsData.filter(w => w.name.toLowerCase().includes(query));
    showSuggestions(filtered);
  });

  wardInput.addEventListener('blur', () => setTimeout(() => suggestions.classList.remove('show'), 200));
}

// Khởi chạy loadProvinces khi file được tải
document.addEventListener("DOMContentLoaded", function() {
  loadProvinces();
});
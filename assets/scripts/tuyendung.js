const jobs = [
    { title: "THÔNG BÁO TUYỂN DỤNG - VINAFARM", location: "Tỉnh Tây Ninh" },
    { title: "Tuyển Dụng Nhân Viên Vận Hành Máy, Công Nhân Sản Xuất", location: "Tỉnh Đồng Nai" },
    { title: "TUYỂN DỤNG NHÂN VIÊN PHÒNG KINH DOANH", location: "Tỉnh Hà Nam" },
    { title: "External Relations 2", location: "Tỉnh Đồng Nai" },
    { title: "Assistant Brand Manager 2", location: "Tỉnh Đồng Nai" },
    { title: "Account Manager", location: "Tỉnh Hà Nam" },
    { title: "Marketing Specialist", location: "Tỉnh Tây Ninh" },
    { title: "Production Supervisor", location: "Tỉnh Đồng Nai" },
    { title: "Quality Control Inspector", location: "Tỉnh Tây Ninh" },
    { title: "Logistics Coordinator", location: "Tỉnh Hà Nam" },
    { title: "THÔNG BÁO TUYỂN DỤNG - VINAFARM", location: "Tỉnh Tây Ninh" },
    { title: "Tuyển Dụng Nhân Viên Vận Hành Máy, Công Nhân Sản Xuất", location: "Tỉnh Đồng Nai" },
    { title: "TUYỂN DỤNG NHÂN VIÊN PHÒNG KINH DOANH", location: "Tỉnh Hà Nam" },
    { title: "External Relations 2", location: "Tỉnh Đồng Nai" },
    { title: "Assistant Brand Manager 2", location: "Tỉnh Đồng Nai" },
    { title: "Account Manager", location: "Tỉnh Hà Nam" },
    { title: "Marketing Specialist", location: "Tỉnh Tây Ninh" },
    { title: "Production Supervisor", location: "Tỉnh Đồng Nai" },
    { title: "Quality Control Inspector", location: "Tỉnh Tây Ninh" },
    { title: "Logistics Coordinator", location: "Tỉnh Hà Nam" }
];

let currentPage = 1;
const itemsPerPage = 5;

function renderJobs(jobsToRender) {
    const jobList = document.getElementById('jobList');
    jobList.innerHTML = '';
    jobsToRender.forEach(job => {
        const row = document.createElement('tr');
        row.innerHTML = `
                <td>${job.title}</td>
                <td>${job.location}</td>
                <td><button class="detail-button">Xem chi tiết</button></td>
            `;
        jobList.appendChild(row);
    });
}

function renderPagination(totalItems) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    const totalPages = Math.ceil(totalItems / itemsPerPage);
    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.className = (i === currentPage) ? 'disabled' : '';
        button.onclick = () => changePage(i);
        pagination.appendChild(button);
    }
}

function changePage(page) {
    currentPage = page;
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const filteredJobs = getFilteredJobs();
    const paginatedJobs = filteredJobs.slice(startIndex, endIndex);
    renderJobs(paginatedJobs);
    renderPagination(filteredJobs.length);
}

function getFilteredJobs() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const locationFilter = document.getElementById('locationFilter').value;

    return jobs.filter(job => {
        const matchesSearch = job.title.toLowerCase().includes(searchInput);
        const matchesLocation = !locationFilter || job.location === locationFilter;
        return matchesSearch && matchesLocation;
    });
}

function searchJobs() {
    currentPage = 1; // Reset to first page on search
    const filteredJobs = getFilteredJobs();
    const paginatedJobs = filteredJobs.slice(0, itemsPerPage);
    renderJobs(paginatedJobs);
    renderPagination(filteredJobs.length);
}

// Initial render
searchJobs();
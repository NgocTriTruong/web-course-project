function searchJobs() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const locationFilter = document.getElementById('locationFilter').value;
    const jobList = document.getElementById('jobList');
    const rows = jobList.getElementsByTagName('tr');

    for (let i = 0; i < rows.length; i++) {
        const position = rows[i].getElementsByTagName('td')[0].textContent.toLowerCase();
        const location = rows[i].getElementsByTagName('td')[1].textContent;
        const matchesSearch = position.includes(searchInput);
        const matchesLocation = !locationFilter || location === locationFilter;

        if (matchesSearch && matchesLocation) {
            rows[i].style.display = '';
        } else {
            rows[i].style.display = 'none';
        }
    }
}
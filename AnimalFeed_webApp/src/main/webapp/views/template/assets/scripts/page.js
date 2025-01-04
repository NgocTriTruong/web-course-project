    // Cuộn đến vị trí phần tử có ID "pagination" sau khi tải trang
    window.onload = function () {
    const paginationElement = document.getElementById('pagination');
    if (paginationElement) {
    paginationElement.scrollIntoView({ behavior: 'auto' });
}
};

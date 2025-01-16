function showMessConfirm(id) {
    var option = confirm("Bạn có chắc chắn muốn xóa?");
    if(option === true) {
        window.location.href = "delete-product?productId=" + id;
    }
}
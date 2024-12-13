document.getElementById('login-form').addEventListener('submit', function (event){
    event.preventDefault();

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    //Kiem tra username va pass co dung hay khong?
    if (username === 'admin' && password === 'admin') {
        window.location.replace('home_admin.html');
    }else{
        alert("Tai khoan hoac mat khau da sai. Vui long kiem tra lai !")
    }
})
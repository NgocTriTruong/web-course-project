const listImage = document.querySelector('.list_images')
const imgs = document.getElementsByTagName('img')
let i = 0

setInterval(() => {
    if(i === imgs.length - 1){
        i = 0
        listImage.style.transform = `translateX(0px)`
    }else {
        i++
        let width = imgs[0].offsetWidth
        listImage.style.transform = `translateX(-${width * i}px)`
    }
}, 4000)
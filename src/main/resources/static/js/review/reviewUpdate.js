const starBox = document.querySelectorAll(".star-box");
const rating = document.querySelector('[name="rating"]');


starBox.forEach((star, idx) => {
    star.addEventListener("click", () => {
        rating.value = idx+1;
    });
});






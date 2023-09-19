document.addEventListener("DOMContentLoaded", () => {
    add();
});

const add = () => {
    document.querySelector(".add-fuel-btn")
        .addEventListener("click", () => {
            window.location.replace("/fuels/create")
        })
    document.querySelector(".btn-bck")
        .addEventListener("click", () => {
            window.location.replace("/fuels");
        })
}
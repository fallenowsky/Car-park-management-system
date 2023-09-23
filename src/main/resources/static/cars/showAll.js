document.addEventListener("DOMContentLoaded", () => {
    watchBtns();
})

const watchBtns = () => {
    if (document.querySelector("thead")
        .childElementCount > 0) {
        document.querySelectorAll(".add-to-garage-btn")
            .forEach(btn => {
                btn.addEventListener("click", ev => {
                    window.location.replace(`/cars/add-car?carId=${ev.target.dataset.id}`);
                })
            })
    }
    document.querySelector(".add-car-btn")
        .addEventListener("click", () => {
            window.location.replace(`/cars/add`);
        })
}


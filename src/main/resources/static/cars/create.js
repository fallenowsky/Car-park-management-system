
document.addEventListener("DOMContentLoaded", () => {
    watchBtns();
    validateFuelSelected();
})

const validateFuelSelected = () => {
    document.getElementById("form")
        .addEventListener("submit", ev => {
            ev.preventDefault();

            const fuelId = document.getElementById("fuelId").value;

            if (isNaN(fuelId)) {
                window.alert("Fuel not selected! Select a fuel first!");
                return;
            }

            const brand = document.getElementById("brand").value;
            const width = parseFloat(document.getElementById("width").value);
            const price = parseFloat(document.getElementById("price").value);

            fetch(`/cars/add?fuelId=${fuelId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    brand: brand,
                    width: width,
                    price: price
                })
            }).then(res => {
                if (res.ok) {
                    window.location.replace("/cars");
                }
            })
        });
}

const watchBtns = () => {
    document.querySelector(".btn-bck")
        .addEventListener("click", () => {
            window.location.replace(`/cars`);
        })
}
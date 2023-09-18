
document.addEventListener("DOMContentLoaded", () => {
    sendForm();
    watchBtn();
})

const sendForm = () => {
    document.getElementById("form")
        .addEventListener("submit", ev => {
            ev.preventDefault();

            const width = parseFloat(document.getElementById("width").value);
            const garageId = parseFloat(document.getElementById("garageId").value);
            const fuelId = parseFloat(document.getElementById("fuelId").value);
            const price = parseFloat(document.getElementById("price").value);
            const brand = document.getElementById("brand").value;

            fetch(`/cars/create?garageId=${garageId}&fuelId=${fuelId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    width: width,
                    brand: brand,
                    price: price
                })
            })
                .then(res => {
                    if (!res.ok) {
                        return res.json();
                    }
                    window.location.replace("/garages");
                })
                .then(body => {
                    if (body)
                        window.alert(body.message);
                })
                .catch(err => {
                    throw new Error(err);
                })
        })
}

const watchBtn = () => {
    document.querySelector(".btn-bck")
        .addEventListener("click", () => {
            window.history.back();
        })
}
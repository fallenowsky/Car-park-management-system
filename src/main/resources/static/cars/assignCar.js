document.addEventListener("DOMContentLoaded", () => {
    sendForm();
    watchBtn();
})

const sendForm = () => {
    document.getElementById("form")
        .addEventListener("submit", ev => {
            ev.preventDefault();

            const garageId = parseFloat(document.getElementById("garageId").value);
            const carId = parseFloat(document.getElementById("carId").value);

            fetch(`/garages/add-car?garageId=${garageId}&carId=${carId}`)
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
            window.location.replace("/cars");
        })
}
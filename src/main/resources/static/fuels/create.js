document.addEventListener("DOMContentLoaded", () => {
    addBtn();
    handleException();
});

const addBtn = () => {
    document.querySelector(".btn-bck")
        .addEventListener("click", () => {
            window.location.replace("/fuels");
        })
}

const handleException = () => {
    const errMessage = document.querySelector("input").value;

    if (errMessage) {
        window.alert(errMessage);
    }
}

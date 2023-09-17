document.addEventListener("DOMContentLoaded", () => {
    watchBtns();
})

const watchBtns = () => {
    document.querySelector(".add-garage-btn")
        .addEventListener("click", () => {
            document.location.replace("/garages/addForm")
        })

    document.querySelectorAll(".add-car-btn")
        .forEach(btn => {
            btn.addEventListener("click", ev => {
                document.location.replace(`/cars/addForm?garageId=${ev.target.dataset.id}`);
            })
        })
    document.querySelectorAll(".show-cars-btn")
        .forEach(btn => {
            btn.addEventListener("click", ev => {
                showGarageCars(ev.target.dataset.id);
            })
        })
}

const showGarageCars = async id => {
    const ul = document.querySelector(".redirections");
    const nextElement = ul.nextElementSibling;
    nextElement !== null ? nextElement.innerHTML = '' : '';

    const addressRes = await fetch(`/address/get?garageId=${id}`);
    const address = await addressRes.json();
    if (!addressRes.ok) {
        window.alert(addressRes.message);
    }

    const carsRes = await fetch(`/cars/by-garage-id?garageId=${id}`);
    const cars = await carsRes.json();
    if (!carsRes.ok) {
        window.alert(carsRes.message);
    } else if (cars.length === 0) {
        window.alert("This garage has no cars!");
        return;
    }


    const html = `
                <div>
                  <h3>List of cars of garage name ${address.name}:</h3>
                      <table>
                        <thead>
                             <tr>
                                <th>No.</th>
                                <th>Car brand:</th>
                                <th>Car width:</th>
                                <th>Car fuel:</th>
                                <th>Car price:</th>
                             </tr>
                        </thead>
                        <tbody class=carsTable>
                        </tbody
                      </table>
                </div>`
    ul.insertAdjacentHTML('afterend', html);

    const tBody = document.querySelector(".carsTable");
    let i = 1;

    cars.forEach(car => {
        const html = `<row>
                            <td class="data">${i++}.</td>
                            <td class="data">${car.brand}</td>
                            <td class="data">${car.width}</td>
                            <td class="data">${car.fuelName}</td>
                            <td class="data">${(car.price)}</td>
                          </row>`
        tBody.insertAdjacentHTML('beforeend', html);
    })


    const btnHide = `<button class="hideArchived">Hide</button>`
    tBody.parentElement.insertAdjacentHTML('afterend', btnHide);
    document.querySelector(".hideArchived")
        .addEventListener("click", ev => {
            tBody.closest("div").remove();
            ev.target.style.display = 'none';
        })
}


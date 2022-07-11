window.addEventListener("load",function(){

        //con fetch hacemos una petición a la api de odontólogos, con el método GET. Nos devolverá un JSON con una colección de odontólogos.
        const url = "/odontologos/todos";
        const settings={
            method:"GET"
        };

        fetch(url,settings)
        .then(response => response.json())
        .then(data =>{
            //Recorremos la colección de odontólogos del JSON, por cada odontólogo se genera una fila en la tabla.
                console.log(data);
                let fila = document.querySelector("#filas");
                for (let i = 0; i < data.length; i++) {
                    let contenido = `
                        <tr>
                            <td>${data[i].id}</td>
                            <td>${data[i].nombre}</td>
                            <td>${data[i].apellido}</td>
                        </tr>`;
                        fila.innerHTML += contenido;

                }
                
        })
    });

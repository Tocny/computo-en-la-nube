
// Variable que almacena el auto que se está editando.
let autoEnEdicion = null;

/**
 * Carga la lista de autos desde /listar y los muestra en la tabla HTML.
 */
function cargarAutos() {
    fetch('/listar')
        .then(response => response.json())
        .then(autos => {
            const tbody = document.getElementById('tablaAutos');
            tbody.innerHTML = ''; // Limpia el cuerpo de la tabla

            autos.forEach(auto => {
                const row = tbody.insertRow();
                row.className = 'hover:bg-gray-50 transition';

                // Columna ID
                let cell = row.insertCell(0);
                cell.className = 'px-4 py-2 text-sm text-gray-700';
                cell.innerText = auto.id;

                // Columna Marca
                cell = row.insertCell(1);
                cell.className = 'px-4 py-2 text-sm font-medium text-gray-900';
                cell.innerText = auto.marca;

                // Columna Modelo
                cell = row.insertCell(2);
                cell.className = 'px-4 py-2 text-sm text-gray-700';
                cell.innerText = auto.modelo;

                // Columna Año
                cell = row.insertCell(3);
                cell.className = 'px-4 py-2 text-sm text-gray-700';
                cell.innerText = auto.anio;

                // Columna Precio (formato moneda)
                cell = row.insertCell(4);
                cell.className = 'px-4 py-2 text-sm font-mono text-gray-800';
                cell.innerText = '$' + auto.precio.toLocaleString('es-MX', { minimumFractionDigits: 2 });

                // Columna de acciones (botones Editar / Eliminar)
                const cellAcciones = row.insertCell(5);
                cellAcciones.className = 'px-4 py-2 whitespace-nowrap';

                const btnEditar = document.createElement('button');
                btnEditar.innerText = 'Editar';
                btnEditar.className = 'bg-amber-500 hover:bg-amber-600 text-white text-xs font-medium py-1 px-3 rounded-full mr-2 transition';
                btnEditar.onclick = () => abrirModalEditar(auto);

                const btnEliminar = document.createElement('button');
                btnEliminar.innerText = 'Eliminar';
                btnEliminar.className = 'bg-red-500 hover:bg-red-600 text-white text-xs font-medium py-1 px-3 rounded-full transition';
                btnEliminar.onclick = () => eliminarAuto(auto.id);

                cellAcciones.appendChild(btnEditar);
                cellAcciones.appendChild(btnEliminar);
            });
        })
        .catch(err => console.error('Error al cargar autos:', err));
}

/**
 * Abre el modal de edición y carga los datos del auto seleccionado.
 * @param {Object} auto - Auto a editar.
 */
function abrirModalEditar(auto) {
    autoEnEdicion = auto;
    document.getElementById('editId').value = auto.id;
    document.getElementById('editMarca').value = auto.marca;
    document.getElementById('editModelo').value = auto.modelo;
    document.getElementById('editAnio').value = auto.anio;
    document.getElementById('editPrecio').value = auto.precio;
    document.getElementById('modalEditar').classList.remove('hidden');
}

/**
 * Cierra el modal de edición y limpia la variable.
 */
function cerrarModal() {
    document.getElementById('modalEditar').classList.add('hidden');
    autoEnEdicion = null;
}

/**
 * Envía los cambios del modal al servidor mediante PUT /actualizar/{id}.
 */
function guardarEdicion() {
    if (!autoEnEdicion) return;

    const id = autoEnEdicion.id;
    const autoActualizado = {
        marca: document.getElementById('editMarca').value.trim(),
        modelo: document.getElementById('editModelo').value.trim(),
        anio: document.getElementById('editAnio').value.trim(),
        precio: parseFloat(document.getElementById('editPrecio').value)
    };

    if (!autoActualizado.marca || !autoActualizado.modelo || !autoActualizado.anio || isNaN(autoActualizado.precio)) {
        alert('Por favor completa todos los campos correctamente.');
        return;
    }

    fetch(`/actualizar/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(autoActualizado)
    })
        .then(res => {
            if (res.ok) {
                cerrarModal();
                cargarAutos(); // Refresca la tabla
            } else {
                alert('Error al actualizar el auto');
            }
        })
        .catch(err => alert('Error de red: ' + err.message));
}

/**
 * Crea un nuevo auto con los datos del formulario principal (POST /crear).
 */
function crearAuto() {
    const marca = document.getElementById('marca').value.trim();
    const modelo = document.getElementById('modelo').value.trim();
    const anio = document.getElementById('anio').value.trim();
    const precio = parseFloat(document.getElementById('precio').value);

    if (!marca || !modelo || !anio || isNaN(precio)) {
        alert('Completa todos los campos del nuevo auto.');
        return;
    }

    const nuevoAuto = { marca, modelo, anio, precio };

    fetch('/crear', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(nuevoAuto)
    })
        .then(response => {
            if (!response.ok) throw new Error('Error al crear');
            return response.json();
        })
        .then(() => {
            cargarAutos();
            // Limpiar formulario
            document.getElementById('marca').value = '';
            document.getElementById('modelo').value = '';
            document.getElementById('anio').value = '';
            document.getElementById('precio').value = '';
        })
        .catch(err => alert('No se pudo agregar: ' + err.message));
}

/**
 * Elimina un auto por su ID, previa confirmación (DELETE /eliminar/{id}).
 * @param {number|string} id - Identificador del auto.
 */
function eliminarAuto(id) {
    if (confirm(`¿Eliminar el auto con ID ${id}?`)) {
        fetch(`/eliminar/${id}`, { method: 'DELETE' })
            .then(res => {
                if (res.ok) cargarAutos();
                else alert('Error al eliminar');
            })
            .catch(err => alert('Error de red'));
    }
}

// Inicialización: al cargar la página se obtiene la lista de autos.
cargarAutos();
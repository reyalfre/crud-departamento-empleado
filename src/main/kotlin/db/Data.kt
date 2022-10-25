package db

import model.Departamento
import model.Empleado
import java.time.LocalDate
import java.util.*

fun getDepartamentosInit() = listOf(
    Departamento(
        nombre = "lengua",
        presupuesto = 50.00

    ),
    Departamento(
        nombre="historia",
        presupuesto = 60.00
    )
)
fun getEmpleadosInit()= listOf(
    Empleado(
        UUID.randomUUID(),
        nombre = "manolo",
        fecha = LocalDate.parse("1981-03-01")
    ),
    Empleado(
        UUID.randomUUID(),
        nombre = "Teofilo",
        fecha = LocalDate.parse("1983-01-20")
    )
)

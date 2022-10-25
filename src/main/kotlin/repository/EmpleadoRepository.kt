package repository

import model.Departamento
import model.Empleado
import java.util.*

interface EmpleadoRepository: CrudRepository<Empleado, UUID>{
}
package repository

import model.Departamento
import java.util.UUID

interface DepartamentoRepository: CrudRepository<Departamento, UUID>{
}
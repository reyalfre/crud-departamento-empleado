package controller

import model.Empleado
import mu.KotlinLogging
import repository.EmpleadoRepository
import java.util.*

private val logger = KotlinLogging.logger {}
class EmpleadoController(private val empleadoRepository: EmpleadoRepository) {
    fun getEmpleados(): List<Empleado> {
        logger.info("Obteniendo Empleados")
        return empleadoRepository.findAll()
    }

    fun createEmpleado(empleado: Empleado): Empleado {
        logger.debug { "Creando empleado $empleado" }
        empleadoRepository.save(empleado)
        return empleado
    }

    fun getEmpleadoById(uuid: UUID): Empleado? {
        logger.debug { "Obteniendo empleado con uuid $uuid" }
        return empleadoRepository.findById(uuid)
    }

    fun updateEmpleado(empleado: Empleado) {
        logger.debug { "Actualizando empleado $empleado" }
        empleadoRepository.save(empleado)
    }

    fun deleteEmpleado(it: Empleado): Boolean {
        logger.debug { "Borrando empleado $it" }
        return empleadoRepository.delete(it)
    }
}
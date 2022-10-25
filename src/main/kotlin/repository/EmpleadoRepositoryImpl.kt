package repository

import db.DataBaseManager
import model.Empleado
import mu.KotlinLogging
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {}

class EmpleadoRepositoryImpl : EmpleadoRepository {
    override fun findAll(): List<Empleado> {
        val query = "SELECT * FROM empleado"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val empleados = mutableListOf<Empleado>()
        result?.let {
            while (result.next()) {
                val empleado = Empleado(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    fecha = LocalDate.parse(it.getObject("fecha").toString())
                )
                empleados.add(empleado)
            }
        }

        DataBaseManager.close()
        logger.debug { "Empleados encontrados: ${empleados.size}" }
        return empleados.toList()
    }

    override fun findById(id: UUID): Empleado? {
        val query = "SELECT * FROM empleado WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var empleado: Empleado? = null
        result?.let {
            if (result.next()) {
                empleado = Empleado(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    fecha = LocalDate.parse(it.getObject("fecha").toString())
                )
            }
        }
        DataBaseManager.close()
        logger.debug { "Empleado encontrado con $id: $empleado" }
        return empleado
    }

    override fun save(entity: Empleado): Empleado {
        val empleado = findById(entity.uuid)
        empleado?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    private fun insert(empleado: Empleado): Empleado {
        val query = """INSERT INTO empleado 
            (uuid, nombre, fecha) 
            VALUES (?, ?, ?)"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.insert(
            query, empleado.uuid, empleado.nombre, empleado.fecha
        )
        DataBaseManager.close()
        logger.debug { "Empleado insertado: $empleado - Resultado: ${result == 1}" }
        return empleado
    }

    private fun update(empleado: Empleado): Empleado {
        val query = """UPDATE empleado 
            SET nombre = ?, fecha = ? 
            WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(
            query, empleado.nombre, empleado.fecha, empleado.uuid
        )
        DataBaseManager.close()
        logger.debug { "Empleado actualizado: $empleado - Resultado: ${result == 1}" }
        return empleado
    }

    override fun delete(entity: Empleado): Boolean {
        val query = "DELETE FROM empleado WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Empleado eliminado: $entity - Resultado: ${result == 1}" }
        return result == 1
    }

}
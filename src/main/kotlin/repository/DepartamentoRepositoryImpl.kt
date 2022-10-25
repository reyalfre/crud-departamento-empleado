package repository

import db.DataBaseManager
import model.Departamento
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class DepartamentoRepositoryImpl : DepartamentoRepository {
    override fun findAll(): List<Departamento> {
        val query = "SELECT * FROM departamento"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val departamentos = mutableListOf<Departamento>()
        result?.let {
            while (result.next()) {
                val departamento = Departamento(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    presupuesto = it.getDouble("presupuesto")
                )
                departamentos.add(departamento)
            }
        }

        DataBaseManager.close()

        logger.debug { "Departamentos encontrados: ${departamentos.size}" }
        return departamentos.toList()
    }

    override fun findById(id: UUID): Departamento? {
        val query = "SELECT * FROM departamento WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var departamento: Departamento? = null
        result?.let {
            if (result.next()) {
                departamento = Departamento(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    presupuesto = it.getDouble("ganancias")
                )
            }
        }
        DataBaseManager.close()
        logger.debug { "Departamento encontrado con $id: $departamento" }
        return departamento
    }

    override fun save(entity: Departamento): Departamento {
        val departamento = findById(entity.uuid)
        departamento?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    private fun insert(departamento: Departamento): Departamento {
        val query = """INSERT INTO departamento
            (uuid, nombre, presupuesto) 
            VALUES (?, ?, ?)"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.insert(
            query, departamento.uuid, departamento.nombre, departamento.presupuesto
        )
        DataBaseManager.close()
        logger.debug { "Departamento insertado: $departamento - Resultado: ${result == 1}" }
        return departamento
    }

    private fun update(departamento: Departamento): Departamento {
        val query = """UPDATE departamento 
            SET nombre = ?, presupuesto = ?
            WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(
            query, departamento.nombre, departamento.presupuesto, departamento.uuid
        )
        DataBaseManager.close()
        logger.debug { "Departamento actualizado: $departamento - Resultado: ${result == 1}" }
        return departamento
    }

    override fun delete(entity: Departamento): Boolean {
        val query = "DELETE FROM departamento WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Departamento eliminado: $entity - Resultado: ${result == 1}" }
        return result == 1
    }
}
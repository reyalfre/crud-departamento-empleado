package controller

import model.Departamento
import mu.KotlinLogging
import repository.DepartamentoRepository
import java.util.*

private val logger = KotlinLogging.logger {}

class DepartamentoController(private val departamentoRepository: DepartamentoRepository){

    fun getDepartamentos(): List<Departamento> {
        logger.info("Obteniendo Departamentos")
        return departamentoRepository.findAll()
    }

    fun createDepartamento(departamento: Departamento): Departamento {
        logger.debug { "Creando departamento $departamento" }
        departamentoRepository.save(departamento)
        return departamento
    }

    fun getTenistaById(uuid: UUID): Departamento? {
        logger.debug { "Obteniendo tenista con uuid $uuid" }
        return departamentoRepository.findById(uuid)
    }

    fun updateTenista(departamento: Departamento) {
        logger.debug { "Actualizando departamento $departamento" }
        departamentoRepository.save(departamento)
    }

    fun deleteTenista(it: Departamento): Boolean {
        logger.debug { "Borrando departamento $it" }
        return departamentoRepository.delete(it)
    }


}

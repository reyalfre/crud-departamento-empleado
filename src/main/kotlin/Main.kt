import controller.DepartamentoController
import controller.EmpleadoController
import db.DataBaseManager
import db.DataBaseManager.createTables
import db.createTables
import db.getDepartamentosInit
import db.getEmpleadosInit
import repository.DepartamentoRepositoryImpl
import repository.EmpleadoRepository
import repository.EmpleadoRepositoryImpl

fun main(args: Array<String>) {
    initDataBase()

    val departamentoController = DepartamentoController(DepartamentoRepositoryImpl())
    val empleadoController = EmpleadoController(EmpleadoRepositoryImpl())

    getDepartamentosInit().forEach { departamento -> departamentoController.createDepartamento(departamento) }
    getEmpleadosInit().forEach { empleado -> empleadoController.createEmpleado(empleado) }



var departamento=departamentoController.getDepartamentos().sortedBy { it.nombre }
    departamento.forEach { departamento -> println(departamento)  }

var empleado=empleadoController.getEmpleados().sortedBy { it.nombre }
    empleado.forEach { empleado -> println(empleado) }
}
fun initDataBase(){
    DataBaseManager.open()
    DataBaseManager.createTables(createTables())
    DataBaseManager.close()
}









//fun deleteRaquetas(raqueta:Raqueta): Boolean{
//
//val tenista= raquetasRepository.cuantosTenistas(raqueta)

//check(tenistas=0)
// }



//val query = "UPDATE tenistas SET raqueta_uuid = NULL WHERE raqueta_uuid = ?"


import controller.DepartamentoController
import db.DataBaseManager
import db.DataBaseManager.createTables
import db.createTables
import db.getDepartamentosInit
import repository.DepartamentoRepositoryImpl

fun main(args: Array<String>) {
    initDataBase()

    val controller = DepartamentoController(DepartamentoRepositoryImpl())

    getDepartamentosInit().forEach { departamento -> controller.createDepartamento(departamento) }



var departamento=controller.getDepartamentos().sortedBy { it.nombre }
    departamento.forEach { departamento -> println(departamento)  }
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


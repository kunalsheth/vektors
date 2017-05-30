import kotlinx.coroutines.experimental.CommonPool

/**
 * Created by the-magical-llamicorn on 5/29/17.
 */
object VektorConfig {

    var calculator = CyclicCalculator()
        set(newCalculator) {
            println("VektorConfig.calculator has been changed from $calculator to $newCalculator.")
            field = newCalculator
        }

    var context = CommonPool
        set(newContext) {
            println("VektorConfig.context has been changed from $calculator to $newContext.")
            field = newContext
        }
}
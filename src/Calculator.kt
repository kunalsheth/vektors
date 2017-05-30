/**
 * Created by the-magical-llamicorn on 5/28/17.
 */
interface Calculator {

    enum class Operations { ADD, SUBTRACT, MULTIPLY, DIVIDE, POWER, ROOT }

    fun execute(v1: FloatArray, operation: Operations, v2: FloatArray): FloatArray
    fun execute(v: FloatArray, operation: Operations, scalar: Float): FloatArray
}

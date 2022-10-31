package sumif

import sumif.utils.SparseMatrix

/**
 * Sum the elements of a sparse matrix satisfying a function of the indices
 *
 * @param sumMatrix matrix whose values to sum
 * @param conditionFunc condition on the indices
 * @receiver
 * @return sum of the elements whose indices satisfy [conditionFunc]
 */
fun sumIfBy(sumMatrix: SparseMatrix<Double>, conditionFunc: (Int, Int) -> Boolean): Double {
    return sumMatrix.mainMatrix.map {
        if (conditionFunc(it.key.first, it.key.second)) {
            it.value
        } else {
            sumMatrix.tZero
        }
    }.sumOf { (it ?: 0.0) }
}

/**
 * Sum the elements of a sparse matrix [sumMatrix] with indices of the elements of [conditionMatrix] satisfying a
 * function. Equivalent to Excel SUMIF function.
 *
 * @param sumMatrix matrix whose elements are summed up
 * @param conditionMatrix matrix whose elements satisfying [conditionFunc] dictate which elements of [sumMatrix]
 * are summed up
 * @param conditionFunc condition on the elements of [conditionMatrix]
 * @receiver
 * @return
 */
fun sumIf(
    sumMatrix: SparseMatrix<Double>,
    conditionMatrix: SparseMatrix<Double>,
    conditionFunc: (Double) -> Boolean
): Double {
    return sumMatrix.mainMatrix.filter { i ->
        conditionMatrix.mainMatrix.filter { j ->
            conditionFunc(j.value ?: 0.0)
        }.keys.contains(i.key)
    }.values.sumOf { (it ?: 0.0) }
}
package sumif

import org.junit.jupiter.api.Test
import org.apache.poi.ss.usermodel.WorkbookFactory
import sumif.utils.SparseMatrix

internal class SumIfKtTest {
    private fun rangeToSparseMatrix(startCell: Pair<Int, Int>, endCell: Pair<Int, Int>): SparseMatrix<Double> {
        val retMatrix = SparseMatrix(tZero = 0.0)
        val inputStream = object {}.javaClass.getResourceAsStream("/SumIfTest.xlsx")
        val xlWb = WorkbookFactory.create(inputStream)
        val xlWs = xlWb.getSheet("Sheet1")

        for (i in startCell.first..endCell.first) {
            for (j in startCell.second..endCell.second) {
                if (xlWs.getRow(i).getCell(j).numericCellValue > 0)
                    retMatrix[i - startCell.first, j - startCell.second] = xlWs.getRow(i).getCell(j).numericCellValue
            }
        }

        inputStream?.close()

        return retMatrix
    }

    @Test
    fun testSumIfBy() {
        val maxTol = 1e-7

        val inputStream = object {}.javaClass.getResourceAsStream("/SumIfTest.xlsx")
        val xlWb = WorkbookFactory.create(inputStream)
        val xlWs = xlWb.getSheet("Sheet1")

        val sumMatrix = rangeToSparseMatrix(1 to 3, 11 to 13)
        val sumCond = xlWs.getRow(27).getCell(0).numericCellValue

        kotlin.test.assertEquals(
            sumCond,
            sumIfBy(sumMatrix) { i, j ->
                i + j < 5
            },
            maxTol
        )

        inputStream?.close()
    }

    @Test
    fun testSumIf() {
        val maxTol = 1e-7

        val inputStream = object {}.javaClass.getResourceAsStream("/SumIfTest.xlsx")
        val xlWb = WorkbookFactory.create(inputStream)
        val xlWs = xlWb.getSheet("Sheet1")

        val sumMatrix = rangeToSparseMatrix(1 to 3, 11 to 13)
        val conditionMatrix = rangeToSparseMatrix(14 to 16, 24 to 26)
        val sumCond = xlWs.getRow(30).getCell(0).numericCellValue

        kotlin.test.assertEquals(
            sumCond,
            sumIf(sumMatrix, conditionMatrix) { i ->
                i < 5
            },
            maxTol
        )

        inputStream?.close()
    }
}
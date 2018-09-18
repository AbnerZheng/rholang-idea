package coop.rchain.ide.folding

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.intellij.util.containers.ContainerUtil
import coop.rchain.RholangTestUtil


class RholangCodeInsightTest : LightCodeInsightFixtureTestCase() {
  override fun getTestDataPath(): String {
    return RholangTestUtil.baseTestDataPath
  }

  fun testFolding() {
    doTestFolding("procedure")
  }

  private fun doFormatterTest(testName: String) {
    object : WriteCommandAction.Simple<Any>(project) {
      @Throws(Throwable::class)
      override fun run() {
        myFixture.configureByFiles("./ide/formatter/$testName.rho")
        CodeStyleManager.getInstance(project).reformatText(myFixture.file,
          ContainerUtil.newArrayList<TextRange>(myFixture.file.textRange))
      }
    }.execute()
    myFixture.checkResultByFile("./ide/formatter/${testName}Formatted.rho")
  }

  fun testComplex() {
    doFormatterTest("complex")
  }

  fun testUri() {
    doFormatterTest("URI")
  }

  fun testIfElse() {
    doFormatterTest("ifElse")
  }

  fun testMapMethod(){
    doFormatterTest("mapMethods")
  }

  fun testParens(){
    doFormatterTest("parens")
  }

  private fun doTestFolding(testName: String) {
    myFixture.testFolding("$testDataPath/ide/folding/$testName.rho")
  }

}

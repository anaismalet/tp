import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("isClimateRelated - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
  }

  test("isClimateRelated - my test climate related words should return true") {
    assert(ClimateService.isClimateRelated("global warming") == true)
  }


  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to access 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  //@TODO
  test("filterDecemberData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2) //help: to access 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 12, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our input of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val input = List(co2RecordWithType,co2RecordWithType2)

    // our output
    val output = List(co2RecordWithType).map(Some(_))

    assert(ClimateService.filterDecemberData(input) == output)
  }

  test("findMinMax") {
    // Input list
    val input = List(
      CO2Record(2003, 1, 310),
      CO2Record(2004, 12, 375.2),
      CO2Record(2004, 12, 400)
    )

    // Expected output
    val expectedOutput = Some((310.0, 400.0))

    // Check if output matches expected output
    assert(ClimateService.getMinMax(input) == expectedOutput)
  }

  test("getMinMaxByYear") {
    val input = List(
      CO2Record(2003, 1, 310.0),
      CO2Record(2004, 12, 375.2),
      CO2Record(2004, 5, 350.0),
      CO2Record(2005, 3, 390.2)
    )
    val year = 2004

    val expectedOutput = Some((350.0, 375.2))
    // Check if output matches expected output
    assert(ClimateService.getMinMaxByYear(input, year) == expectedOutput)
  }

}

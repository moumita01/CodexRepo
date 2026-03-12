import java.util.Properties
import java.io.InputStream
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

/*
 * Boomi Data Process (Groovy) script
 *
 * Expects two Dynamic Document Properties on each incoming document:
 *  - userdefined.intA
 *  - userdefined.intB
 *
 * Writes the sum into:
 *  - userdefined.sum
 *
 * Also replaces the document body with a small JSON payload:
 *  {"intA":1,"intB":2,"sum":3}
 */
for (int i = 0; i < dataContext.getDataCount(); i++) {
    InputStream is = dataContext.getStream(i)
    Properties props = dataContext.getProperties(i)

    int intA = (props.getProperty("document.dynamic.userdefined.intA") ?: "0") as int
    int intB = (props.getProperty("document.dynamic.userdefined.intB") ?: "0") as int
    int sum = intA + intB

    props.setProperty("document.dynamic.userdefined.sum", String.valueOf(sum))

    String json = "{\"intA\":${intA},\"intB\":${intB},\"sum\":${sum}}"
    InputStream out = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))

    dataContext.storeStream(out, props)
}

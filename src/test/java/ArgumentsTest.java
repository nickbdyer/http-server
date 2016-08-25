import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentsTest {

    @Test
    public void argumentsCanParseAPortNumber() {
        String[] arguments = new String[]{"-p", "5000"};

        int port = Arguments.getPort(arguments);

        assertEquals(5000, port);
    }

}

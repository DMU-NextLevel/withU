package NextLevel.demo.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void getFormattedNumberTest() {
        String number = "01011111111";

        String formattedNumber = StringUtil.getFormattedNumber(number, StringUtil.PHONE_NUMBER_FORMAT);

        assertThat(formattedNumber).isEqualTo("010-1111-1111");
    }
}

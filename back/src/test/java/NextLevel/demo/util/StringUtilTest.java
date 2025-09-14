package NextLevel.demo.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void getFormattedNumberTest() {
        String number = "01011111111";

        String formattedNumber = StringUtil.getFormattedNumber(number, StringUtil.PHONE_NUMBER_FORMAT);

        assertThat(formattedNumber).isEqualTo("010-1111-1111");
    }

    // StringUtil.setGetterName(dto.getName()), String.class :: UserService :: update UserInfo
    @Test
    public void getSetterNameTest() {
        String field1 = "name";
        String field2 = "is";
        String field3 = "role";
        String field4 = "areaNumber";

        String setterMethod1 = StringUtil.setGetterName(field1);
        String setterMethod2 = StringUtil.setGetterName(field2);
        String setterMethod3 = StringUtil.setGetterName(field3);
        String setterMethod4 = StringUtil.setGetterName(field4);

        Assertions.assertAll(
                ()->Assertions.assertEquals("setName", setterMethod1),
                ()->Assertions.assertEquals("setIs", setterMethod2),
                ()->Assertions.assertEquals("setRole", setterMethod3),
                ()->Assertions.assertEquals("setAreaNumber", setterMethod4)
        );
    }
}

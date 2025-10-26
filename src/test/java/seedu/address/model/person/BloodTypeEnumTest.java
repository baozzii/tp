package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BloodTypeEnumTest {
    @Test
    public void isValidBloodType() {
        // null blood type
        assertThrows(NullPointerException.class, () -> BloodTypeEnum.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodTypeEnum.isValidBloodType("")); // empty string
        assertFalse(BloodTypeEnum.isValidBloodType(" ")); // spaces only
        assertFalse(BloodTypeEnum.isValidBloodType("B^"));
        assertFalse(BloodTypeEnum.isValidBloodType("A+*"));
        assertFalse(BloodTypeEnum.isValidBloodType("O++"));

        // valid blood type
        assertTrue(BloodTypeEnum.isValidBloodType("A+"));
        assertTrue(BloodTypeEnum.isValidBloodType("O-"));
        assertTrue(BloodTypeEnum.isValidBloodType("AB-"));
        assertTrue(BloodTypeEnum.isValidBloodType("B+"));
        assertTrue(BloodTypeEnum.isValidBloodType("B-"));
    }
}

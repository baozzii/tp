package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrganTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Organ(null));
    }

    @Test
    public void constructor_invalidOrgan_throwsIllegalArgumentException() {
        String invalidOrgan = "";
        assertThrows(IllegalArgumentException.class, () -> new Organ(invalidOrgan));
    }

    @Test
    public void isValidOrgan() {
        // null organ
        assertThrows(NullPointerException.class, () -> Organ.isValidOrgan(null));

        // invalid organs
        assertFalse(Organ.isValidOrgan("")); // empty string
        assertFalse(Organ.isValidOrgan(" ")); // spaces only

        // valid organs
        assertTrue(Organ.isValidOrgan("kidney"));
        assertTrue(Organ.isValidOrgan("l")); // one character
        assertTrue(Organ.isValidOrgan("mitochondria")); // long organ
    }

    @Test
    public void equals() {
        Organ organ = new Organ("Valid Organ");

        // same values -> returns true
        assertTrue(organ.equals(new Organ("Valid Organ")));

        // same object -> returns true
        assertTrue(organ.equals(organ));

        // null -> returns false
        assertFalse(organ.equals(null));

        // different types -> returns false
        assertFalse(organ.equals(5.0f));

        // different values -> returns false
        assertFalse(organ.equals(new Address("Other Valid Organ")));
    }
}

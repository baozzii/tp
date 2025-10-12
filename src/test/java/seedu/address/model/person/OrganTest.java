package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrganTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidOrgan() {
        // null address
        assertThrows(NullPointerException.class, () -> Organ.isValidOrgan(null));

        // invalid organs
        assertFalse(Organ.isValidOrgan("")); // empty string
        assertFalse(Organ.isValidOrgan(" ")); // spaces only

        // valid organs
        assertTrue(Address.isValidAddress("kidney"));
        assertTrue(Address.isValidAddress("l")); // one character
        assertTrue(Address.isValidAddress("mitochondria")); // long organ
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

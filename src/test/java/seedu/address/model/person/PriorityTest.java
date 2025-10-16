package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        Integer invalidPriority = -1;
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null Priority 
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid Priority 
        assertFalse(Priority.isValidPriority(-1)); // negative
        assertFalse(Priority.isValidPriority(0)); // zero
        assertFalse(Priority.isValidPriority(6)); // too large
        assertFalse(Priority.isValidPriority(10)); 

        // valid Priority 
        assertTrue(Priority.isValidPriority(1));
        assertTrue(Priority.isValidPriority(2));
        assertTrue(Priority.isValidPriority(3));
        assertTrue(Priority.isValidPriority(4));
        assertTrue(Priority.isValidPriority(5));
    }

    @Test
    public void equals() {
        Priority priority = new Priority(5);

        // same values -> returns true
        assertTrue(priority.equals(new Priority(5)));

        // same object -> returns true
        assertTrue(priority.equals(Priority));

        // null -> returns false
        assertFalse(priority.equals(null));

        // different types -> returns false
        assertFalse(priority.equals(5.0f));

        // different values -> returns false
        assertFalse(priority.equals(new Priority(4)));
    }

    @Test
    public void compareTo() {
        for (Integer i = 1; i <= 5; i++) {
            Priority priority1 = new Priority(i);
            for (Integer j = 1; j <= 5; j++) {
                Priority priority2 = new Priority(j);
                if (i < j) {
                    assertTrue(priority1.compareTo(priority2));
                } else {
                    assertFalse(priority1.compareTo(priority2));
                }
            }
        }
    }
}

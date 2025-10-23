package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmergencyContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmergencyContact(null, new Phone("91234567"), ""));
        assertThrows(NullPointerException.class, () -> new EmergencyContact(new Name("John"), null, ""));
        assertThrows(NullPointerException.class, () -> new EmergencyContact(
                new Name("John"), new Phone("91234567"), null));
    }

    @Test
    public void equals() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("91234567");
        String relationship = "spouse";
        EmergencyContact emergencyContact = new EmergencyContact(name, phone, relationship);

        // same values -> returns true
        assertTrue(emergencyContact.equals(new EmergencyContact(
                new Name("John Doe"), new Phone("91234567"), "spouse")));

        // same object -> returns true
        assertTrue(emergencyContact.equals(emergencyContact));

        // null -> returns false
        assertFalse(emergencyContact.equals(null));

        // different types -> returns false
        assertFalse(emergencyContact.equals(5.0f));

        // different name -> returns false
        assertFalse(emergencyContact.equals(new EmergencyContact(new Name("Jane Doe"), phone, relationship)));

        // different phone -> returns false
        assertFalse(emergencyContact.equals(new EmergencyContact(name, new Phone("98765432"), relationship)));

        // different relationship -> returns false
        assertFalse(emergencyContact.equals(new EmergencyContact(name, phone, "parent")));

        // empty relationship -> returns false when compared to non-empty
        assertFalse(emergencyContact.equals(new EmergencyContact(name, phone, "")));

        // both empty relationships -> returns true
        EmergencyContact emptyRelation1 = new EmergencyContact(name, phone, "");
        EmergencyContact emptyRelation2 = new EmergencyContact(new Name("John Doe"), new Phone("91234567"), "");
        assertTrue(emptyRelation1.equals(emptyRelation2));
    }

    @Test
    public void toString_test() {
        EmergencyContact emergencyContactWithRelation = new EmergencyContact(
                new Name("John Doe"), new Phone("91234567"), "spouse");
        assertTrue(emergencyContactWithRelation.toString().contains("John Doe"));
        assertTrue(emergencyContactWithRelation.toString().contains("91234567"));
        assertTrue(emergencyContactWithRelation.toString().contains("spouse"));

        EmergencyContact emergencyContactWithoutRelation = new EmergencyContact(
                new Name("Jane Doe"), new Phone("98765432"), "");
        assertTrue(emergencyContactWithoutRelation.toString().contains("Jane Doe"));
        assertTrue(emergencyContactWithoutRelation.toString().contains("98765432"));
        assertFalse(emergencyContactWithoutRelation.toString().contains("()"));
    }
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents an emergency contact for a Person.
 */
public class EmergencyContact {

    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact requires both name (en/) and phone (ep/) specified or both empty to clear."
            + " Relationship (er/) is optional.";

    public static final String EMERGENCY_CONTACT_IS_RECIPIENT =
            "Emergency contact cannot be the same as the recipient's phone number.";

    private final Name name;
    private final Phone phone;
    private final String relationship;

    /**
     * Constructs an {@code EmergencyContact}.
     *
     * @param name
     * @param phone
     * @param relationship
     */
    public EmergencyContact(Name name, Phone phone, String relationship) {
        requireNonNull(name);
        requireNonNull(phone);
        requireNonNull(relationship);
        validateEmergencyContactFields(name, phone);
        this.name = name;
        this.phone = phone;
        this.relationship = relationship;
    }

    private void validateEmergencyContactFields(Name name, Phone phone) {
        assert name != null : "Emergency contact name should not be null";
        assert phone != null : "Emergency contact phone should not be null";

        String nameStr = name.toString();
        String phoneStr = phone.toString();

        if (nameStr.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        if (phoneStr.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        if (!isValidPhoneFormat(phoneStr)) {
            throw new IllegalArgumentException(Phone.MESSAGE_CONSTRAINTS);
        }
    }

    private boolean isValidPhoneFormat(String phone) {
        assert phone != null : "Phone should not be null";
        return phone.matches("\\d{8}");
    }

    public Name getName() {
        assert name != null : "Emergency contact name should not be null";
        return name;
    }

    public Phone getPhone() {
        assert phone != null : "Emergency contact phone should not be null";
        return phone;
    }

    public String getRelationship() {
        return relationship;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EmergencyContact)) {
            return false;
        }
        EmergencyContact o = (EmergencyContact) other;
        return name.equals(o.name) && phone.equals(o.phone) && relationship.equals(o.relationship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, relationship);
    }

    @Override
    public String toString() {
        return name + " / " + phone + (relationship.isEmpty() ? "" : " (" + relationship + ")");
    }
}

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
        this.name = name;
        this.phone = phone;
        this.relationship = relationship;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
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

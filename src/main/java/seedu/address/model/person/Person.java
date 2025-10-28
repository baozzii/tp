package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Organ organ;
    private final BloodType bloodType;
    private final Priority priority;
    private final Set<Tag> tags = new HashSet<>();
    private final EmergencyContact emergencyContact;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Organ organ, BloodType bloodType,
                  Priority priority, Set<Tag> tags, EmergencyContact emergencyContact) {
        requireAllNonNull(name, phone, email, address, organ, tags, priority);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.emergencyContact = emergencyContact;
        this.organ = organ;
        this.bloodType = bloodType;
        this.priority = priority;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Organ getOrgan() {
        return organ;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls - do this BEFORE casting
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && organ.equals(otherPerson.organ)
                && bloodType.equals(otherPerson.bloodType)
                && priority.equals(otherPerson.priority)
                && tags.equals(otherPerson.tags)
                && Objects.equals(emergencyContact, otherPerson.emergencyContact); // Only ONE emergency contact check
    }

    /**
     * Returns true if donor's organ matches the person's organ.
     * @param organAvailable available organ from donor.
     * @return boolean to indicate if organs match.
     */
    public boolean isMatch(Organ organAvailable) {
        return organ.equals(organAvailable);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, organ, bloodType, tags, emergencyContact, priority);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("organ", organ)
                .add("blood type", bloodType)
                .add("priority", priority)
                .add("tags", tags)
                .add("emergencyContact", emergencyContact)
                .toString();
    }

    @Override
    public int compareTo(Person other) {
        if (priority.compareTo(other.priority) == 0) {
            return name.fullName.compareTo(other.name.fullName);
        }
        return  priority.compareTo(other.priority);
    }
}

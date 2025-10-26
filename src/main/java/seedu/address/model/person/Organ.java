package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's organ in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrgan(String)}
 */
public class Organ {

    public static final String MESSAGE_CONSTRAINTS =
            "Organs should only contain alphabetical characters and spaces, and it should not be blank";

    /*
     * The first character of the organ must be an alphabet,
     * otherwise " " (a blank string) becomes a valid input.
     * Subsequent characters should be alphabetical only.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z]*";

    public final String organName;

    /**
     * Constructs a {@code Organ}.
     *
     * @param organ A valid organ.
     */
    public Organ(String organ) {
        requireNonNull(organ);
        checkArgument(isValidOrgan(organ), MESSAGE_CONSTRAINTS);
        organName = organ;
    }

    /**
     * Returns true if a given string is a valid organ.
     */
    public static boolean isValidOrgan(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return organName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Organ)) {
            return false;
        }
        Organ otherOrgan = (Organ) other;
        assert otherOrgan != null : "otherOrgan is null!";
        return organName.equals(otherOrgan.organName);
    }

    @Override
    public int hashCode() {
        return organName.hashCode();
    }

}

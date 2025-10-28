package seedu.address.testutil;

import java.util.Collections;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class for UniquePersonList
 */
public class UniquePersonListUtil {

    /**
     * Returns whether {@code list} is sorted.
     */
    public static boolean isSorted(UniquePersonList list) {
        Person person = null;
        for (Person nextPerson : list) {
            if (person != null && person.compareTo(nextPerson) >= 0) {
                return false;
            }
            person = nextPerson;
        }
        return true;
    }

    /**
     * Returns a shuffled list of typical persons
     */
    public static List<Person> getShuffledTypicalPersons() {
        List<Person> persons = TypicalPersons.getTypicalPersons();
        Collections.shuffle(persons);
        return persons;
    }
}

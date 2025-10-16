package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        OrganContainsSubstringPredicate firstPredicate = new OrganContainsSubstringPredicate(firstPredicateKeyword);
        OrganContainsSubstringPredicate secondPredicate = new OrganContainsSubstringPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrganContainsSubstringPredicate firstPredicateCopy = new OrganContainsSubstringPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_userContainsOrgan_returnsTrue() {
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("kidney");
        assertTrue(predicate.test(new PersonBuilder().withOrgan("kidney").build()));

        predicate = new OrganContainsSubstringPredicate("liver");
        assertTrue(predicate.test(new PersonBuilder().withOrgan("liver").build()));

        predicate = new OrganContainsSubstringPredicate("liver kidney");
        assertTrue(predicate.test(new PersonBuilder().withOrgan("liver kidney heart").build()));

        predicate = new OrganContainsSubstringPredicate("lIvEr KidNEY");
        assertTrue(predicate.test(new PersonBuilder().withOrgan("lIVER kIDney hEaRT").build()));
    }

    @Test
    public void test_userDoesNotContainOrgan_returnsFalse() {
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("kidney");
        assertFalse(predicate.test(new PersonBuilder().withOrgan("kidknees").build()));

        predicate = new OrganContainsSubstringPredicate("brain ears");
        assertFalse(predicate.test(new PersonBuilder().withOrgan("brain heart").build()));

        predicate = new OrganContainsSubstringPredicate("mouth eyes");
        assertFalse(predicate.test(new PersonBuilder().withName("mouth eyes").withOrgan("lung tooth").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "keyword";
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate(substring);

        String expected = OrganContainsSubstringPredicate.class.getCanonicalName() + "{substring=" + substring + "}";
        assertEquals(expected, predicate.toString());
    }
}
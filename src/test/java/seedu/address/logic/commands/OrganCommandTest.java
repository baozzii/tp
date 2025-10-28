package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.OrganContainsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code OrganCommand}.
 */
public class OrganCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        OrganContainsSubstringPredicate firstPredicate =
                new OrganContainsSubstringPredicate("kidney");
        OrganContainsSubstringPredicate secondPredicate =
                new OrganContainsSubstringPredicate("liver");

        OrganCommand findFirstCommand = new OrganCommand(firstPredicate);
        OrganCommand findSecondCommand = new OrganCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        OrganCommand findFirstCommandCopy = new OrganCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different organ -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_substringFound_multiplePersonsFound() {
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("kidney");
        OrganCommand command = new OrganCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_substringNotFound_noPersonFound() {
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND, "xyz");
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("xyz");
        OrganCommand command = new OrganCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("kidney");
        OrganCommand organCommand = new OrganCommand(predicate);
        String expected = OrganCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, organCommand.toString());
    }
}

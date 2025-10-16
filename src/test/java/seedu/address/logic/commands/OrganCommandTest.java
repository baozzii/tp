package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.OrganContainsSubstringPredicate;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.*;

class OrganCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        OrganContainsSubstringPredicate firstPredicate =
                new OrganContainsSubstringPredicate("first");
        OrganContainsSubstringPredicate secondPredicate =
                new OrganContainsSubstringPredicate("second");

        OrganCommand organFirstCommand = new OrganCommand(firstPredicate);
        OrganCommand organSecondCommand = new OrganCommand(secondPredicate);

        // same object -> returns true
        assertTrue(organFirstCommand.equals(organFirstCommand));

        // same values -> returns true
        OrganCommand organFirstCommandCopy = new OrganCommand(firstPredicate);
        assertTrue(organFirstCommand.equals(organFirstCommandCopy));

        // different types -> returns false
        assertFalse(organFirstCommand.equals(1));

        // null -> returns false
        assertFalse(organFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(organFirstCommand.equals(organSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        OrganContainsSubstringPredicate predicate = preparePredicate("NonExistent");
        String expectedMessage = String.format(Messages.MESSAGE_NO_PERSONS_FOUND, predicate.getSubstring());
        OrganCommand command = new OrganCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        OrganContainsSubstringPredicate predicate = preparePredicate("heart");
        OrganCommand command = new OrganCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        OrganContainsSubstringPredicate predicate = preparePredicate("kidney");
        OrganCommand command = new OrganCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        OrganContainsSubstringPredicate predicate = new OrganContainsSubstringPredicate("keyword");
        OrganCommand command = new OrganCommand(predicate);
        String expected = OrganCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code OrganContainsSubstringPredicate}.
     */
    private OrganContainsSubstringPredicate preparePredicate(String userInput) {
        return new OrganContainsSubstringPredicate(userInput);
    }
}

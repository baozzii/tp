package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeMatchesPredicate;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrganContainsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code CombinedCommand}.
 */
public class CombinedCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CombinedPredicate firstPredicate = new CombinedPredicate(
                Optional.of(new NameContainsKeywordsPredicate(Collections.singletonList("first"))),
                Optional.empty(),
                Optional.empty());
        CombinedPredicate secondPredicate = new CombinedPredicate(
                Optional.of(new NameContainsKeywordsPredicate(Collections.singletonList("second"))),
                Optional.empty(),
                Optional.empty());

        CombinedCommand findFirstCommand = new CombinedCommand(firstPredicate);
        CombinedCommand findSecondCommand = new CombinedCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        CombinedCommand findFirstCommandCopy = new CombinedCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_nameOnly_personsFound() {
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        CombinedPredicate predicate = new CombinedPredicate(
                Optional.of(new NameContainsKeywordsPredicate(Collections.singletonList("Alice"))),
                Optional.empty(),
                Optional.empty());
        CombinedCommand command = new CombinedCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_organOnly_personsFound() {
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CombinedPredicate predicate = new CombinedPredicate(
                Optional.empty(),
                Optional.of(new OrganContainsSubstringPredicate("kidney")),
                Optional.empty());
        CombinedCommand command = new CombinedCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bloodTypeOnly_personsFound() {
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<BloodType> bloodTypes = Arrays.asList(new BloodType("O+"));
        CombinedPredicate predicate = new CombinedPredicate(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new BloodTypeMatchesPredicate(bloodTypes)));
        CombinedCommand command = new CombinedCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allCriteria_personsFound() {
        // Test with name AND organ AND blood type
        List<BloodType> bloodTypes = Arrays.asList(new BloodType("O+"));
        CombinedPredicate predicate = new CombinedPredicate(
                Optional.of(new NameContainsKeywordsPredicate(Collections.singletonList("Alice"))),
                Optional.of(new OrganContainsSubstringPredicate("kidney")),
                Optional.of(new BloodTypeMatchesPredicate(bloodTypes)));
        CombinedCommand command = new CombinedCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        // Execute and check result
        model.updateFilteredPersonList(predicate);
        int resultSize = model.getFilteredPersonList().size();
        String expectedMessage = resultSize == 0
            ? String.format(seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND, predicate.getCriteria())
            : String.format(seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, resultSize);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        CombinedPredicate predicate = new CombinedPredicate(
                Optional.of(new NameContainsKeywordsPredicate(Collections.singletonList("keyword"))),
                Optional.empty(),
                Optional.empty());
        CombinedCommand combinedCommand = new CombinedCommand(predicate);
        String expected = CombinedCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, combinedCommand.toString());
    }
}


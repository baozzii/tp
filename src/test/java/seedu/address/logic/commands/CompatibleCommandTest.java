package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeCompatibilityPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code CompatibleCommand}.
 */
public class CompatibleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        BloodTypeCompatibilityPredicate firstPredicate =
                new BloodTypeCompatibilityPredicate(new BloodType("A+"));
        BloodTypeCompatibilityPredicate secondPredicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O+"));

        CompatibleCommand findFirstCommand = new CompatibleCommand(firstPredicate);
        CompatibleCommand findSecondCommand = new CompatibleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        CompatibleCommand findFirstCommandCopy = new CompatibleCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different blood type -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_universalDonor_allPersonsFound() {
        // O- can donate to everyone
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O-"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oPlusDonor_compatibleRecipientsFound() {
        // O+ can donate to O+, A+, B+, AB+
        // From TypicalPersons: ALICE is O+
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_abPlusDonor_onlyAbPlusRecipientsFound() {
        // AB+ can only donate to AB+ recipients
        // From TypicalPersons: no AB+ recipients in typical persons
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND,
                "compatible recipients for donor AB+");
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("AB+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_aPlusDonor_compatibleRecipientsFound() {
        // A+ can donate to A+, AB+
        // This will depend on what's in the typical persons
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("A+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        model.updateFilteredPersonList(predicate);
        int resultSize = model.getFilteredPersonList().size();
        String expectedMessage = resultSize == 0
            ? String.format(seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND,
                    "compatible recipients for donor A+")
            : String.format(seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, resultSize);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noCompatibleRecipients_noPersonFound() {
        // Test a scenario where no compatible recipients exist
        Model emptyModel = new ModelManager();
        Model emptyExpectedModel = new ModelManager();

        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND,
                "compatible recipients for donor O+");
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        emptyExpectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, emptyModel, expectedMessage, emptyExpectedModel);
        assertEquals(Collections.emptyList(), emptyModel.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("A+"));
        CompatibleCommand compatibleCommand = new CompatibleCommand(predicate);
        String expected = CompatibleCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, compatibleCommand.toString());
    }
}


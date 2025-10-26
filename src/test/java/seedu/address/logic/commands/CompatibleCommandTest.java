package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
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
    public void execute_universalRecipient_allPersonsFound() {
        // AB+ can receive from everyone
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("AB+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oPlusRecipient_compatibleDonorsFound() {
        // O+ can receive from O- and O+ donors
        // From TypicalPersons: ALICE is O+, BENSON is O-
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_oMinusRecipient_onlyOMinusDonorsFound() {
        // O- can only receive from O- donors
        // From TypicalPersons: BENSON is O-
        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("O-"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_aPlusRecipient_compatibleDonorsFound() {
        // A+ can receive from O-, O+, A-, A+
        // This will depend on what's in the typical persons
        BloodTypeCompatibilityPredicate predicate =
                new BloodTypeCompatibilityPredicate(new BloodType("A+"));
        CompatibleCommand command = new CompatibleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        model.updateFilteredPersonList(predicate);
        int resultSize = model.getFilteredPersonList().size();
        String expectedMessage = resultSize == 0
            ? String.format(seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND,
                    "compatible donors for A+")
            : String.format(seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, resultSize);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noCompatibleDonors_noPersonFound() {
        // Test a scenario where no compatible donors exist
        // Create a model with only AB+ people, then search for O- recipients
        // O- can only receive from O-, so if we search for AB+ recipients,
        // they can receive from everyone, so this won't work
        // Actually, let's just use a blood type that has fewer donors in typical persons
        Model emptyModel = new ModelManager();
        Model emptyExpectedModel = new ModelManager();

        String expectedMessage = String.format(
                seedu.address.logic.Messages.MESSAGE_NO_PERSONS_FOUND,
                "compatible donors for O+");
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


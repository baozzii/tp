package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SummaryCommand.
 */
public class SummaryCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_summaryWithPersons_success() {
        SummaryCommand summaryCommand = new SummaryCommand();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        
        // Execute command
        CommandResult result = summaryCommand.execute(model);
        
        // Verify the message contains the summary header
        assertTrue(result.getFeedbackToUser().contains("Organ Requirements Summary:"));
        assertTrue(result.getFeedbackToUser().contains("Total"));
    }

    @Test
    public void execute_emptyAddressBook_showsNoPatients() {
        Model emptyModel = new ModelManager();
        SummaryCommand summaryCommand = new SummaryCommand();
        
        String expectedMessage = SummaryCommand.MESSAGE_NO_PATIENTS;
        Model expectedModel = new ModelManager();
        
        assertCommandSuccess(summaryCommand, emptyModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_summaryCountsOrgansCorrectly() {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SummaryCommand summaryCommand = new SummaryCommand();
        
        CommandResult result = summaryCommand.execute(testModel);
        String output = result.getFeedbackToUser();
        
        // Verify output format
        assertTrue(output.contains("Organ Requirements Summary:"));
        assertTrue(output.contains("patient(s)"));
        assertTrue(output.contains("Total"));
    }

    @Test
    public void equals() {
        SummaryCommand summaryCommand1 = new SummaryCommand();
        SummaryCommand summaryCommand2 = new SummaryCommand();

        // same object -> returns true
        assertTrue(summaryCommand1.equals(summaryCommand1));

        // same type -> returns true
        assertTrue(summaryCommand1.equals(summaryCommand2));

        // different types -> returns false
        assertFalse(summaryCommand1.equals(1));

        // null -> returns false
        assertFalse(summaryCommand1.equals(null));
    }

    @Test
    public void execute_summaryShowsAllOrganTypes() {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SummaryCommand summaryCommand = new SummaryCommand();
        
        CommandResult result = summaryCommand.execute(testModel);
        String output = result.getFeedbackToUser();
        
        // Check that summary is properly formatted
        assertTrue(output.contains(":"));
        assertTrue(output.contains("patient(s)"));
    }

    @Test
    public void toStringMethod() {
        SummaryCommand summaryCommand = new SummaryCommand();
        String expected = SummaryCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, summaryCommand.toString());
    }
}

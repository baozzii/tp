package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.util.SampleDataUtil;

public class SearchCommandNoResultsTest {

    @Test
    public void execute_noMatchingKeywords_showsNoPersonsFoundMessage() {
        Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("zzzzzz"));
        SearchCommand command = new SearchCommand(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_NO_PERSONS_FOUND, predicate.getKeywords());
        Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }
}

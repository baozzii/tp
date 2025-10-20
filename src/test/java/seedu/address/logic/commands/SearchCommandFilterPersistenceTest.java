package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests for filter persistence functionality.
 */
public class SearchCommandFilterPersistenceTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_searchCommand_savesFilterKeywords() {
        List<String> keywords = Arrays.asList("Alice");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        SearchCommand searchCommand = new SearchCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(searchCommand, model, expectedMessage, expectedModel);
        assertEquals("Alice", model.getUserPrefs().getLastFilterKeywords());
    }

    @Test
    public void execute_listCommand_clearsFilterKeywords() {
        List<String> keywords = Arrays.asList("Alice");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        model.updateFilteredPersonList(predicate);

        ListCommand listCommand = new ListCommand();
        String expectedMessage = ListCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
        assertEquals("", model.getUserPrefs().getLastFilterKeywords());
    }

    @Test
    public void execute_searchMultipleKeywords_savesAllKeywords() {
        List<String> keywords = Arrays.asList("Alice", "Benson");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        SearchCommand searchCommand = new SearchCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(searchCommand, model, expectedMessage, expectedModel);
        assertEquals("Alice Benson", model.getUserPrefs().getLastFilterKeywords());
    }
}

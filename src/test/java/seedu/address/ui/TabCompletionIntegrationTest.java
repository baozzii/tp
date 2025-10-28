package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CommandTemplates;
import seedu.address.logic.parser.CommandTemplates.Template;

/**
 * Integration tests for tab completion functionality.
 * Tests the interaction between CommandTemplates and potential UI components.
 */
public class TabCompletionIntegrationTest {

    @Test
    public void tabCompletion_addCommand_expandsCorrectly() {
        String userInput = "add";
        Optional<Template> template = CommandTemplates.getTemplate(userInput);

        assertTrue(template.isPresent());

        String expandedText = template.get().getTemplateText();
        int cursorPosition = template.get().getFirstEmptyPosition();

        // Verify expansion
        assertTrue(expandedText.startsWith("add "));
        assertTrue(expandedText.contains("n/ p/ e/ a/ o/ b/ r/ t/ en/ ep/ er/"));

        // Verify cursor position is after "n/"
        assertTrue(cursorPosition > 0);
        assertTrue(cursorPosition < expandedText.length());
        assertEquals("n/", expandedText.substring(cursorPosition - 2, cursorPosition));
    }

    @Test
    public void tabCompletion_nonExistentCommand_noExpansion() {
        String userInput = "nonexistent";
        Optional<Template> template = CommandTemplates.getTemplate(userInput);

        assertTrue(template.isEmpty());
    }

    @Test
    public void tabCompletion_caseInsensitive_worksCorrectly() {
        String[] variations = {"add", "ADD", "Add", "aDd"};

        for (String variation : variations) {
            Optional<Template> template = CommandTemplates.getTemplate(variation);
            assertTrue(template.isPresent(), "Template should exist for: " + variation);

            String templateText = template.get().getTemplateText();
            assertTrue(templateText.startsWith("add "), "Template should start with 'add ' for: " + variation);
        }
    }

    @Test
    public void tabCompletion_workflowSimulation_success() {
        // Simulate user typing workflow
        // Step 1: User types "add"
        String userInput = "add";

        // Step 2: User presses Tab - system should expand
        Optional<Template> template = CommandTemplates.getTemplate(userInput);
        assertTrue(template.isPresent());

        // Step 3: System expands to full template
        String expandedText = template.get().getTemplateText();
        int cursorPos = template.get().getFirstEmptyPosition();

        // Step 4: Verify user can start typing at correct position
        String beforeCursor = expandedText.substring(0, cursorPos);
        String afterCursor = expandedText.substring(cursorPos);

        assertTrue(beforeCursor.endsWith("n/"));
        assertTrue(afterCursor.startsWith(" p/"));

        // Step 5: Simulate user typing name
        String withName = beforeCursor + "John Doe" + afterCursor;
        assertTrue(withName.contains("n/John Doe p/"));
    }
}

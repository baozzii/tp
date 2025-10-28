package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CommandTemplates.Template;

public class CommandTemplatesTest {

    @Test
    public void getTemplate_validCommand_returnsTemplate() {
        Optional<Template> template = CommandTemplates.getTemplate("add");
        assertTrue(template.isPresent());

        String expectedTemplate = "add n/ p/ e/ a/ o/ b/ r/ t/ en/ ep/ er/";
        assertEquals(expectedTemplate, template.get().getTemplateText());
    }

    @Test
    public void getTemplate_invalidCommand_returnsEmpty() {
        Optional<Template> template = CommandTemplates.getTemplate("nonexistent");
        assertFalse(template.isPresent());
    }

    @Test
    public void getTemplate_caseInsensitive_returnsTemplate() {
        Optional<Template> templateLower = CommandTemplates.getTemplate("add");
        Optional<Template> templateUpper = CommandTemplates.getTemplate("ADD");
        Optional<Template> templateMixed = CommandTemplates.getTemplate("Add");

        assertTrue(templateLower.isPresent());
        assertTrue(templateUpper.isPresent());
        assertTrue(templateMixed.isPresent());

        // All should return the same template
        assertEquals(templateLower.get().getTemplateText(), templateUpper.get().getTemplateText());
        assertEquals(templateLower.get().getTemplateText(), templateMixed.get().getTemplateText());
    }

    @Test
    public void hasTemplate_validCommand_returnsTrue() {
        assertTrue(CommandTemplates.hasTemplate("add"));
        assertTrue(CommandTemplates.hasTemplate("ADD"));
        assertTrue(CommandTemplates.hasTemplate("Add"));
    }

    @Test
    public void hasTemplate_invalidCommand_returnsFalse() {
        assertFalse(CommandTemplates.hasTemplate("nonexistent"));
        assertFalse(CommandTemplates.hasTemplate(""));
        assertFalse(CommandTemplates.hasTemplate("delete"));
    }

    @Test
    public void template_constructor_setsFieldsCorrectly() {
        String templateText = "add n/ p/ e/";
        int cursorPosition = 6;

        Template template = new Template(templateText, cursorPosition);

        assertEquals(templateText, template.getTemplateText());
        assertEquals(cursorPosition, template.getFirstEmptyPosition());
    }

    @Test
    public void template_getters_returnCorrectValues() {
        Template template = new Template("edit INDEX n/ p/", 11);

        assertEquals("edit INDEX n/ p/", template.getTemplateText());
        assertEquals(11, template.getFirstEmptyPosition());
    }

    @Test
    public void addTemplate_cursorPositionCalculation_correct() {
        String addTemplate = "add n/ p/ e/ a/ o/ b/ r/ t/ en/ ep/ er/";
        int expectedCursorPos = addTemplate.indexOf("n/") + 2; // After "n/"

        Optional<Template> template = CommandTemplates.getTemplate("add");
        assertTrue(template.isPresent());
        assertEquals(expectedCursorPos, template.get().getFirstEmptyPosition());
    }

    @Test
    public void addTemplate_templateStructure_containsAllPrefixes() {
        Optional<Template> template = CommandTemplates.getTemplate("add");
        assertTrue(template.isPresent());

        String templateText = template.get().getTemplateText();

        // Verify all required prefixes are present
        assertTrue(templateText.contains("n/")); // name
        assertTrue(templateText.contains("p/")); // phone
        assertTrue(templateText.contains("e/")); // email
        assertTrue(templateText.contains("a/")); // address
        assertTrue(templateText.contains("o/")); // organ
        assertTrue(templateText.contains("b/")); // blood type
        assertTrue(templateText.contains("r/")); // priority
        assertTrue(templateText.contains("t/")); // tags
        assertTrue(templateText.contains("en/")); // emergency contact name
        assertTrue(templateText.contains("ep/")); // emergency contact phone
        assertTrue(templateText.contains("er/")); // emergency contact relationship

        // Verify template starts with command word
        assertTrue(templateText.startsWith("add "));
    }
}

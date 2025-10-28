package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains command templates for tab completion functionality.
 * Easily extendible for new commands.
 */
public class CommandTemplates {

    /**
     * Represents a command template with prefixes and cursor position info.
     */
    public static class Template {
        private final String templateText;
        private final int firstEmptyPosition;

        /**
         * Creates a Template with the specified template text and cursor position.
         *
         * @param templateText the full command template string with all prefixes
         * @param firstEmptyPosition the character position where the cursor should be placed after expansion
         */
        public Template(String templateText, int firstEmptyPosition) {
            this.templateText = templateText;
            this.firstEmptyPosition = firstEmptyPosition;
        }

        public String getTemplateText() {
            return templateText;
        }

        public int getFirstEmptyPosition() {
            return firstEmptyPosition;
        }
    }

    private static final Map<String, Template> COMMAND_TEMPLATES = new HashMap<>();

    static {
        // AddCommand template - includes all prefixes (required and optional)
        String addTemplate = "add "
                + "n/" + " " // name (required)
                + "p/" + " " // phone (required)
                + "e/" + " " // email (required)
                + "a/" + " " // address (required)
                + "o/" + " " // organ (required)
                + "b/" + " " // blood type (required)
                + "r/" + " " // priority (required)
                + "t/" + " " // tags (optional)
                + "en/" + " " // emergency contact name (optional)
                + "ep/" + " " // emergency contact phone (optional)
                + "er/" + ""; // emergency contact relationship (optional)

        // Position cursor after "n/"
        int firstEmptyPos = addTemplate.indexOf("n/") + 2;
        COMMAND_TEMPLATES.put("add", new Template(addTemplate, firstEmptyPos));
    }

    /**
     * Gets the template for a given command word.
     *
     * @param commandWord the command word (e.g., "add")
     * @return Optional containing the template, or empty if no template exists
     */
    public static Optional<Template> getTemplate(String commandWord) {
        return Optional.ofNullable(COMMAND_TEMPLATES.get(commandWord.toLowerCase()));
    }

    /**
     * Checks if a command has a template available.
     *
     * @param commandWord the command word to check
     * @return true if template exists, false otherwise
     */
    public static boolean hasTemplate(String commandWord) {
        return COMMAND_TEMPLATES.containsKey(commandWord.toLowerCase());
    }

    /**
     * Adds a new command template. Used for future extensibility.
     *
     * @param commandWord the command word
     * @param template the template to add
     */
    public static void addTemplate(String commandWord, Template template) {
        COMMAND_TEMPLATES.put(commandWord.toLowerCase(), template);
    }
}

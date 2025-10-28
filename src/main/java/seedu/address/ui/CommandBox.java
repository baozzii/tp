package seedu.address.ui;

import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandTemplates;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     *
     * @param commandExecutor the command executor to execute user commands
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        // key event handler for tab completion
        commandTextField.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles key press events for the command text field.
     * @param event the key event
     */
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            event.consume(); // Prevent default tab behavior
            handleTabCompletion();
        }
    }

    /**
     * Handles tab completion for commands.
     * Expands command words to their full templates and positions cursor appropriately.
     */
    private void handleTabCompletion() {
        String currentText = commandTextField.getText().trim();

        if (currentText.isEmpty()) {
            return;
        }

        String[] parts = currentText.split("\\s+");
        String commandWord = parts[0].toLowerCase();

        Optional<CommandTemplates.Template> templateOpt = CommandTemplates.getTemplate(commandWord);

        if (templateOpt.isPresent()) {
            CommandTemplates.Template template = templateOpt.get();

            if (parts.length == 1 || (parts.length == 2 && parts[1].isEmpty())) {
                commandTextField.setText(template.getTemplateText());

                Platform.runLater(() -> {
                    commandTextField.requestFocus();
                    commandTextField.positionCaret(template.getFirstEmptyPosition());
                });
            }
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

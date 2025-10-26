package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Displays a summary of organ requirements across all patients.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a summary of how many patients require each type of organ.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Organ Requirements Summary:\n%s";
    public static final String MESSAGE_NO_PATIENTS = "No patients in the system.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Map<String, Integer> organCounts = new TreeMap<>();

        for (Person person : model.getAddressBook().getPersonList()) {
            String organName = person.getOrgan().organName;
            organCounts.put(organName, organCounts.getOrDefault(organName, 0) + 1);
        }

        if (organCounts.isEmpty()) {
            return new CommandResult(MESSAGE_NO_PATIENTS);
        }

        StringBuilder summary = new StringBuilder();
        int totalPatients = 0;

        for (Map.Entry<String, Integer> entry : organCounts.entrySet()) {
            String organ = entry.getKey();
            int count = entry.getValue();
            totalPatients += count;
            summary.append(String.format("%-15s : %d patient(s)\n", organ, count));
        }

        summary.append(String.format("\n%-15s : %d patient(s)", "Total", totalPatients));

        return new CommandResult(String.format(MESSAGE_SUCCESS, summary.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryCommand); // instanceof handles nulls
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "{}";
    }
}

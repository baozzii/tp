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
        assert model.getAddressBook() != null : "Address book should not be null";

        Map<String, Integer> organCounts = collectOrganCounts(model);

        if (organCounts.isEmpty()) {
            return new CommandResult(MESSAGE_NO_PATIENTS);
        }

        String summaryText = buildSummaryText(organCounts);
        return new CommandResult(String.format(MESSAGE_SUCCESS, summaryText));
    }

    private Map<String, Integer> collectOrganCounts(Model model) {
        assert model != null : "Model should not be null";
        Map<String, Integer> organCounts = new TreeMap<>();

        for (Person person : model.getAddressBook().getPersonList()) {
            if (person == null || person.getOrgan() == null) {
                continue;
            }
            incrementOrganCount(organCounts, person);
        }

        return organCounts;
    }

    private void incrementOrganCount(Map<String, Integer> organCounts, Person person) {
        assert organCounts != null : "Organ counts map should not be null";
        assert person != null : "Person should not be null";
        assert person.getOrgan() != null : "Person organ should not be null";

        String organName = person.getOrgan().organName;
        if (organName == null || organName.isEmpty()) {
            return;
        }
        organCounts.put(organName, organCounts.getOrDefault(organName, 0) + 1);
    }

    private String buildSummaryText(Map<String, Integer> organCounts) {
        assert organCounts != null && !organCounts.isEmpty() : "Organ counts should not be null or empty";

        StringBuilder summary = new StringBuilder();
        int totalPatients = appendOrganEntries(summary, organCounts);
        appendTotalCount(summary, totalPatients);

        return summary.toString();
    }

    private int appendOrganEntries(StringBuilder summary, Map<String, Integer> organCounts) {
        assert summary != null : "Summary builder should not be null";
        assert organCounts != null : "Organ counts map should not be null";

        int totalPatients = 0;
        for (Map.Entry<String, Integer> entry : organCounts.entrySet()) {
            if (entry == null || entry.getKey() == null) {
                continue;
            }
            totalPatients += appendSingleOrganEntry(summary, entry);
        }
        return totalPatients;
    }

    private int appendSingleOrganEntry(StringBuilder summary, Map.Entry<String, Integer> entry) {
        assert summary != null : "Summary builder should not be null";
        assert entry != null : "Entry should not be null";

        String organ = entry.getKey();
        int count = entry.getValue();
        assert count >= 0 : "Count should not be negative";

        summary.append(String.format("%-15s : %d patient(s)%n", organ, count));
        return count;
    }

    private void appendTotalCount(StringBuilder summary, int totalPatients) {
        assert summary != null : "Summary builder should not be null";
        assert totalPatients >= 0 : "Total patients should not be negative";

        summary.append(String.format("%n%-15s : %d patient(s)", "Total", totalPatients));
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

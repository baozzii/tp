---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Organ-izer Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI component is the stakeholder responsible for the graphical user interface seen by the user.

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

#### Overview

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. The various components are shown in the table below:

| Component | Description | FXML File |
|------------|--------------|-----------|
| `MainWindow` | The root container holding all components together. | `MainWindow.fxml` |
| `StatusBarFooter` | Shows the save location at the bottom of the application window. | `StatusBarFooter.fxml` |
| `ResultDisplay` | Shows messages for user commands for confirmation on commands being completed successfully, or errors that arose from the command. | `ResultDisplay.fxml` |
| `CommandBox` | Receives user inputs to execute commands. | `CommandBox.fxml` |
| `HelpWindow` | Shows a window that describes and gives examples for the commands available to the user. | `HelpWindow.fxml` |
| `PersonListPanel` | Shows a scrollable list of all recipient entries, where each is saved as a `PersonCard`. | `PersonListPanel.fxml` |
| `PersonCard` | Shows each field in recipient entries. | `PersonListCard.fxml` |

All the components stated above inherit from the abstract `UiPart` class, which encapsulates common functionality for JavaFX UI elements.

#### Error Handling & Validation

The UI component handles the following error scenarios:

| Rule | Description | Handling |
|------|--------------|----------|
| **Command Execution Failure** | `CommandException` thrown during command execution. | Display error message in `ResultDisplay`, highlight command box in red |
| **Invalid Command Format** | `ParseException` thrown during command parsing. | Display parse error message in `ResultDisplay`, highlight command box in red |
| **FXML Load Failure** | FXML file cannot be loaded or contains errors. | Log error and throw `AssertionError` |
| **Help Window Already Open** | User attempts to open help window when already open. | Focus existing help window instead of creating new one |
| **Empty Command Input** | User presses Enter with blank command box. | Ignore input, no action taken |
| **Invalid Tab Completion** | Tab pressed but no valid command template matches. | No completion performed, retain current text |

These error handling mechanisms ensure graceful degradation and provide clear feedback to users when issues occur.

#### Responsibilities

* **Command Execution**: Executes user commands using the `Logic` component by calling `Logic#execute(String commandText)`.
* **Data Observation**: Listens for changes to `Model` data so that the UI can be updated with the modified data.
* **Logic Integration**: Keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* **Data Display**: Depends on some classes in the `Model` component, as it displays `Person` objects residing in the `Model`.

#### Design Considerations
* Dropdown format in `HelpWindow` to reduce cluttering from other more basic commands. Allows the user to see an overview of all commands available, before zooming in on details of a specific command to be used.
* Resizable window dimensions to accommodate varying screen sizes and side by side usage of the application and other tools such as the internet browser.
* Horizontally scrollable `PersonListCard` and `ResultDisplay` to allow for viewing of longer entries or messages that may exceed horizontal window dimensions. Vertically scrollable `HelpWindow` and  `PersonListPanel` to accommodate viewing descriptions of commands and recipient entries that exceed the vertical window dimensions.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

#### Error Handling & Validation

During command parsing and execution, the following checks ensure command validity:

| Rule | Description | Exception |
|------|--------------|-----------|
| **Unknown Command** | Command word does not match any registered commands. | `ParseException(MESSAGE_UNKNOWN_COMMAND)` |
| **Invalid Command Format** | Command arguments do not match expected format. | `ParseException(MESSAGE_INVALID_COMMAND_FORMAT)` |
| **Invalid Person Index** | Index provided is out of bounds of the displayed person list. | `CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX)` |
| **Duplicate Person** | Attempting to add a person that already exists (same name and phone). | `CommandException(MESSAGE_DUPLICATE_PERSON)` |
| **Missing Prefixes** | Required prefixes (n/, p/, e/, etc.) are not provided. | `ParseException("Required prefixes missing")` |
| **Invalid Field Values** | Field values fail validation (e.g., invalid phone format, blood type). | `ParseException` with field-specific message |
| **Incomplete Emergency Contact** | Emergency contact provided with only name or phone, not both. | `ParseException(EmergencyContact.MESSAGE_CONSTRAINTS)` |
| **Emergency Contact Same as Recipient** | Emergency contact phone number matches the recipient's phone. | `ParseException(EmergencyContact.EMERGENCY_CONTACT_IS_RECIPIENT)` |

These validations ensure that only well-formed, valid commands are executed against the model.

#### Responsibilities

* **Command Parsing**: Parses user input strings into executable `Command` objects using the `AddressBookParser` and the respective command parsers.
* **Command Execution**: Executes parsed commands against the `Model` and returns a `CommandResult` containing the outcome and feedback message.
* **Exception Handling**: Throws `CommandException` for execution errors and `ParseException` for invalid command formats, which are caught and displayed to the user.
* **Command History Management**: Maintains a history of previously executed commands via `CommandHistory` for navigation with arrow keys.
* **Model-UI Bridge**: Provides the UI with access to the filtered person list and address book data through the `Logic` interface, without exposing underlying `Model` implementation.

#### Design Considerations

The following design considerations were maintained during implementation:

Command formats should be **simple and short**:
* This reflects our [non-functional](#non-functional-requirements) requirement that a non-technical user must be able to learn how to operate the app quickly.
* We split up our various search commands to simplify the work of remembering various abstract prefixes.
* For the add command which was inevitably long, we provided command completion with <kbd>Tab</kbd> to circumvent the issue.

Entering commands should be **fast**:
* This reflects our [non-functional](#non-functional-requirements) requirement that a user must be able to operate Organ-izer faster than a traditional mouse-based application.
* Adding the ability to access previously entered commands with the <kbd>Up</kbd> key saves on valuable time re-entering searches.
* Including a full command summary with the `help` command can avoid the overhead of accessing an external browser if a user simply forgets a command.
* Additionally, our command completion functionality also helps to speed up the process of adding users.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

#### Error Handling & Validation

The Model component enforces the following constraints on person data:

| Rule | Description | Exception |
|------|--------------|-----------|
| **Duplicate Person** | Adding a person with the same name and phone as an existing person. | `DuplicatePersonException` |
| **Person Not Found** | Attempting to edit or delete a person that doesn't exist in the list. | `PersonNotFoundException` |
| **Invalid Name** | Name contains non-alphanumeric characters or is blank. | `IllegalArgumentException(Name.MESSAGE_CONSTRAINTS)` |
| **Invalid Phone** | Phone number is not exactly 8 digits. | `IllegalArgumentException(Phone.MESSAGE_CONSTRAINTS)` |
| **Invalid Email** | Email does not follow format: local-part@domain. | `IllegalArgumentException(Email.MESSAGE_CONSTRAINTS)` |
| **Invalid Blood Type** | Blood type is not one of: A+, A-, B+, B-, AB+, AB-, O+, O-. | `IllegalArgumentException(BloodType.MESSAGE_CONSTRAINTS)` |
| **Invalid Organ** | Organ is not one of: kidney, liver, heart, lungs, pancreas, cornea. | `IllegalArgumentException(Organ.MESSAGE_CONSTRAINTS)` |
| **Invalid Priority** | Priority is not an integer between 1-5. | `IllegalArgumentException(Priority.MESSAGE_CONSTRAINTS)` |
| **Invalid Tag** | Tag name contains non-alphanumeric characters or is blank. | `IllegalArgumentException(Tag.MESSAGE_CONSTRAINTS)` |

These validations maintain data integrity and prevent invalid recipient information from being stored.

####Responsibilities

* **Data Management**: Holds and manages all application data, including the `AddressBook` containing recipient information and `UserPrefs` for user preferences.
* **Data Validation**: Ensures data integrity through validation of person attributes using regex patterns.
* **Filtering and Search**: Provides predicates for filtering the person list based on various criteria such as name or organ, without exposing the underlying data structure.
* **State Persistence**: Maintains the current filter state and user preferences set from `search` commands for restoration on application restart.
* **Change Notification**: Exposes observable lists that automatically notify the UI of data changes to allow for reactive updates to the displayed information in the UI component.

### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-T17-3/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

#### Error Handling & Validation

During file operations and deserialization (`JsonSerializableAddressBook#toModelType()`), the following checks ensure data consistency:

| Rule | Description | Exception |
|------|--------------|-----------|
| **Duplicate Person** | A duplicate `Person` entry exists in the JSON file. | `IllegalValueException(MESSAGE_DUPLICATE_PERSON)` |
| **Missing Required Fields** | Required JSON fields (name, phone, email, address, organ, bloodType, priority) are null or missing. | `IllegalValueException` |
| **Corrupted JSON** | JSON file is not properly formatted or cannot be parsed. | `DataLoadingException` |
| **File Not Found** | Address book or preferences file does not exist on first run. | Creates new file with empty/default data |
| **Access Denied** | Insufficient permissions to read/write storage files. | `DataLoadingException` wrapping `AccessDeniedException` |

These validations ensure that storage files are accessible, properly formatted, and do not contain duplicate entries.

#### Responsibilities

* **File Management**: Manages two separate storage files, namely `addressbook.json` for recipient data and `preferences.json` for user settings such as window size.
* **Data Conversion**: Converts between domain objects (eg. `Person` and `Tag`) and JSON-adapted objects (eg. `JsonAdaptedPerson`, `JsonAdaptedTag`) for serialisation and deserialisation.
* **Error Handling**: Handles file I/O exceptions (eg. missing files, access denied, corrupted data) and provides appropriate error messages to the application.
* **Storage Coordination**: Implements the `Storage` interface through`StorageManager` to coordinate between `AddressBookStorage` and `UserPrefsStorage` operations.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Noteworthy features: Combined search and blood type compatibility

We added two new commands to support organ transplant coordination workflows that require combining multiple criteria and reasoning about blood type compatibility.

- Combined search with AND semantics across criteria:
  - Exact name match (case-insensitive)
  - Organ substring
  - Blood type recipient-compatibility (find donors who can donate to specified recipient blood types)
- Compatibility search (recipient-compatibility given a donor blood type):
  - Finds all recipients whose blood type can receive from the specified donor blood type

#### Design overview

The logic flows mirror existing command parsing patterns. The `AddressBookParser` routes to dedicated parsers that build predicates and construct commands.

<puml src="diagrams/CombinedCompatibleClassDiagram.puml" width="700" />

Key points:
- `CombinedCommandParser` composes a `CombinedPredicate` from optional sub-predicates:
  - `NameExactMatchPredicate`
  - `OrganContainsSubstringPredicate`
  - `BloodTypeRecipientCompatiblePredicate`
- `CompatibleCommandParser` constructs a `BloodTypeCompatibilityPredicate` using the donor blood type.
- Both blood type compatibility predicates use a normalized, canonical matrix and compare using uppercase labels to ensure case-insensitive correctness (e.g., "b+" equals "B+").

#### Sequence of parsing and execution (Combined)

<puml src="diagrams/CombinedCommandSequenceDiagram.puml" width="700" />

1. `AddressBookParser` identifies `combined` and delegates to `CombinedCommandParser`.
2. Parser validates inputs, builds the appropriate sub-predicates, and constructs `CombinedCommand`.
3. On `execute`, `CombinedCommand` applies the combined predicate to `Model#updateFilteredPersonList` and returns a result containing either the count or a no-results message.

#### Blood type compatibility matrices (summary)

- Donor-to-recipient compatibility (used when searching donors for recipients):
  - O− → O−, O+, A−, A+, B−, B+, AB−, AB+
  - O+ → O+, A+, B+, AB+
  - A− → A−, A+, AB−, AB+
  - A+ → A+, AB+
  - B− → B−, B+, AB−, AB+
  - B+ → B+, AB+
  - AB− → AB−, AB+
  - AB+ → AB+

These rules are implemented in `BloodTypeRecipientCompatiblePredicate` and `BloodTypeCompatibilityPredicate` and compared with canonical uppercase strings.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

* **Alternative 3:** Save changes to address book as a separate log.
  * Pros: Will use less memory (e.g. for `edit`, just remember the changes to the specific person).
  * Cons: The implementation of a `diff` and rollback operations can be complex.

#### Future Extensions

* **Change Summary** - complement undo operations by allowing the user to view what will be undone.
* **Accountability** - keep track of the user modifying the address book to maintain accountability
* **Change Audits** - export a summary of all changes into an `.xlsx` file for auditing purposes

### \[Proposed\] Importing of Excel Spreadsheets  

#### Proposed Implementation  

This feature allows users to **bulk-import contact and organ donor data from an Excel (.xlsx) file** into the application’s address book. It aims to streamline data entry workflows for organizations that maintain donor information in spreadsheet form, such as hospitals or medical coordinators.  

<puml src="diagrams/ImportExcelSequenceDiagram.puml" width="700" />  

The import process involves reading Excel sheets, validating their contents, and converting rows into `Person` objects that the `Model` can store.  

**Key operations:**  

* `ImportCommand#execute()` — handles user invocation of the `import` command, e.g. `import f/data.xlsx`  
* `ExcelReader#readExcelFile(Path filePath)` — reads `.xlsx` files using Apache POI (or a similar library)  
* `ExcelParser#parseRows(List<Row> rows)` — parses each row, validates format, and converts them into `Person` entities  
* `Model#addPerson(Person person)` — adds each parsed `Person` to the in-memory `AddressBook`  
* `Storage#saveAddressBook(ReadOnlyAddressBook)` — persists the imported data  

#### Example user workflow  

1. The user places an Excel file (e.g. `donors.xlsx`) in the application directory.  
2. The user enters:  
   ```  
   import f/donors.xlsx  
   ```  
3. The app reads the spreadsheet, validates each entry, and adds new records to the address book.  
4. The UI displays a summary message such as:  
   ```  
   25 entries imported successfully. 2 rows skipped due to validation errors.  
   ```  

#### Expected Excel format  

The Excel file is expected to contain a header row with the following columns:  

| Column | Example Value | Description |  
|--------|----------------|-------------|  
| Name | John Doe | Full name of the person |  
| Phone | 91234567 | Contact number |  
| Email | johnd@example.com | Valid email address |  
| Address | 123 Clementi Ave 3 | Home address |  
| Blood Type | O+ | Must match recognized types (O+, A−, etc.) |  
| Organ | Kidney | Organ available or required |  
| Emergency Contact | 91234567 | Contact number |

**Optional extensions:**  
* Additional columns (e.g., “Donor/Recipient” status or “Notes”) may be supported later.  
* Missing optional fields are defaulted to empty strings.  

#### Validation and error handling  

The system enforces strict input validation to maintain data integrity:  
* Rows with missing mandatory fields (e.g. Name, Blood Type) are skipped.  
* Invalid blood types or malformed emails are logged as warnings.  
* The command result summarizes both successful and failed imports.  

Each skipped row produces a corresponding warning in the log:  
```  
[WARN] Row 17: Invalid blood type 'Z+' – skipped.  
```  

#### Sequence of interactions  

1. `LogicManager` parses the `import` command and creates an `ImportCommand` object.  
2. `ImportCommand#execute()` calls `ExcelReader#readExcelFile()`.  
3. `ExcelReader` uses Apache POI to read the `.xlsx` workbook and return its rows.  
4. `ExcelParser` validates and converts each row into a `Person`.  
5. Valid persons are passed to `Model#addPerson()`, which updates the in-memory data.  
6. `Storage#saveAddressBook()` persists the updated state.  

<puml src="diagrams/ImportExcelDetailedSequence.puml" width="750" />  

#### Design considerations  

**Aspect: How data is read from Excel**  

* **Alternative 1 (current choice):** Use Apache POI.  
  * Pros: Mature library, supports `.xlsx`, handles cell types robustly.  
  * Cons: Slightly higher memory footprint for large spreadsheets.  

* **Alternative 2:** Convert `.xlsx` to `.csv` before import and use existing text parsing logic.  
  * Pros: Simpler and lighter implementation.  
  * Cons: Requires extra preprocessing by users and may lose formatting.  

**Aspect: Handling duplicates**  

* **Alternative 1 (current choice):** Skip duplicates based on identical name and phone.  
  * Pros: Prevents accidental overwriting of existing data.  
  * Cons: May miss legitimate updates.  

* **Alternative 2:** Overwrite existing entries.  
  * Pros: Simplifies synchronization for updated records.  
  * Cons: Risk of unintended data loss.  

#### Future extensions  

* **Export to Excel** — complement import by providing a way to export all contacts back into an `.xlsx` file.  
* **Interactive conflict resolution** — prompt the user when duplicates or data inconsistencies are detected.  
* **Partial rollback** — implement atomic batch imports (commit all or none).  
* **GUI integration** — allow file selection via a graphical file chooser.  


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of recipients which need donors
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Organ donation is time-critical and requires a recipient to be present within hours of organ availability. Organ-iser solves coordination and prioritization challenges that donor coordinators face.




### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                                    | I want to …                 | So that I can…                                                        |
|----------|--------------------------------------------|------------------------------|------------------------------------------------------------------------|
|`***`|Organ transplant coordinator|search up patients by organ|I can quickly connect them with the available donor
|`***`|Organ transplant coordinator|delete patients from my list|I can clear discharged patients from the database
|`**`|Organ transplant coordinator|search up patients by compatible blood type|I can check for donor compatibility
|`*`|Organ transplant coordinator|search up patients by address|I can filter the nearest patients that need transplant with respect to the donor
|`**`|Organ transplant coordinator|add an excel list of patient information to the database|I can automate adding patients
|`***`|Organ transplant coordinator|quickly navigate through the pages of the application|I can search for patients quickly
|`**`|Organ transplant coordinator|automatically match donors to possible recipients|I can find recipients more quickly
|`**`|Organ transplant coordinator|edit current entries of patient details|I can update old details of the patients
|`*`|Organ transplant coordinator|receive real-time alerts when a new donor becomes available|I can act immediately without delays
|`*`|Organ transplant coordinator|provide estimated transport times for organs|I can match donors and recipients within viable windows
|`*`|Organ transplant coordinator|access the system from a mobile device|I can respond to urgent cases even when I’m away from my desk
|`**`|Organ transplant coordinator|generate summaries of matching recipients|I can communicate options efficiently with medical teams
|`*`|Organ transplant coordinator|archive (instead of permanently delete) patient records|I can maintain a history for auditing and future reference
|`**`|Organ transplant coordinator|mark patients as “urgent priority”|I can easily identify critical cases
|`*`|Organ transplant coordinator|track the transplant status (waiting, matched, transplanted, follow-up)|I can monitor each patient’s journey
|`**`|Organ transplant coordinator|filter patients by multiple criteria (e.g., organ + blood type + urgency)|I can narrow down possible matches more efficiently
|`**`|Organ transplant coordinator|automatically check donor-recipient compatibility based on medical criteria (HLA match, age, weight, etc.)|I don’t have to cross-check manually
|`*`|Organ transplant coordinator|view ranked compatibility scores for each potential recipient|I can make more informed decisions
|`*`|Organ transplant coordinator|send notifications to doctors and hospitals directly from the system|I can coordinate transplant logistics quickly
|`*`|Organ transplant coordinator|set reminders for patient follow-ups|I don’t miss critical post-transplant checks
|`**`|Organ transplant coordinator|get notified when patient details are incomplete|I can ensure accurate data for matching
|`*`|Organ transplant coordinator|generate monthly/ weekly reports on donor-recipient matches|I can analyze system performance and outcomes
|`**`|Organ transplant coordinator|export patient and donor data into secure formats (Excel, PDF)|I can share them with authorized stakeholders
|`*`|Organ transplant coordinator|view a log of all action history (adding, editing, deleting patients)|I can maintain compliance and traceability
|`**`|Organ transplant coordinator|receive command completion prompts|I can use the system without remembering all of its commands
|`**`|Organ transplant coordinator|re-enter previous commands without needing to retype them|I can perform my patient coordination faster even if I make mistakes
|`*`|Organ transplant coordinator|mass upload patient data even if there is overlapping patient info|old patient logs can be combined without causing issues
|`***`|Organ transplant coordinator|add individual new patients from the command line|I can update the system for individual new patients
|`*`|Organ transplant coordinator|see the output of the last search I previously performed when I reopen the application|I can continue my work where I left off
1. Coordinator receives notification about a willing organ donor.
2. Coordinator searches for patients which need that organ.
3. Organ-iser returns a list of  suitable patients.
4. The coordinator selects one patient to be matched to the donor.
5. Coordinator uses the patient’s contact details stored in the app to contact the patient for transplant.
*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Organ-iser` and the **Actor** is the `Coordinator`, unless specified otherwise)

**Use Case: Matching a donor to a patient**

**MSS**
1. Coordinator receives notification about a willing organ donor.
2. Coordinator searches for patients which need that organ.
3. Organ-iser returns a list of  suitable patients.
4. The coordinator selects one patient to be matched to the donor.
5. Coordinator uses the patient’s contact details stored in the app to contact the patient for transplant.

   Use case ends.

**Extensions**  

1. Organ-iser returned an empty list.
2. Coordinator notifies the organ donor that no patients match his donation, and no transplant is organised.  
    

**Use Case: Marking a recipient as urgent priority**  

**MSS**
1. Coordinator is notified that a patient is identified as urgently in need of the organ transplant.
2. Coordinator edits the patient’s existing record to mark it as urgent.
3. Organ-iser ranks patients by urgency when listing.  

   Use case ends.  
  
**Use Case: A new patient requires organ transplant**    

**MSS**
1. Coordinator wants to add a new patient.
2. Coordinator adds patient’s details to the Organ-iser.  

   Use case ends.  

**Extensions**
* 2a. Organ-iser recognises duplicate entry and returns error.  
  

**Use Case: Patient no longer requires transplant**  

**MSS**
1. Coordinator removes the patient’s entry.
2. Patient’s entry is deleted from Organ-iser.  

   Use case ends.  

**Extensions**
* 1a. The patient still requires other organs.
    * 1a1. The Coordinator removes only the organ no longer required by the patient.
    * 1a2. Organ-iser deletes the organ.  

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  All patients’, donors’, and recipients’ data should be encrypted for data privacy.
5.  Gracefully resolve system crashes with no data loss.
6.  Accepts multiple data formats (.csv, .xlsx, .json), and allows exports in multiple data formats (.pdf, etc.).
7.  Should hold up at least 10000 entries without any noticeable delays.
8.  Should be able to display at least 2 profiles at once even at minimum window size
9.  Should be able to handle international phone numbers
10. Must display a start screen within 5s of launching the app
11. All available patient info must be formatted correctly to fit the profile listings page
12. Compatible blood types must be correctly identified for a particular search
13. Should automatically log all actions for compliance, and ensure all logs are immutable.


*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

* **Private contact detail**: A contact detail that is not meant to be shared with others

* **Donor**: An organ donor

* **Recipient**: A recipient awaiting an organ donation

* **Tag**: A customizable label or keyword assigned to a person’s record to categorize or group similar entries (e.g., “Urgent”, “Pediatric”, “AB+ Donor”).

* **Emergency Contact**: A secondary phone number associated with a person, typically used to reach next-of-kin or responsible medical staff in urgent situations.

* **Gradle**: A build automation tool used for compiling, testing, packaging, and managing dependencies of the application.

* **FXML**: An XML-based markup language used by JavaFX to define the structure of user interfaces.

* **Predicate**: A condition or logical test used to filter or match entries based on specific attributes (e.g., name, organ, or blood type).

* **Model**: The component responsible for managing the in-memory data of the application.

* **Logic**: The component that interprets user commands and updates the model accordingly.

* **UI (User Interface)**: The visible part of the application that displays information and accepts user input.

* **Storage**: The component that reads from and writes to persistent storage (e.g., JSON, Excel files).

* **User Preferences**: Configuration data that stores user-specific settings (e.g., GUI layout, file paths).

* **Apache POI**: A Java library used for reading and writing Microsoft Excel (.xlsx) files.

* **CLI (Command Line Interface)**: A text-based interface through which the user interacts with the application by typing commands.

* **Compatibility Matrix**: A reference table defining valid donor-to-recipient blood type compatibility mappings.

* **ObservableList**: A list that allows UI components to automatically update when data changes.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   2. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

### Command summary

#### Viewing Recipients

Action 	| Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**List all recipients**   | `list`
**Summary of organs needed**   | `summary`
**Filter recipients by blood type**   | `bloodtype BLOOD_TYPE [BLOOD_TYPE]...`, eg. `bloodtype A+ B+`
**Filter recipients by organ**   | `organ KEYWORD`, eg. `organ liver`
**Filter recipients by priority**   | `priority PRIORITY [PRIORITY]...`, eg. `priority 1 2 3`
**Search for a recipient by name**   | `search KEYWORD [KEYWORD]`, eg. `search John`
**Combined search using name, organ, and/or blood type**   | `combined [n/NAME] [o/ORGAN] [b/BLOOD_TYPE]`, eg. `combined n/Alice o/kidney b/O+`
**Find recipients compatible with a blood type**   | `compatible BLOOD_TYPE`, eg. `compatible O-`

#### Modifying Recipients

Action 	| Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add a recipient**   	| `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Delete a recipient** | `delete INDEX`<br> e.g., `delete 3`
**Edit a recipient’s details**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
⚠️ **Clear all recipients**  | `clear` Note: **this will clear all recipients in the application.**

#### Utility Functions
Action 	| Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Access Last Command** | <kbd>Up</kbd> key
**Exit the application**   | `exit`
**View help** | `help`

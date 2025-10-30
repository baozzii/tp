---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Organ-izer User Guide

**Organi-zer** is a **desktop application for the management of organ recipients in a hospital**.
It is intended for **organ donation coordinators** who require easy, fast, and effective access to organ recipients
during the time window that an organ is available for donation.

If you are a coordinator for organ donation within a hospital or healthcare facility, **Organ-izer** allows you to:

* **Easily** view your list of potential organ recipients, complete with emergency contact details, blood type, and priority.

* **Efficiently** manage a database of potential organ recipients.

    * **Organ-izer** uses a **Command Line Interface** (CLI), which means that all functions of the application are accessible via typed commands.

    * If you can type fast, **Organ-izer** can coordinate organ donation much faster than traditional mouse-based applications.

* **Securely** store information about users locally without external internet connection.

**Organ-izer** is based on the existing AddressBook Level 3 (AB3) project, and is optimized for the time-critical environment under which organ donation occurs in a hospital.

<box type="info" seamless>
If you wish to navigate this page quickly, feel free to use the **table of contents** on the right
</box>

<!-- * Table of Contents -->

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   <box type="info" seamless>
   You can **check your Java version** by following [this guide](#https://www.java.com/en/download/help/version_manual.html). <br>
   How to install Java `17`: [Windows](https://se-education.org/guides/tutorials/javaInstallationWindows.html) | [Mac](https://se-education.org/guides/tutorials/javaInstallationMac.html) | [Linux](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
   </box>

1. Download the latest version of **Organ-izer** from [here](https://github.com/AY2526S1-CS2103T-T17-3/tp/releases). You should find a file named `organ-izer.jar` in your downloads.

1. Make a folder and place `organ-izer.jar` into it.

1. If Java `17` is correctly installed, double-clicking `organ-izer.jar` should launch the application. <br>

   A screen similar to the one below should be visible after a few seconds. Note that the app contains some sample data.<br><br>
   ![Ui](images/Ui.png)
   <br><br>

1. To start, try typing `list` in the command box at the top. **If it is your first time using the application, it will not display any data**.

1. To add a new user, try typing `
add n/Alice Pauline p/94351253 e/alice@example.com a/123, Jurong West Ave 6, #08-111 o/kidney b/O+ r/1 t/friends
`
1. A list of other commands you can try:
    * `list` : Lists all patients.

    * `clear` : Deletes all patients.

    * `exit` : Exits the app.

1. `Organ-izer` has many other useful commands to help with donation coordination. Details about all available commands can be found in the [Features](#features) section below.

<box type="info" seamless>

If you **forget the format for the `add` command**, you can always press the `tab` key after typing `add` to make use of our **autofill** feature.

If you **forget how to use any command**, simply typing in the command and pressing `enter` will display information on how to use the command.

</box>

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a recipient: `add`

Adds a recipient to the **Organ-izer**.

Format: `add n/NAME p/PHONE e/EMAIL a/ADDRESS o/ORGAN b/BLOOD TYPE r/PRIORITY [en/EMERGENCY_NAME] [ep/EMERGENCY_PHONE] [er/EMERGENCY_RELATION] [t/TAG]...`

<box type="tip" seamless>

**Tip:** A recipient can have any number of tags (including 0)
</box>

<box type="note" seamless>

**Note:**
Names should only contain alphanumeric characters and spaces, and it should not be blank.

Phone numbers should only contain numbers, and it should be at least 3 digits long.

Emails should be of the format local-part@domain and adhere to the following constraints:
1. The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). The local-part may not start or end with any special characters.
2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods.
   The domain name must:
    - end with a domain label at least 2 characters long
    - have each domain label start and end with alphanumeric characters
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.

</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 o/kidney b/O+ r/1 en/Jane Doe ep/91234567 er/spouse t/friends t/owesMoney`

### Tab Completion for `add` command

Auto creates all required and optional prefixes for the `add` command.

Format: `add` then press the <kbd>tab</kbd> key

<box type="note" seamless>

**Note:** The tab completion feature brings the cursor to the first field to be filled in
</box>

Examples:
* `add` followed by <kbd>tab</kbd> gives `add n/ p/ e/ a/ o/ b/ r/ t/ en/ ep/ er/`
  
### Listing all recipients : `list`

Shows a list of all recipients in the Organiser.

Format: `list`

### Finding compatible blood types: `compatible`

Finds and lists all recipients whose blood type is compatible with the specified donor blood type. 

* Compatible means they can receive from someone with the specified blood type.

Format: `compatible BLOOD_TYPE`

<box type="note" seamless>

**Note:** Blood types must be one of A+, A-, B+, B-, AB+, AB-, O+, O-
</box>

Examples:
* `compatible O-` Finds all recipients whose blood type is compatible with O-.

### Finding recipients by organ : `organ`

Finds and lists all recipients in **Organ-izer** whose organ contains the specified substring. 

* Substring matching is case insensitive.

Format: `organ ORGAN`

<box type="note" seamless>

**Note:** Organ must be all alphabetical characters or spaces, but first letter must alphabetical
</box>

Examples:
* `organ kidney` Finds all recipients in **Organ-izer** whose organ contains `kidney` as a substring.

### Finding recipients by bloodtype : `bloodtype`

Finds and lists all recipients in **Organ-izer** whose blood type matches the specified blood types.

Format: `bloodtype BLOODTYPE`

<box type="note" seamless>

**Note:** BLOODTYPE must be A+, A- B+, B- AB+, AB-, O+ or O- only
 and can accept multiple values to find recipients of either blood type
</box>

Examples:
* `priority 1 2` Finds all recipients in **Organ-izer** whose priority is either `1` or `2`.
* `priority 3` Finds all recipients in **Organ-izer** whose priority is 3.

### Finding recipients by priority : `priority`

Finds and lists all recipients in **Organ-izer** whose priority matches the specified priorities.

Format: `priority PRIORITY`

<box type="note" seamless>

**Note:** Priority must be a number from 1-5, and can accept multiple values to find recipients of either priority value
</box>

Examples:
* `priority 1 2` Finds all recipients in **Organ-izer** whose priority is either `1` or `2`.
* `priority 3` Finds all recipients in **Organ-izer** whose priority is 3.

### Finding recipients by multiple criteria: `combined`

Finds and lists all recipients in **Organ-izer** that match all specified criteria. 

Format: `[n/EXACT_NAME] [o/ORGAN_SUBSTRING] [b/RECIPIENT_BLOOD_TYPES]`

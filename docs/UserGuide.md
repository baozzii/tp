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

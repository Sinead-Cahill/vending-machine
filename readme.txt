Project Instructions
IDE: IntelliJ IDEA Community Edition 2019.3.4
Java Version: 14

To Build:
	1)File 
	2)New Project -> VendingMachineProject
	3)Maven (SDK: 14) -> create from archetype: ‘org.openjfx:javafx-maven-archetypes’
	4)Update archetypeArtifactId property -> javafx-archetype-simple
	5)Create new property -> javafx-version: 14.

To Run:
	1)Add all attached files to project.
	2)Update the FileInputStream file location accordingly in the class FileInputService.java

NB.  The file inputs are read as follows:
     Products -> Name, Location, price, quantity
     Clients ->  Name, Credit, Password
     Admins -> Name, Password

	3)Update the FileOutputStream file location accordingly in the class FileOutputService.java
	4)Run Program

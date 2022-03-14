EverLib:
a wpilib project template with a unique motor system.

setting up:
to change the team number and year go to .wpilib/wpilib_preferences.json and change the values there.
when declaring a new motor set it up in Constants.java under the static class "UsableMotors" and initialize the motor in the method "init()" (this is not necessary unless you want to use the record movements feature).
when running different methods run the methods with higher importance last.

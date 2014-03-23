This project is for the Planning Poker module.  The project contains entry 
points for both Janeway and the Core.

Compiling
=========

Eclipse
-------

In order to compile this module, make sure that you have imported it properly 
into your Project Explorer. You should then be able to select "PlanningPoker"
in your Project Explorer and click `Project -> Build Project` in your Eclipse
menu bar. You can also secondary click (right-click) the build.xml file under
the PlanningPoker project and choose `Run As -> Ant Build`.

UNIX Command Line
-----------------

`cd` into the PlanningPoker directory and run the command `ant`.

If the build was successful, you should see a "PlanningPoker.jar" file in the
Janeway modules directory.
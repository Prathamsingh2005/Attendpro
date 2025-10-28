@echo off
echo Starting AttendPro Java Backend...
echo.
echo Make sure you have:
echo 1. Java 11+ installed
echo 2. MySQL running on localhost:3306
echo 3. Database 'attendpro' created
echo.
echo If you have Maven installed, run: mvn spring-boot:run
echo Otherwise, compile and run manually:
echo.
echo javac -cp "lib/*" src/main/java/com/attendpro/*.java
echo java -cp "lib/*;src/main/java" com.attendpro.AttendProApplication
echo.
pause

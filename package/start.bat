@echo off

for /f "tokens=1,2 delims==" %%a in (config.ini) do (
	if %%a==jrepath set jrepath=%%b
	if %%a==debug set debug=%%b
	if %%a==port set port=%%b
)

if %debug% == 1 %jrepath%\bin\java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=%port% -classpath "lib/*" com.maohi.software.maohifx.client.Main
if %debug% == 0	start %jrepath%\bin\javaw -classpath "lib/*" com.maohi.software.maohifx.client.Main

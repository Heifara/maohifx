@echo off

for /f "tokens=1,2 delims==" %%a in (config.ini) do (
	if %%a==jrepath set jrepath=%%b
)

%jrepath%\bin\java -classpath "lib/*" com.maohi.software.maohifx.client.Main
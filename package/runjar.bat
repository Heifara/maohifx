@echo off

for /f "tokens=1,2 delims==" %%a in (config.ini) do (
	if %%a==jrepath set jrepath=%%b
)

%jrepath%\bin\java -jar lib/maohifx.client-0.0.1-SNAPSHOT.jar
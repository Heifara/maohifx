@echo off

for /f "tokens=1,2 delims==" %%a in (config.ini) do (
	if %%a==jrepath set jrepath=%%b
)

%jrepath%\bin\java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5044 -classpath "lib/*" com.maohi.software.maohifx.client.Main
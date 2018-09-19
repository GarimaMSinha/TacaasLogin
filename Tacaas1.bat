set projectLocation=C:\Users\IBM_ADMIN\Desktop\selenium\Trial\TaccasLogin

cd %projectLocation% 

set classpath=%projectLocation%\bin;%projectLocation%\lib\*

java org.testng.TestNG %projectLocation%\testng.xml

pause
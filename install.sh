NAME=afp
echo ---------------------------------------
echo INSTALACION PROYECTO: $NAME
echo ---------------------------------------

sudo service $NAME stop
git pull origin master
mvn -f projects/afpcalculator clean install
mvn -f projects/afp clean package
sudo service $NAME start

DIR=projects/afp
NAME=afp
echo ---------------------------------------
echo INSTALACION
echo Proyecto '$NAME'
echo Directorio: $DIR
echo ---------------------------------------

sudo service $NAME stop
git pull origin master
mvn -f $DIR clean package
sudo service $NAME start

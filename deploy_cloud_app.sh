#mkdir -p cloud-deploy
#mvn clean install ./editnow-core for actual jar
#cp ./editnow-core/manifest.yml ./cloud-deploy/manifest.yml
cp ./editnow-core/backend/target/backend-0.0.1-SNAPSHOT.jar ./cloud-deploy/editnow-core.jar
cd ./cloud-deploy
cf push

#bin/bash

IMAGE_NAME="docker-testdrive"

export PATH="/home/patiem/.minishift/cache/oc/v3.7.2/linux:$PATH"

oc login -u developer
docker login -u `oc whoami` -p `oc whoami -t` 172.30.1.1:5000
oc create is ${IMAGE_NAME} -n myproject
docker tag ${IMAGE_NAME} 172.30.1.1:5000/myproject/${IMAGE_NAME}
docker push 172.30.1.1:5000/myproject/${IMAGE_NAME}

oc new-app --image-stream=${IMAGE_NAME} --name=${IMAGE_NAME}
oc expose service ${IMAGE_NAME}

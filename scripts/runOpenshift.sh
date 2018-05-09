#bin/bash


export PATH="/home/patiem/.minishift/cache/oc/v3.7.2/linux:$PATH"

oc cluster up

oc login -u developer
docker login -u `oc whoami` -p `oc whoami -t` 172.30.1.1:5000


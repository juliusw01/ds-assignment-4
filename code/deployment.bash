#!/usr/bin/env bash

echo "  _____             _                                        _"
echo " |  __ \           | |                                      | |"
echo " | |  | | ___ _ __ | | ___  _   _ _ __ ___  _ __   ___ _ __ | |_"
echo " | |  | |/ _ \ '_ \| |/ _ \| | | | '_ \` _ \| '_ \ / _ \ '_ \| __|"
echo " | |__| |  __/ |_) | | (_) | |_| | | | | | | | | |  __/ | | | |_"
echo " |_____/ \___| .__/|_|\___/ \__, |_| |_| |_|_| |_|\___|_| |_|\__|"
echo "             | |             __/ |"
echo "             |_|            |___/"
echo

if ! helm version
then
  echo
  echo "Helm was not found. Installing it now..."
  brew install helm
  echo
fi

if ! minikube status
then
  echo
  echo "minikube not started. starting it now"
  minikube start
fi

echo

if ! minikube image ls | grep docker.io/library/orderservice:latest
then
    minikube image build -t orderservice ./orderservice
fi

if ! minikube image ls | grep docker.io/library/productservice:latest
then
    minikube image build -t productservice ./productservice
fi

# Install the orderservice
helm install orderservice orderservice/orderservice --atomic

# Install the productservice
helm install productservice productservice/productservice --atomic

#Install the envoy gateway
helm install envoy Envoy/envoy --atomic

if [ $? -eq 0 ]; then
    echo "Deployment successful. Please access the app with this URL"
    echo
    minikube service envoy --url
fi

#Expose the envoy gateway to localhost
#export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=envoy,app.kubernetes.io/instance=envoy" -o jsonpath="{.items[0].metadata.name}")
#export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
#kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT
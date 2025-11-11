#!/usr/bin/env bash

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

#Expose the envoy gateway to localhost
export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=envoy,app.kubernetes.io/instance=envoy" -o jsonpath="{.items[0].metadata.name}")
export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT
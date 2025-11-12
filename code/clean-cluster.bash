#!/usr/bin/env bash

helm uninstall orderservice
helm uninstall productservice
helm uninstall envoy
helm uninstall postgres-operator
helm uninstall postgres-operator-ui
helm uninstall rabbitmq
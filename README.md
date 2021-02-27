# Environments requirements
In this spike I will use minikube like k8s local environment. In order to speed up a fresh minikube instance for this purpose 
you can use this commnad:
```minikube start --vm-driver=virtualbox --cpus 4 --memory 8192 -p consul```

tip. if you want restart a consul minukube instance you can do like below:
```minikube start --vm-driver=virtualbox -p consul```

# Application build
All the applications are spring cloud app, you have compile with mvn command and then build the images.

first tip type this command in the docker build shell in order to use the same local docker registry of miniukbe otherwise 
you will not be able to use the built docker image for your minikube environment. 
```eval $(minikube --profile consul docker-env)```

after that move with the same shell session in which you had run the previous command under helloservice folder 
build with maven and typ this command ```docker build -t mrflick72/consule-spike_hello-service:latest .``` 
do the same for helloservice-client but of course use a different command for build docker image like this 
```docker build -t mrflick72/consule-spike_hello-service-client:latest .```

And that's all images built

# Consul installation
In order to install a consul cluster in your minikube environment one option is the follow.
- install helm in your local pc
- add bitnami helm chat in your repo with this command ```helm repo add bitnami https://charts.bitnami.com/bitnami```
- install the helm chart ```helm install consul -f values.yaml bitnami/consul```

in order to uninstall consul you can do with the usual helm command like this: ```helm uninstall consul```

after this simple step you can apply a service portforward to the ui service in order to access to the consul ui like below: 

```
kubectl port-forward svc/consul-ui 8500:80
http://localhost:8500/ui
```

for chart customization please refer to this [link](https://github.com/bitnami/charts/tree/master/bitnami/consul)

# Distributed configuration
In order to delivery dynamic configurations in this spike I experimented consul key/value feature.
Only one project will benefit of thsi feature due to I require only in one project, in order to facilitate the job I provided 
a terraform script under config folder that facilitate the configuration step.

first apply portforwarding for the consul-headless service in order to connect from your local pc to the remote consul on minikube

```kubectl port-forward svc/consul-headless 8500:8500```

then type the command ```terraform init``` in order to initialize terraform for use consul provider, then apply the command 
```terraform apply -auto-approve``` and that's it.


# App installation
After that consul is installed, applications are built and pushed in your local shared with minikube docker registry you have to 
install you app in kubernetes. in order to do that you can move under the single project folder and use the command
```kubectl apply -f kubernetes.yml``` in order to install the two applications.

In order to test all the flow you should get the local ip fo you minikube cluster with this command ```minikube ip -p consul``` 
and use this ip to access on the web resources like this ```http://your-minikube-ip/hello-service-client/say-hello-to/valerio```


# Basic Consul admin

- Get all the services: ```curl http://localhost:8500/v1/agent/services```.
- Deregister one specific service: ```curl -X PUT  http://localhost:8500/v1/agent/service/deregister/:service_id ```
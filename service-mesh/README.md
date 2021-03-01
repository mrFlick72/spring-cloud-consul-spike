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
- add bitnami helm chat in your repo with this command ```helm repo add hashicorp https://helm.releases.hashicorp.com```
- install the helm chart under service-mesh folder type the command: ```helm install -f values.yaml hashicorp hashicorp/consul```

in order to uninstall consul you can do with the usual helm command like this: ```helm uninstall hashicorp```

after this simple step you can apply a service portforward to the ui service in order to access to the consul ui like below: 

```
kubectl port-forward service/hello-service-client 8080:8080
kubectl port-forward service/hashicorp-consul-ui 8500:80 --address 0.0.0.0
http://localhost:8500/ui
```

for chart customization please refer to this [link](https://github.com/bitnami/charts/tree/master/bitnami/consul)

# App installation
After that consul is installed, applications are built and pushed in your local shared with minikube docker registry you have to 
install you app in kubernetes. in order to do that you can move under the single project folder and use the command
```kubectl apply -f kubernetes.yml``` in order to install the two applications.

In order to test all the flow you should get the local ip fo you minikube cluster with this command ```minikube ip -p consul``` 
and use this ip to access on the web resources like this ```http://your-minikube-ip/hello-service-client/say-hello-to/valerio```


# Basic Consul admin

- Get all the services: ```curl http://localhost:8500/v1/agent/services```.
- Deregister one specific service: ```curl -X PUT  http://localhost:8500/v1/agent/service/deregister/:service_id ```

# Some screenshot 

![](images/consul_services.png)

![](images/consul_key_value.png)

minikube start --vm-driver=virtualbox --cpus 4 --memory 8192 -p consul
minikube start --vm-driver=virtualbox -p consul

eval $(minikube --profile consul docker-env)

docker build -t mrflick72/consule-spike_hello-service:latest .

docker build -t mrflick72/consule-spike_hello-service-client:latest .

kubectl port-forward svc/consul-ui 8500:80
http://localhost:8500/ui

helm repo add bitnami https://charts.bitnami.com/bitnami
helm install consul bitnami/consul
helm install consul bitnami/consul
helm uninstall consul 


```
NAME: consul
LAST DEPLOYED: Fri Feb 26 12:55:42 2021
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
** Please be patient while the chart is being deployed **

  Consul can be accessed within the cluster on port 8300 at consul-headless.default.svc.cluster.local

In order to access to the Consul Web UI:

1. Get the Consul URL by running these commands:

    kubectl port-forward --namespace default svc/consul-ui 80:80
    echo "Consul URL: http://127.0.0.1:80"

2. Access ASP.NET Core using the obtained URL.

Please take into account that you need to wait until a cluster leader is elected before using the Consul Web UI.

In order to check the status of the cluster you can run the following command:

    kubectl exec -it consul-0 -- consul members

Furthermore, to know which Consul node is the cluster leader run this other command:

    kubectl exec -it consul-0 -- consul operator raft list-peers

```



docker run -d --net=host -e 'CONSUL_LOCAL_CONFIG={"leave_on_terminate": true}' consul agent -bind=172.17.0.9

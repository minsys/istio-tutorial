minishift profile set tutorial 
minishift config set memory 8GB 
minishift config set cpus 3 
minishift config set image-caching true 
minishift addon enable admin-user 
minishift addon enable anyuid 
minishift config set openshift-version v3.11.0
minishift start 
@FOR /f "tokens=*" %i IN ('minishift oc-env') DO @call %i
@FOR /f "tokens=*" %i IN ('minishift docker-env') DO @call %i
@FOR /f "tokens=*" %i IN ('minishift ip') DO set ip=%i
oc login %ip%:8443 -u admin -p admin
cd ..\istio-1.0.4
minishift openshift config set --target=kube --patch "{   \"admissionConfig\" : {      \"pluginConfig\" : {         \"MutatingAdmissionWebhook\" : {            \"configuration\" : {               \"apiVersion\" : \"v1\",               \"disable\" : false,               \"kind\" : \"DefaultAdmissionConfig\"            }         },         \"ValidatingAdmissionWebhook\" : {            \"configuration\" : {               \"apiVersion\" : \"v1\",               \"disable\" : false,               \"kind\" : \"DefaultAdmissionConfig\"            }         }      }   }}"
oc apply -f install/kubernetes/helm/istio/templates/crds.yaml 
oc apply -f install/kubernetes/istio-demo.yaml
oc project istio-system 
oc expose svc servicegraph 
oc expose svc grafana 
oc expose svc prometheus 
oc expose svc tracing
set PATH=C:\Users\Hugo\Documents\Code\java\istio-1.0.4\bin:%PATH%
cd ..\istio-tutorial\customer\java\microprofile
oc new-project tutorial
oc adm policy add-scc-to-user privileged -z default -n tutorial 
mvn clean package thorntail:package
docker build -t example/customer . 
oc apply -f depl.yml -n tutorial
oc create -f ../../kubernetes/Service.yml -n tutorial 
oc expose service customer
cd ..\..\..\preference\java\microprofile
mvn clean package thorntail:package
docker build -t example/preference:v1 .
oc apply -f depl.yml -n tutorial
oc create -f ../../kubernetes/Service.yml
cd ..\..\..\recommendation\java\microprofile
mvn clean package thorntail:package
docker build -t example/recommendation:v1 .
del depl_v1.yml
istioctl kube-inject -f ../../kubernetes/Deployment.yml > depl_v1.yml
REM Increase Liveness and Readiness Probe delays to 60s in depl_v1.yml
oc apply -f depl_v1.yml -n tutorial
oc create -f ../../kubernetes/Service.yml
REM Change v1 to v2 in string
cd ..\..\..\recommendation\java\microprofile
mvn clean package thorntail:package
docker build -t example/recommendation:v2 .
del depl_v2.yml
istioctl kube-inject -f ../../kubernetes/Deployment-v2.yml > depl_v2.yml
oc apply -f depl_v2.yml -n tutorial
cd ..\..\..\
oc -n tutorial create -f istiofiles/destination-rule-recommendation-v1-v2.yml
oc -n tutorial create -f istiofiles/virtual-service-recommendation-v1.yml
oc delete virtualservice/recommendation -n tutorial 
REM Dark Launch
oc delete virtualservice recommendation -n tutorial 
oc delete destinationrule recommendation -n tutorial 
REM rest of dark launch is skipped
REM Egress

# B2C Adapter Project

This project is part of the KBC Group B2C Use Case which leads to their middleware evolution platform using OpenShift Container Platform (OCP). All this microservices will serve as a direct interface between business clients and orchestration microservices layer. 

## Clone the Project

On your local, type the following command to clone the project:

```bash
git clone https://kcb-dev.visualstudio.com/KCB%20Tech%20Delivery/_git/b2c-fundtransfer-adapter-project
```

## Deploy the Project in OCP

Change to your cloned project directory to execute next steps using the following instruction:

```bash
cd /path/to/your/cloned/project/directory
```

Login to KCB OpenShift Platform typing the following command:

```bash
oc login --token=<token> --server=https://api-default.apps.dev.aro.kcbgroup.com:443
```

(Optional) Create a new project on KCB OpenShift Platform with below command:

```bash
oc new-project demo-project --description="Demo Project" --display-name="Demo Project"
```

(Optional) Check the created project on KCB OpenShift Platform executing the next instruction:

```bash
oc get projects
```

Get into the created project on KCB OpenShift Platform using the following command:

```bash
oc project demo-project
```

Clean, test and package the project using below instruction: 

```bash
mvn clean package
```

Deploy the project directly to KCB OpenShift Platform executing the next instruction:

```bash
mvn fabric8:deploy -Popenshift
```

Finally, after deploying the project, check the new PODs on KCB OpenShift Platform with the next command:

```bash
oc get pods -w
```

This project will create automatically a new **Route** that exposes services to the internet with the following URI:

```bash
http://b2c-fundtransfer-adapter-project-demo-project.apps.dev.aro.kcbgroup.com/
```

## Test the Project

First, check the status of the project:

```bash
curl --location --request GET 'http://b2c-fundtransfer-adapter-project-demo-project.apps.dev.aro.kcbgroup.com/health'
```

Then, send a POST message to the API endpoint:

```bash
curl --location --request POST 'http://b2c-fundtransfer-adapter-project-demo-project.apps.dev.aro.kcbgroup.com/api/fundTransfers' \
--header 'Authorization: Basic abc=' \
--header 'Content-Type: application/json' \
--data '{
    "header": {
        "messageID": "test0000002",
        "serviceName": "b2cfundtransfer",
        "routeID": "01",
        "channelID": "01",
        "messageTimeStamp": "11111",
        "serviceVersion": "1.0",
        "callBackURL": "http://b2c-fundtransfer-adapter-project:8080/api/callback"
    },
    "payload": {
        "accountInfo": {
            "debitAcctNumber": "1218577266",
            "creditAcctNumber": "1184392552"
        },
        "transactionInfo": {
            "type": "ACZT",
            "debitAmount": "1",
            "debitCurrency": "KES",
            "debitRef": "1111",
            "creditRef": "1111",
            "companyCode": "KE0010001",
            "cbsFunctionCode": "",
            "orderingBank": "KCBK",
            "branchCode": "4001",
            "reference1": "1111",
            "rrn": "1111",
            "messageType": "200",
            "timeStamp": "1111"
        },
        "additionalInfo": {
            "paymentInfo": "b2c transaction",
            "comissionType1": "SAFMPESA",
            "comissionType2": "MOBEEMPESA",
            "countryCode": "404",
            "amtCurrency": "00002111",
            "uniqueReference": "",
            "systemTraceAuditNumber": "1111",
            "serviceRestrictionCode": "07",
            "batchDetails": "details"
        }
    }
}'
```

## Dependencies
#### Redis    
Please refer to the **b2c-dependencies-setup-artifacts** project to install Redis pod.

#### ActiveMQ
Please refer to the **b2c-dependencies-setup-artifacts** project to install ActiveMQ pods.

#### Related Projects
Please consider installing the following projects to complete the flow:
- b2c-fundtransfer-ms-project
- b2c-fundtransfer-mock-project
- b2c-funstramsfer-reversal-project
- b2c-mpesa-ms-project

## About the Project
If you change the name of the project, namespaces and some data listed in this file will also change. This projects was developed using 7.4.0.fuse-sb2-740019-redhat-00005 BOM. See [Red Hat Maven Repository](https://maven.repository.redhat.com/ga/org/jboss/redhat-fuse/fuse-springboot-bom/7.4.0.fuse-sb2-740019-redhat-00005/fuse-springboot-bom-7.4.0.fuse-sb2-740019-redhat-00005.pom)


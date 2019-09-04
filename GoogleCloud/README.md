# Deploy docker image to Google Cloud

There is a step-by-step guide how to deploy the indicore backtester image in Google Cloud. Please pay attention - usage of the mentioned services of Google is not free and you would better to know the costs in advance.

1. Login to Google Cloud Console https://console.cloud.google.com/compute/instances
2. Press "Create" button  
![NewVM](img/NewVM.JPG)
3. Set Name, Machine type (f1-micro is enough)  
![VMName](img/VMName.JPG)
4. Choose "Deploy a container image to this VM instance", enter Container image - docker.io/gehtsoft/indicore-backtest, choose "Allow HTTP traffic" and press "Create" button  
![VMContainer](img/VMContainer.JPG)
5. Wait till VM instance is created, and state changed to "Running". BacktestUtils REST API is available at External IP on port 80  
![ViewVM](img/ViewVM.JPG)

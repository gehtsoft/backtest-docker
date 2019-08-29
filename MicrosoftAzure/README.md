# Deploy docker image to Microsoft Azure

1. Login to Microsoft Azure https://portal.azure.com
2. Go to App Services then press "Add" button  
![NewService](img/NewService.JPG)
3. Set Subscription, Resource Group (create new if neccessary), Name, Publish - Docker Image, Operating system - Linux, App Service Plan (F1 is enough), then press "Next: Docker" button  
![ServiceName](img/ServiceName.JPG)
4. Choose Image Source - Docker Hub, Image and tag - gehtsoft/backtestutils, then press "Review and create" button  
![ServiceContainer](img/ServiceContainer.JPG)
5. Press "Create" button  
![CreateService](img/CreateService.JPG)
6. Wait till deployment complete and press "Go to resource" button
![ServiceComplete](img/ServiceComplete.JPG)
7. BacktestUtils rest deployed to URL on port 80  
![ViewService](img/ViewService.JPG)
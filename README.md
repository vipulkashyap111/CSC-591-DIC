# CSC-591-DIC
Distributed In-Memory Key Value Store

This project is based off of Amazon's Dynamo [paper](http://s3.amazonaws.com/AllThingsDistributed/sosp/amazon-dynamo-sosp2007.pdf) with certain design modifications as specified in the [report](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/DIC-Report%20Final.pdf).  

## System architecture: 
![System Architecture](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/images/System%20architecture.png)  
The components present are Client, Proxy Server, Request Coordinators and Memory Nodes.  
## Sequence diagram:
![Sequence Diagram](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/images/Sequence%20diagram.png)  

Client sends the get or put request to the Proxy server to retrieve the IP address of the request
coordinator. Primary purpose of request proxy server is to balance the load of incoming requests
to all the coordinators in a round-robin fashion. Upon receiving the IP of the current request
coordinator, client now sends the request directly to the coordinator. Note that this process
happens in the background and not visible to the client. This is done so that proxy server only
does the work of load balancing.  

Request coordinator now computes hash from the key to determine which memory node to
choose from the ring structure (explained below) to store this key value pair. The request
coordinator then sends the request to the primary memory node and to it's next N replicas in
the ring at the same time. We follow quorum protocol to acknowledge successful read or write
operation. After successful acknowledgement from all at least W or R memory nodes, request
coordinator acknowledges the client about the success or failure of the request.  

## Operations Supported:
1. put
2. get

## System Interface:
A client can interact with system via simple REST calls. A get request with the key would fetch
the value for that key and a post request with a key value pair would set the key in the memory
node. If a post request with an existing key is set, then it will overwrite the previously set value.
These requests can be sent by simple curl commands or by using utilities like Postman. To
make the task of sending these requests easier, we've integrated the client with Slack so that
requests can now be sent by using Slack commands instead of typing commands on terminal

## Usage
1. Clone this [repo](https://github.com/vipulkashyap111/CSC-591-DIC.git)
2. Create a jar file containing code in the [src](https://github.com/vipulkashyap111/CSC-591-DIC/tree/master/src) directory
3. Install Java v8 for proxy server, request coordinator and memory nodes. Install Java, Nodejs and npm for client. You can have a dedicated machine for proxy server, request coordinator and memory nodes can run on same machine. Since replication factor is 2, we need atleast one request coordinator and 2 memory nodes for successful write operation and 1 memory node for successful operation as per quorom protocol.
4. Run the following files in sequence:
  4.1 Start the proxy server on a machine: `java -cp jarfile.jar kv_proxy.ProxyProc`
  4.2 Start n request coordinators as needed passing prox:
      `java -cp jarfile.jar kv_requestCoordinator.Server <proxy server IP>`
  4.3 Start atleast 2 memory nodes: `java -cp jarfile.jar kv_memNodes.MemNodeProc`
  4.4 Start client: `java -cp jarfile.jar kv_client.ClientServer`
  4.5 Optionally you send put/get request via Slack slash commands. Follow this [tutorial](tutorial link) to set up slack           slash command for your team and add SLACK_TOKEN_GET and SLACK_TOKEN_PUT to your environment variables. You will also           need to use [ngrok](link for ngrok) to establish a tunnel for your slack slash command inorder to run slash commands on       local server.
      Go to [kv_slack] folder, run `npm install` to install all dependencies, run `node client.js` to start the Slack server.
5. Perform put/get operations in the datastore using HTTP requests. You can use cURL, Postman or Slack to perform HTTP requests.

## Sample Requests:
Postman put operation: 
![Postman](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/images/Sample%20put%20Postman.png)    
Slack put operation:  
![Slack put](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/images/Sample%20put%20slack.png)  
Slack get operation:
![Slack get](https://github.com/vipulkashyap111/CSC-591-DIC/blob/master/images/Sample%20get%20slack.png)

# KafkaWeb
A Sample app to push the data to kafka topic using spring boot

Follow the  below steps to run the app
 
1) Using Application class and run the application

2) To push the data to the kafka topic

	URL: http://localhost:8080/user
	Method : POST
	Content-Type: application/Json
	RequestBody:
	{
		"id":"1",
		"userName":"rakesh",
		"location":"chennai"
	}
	The topic name is "test" - given in the java files

3) use the Sample consumer file to run the kafka consumer or a kafka-console-consumer to validate the result.

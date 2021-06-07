					# RabbitMQ

docker run -d --name rabbitmq --hostname docker-rabbitmq -p 5672:5672 -p 15672:15672 -v c:/tmp/rabbitmq/data:/var/lib/rabbitmq/mnesia rabbitmq:management

a consumer is essentially a subscriber --->>> SEE RABBITMQ FOLDER FOR FANOUT DIAGRAM
				===============
				FANOUT EXCHANGE		SEE RABBITMQ FOLDER FOR FANOUT DIAGRAM
				================

 sometimes called Pub/Sub or Publish/Subscribe will broadcast message to all queues binding to it
 Example:
	if an emloyee is added or resigns from hr, both Accounting System and Marketing System needs to know about this data
	so that they can process data according to their needs
	
	Human Resource -------Accounting (CONSUMER)
		PRODUCER   \
					 \
						Marketing (CONSUMER)
						consumers === queue, 2 queues
						human resource will send message to both queues

SCHEMA				
Exchange name: q.[name]
Queue name: q.[name].[sub-name]

Fanout message needs no routing key
we practically blast-out message to every queue that has binding to it 
hence say we have 10 queues, all 10 queues will get published message

CLASSES USED -> 
Employee, HumanResourceProducer >>> Producer
Employee, AccountingConsumer, MarketingConsumer >>> Consumer

				================
				DIRECT EXCHANGE	(SINGLE CRITERIA) here we use file type	SEE RABBITMQ FOLDER FOR DIRECT DIAGRAM
				================
Send to selective queue(s) based on routing key and routing rule
however if routing does not match any rule message will be discarded and never sent to any queue

EXAMPLE:
	1 exchange
	2 queues
	if extension is jpg or png, its sent to queue 1
	while queue2 converts svg to png/jpg
	*****if gif or any other format, it will be discarded****
	
SCHEMA
Exchange name: q.picture	(3 routes [bindings] in total))
Queue1 : q.picture.image   >>>>>> for jpg n png				ROUTING KEY: jpg and png (2 bindings)		i.e queue is same but key is different
Queue2 : q.picture.vector  >>>>>> for processing vectors	ROUTING KEY: svg
	
CLASSES USED -> 
Picture, PictureProducer >>> Producer
Picture, PictureImageConsumer, PictureVectorConsumer >>> Consumer

				================
				TOPIC EXCHANGE	(MULTIPLE CRITERIA)
				================
				
STEPS to using topic: identity all criteria or keywords to sort by
here mobile, size and image type

message routing key must be list of words, delimited by dots	{{%%%}}	
Special character for binding
	* (star) can substitute/replace for exactly one word
	# (hash) can substitute/replace for zero or more words
				
				
EXAMPLE:
	1 exchange
	4 queues
	queue 1, queue 2:  process jpg and png
	queue 2 process svg to  png/jpg
	queue 4 process pictures from mobile regardless of their type
	queue 5 log only svg with large size(say more than 4000)
			
	remember {{%%%}} above
	Routing key structure we will be using: SOURCE.SIZE.TYPE
	SOURCE: web or mobile
	SIZE: small or large
	TYPE: png, jpg or svg
				
	ROUTING KEYS WILL BE USING TO ACHIEVE THE ABOVE
	================================================
	>>>> Red1 queue: ignore SOURCE and SIZE, process all image types		
	:::::::::    *.*.png 		:: below can also be *.*.jpg
	>>> Red2	#.jpg
	>>>> Green queue: handles all vectors ::::::::     #.svg or *.*.svg
	
	>>>> Orange queue: all picture that comes from mobile :::::::::::   mobile.# or mobile.*.*
	
	>>>> Purple queue: two criteria, large picture and must be vector :::::    *.large.svg
	
	
SCHEMA
Exchange name: q.picture2	(5 routes [bindings] in total))
Queue1, Queue2 : q.picture.image   >>>>>> for jpg AND png				ROUTING KEY: *.*.png		i.e queue is same but key is different
Queue3 : q.picture.vector  >>>>>> for processing vectors	ROUTING KEY: #.svg	
Queue4 : q.picture.filter  >>>>>> for processing any pix from mobile	ROUTING KEY: mobile.#
Queue5 : q.picture.log  >>>>>> logging large svgs more than 4000	ROUTING KEY: *.large.svg	

CLASSES USED -> 
Picture, PictureProducer2 >>> Producer
Picture, PictureImageConsumer, PictureVectorConsumer >>> Consumer

				
				================
				HEADERS EXCHANGE (MULTIPLE CRITERIA) WE USE ARGUMENTS INSTEAD OF ROUTING KEY
				================
it can use special x-match which can act as boolean logic		
	"all" (default) [acts like boolean AND]
	"any" [acts like boolean OR]
	
Criterias: color and material
Prmotion: discount, free delivery
Details:
	white AND wood : discount 
	red AND steel : discount 
	red OR wood : free-delivery 
	
Example
	Christmas Promotion
	1 exchange
	
	queue 1 process jpg or png
	queue 2 process svg to  png/jpg
				
				
SCHEMA
Exchange name: q.promotion	(5 routes [bindings] in total))
Queue1 : q.promotion.discount		::: this is AND so need to do x-match all, as this is default
Queue2 : q.promotion.discount		::: this is AND so need to do x-match all, as this is default
Queue3 : q.promotion.free-delivery   ::: this is OR so set x-match any

CLASSES USED -> 
Picture, PictureProducer2 >>> Producer
Picture, PictureImageConsumer, PictureVectorConsumer >>> Consumer



			======================
				ERROR HANDLING
			======================
DLX -> dead letter exchange (Another Queue)
when a message time is more than ttl (time to live)
we can send it to another queue for procesing

do we basically assign create another queue to handle dead letter exchange say q.image.dlx
then images processed by say q.mypicture.image that throws an exception is routed to q.image.dlx

we do this by using the Dead letter exchange argument (s-dead-letter-exchange) and assign q.image.dlx ::: i.e where exceptions will go

NOTE: if we are not using fanout, we must assign routing key, 
x-dead-letter-routing-key: my_dlx_routing_key
so it knows where to go


ALTERNATIVE
===========
we can reject message manually rather than throwing exception
for this we set the Acknowledge mechanism to manual

spring.rabbitmq.listener.simple.acknowledge-mode = manual

Side EFFECT, we must tell RABBITMQ that our process is done:::::::  channel.basicAck(tag, false);
else the message will thrown back into the queue as unacknowledged


TTL DEMO (When no consumer is avilable)
======================
if no consumer is availble we can set a TTL  on queue after which 
message that exceed TTL can be passed to DLX queue

		
		=====================
		   RETRY MECHANISM
		=====================
SAMPLE

WORK, WAIT, and DEAD ::::: 3 pairs of exchange and queue

when message is sent to WORK, if an exception occurs after a time,
we send to WAIT queue, after a time on WAIT, it is sent back to work exchage, to achieve this we will use TTL and DLX

DEAD will contain messages which has been retried for N times and still has error


WAIT >>>> DLX for WORK
WORK >>> DLX for WAIT	::: for retrying, to move message back to work, we set TTL on WAIT QUEUE

ONE PUBLISHER and TWO CONSUMER
First Consumer (Image consumer) will consume from work queue and reject message that is not processable by sending to WAIT
after specific N retries, Image Consumer will publish to DEAD QUEUE

Second consumer comsumes from DEAD QUEUE and do maybe, logging unprocessable msgs or send notification to administrator

1. Check retry limit
2. if retry limit < threshold, send message to wait exchange
3. if retry limit reached, send message to dead exchange

*********************we will use X-Death header in each message as it holds retry count info


CLASS USED	:::--->>in  retry Package (consumer)


===============0===================
RETRY MECHANISM FOR FANOUT EXCHANGE
===============0===================
Though Work Queue is fanout
dead retry wait are direct
Hence we cant have duplicate messages. Check diagram


SCHEDULING
============

@EnableScheduling
CLASSES USED -> client package, scheduler package, RabbitmqQueue class


SPRING RETRY FOR DIRECT EXCHANGE
============
producer -->>SpringPictureProducer
consumer	--> SpringPictureConsumer
config application.yml >>>retries managed by spring


SPRING RETRY FOR FANOUT EXCHANGE
============
producer -->>SpringEmployeeProducer
consumer	--> SpringEmployeeConsumer
config application.yml >>>retries managed by spring

					
					
					## HALTING LISTENER FOR A PERIOD OF TIME
to halt listeners for a period of time in order to complete some actions
Example: halt processing of payment for aspecific time  (using cron job)

classes used -> 
producer ::DummyProducer and commandline runner
consumer ::RabbitmqScheduler
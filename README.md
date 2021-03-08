# 3Rivers API Developer Interview “Assignment”
## _Hello_

I enjoyed doing this project and I learned quite a lot on the way. I hope I have been able to demonstrate my Knowledge and my taste in design and development of api and micro services. Having said that, I need to clarify that I don't believe that this is a production ready application. There are a few points that need to be mentioned about this project. 

- This has been done on my personal laptop which is very old :(
- Many technologies don't work on my laptop or at least their new versions don't. To list a few:
-- Docker
-- Confluent Framework
-- Latest versions of almost any Database, Database Client ...
- Unfortunately, I was forced to make decisions that were not my first choice. Here are the top sacrifices I had to make:

| Subject | First Choice | Alternate | 
| ------ | ------ | ------ |
| Sinking data from Kafka Topic to db | kafka Connectors | Writing a Consumer |
| Searching for the last balance update | Kafka Streams API | Put it in DB as the last state |
## Micro Services
#### Event Simulator
This tiny micro service that just produces the Kafka events every few seconds. I haven't put much time and effort in it therefore there is not much to talk about.
#### Transaction Service
Transaction Service consumes transaction events from Kafka Topic, sinks the transaction into a database and exposes rest endpoints to fetch transactions from the database. 
#### Balance Service
Balance Service does pretty much the same for balance updates. The major difference is that Balance Service only keeps the latest update as the current state.

The two important services have a little API doc created by Swagger that is published on /api-docs once you run the service. And they are created by Spring Boot, hence the only strict requirement is met. (I would have done the same even if it was not required :D )

> There are lots of details in this project that I tried to take care of.
> Other than the decisions I was forced to make I have strong support
> of other major approaches that I opted for. I hope I get the chance to 
> discuss them. This kind of interview is really fun. I know I will 
> enjoy it.


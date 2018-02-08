package com.test;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.domain.User;

@RestController
@EnableAutoConfiguration
@ComponentScan("{com.kafka}")
public class Application {

    @PostMapping("user")
	public ResponseEntity<Void> addArticle(@RequestBody User user, UriComponentsBuilder builder) {
    	pushToTopic(user);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
    
    private String pushToTopic(User user) {
   
    	  Properties props = new Properties();
    	  // if you want to send the user string object to the topic
    	  props.put("bootstrap.servers", "localhost:9092");
    	  props.put("acks", "all");
    	  props.put("retries", 0);
    	  props.put("batch.size", 16384);
    	  props.put("linger.ms", 1);
    	  props.put("buffer.memory", 33554432);
    	  props.put("key.serializer", 
    	     "org.apache.kafka.common.serialization.StringSerializer");
    	  props.put("value.serializer", 
    	     "org.apache.kafka.common.serialization.StringSerializer");
    	  /*
      	// if you want to send the user object to the topic instead of string
  		  props.put("key.serializer", 
  		 "org.apache.kafka.common.serialization.ByteArraySerializer");
  		  props.put("value.serializer", 
  		 "org.apache.kafka.common.serialization.ByteArraySerializer");
  		  Producer<byte[], byte[]> producer = new KafkaProducer
  		     <byte[], byte[]>(props);
  		        
  		     producer.send(new ProducerRecord<byte[],byte[]>("test", user.toString().getBytes(), user.toString().getBytes()));
  		*/    	  
    	  Producer<String, String> producer = new KafkaProducer
    	     <String, String>(props);
    	        
    	     producer.send(new ProducerRecord<String, String>("test", 
    	    		 user.toString(), user.toString()));
    	           System.out.println("Message sent successfully");
    	     producer.close();
    	     return null;
    	           
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
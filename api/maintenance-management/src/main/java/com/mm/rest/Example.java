/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest;


import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author david
 */
public class Example {
    private final long id;
    private final String name;
    
  private static final AtomicLong counter = new AtomicLong(100);
    
    private Example(ExampleBuilder builder){
    this.id = builder.id;
    this.name = builder.name;
  }
  
  public Example(){
    Example cust = new Example.ExampleBuilder().id().build();
      this.id = cust.getId();
      this.name = cust.getName();
  }
  
  public Example(long id, String name){
      Example cust = new Example.ExampleBuilder().id()
           .name(name)
           .build();
      this.id = cust.getId();
      this.name = cust.getName();
  }
  
  public long getId(){
    return this.id;
  }

  public String getName() {
    return this.name;
  }
  
  @Override
  public String toString(){
    return "ID: " + id 
        + " Name: " + name;
  }  
  
  public static class ExampleBuilder{
    private long id;
    private String name = "";
    
    public ExampleBuilder id(){
      this.id = Example.counter.getAndIncrement();
      return this;
    }

    public ExampleBuilder id(long id){
      this.id = id;
      return this;
    }
    
    public ExampleBuilder name(String name){
      this.name = name;
      return this;
    }
    
    public Example build(){
      return new Example(this);
    }
    
  }    
}

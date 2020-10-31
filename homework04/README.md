# Streaming processing graph

## What it is?

## Main concepts

### record

**record** is a group of key-value pairs. It represents a single unit of data.  

![record](pics/record.png)

Our graph can accept records as input, transform them in different ways and then output them.  

Sometimes it is needed to mark the end of the dataset. (FINISH) 

![Terminal record](pics/terminal_record.png)

### Operator

**Operators** represent basic actions, which transform records in some way. Operators can be roughly divided into three types: mappers, reducers and joiners.  

#### Mapper

Mapper operator accepts a single record and outputs one or more new records. 

![Mapper](pics/mapper.png)

#### Reducer

Reducer operator accepts a sorted sequence of records, groups them by certain keys and outputs one or more new records for each group.

![Reducer](pics/reducer.png)

#### Joiner

Reducer operator accepts two sorted sequences of records and joins them on certain keys.

![Joiner](pics/joiner.png)

### Node

Each operator is contained inside a **Node** object. Node accepts records, transform them with its operator and then passes the result to the next nodes.

Each node has several input **gates**, which allows it to accept different streams of records (this is used in join operations, for example).  
   
![Node](pics/node.png)

### Graph

**Graph** object represents the actual processing graph - a group of interconnected Nodes. It exposes input nodes and output nodes.
 
![Graph](pics/graph.png)

## Our project

### Visualization

## Your task

Your task can be divided into two parts:
* Develop a "library" of operators.
* Use these operators to assemble processing graphs for solving actual practical problems.

### Operators library

You must implement the following operators (and pass tests for them):
* Mappers
* Reducers
* Joiners
    - InnerJoiner
    
### Practical problems

#### Problem 1
#### Problem 2

## Score

## Build and test
 
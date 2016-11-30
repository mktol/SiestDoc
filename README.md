# SiestaDoc
Develop web-based REST demo application which allows users to perform CRUD operations on documents* from multiple
content repositories. 
Application consist with 2 logical modules: Collector and Repository. 
Repository is REST service that contains documents and provide REST endpoints to perform CRUD operations on documents. 
Document is an entity with next JSON structure:
{
    "id": "<document id>",
    "name": "<document name>",
    "title": "<document title>",
    "indexes": {
        "<index name>": "<index value>",
        "...": "..."
    },
    "content": "<document text content>",
    "comments": [
        {
            "id": "comment id",
            "userId": "the id of the user which left the comment",
            "content": "comment text content"
        }
    ]
}

Collector is REST services that provides API for end user to perform CRUD operations on documents from all the repositories, which are registered in Collector service (this could be hard codded). 
	                                
				
Requirements
1.	Application should be RESTfull
2.	Application should expose next REST endpoints to:
    - get all documents (from all the repositories)
    - get document by id
    - add document
    - update (modify) document
    - delete document
3.	 The Repositories should contains initial collection of documents. It's OK to hardcode existing documents for a service in JSON file.
4.	The documents should be retrieved from the services as fast as possible, but restriction is - one REST call one Document, no bulk downloading.
5.	All retrieved documents should be cached in the database. Database type is H2, ORM - Hibernate.
6.	Application should be fault tolerant. If one of repositories crashed, application should react in a proper way.
7.	You are free to use any framework like Spring, Lombok, etc.
8.	Don’t forget about unit testing.
9.	Publish code on GitHub.
How to demo

1. Deploy 2 (or more) Repository services with different set of documents and one Collector service. 
2. Demonstrate all request in working environment.
3. Stop (crash) one of repositories and demonstrate working request.


Show your best and GOOD LUCK!

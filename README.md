# Thomas Millers githib repo downloader


This is a project where i wanted to have some fun with RxJava, GraphQl, Docker,

I have spent more time on this project then i would do for a coding challange however i could not help myself in playing around GraphQl and setting up a build pipline which would start the app with a fully confiuraed database using flyway.


The challange was to query github apis which would return a list of repos and from that response i would need to do an addtional api call. Hence using somthing which deals with streams and async makes things much easier

I was then to store those results which could then be queried via graphql. (I have never used graphql before but i have no idea why i have not come across it sooner and whydo not more people use it)



## To Run This project

### Pre required
You will need the following
 - [GitHub Access Token](https://help.github.com/en/articles/creating-a-personal-access-token-for-the-command-line)
 - Docker
 - graphql-playground (` brew cask install graphql-playground`)
 
### To build, dockerize and start
 
 - from the root of project run `./scripts/complete-start.sh <YOUR ACCESS TOKEN>` 
 
This will do the following
 - Stop
   - Stop the app if already running and rm container
   - Stop the db if already running and rm container
   - Remove the docker network
 - Build
    - Build the project
 - Dockerize 
    - Dockerize the jar and expose port 8080
 - Start
    - Create docker network
    - Start SQL database connected to the network with a given ip
    - Start the app (On startup:)
        - Will run the flyway migration (db schema)
        - It will pre populate the db with a 100 repos, if the access token is valid
        
    
 
 
 ### How to query
 
 Open Graphql playground and use the URL http://localhost:8080/graphql
 
 ```
query{
  topStars(count: 1){
    name
    fullName
    description
    startedUrl
    privateRepo
    
  
  }
}

```
Result
```
{
  "data": {
    "topStars": [
      {
        "name": "capsize",
        "fullName": "jnewland/capsize",
        "description": "A Capistrano extension for managing and running your app on Amazon EC2.",
        "startedUrl": "https://api.github.com/repos/jnewland/capsize/stargazers",
        "privateRepo": false
      }
    ]
  }
}
```
 
 
 
 ## To note
 Allot of the pain of having to making mutiple api calls could have been reduced if using the github v4 graphql as i could have used
 
 ```
 {
  search(query: "is:public", type: REPOSITORY, first: 50, after:"Y3Vyc29yOjUw" ) {
    repositoryCount
    pageInfo {
      endCursor
      startCursor
    }
    edges {
      node {
        ... on Repository {
          name
          stargazers {
            totalCount
          }
        }
      }
    }
  }
}
 ```
 
 ## Left to do
 
 Currently the app only downloads a 100 results for the 7 days as i did not get round to setup a paging system. Looking at the doc it looks simple but wanted to focus on the other cool areas.
 Would like to convert the project to kotlin
 Expose an endpoint which can refresh the data
 
 
 

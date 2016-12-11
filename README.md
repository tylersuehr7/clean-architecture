# Clean Android Architecture
An Android app that shows a sufficiently clean architecture for local database storage and persistent storage.

The application is split into three main packages: `data`, `tasks`, and `ui`:
The `data` package includes objects that allow us to use local data storage. This uses a repository-style design pattern, but mainly is the Bridge design pattern, and contains appropriate objects to map local data returned from the repository to a model object.

The `tasks` package includes asynchronous tasks that can be used to execute requests on our repositories.

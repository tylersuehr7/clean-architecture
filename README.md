# Clean Android Architecture
An Android app that shows a sufficiently clean architecture for local database storage and persistent storage.

The application is split into three main packages: `data`, `tasks`, and `ui`:

The `data` package includes objects that allow us to use local data storage. This uses a repository-style design pattern, but mainly is the Bridge design pattern, and contains appropriate objects to map local data returned from the repository to a model object.

The `tasks` package includes asynchronous tasks that can be used to execute requests on the repository.

The `ui` package contains everything needed to display views. This uses an MVC architecture because I'm not a fan of the MVP pattern. So I use Activities themselves as controllers.

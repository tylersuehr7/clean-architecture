# Clean Android Architecture
An Android app that shows a sufficiently clean architecture for local database storage and persistent storage.

### Architecture Overview
The application is split into three main packages: `data`, `domain`, and `ui`.

The `data` package includes objects that allow us to use local data storage. This uses a repository-style design pattern, but mainly is the Bridge design pattern, and contains appropriate objects to map local data returned from the repository to a model object. The repository uses the Command design pattern.

The `domain` package includes asynchronous tasks that can be used to execute requests on the repository, and is mainly all the business logic of the application.

The `ui` package contains everything needed to display views and allow the user to interact with the application; it uses the MVP design pattern.

![Structure Diagram](https://github.com/tylersuehr7/rss-clean-architecture/blob/master/img_application_structure.jpg "Structure Diagram")

### Screenshots
<img src="https://github.com/tylersuehr7/clean-architecture/blob/master/img_screenshot.png" width="200">

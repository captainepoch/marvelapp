# Architecture

This application is implemented using the MVVM pattern and Clean Architecture
principles.

## Single Activity

This application follows the guidelines to have one Activity and multiple
fragments.

Making a single Activity application keeps the task of handling the backstack
using a single source or truth (in this case, the navigation backstack). This
way, we do not encounter problems handling the state of multiple Activities
accross the lifecycle of the application.

The navigation backstack lives while the Activity lives. Once the application
is closed, the backstack is cleaned and the user will have to start from the
begining of the navigation.

The navigation is provided by the Navigation component, part of Jetpack.

## Modularization

The application is separated into different modules that provide different
functionality to the application:

-  `app`: this module contains all the Views and ViewModels of the application,
   along the Activity and resources only for the application. It is organized in
   features (and a common part).
-  `characters`: this module provides the functionality of the Characters and
   all the services related to it. This module contains the data and domain
   parts of the Clean Architecture dedicated to the characters feature.
-  `core`: this module contains all the shared classes and extensions for the
   whole application, along the Base clases.
-  `core-testing`: this module contains **only** the testing libraries and
   classes needed in the application. This will be a dependency in each module
   that requires testing.
-  `design`: this module contains the components used throught the application,
   along with shared resources accoss all the modules.

## MVVM (Model-View-ViewModel)

MVVM is a design patter that allows the developer to keep the View and the
ViewModel (known also as Presenter) decoupled, meaning that the ViewModel does
not know anything about the view.

<p align="center">
	<img
	src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png"
	width="700"/>
</p>

The View gets the data from the ViewModel by observing changes through and
observer. In this case, (Mutable)LiveData (but there are also MutableStateFlow,
MutableSharedFlow, StateFlow, SharedFlow, or Single or Observable using RxJava).

This way, we keep a single source of truth for the views, meaning that all the
data will live in the LiveData(s) that the View is observing, making the
restoration of the view while navigating an easy task.

## Clean Architecture

This application follows the Clean Architecture principles. Each layer has a
purpose inside the application.

The data is provided through a DataSource. This data source will gather the
information needed from the local cache (a database) or from the service.

The information retrived from the DataSource will be returned to the Repository.
The repository will handle one or multiple DataSources, process the information,
and send it to the UseCase.

The UseCase is the interactor for the ViewModel. The UseCase has defined its set
of Parameters and the return type of the data.

The ViewModel will collect the information from the UseCase and send it to the
View. The ViewModel might also prepare the data before sending it to the view,
adjusting some values or making mathematical operations. The View does not have
to handle this kind of operations.

The View then will handle the data accordingly to the State type (Success or
Failure), providing the user with a View filled with the information according
with each case.

### Concurrency

For being able to retrive information from a REST API the framework used for
creating and managing background jobs has been Kotlin's Coroutines.

This new framework provides an easy way to create Jobs, being able to have
background operations in the desired scope.

### Flow

To get the information from the Repository to the ViewModel, Kotlin's Flow has
been the choosen way.

Flow allows us to send multiple data values sequentially while executing it. It
also allows us to modify the data using the intermediate operators (normally.
`map`), allowing us to get data from one type and transforming it to another
type.

## Dependency injection

The framework used for providing dependencies through the application is
[Koin](koin). Koin provides a simple and easy way to declare modules and their
dependencies in order to provide ViewModels, UseCases, Repositories, and more,
to any part of the application when needed.

Also, it allows us to create Singleton objects in one line, making easy to keep
shared dependencies used as an unique dependencies.

## Making a cache

The application provides a simple caching mechanism for the list of characters.
Each time the user scrolls through the list (or the first time), the data
retrived from the service will be stored locally.

The next time the user opens the application, the first set of characters will
be retrived from the cache.

# Introduction #
This application is a simple web module which has spring security based authentication and authorisation with JSF2. The initial idea to start this project to play with some of the metawidget features. But eventually the application had other web framework features included.


---


# Features #

## Metawidget ##
Metawidget is used for all the beans. If you look at the XHTML files, the content is kept minimal, readable and maintainable. All the validations are done through annotations.


---


## Pure JSF2 based MVC architecture ##
There is a big discussion among the JSF developers on how to use MVC architecture with JSF2. JSF2 itself is taking care most of the MVC model implicitly. I have used a slightly different approach on using the controllers. The controllers are still the ManagedBeans but the domain model objects don't have the framework specific code or even the annotations. The controller beans extend the model objects and override the getters which require the annotations for validations and other UI decorations. Model objects are kept away from the framework specific code. This gives a very visible MVC architecture and make the code more testable also.

The navigations are defined inside the controller classes. faces-config.xml is only used for ResourceBundle definitions and converters.


---


## Domain Driven Design ##
Not a fully fledged domain driven design used though. But I use Repository patterns and make the User class as the aggregate root object.


---


## Spring Security ##
Integrating Spring Security with JSF2 is not an easy ride for most of the times. Here the User object is extending the UserDetails and spring-security based taglibs added in the web.xml to support the authorisation support at UI. The navigation menu is different for ANONYMOUS user and ROLE\_USER privileges.

Registration page is allowed for ANONYMOUS users. But the applications related pages require ROLE\_USER page.

In addition, InMemoryDao is used for keeping the Authentication details.
Login/Logout features supported.


---


## Templates Used ##
Masterlayout is used for header, side and footer bars and the content is only used at other pages.


---


## I18N Support ##
No messages are hard coded inside the xhtml or java files. Message bundle is used to support I18N.


---


## TestNG Support ##
Added a simple test class based on TestNG for the MockRepository class.


---


## Maven Support ##
Integration and build are completely done through maven.


---


## CSS Support ##
All the UI components styles are kept inside the css file, leaving the XHTML contents neat and tight!


---


## SLF4J Support ##
SLF4J is used for logging(Better than log4j or java.util.Logging)


---

# Example 06

A small web application, that serves as a reference point for the concepts presented in the lecture.


## Structure

The application follows a basic 3 layer structure, separating the persistence, business and presentation layer.

* **persistence**: A maven module to manage the database entities of the application.

  Similar to examples 04 and 05, the basic configuration can be found in the `persistence.xml` file.
  A noteworthy difference: Since we don't want to manually manage the persistence provider but use the one provided by the application server, some settings have to applied to the application server, rather than the application.
  In detail: the database connection is configured in the `example06-ds.xml` file (in the `example06-presentation` module) and configured to be available under the JNDI name `java:jboss/datasources/example06`.
  In the `persistence.xml`, this JNDI name is configured as the source of our entity manager.


* **business**: A maven module containing the RESTful application interface.

  The `NewsCRUD` class (in the `de.ls5.wt2.rest` package) is an exemplary class for handling resource requests using the tools of the JAX-RS specification presented in the lecture.
  The `de.ls5.wt2.conf` package holds additional configurations, such as the activator for the JAX-RS subsystem and a provider for the JSON (de-)serialization standard 'Jsonb'.
  Additionally an EJB Startup Bean (`StartupBean`) is provided, that allows to create some dummy data during the application startup.


* **presentation**: A maven module for the web resources accessed by the users browser:

  This module holds the resources for the Angular frontend.
  By default the Angular app will be ignored during the build, so that only the backend part of the application is built (for details on the Angular app, see the corresponding section below).
  However, this module will produce the final `war` archive that will be deployed to the application server.
  As such, the `META-INF` and `WEB-INF` folders are important because they hold the relevant configuration files (e.g. the `web.xml` for configuring the context-root for the application or the Shiro configuration).


## Deployment


Using only classes and annotations of the Java EE 8 API, the application should run in any Java EE 8 compliant application server.
However, some configurations are specific to the Wildfly application server, which is the suggested application server for this application (e.g. the datasource configuration in the `example06-ds.xml` file).
While other application servers are possible (e.g. Glassfish) they may need additional configuration.


For using the Wildfly application server, you have two possible deployment configurations:


* The standalone way:

  * Download the [Wildfly Application Server 16.0.0.Final Full Profile](http://wildfly.org/downloads/) and extract the archive to a location of your choice.
  * Run `$WILDFLY_HOME/bin/standalone.sh` (or `.bat` if on windows) to start your application server.
  * Build your application and copy your web archive (located under `example06-presentation/target/example06-presentation-0.1.SNAPSHOT.war`) to `$WILDFLY_HOME/standalone/deployments/`.
    Alternatively, you can also use the `copy-to-wildfly` profile (`-Pcopy-to-wildfly`) to automatically copy your web application artifact to Wildfly's deployment folder at the end of the build process.
    This requires you to correctly set the `wildfly.path` property in the `pom.xml` of the `example06-parent`.

* Using the `thorntail-maven-plugin`:

  * Use the `thorntail` profile (`-Pthorntail`) to build a standalone, executable `.jar` file that contains your application archive *and* all necessary parts of the application server.
  This profile also executes the `.jar` file to start the application.
  **Warning**: the first build with this profile enabled may take a while, since the whole application server is downloaded in form of maven artifacts.


Once the application server successfully started, the provided resources should be available under e.g. [http://localhost:8080/example06/rest/news](http://localhost:8080/example06/rest/news), etc.


## Angular


The Angular project is located in `example06-presentation/src/main/angular`.
In order to build/develop the frontend, two alternatives exist:

* **development mode**:
  By running `npm install` once (to download all dependencies) followed by `npm run start` in the above folder, you can start a small live web server that serves your Angular application and recompiles it whenever changes to your source code are detected.
  This is useful during development of the frontend and may be used completely independent or paired with a running backend to test/develop the backend REST calls.

* **production mode**:
  Building the example application with the `with-frontend` profile (`-Pwith-frontend`) executes the above steps in the default Maven lifecycle and compiles the Angular project to the `target` directory of the `example06-presentation` module.
  Consequently, both backend and frontend code will be packaged in the `.war` archive for easy deployment in a single application server.

  **Note for production mode**: Since routing in Angular apps is handled by Angular itself, we need to make sure that any request that is not a REST request is redirect to the main `index.html` of the Angular app.
  This is achieved by using the `urlrewritefilter` dependency, which is configured in the `urlrewrite.xml` file.


## Shiro


The main configuration file (`shiro.ini`) can be found in the presentation module under `example06-presentation/src/main/webapp/WEB-INF/shiro.ini`.
It contains two realm definitions:

* The `WT2Realm` is a very basic realm that will suffice for most people who want to use either session-based of basic authentication.
* The `JWTWT2Realm` serves as a starting point for people who want to dig further into custom authentication mechanisms such as JSON Web Tokens.

Both realms are located in the business module.
The `WT2Realm` extends the existing `AuthorizingRealm` class of the Shiro framework, so that only the two methods `doGetAuthenticationInfo` and `doGetAuthorizationInfo` need to be implemented, which should return the necessary information to Shiro to perform authentication and authorization tasks.
If no authorization is required for the web app, one may extend the `AuthenticatingRealm` class, which only requires to implement the `doGetAuthenticationInfo` method.

Since the Shiro framework directly instantiates the specified realm, it will not receive a container-managed instance, which may be desirable due to its automated transaction and injection support.
Therefore the `doGetAuthenticationInfo` delegates the actual fetching of the authentication info to the `DatabaseAuthenticator` class and only deals with the programmatic lookup of a container-managed instance for this class.
The `DatabaseAuthenticator` class may then use already known features (`@Transactional`, `@PersistenceContext`) to perform a database lookup.

As for authorization, this example grants every user with a `ReadNewsItemPermission` permission.
According to Shiro's idea of permissions, permissions owned by a subject may imply permissions required by objects.
According to the implementation of `ReadNewsItemPermission`'s implies method, it implies a `ViewFirstFiveNewsItemsPermission` permission.
However, it does not blindly implies the requested permission, but rather calls its check-method, to actually decide on the implication.
This allows to utilize Shiro's permission-system to implement an attribute-based access control system, because `ViewFirstFiveNewsItemsPermission`'s check-method may return `true` or `false` based on arbitrary variables.


## Security

* **XSS**: The `news-details` component was configured to bypass Angular's internal sanitation system, so that the content of the news item is rendered without sanitation.
  To exploit this security hole use e.g. the following code as the content of the news entry:
  ```html
  <div style="
      position: absolute;
      background-color: dodgerblue;
      top: 0px;
      width: 100%;
      height: 100%;
      z-index: 1;
      text-align: center;
      font-size: large;">
  YOUR SITE HAS BEEN COMPROMISED
  </div>
  ```

* **SQL injection**: the `create-news-security` component was extended to call a new, unsafe REST endpoint in the backend, that persist the news item using raw SQL and therefore being susceptible to SQL injections.
  To exploit this security hole use e.g. the following code as the content of the news entry:
  ```sql
  '; DROP TABLE DBNews; --
  ```

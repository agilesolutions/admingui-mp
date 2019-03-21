# MicroProfile OpenAPI application example

## Getting the openapi yaml

```
Now if you run your application, you can go to /openapi to get the yaml
```

## Adding Swagger UI

```
Now run the swagger UI in this application http://localhost:8080/jenkins/api/openapi-ui/
```

## Personalize your UI

Using the Config API you can Personalize the UI. Here are the config keys you can use:

* openapi-ui.copyrightBy - Adds a name to the copyright in the footer. Defaults to none.
* openapi-ui.copyrightYear - Adds the copyright year. Defaults to current year.
* openapi-ui.title - Adds the title in the window. Defaults to “MicroProfile - Open API”.
* openapi-ui.serverInfo - Adds info on the server. Defaults to the system server info.
* openapi-ui.contextRoot - Adds the context root. Defaults to the current value.
* openapi-ui.swaggerUiTheme - Use a theme from swagger-ui-themes. Defaults to “flattop”.
* openapi-ui.swaggerHeaderVisibility - Show/hide the swagger logo header. Defaults to “visible”.
* openapi-ui.exploreFormVisibility - Show/hide the explore form. Defaults to “hidden”.
* openapi-ui.serverVisibility - Show/hide the server selection. Defaults to “hidden”.
* openapi-ui.createdWithVisibility - Show/hide the created with footer. Defaults to “visible”.

### Example: Adding this to META-INF/microprofile.properties

```
    openapi-ui.copyrightBy=Phillip Kruger
    openapi-ui.title=My awesome services
    openapi-ui.swaggerHeaderVisibility=hidden
    openapi-ui.serverVisibility=visible

```

### read more 
[Swagger UI on MicroProfile OpenAPI](https://www.phillip-kruger.com/post/microprofile_openapi_swaggerui/)

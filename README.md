## Initialising database

Use insertTestData property in application.properties (default = true, so data will be initialised).

## Run app

It's a normal spring boot application, so just click 'Run' in upper-right corner (assuming you are using intellij).

## Acceptance test

While running app, use HTTP methods from ./requests/testing.http. They are arranged in the order in which they should be used.
# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.2.2](N/A) - 2017-11-09
#### Changed
* Updated dependency `jfunc@0.4.0` (latest)


--------
### [0.2.1](https://github.com/TeamworkGuy2/JsonStringify/commit/4cea6f77c21f40388af15d1ca67642ac852658dc) - 2016-10-30
#### Changed
* Updated dependency `jtext-util@0.11.1` (latest)


--------
### [0.2.0](https://github.com/TeamworkGuy2/JsonStringify/commit/cf4cce0fe16b4477c72fc7f749aee2337a0b31e1) - 2016-10-08
#### Added
* JsonWritable interface with toJson() method for objects which can be converted to JSON
* JsonStringify comma(), append(), indent(), toArray(), toStringArray(), toArrayConsume(), toProp(), propName(), and propNameUnquoted() with various overloads for most
* A 'public static final JsonStringify inst' field to the no longer static JsonStringify as a default instance

#### Changed
Rebuilt JsonStringify as an instanced class (but with no fields making it thread save) to allow for method chaining:
* Renamed JsonStringify toJsonProperty() -> toProperty()
* Expanded JsonStringify join*() method generic parameter types


--------
### [0.1.0](https://github.com/TeamworkGuy2/JsonStringify/commit/b72db5682880abffedf4af40c08ad8d39129cc52) - 2016-10-01
#### Added
* Versioning of existing code.  Moved/renamed JsonWrite -> JsonStringify from jfile-io to this new library.
  * Renamed joinStr*() methods to join() and added 'escape' parameter
  * Added joinEscape() methods which call join() with the 'escape' flag set to true
  * Added toJsonProperty() methods

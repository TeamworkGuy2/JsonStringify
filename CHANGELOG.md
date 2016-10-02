# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.1.0](N/A) - 2016-10-01
#### Added
* Versioning of existing code.  Moved/renamed JsonWrite -> JsonStringify from jfile-io to this new library.
  * Renamed joinStr*() methods to join() and added 'escape' parameter
  * Added joinEscape() methods which call join() with the 'escape' flag set to true
  * Added toJsonProperty() methods

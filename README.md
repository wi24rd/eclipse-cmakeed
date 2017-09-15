[Install...](#installation-instructions)
[![Build Status](https://travis-ci.org/15knots/cmakeed.svg?branch=master)](https://travis-ci.org/15knots/cmakeed)
[![GitHub issues](https://img.shields.io/github/issues/15knots/cmakeed.svg)](https://github.com/15knots/cmakeed/issues)

An Eclipse plug-in for editing CMake files such as CMakeLists.txt. Provides syntax coloring, CMake command content assist, 
and the CMake command reference incorporated into the Eclipse help system.

Requires Java 6 or higher and Eclipse 3.8 or higher.

# Screenshots
Screenshots can be found at the <a href="https://marketplace.eclipse.org/content/cmake-editor#group-screenshots">Eclipse Marketplace</a>.

# Installation Instructions
TBD

---
# Release History

## 1.4.0 (2017-07-29)
### Changes
- Update code completion for cmake commands to cmake version 3.5.2 (part of issue #7)
- Editor now respects the preference settings from General|Editors|Text Edtors preference page.

## 1.3.0 (2016-12-12)
### Changes
- Fixed issue #6: CMakeEd inserts always to the text 4 spaces by the tab pressing.
- Fixed feature #8: Allow to configure displayed tab width
- Code clean-up: Imports removed, classes parametrized, redundant null-checks removed

## 1.2.0 (2016-10-14)
### Changes
- New maintainer: Martin Weber.
- Build with maven and tycho.
- Binaries are hosted as zipped p2 repositories on [bintray.com](https://bintray.com/15knots/p2-zip/cmakeed) now.
- Newer version of CMake Editor will be detected by Eclipse`s 'Check for Updates' mechanism.
- Fixed issue #4: Undo History gets cleared on save.
- Fixed feature #7: also consider ctest files.

## 1.1.6 (2011-10-19)
### Changes
- Updated documentation for CMake 2.8.6

## 1.1.5 (2010-02-01)
### Changes
- Updated documentation for CMake 2.8.0
- Added preference to use either UPPERCASE or lowercase for CMake commands to better integrate with your current CMake files
- Cleaned up some parsing errors.

## 1.1.4 (2009-00-01)
### Changes
- Updated documentation for CMake 2.6.4

## 1.1.2 (2009-01-26)

## 1.1.1 (2009-01-19)
### Changes
- Syntax highlighting for CMake defined variables, User defined Variables, Properties and Reserved Words
- Preference Panel to set custom syntax highlighting colors
- Documentation updated to CMake version 2.6.2
- Support for keystroke code templates to insert often used cmake code
- Preference Panel to define custom keystroke code templates
- Keystroke short cuts to comment/uncomment blocks of code
- Basic hover support to show brief description of command or variable being hovered over.
- CMake command reference intregrated in the Eclipse Help system
- Editing of CMakeLists.txt and any *.cmake file or *.cmake.in file

## 1.1.0 (2008-12-29)

## 1.0.1 (2008-12-16)
New maintainer: M. Jackson.

## 1.0.0 (2007-04-21)
Initial version by Baron Roberts.
